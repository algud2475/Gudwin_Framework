package helpers;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.LogEvent;
import com.codeborne.selenide.logevents.LogEventListener;
import com.codeborne.selenide.logevents.SelenideLog;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StatusDetails;
import io.qameta.allure.model.StepResult;
import io.qameta.allure.restassured.AllureRestAssured;
import io.qameta.allure.selenide.LogType;
import io.qameta.allure.util.ResultsUtils;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Level;

import static config.PropsJUnit.propsWeb;

/**
 * Пользовательская реализация обработчика событий (listener)
 * io.qameta.allure.selenide.AllureSelenide
 */
public class AllureSelenideListener implements LogEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(AllureSelenideListener.class);
    private boolean saveScreenshots;
    private Boolean screenshotSteps = propsWeb.screenshotSteps();
    private boolean savePageHtml;
    private boolean saveApiRequests;
    private ByteArrayOutputStream apiRequest = new ByteArrayOutputStream();
    private ByteArrayOutputStream apiResponse = new ByteArrayOutputStream();
    private PrintStream apiRequestVar = new PrintStream(apiRequest, true);
    private PrintStream apiResponseVar = new PrintStream(apiResponse, true);
    private boolean saveDbRequests;
    private boolean includeSelenideLocatorsSteps;
    private Optional<byte[]> stepBuffer;
    private final Map<LogType, Level> logTypesToSave;
    private final AllureLifecycle lifecycle;
    private static AllureSelenideListener INSTANCE = null;

    public static AllureSelenideListener getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AllureSelenideListener();
        }
        return INSTANCE;
    }

    private AllureSelenideListener() {
        this(Allure.getLifecycle());
    }

    private AllureSelenideListener(AllureLifecycle lifecycle) {
        this.saveScreenshots = true;
        this.savePageHtml = true;
        this.includeSelenideLocatorsSteps = true;
        saveApiRequests(true);
        this.saveDbRequests = true;
        this.logTypesToSave = new HashMap();
        this.lifecycle = lifecycle;
    }

    /**
     * Вкл/выкл прикрепление к Allure-отчету скриншотов на каждом шагу
     *
     * @param screenshotSteps true, если требуется включить прикрепление
     * @return текущий экземпляр обработчика событий
     */
    public AllureSelenideListener screenshotSteps(boolean screenshotSteps) {
        this.screenshotSteps = screenshotSteps;
        return this;
    }

    /**
     * Вкл/выкл прикрепление к Allure-отчету скриншотов
     *
     * @param saveScreenshots true, если требуется включить прикрепление
     * @return текущий экземпляр обработчика событий
     */
    public AllureSelenideListener saveScreenshots(boolean saveScreenshots) {
        this.saveScreenshots = saveScreenshots;
        return this;
    }

    /**
     * Вкл/выкл прикрепление к Allure-отчету HTML-код страниц
     *
     * @param savePageHtml true, если требуется включить прикрепление
     * @return текущий экземпляр обработчика событий
     */
    public AllureSelenideListener savePageSource(boolean savePageHtml) {
        this.savePageHtml = savePageHtml;
        return this;
    }

    /**
     * Вкл/выкл прикрепление к Allure-отчету API-запросов и ответов
     *
     * @param saveApiRequests true, если требуется включить прикрепление
     * @return текущий экземпляр обработчика событий
     */
    public AllureSelenideListener saveApiRequests(boolean saveApiRequests) {
        this.saveApiRequests = saveApiRequests;
        RestAssured.filters(new ResponseLoggingFilter(LogDetail.ALL, apiResponseVar),
                new RequestLoggingFilter(LogDetail.ALL, apiRequestVar));
        //RestAssured.filters(new AllureRestAssured()
        //        .setRequestAttachmentName("Отправка API-запроса: " + Thread.currentThread().getStackTrace()[3].getMethodName())
        //        .setResponseAttachmentName("Ответ на API-запрос: " + Thread.currentThread().getStackTrace()[2].getMethodName()));
        return this;
    }

    /**
     * Вкл/выкл прикрепление к Allure-отчету запросы к БД и результаты выполнения
     *
     * @param saveDbRequests true, если требуется включить прикрепление
     * @return текущий экземпляр обработчика событий
     */
    public AllureSelenideListener saveDbRequests(boolean saveDbRequests) {
        this.saveDbRequests = saveDbRequests;
        return this;
    }

    /**
     * Вкл/выкл прикрепление к Allure-отчету шагов Selenide-событий
     *
     * @param includeSelenideSteps true, если требуется включить прикрепление
     * @return текущий экземпляр обработчика событий
     */
    public AllureSelenideListener includeSelenideSteps(boolean includeSelenideSteps) {
        this.includeSelenideLocatorsSteps = includeSelenideSteps;
        return this;
    }

    public AllureSelenideListener enableLogs(LogType logType, Level logLevel) {
        this.logTypesToSave.put(logType, logLevel);
        return this;
    }

    public AllureSelenideListener disableLogs(LogType logType) {
        this.logTypesToSave.remove(logType);
        return this;
    }

    private static Optional<byte[]> getScreenshotBytes() {
        try {
            return WebDriverRunner.hasWebDriverStarted() ? Optional.of((byte[]) ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES)) : Optional.empty();
        } catch (WebDriverException ex) {
            LOGGER.warn("Не удалось получить скриншот", ex);
            return Optional.empty();
        }
    }

    private static Optional<byte[]> getPageSourceBytes() {
        try {
            return WebDriverRunner.hasWebDriverStarted() ? Optional.of(WebDriverRunner.getWebDriver().getPageSource().getBytes(StandardCharsets.UTF_8)) : Optional.empty();
        } catch (WebDriverException ex) {
            LOGGER.warn("Не удалось получить HTML-код страницы", ex);
            return Optional.empty();
        }
    }

    private static String getBrowserLogs(LogType logType, Level level) {
        return String.join("\n\n", Selenide.getWebDriverLogs(logType.toString(), level));
    }

    public void attachApiRequest() {
        if (getInstance().saveApiRequests) {
            customAttachment("Отправка API-запроса", "txt", convertApiLog(getInstance().apiRequest));
            customAttachment("Ответ на API-запрос", "txt", convertApiLog(getInstance().apiResponse));
        }
    }

    private static byte[] convertApiLog(ByteArrayOutputStream log) {
        byte[] array = log.toByteArray();
        log.reset();
        return array;
    }

    public static void attachDbRequest(String name, String content) {
        if (getInstance().saveDbRequests) {
            customAttachment(name, "txt", content.getBytes(StandardCharsets.UTF_8));
        }
    }

    public static void attachDbRequest(String name, ArrayNode content) {
        if (getInstance().saveDbRequests) {
            customAttachment(name, "txt", String.valueOf(content).getBytes(StandardCharsets.UTF_8));
        }
    }

    @Override
    public void beforeEvent(LogEvent event) {
        if (this.stepsShouldBeLogged(event)) {
            this.lifecycle.getCurrentTestCaseOrStep().ifPresent((parentUuid) -> {
                String uuid = UUID.randomUUID().toString();
                this.lifecycle.startStep(parentUuid, uuid, (new StepResult()).setName(event.toString()));
            });
        }

    }

    @Override
    public void afterEvent(LogEvent event) {
        LOGGER.info("%%%%%%%%% ПРОВЕРКА, ЧТО LOG EVENT AFTER %%%%%%%%%");
        if (event.getStatus().equals(LogEvent.EventStatus.FAIL)) {
            this.lifecycle.getCurrentTestCaseOrStep().ifPresent((parentUuid) -> {
                if (this.saveScreenshots) {
                    getScreenshotBytes().ifPresent((bytes) -> {
                        this.lifecycle.addAttachment("Скриншот", "image/png", "png", bytes);
                    });
                }

                if (this.savePageHtml) {
                    getPageSourceBytes().ifPresent((bytes) -> {
                        this.lifecycle.addAttachment("HTML-код страницы", "text/html", "html", bytes);
                    });
                }

                if (!this.logTypesToSave.isEmpty()) {
                    this.logTypesToSave.forEach((logType, level) -> {
                        byte[] content = getBrowserLogs(logType, level).getBytes(StandardCharsets.UTF_8);
                        this.lifecycle.addAttachment("Логи: " + String.valueOf(logType), "application/json", ".txt", content);
                    });
                }
            });
        }

        if (this.stepsShouldBeLogged(event)) {
            this.lifecycle.getCurrentTestCaseOrStep().ifPresent((parentUuid) -> {
                switch (event.getStatus()) {
                    case PASS:
                        this.lifecycle.updateStep((step) -> {
                            step.setStatus(Status.PASSED);
                        });
                        if (this.saveScreenshots && screenshotSteps) {
                            getScreenshotBytes().ifPresent((bytes) -> {
                                this.lifecycle.addAttachment("Скриншот", "image/png", "png", bytes);
                            });
                        }
                        break;
                    case FAIL:
                        this.lifecycle.updateStep((stepResult) -> {
                            stepResult.setStatus((Status) ResultsUtils.getStatus(event.getError()).orElse(Status.BROKEN));
                            stepResult.setStatusDetails((StatusDetails) ResultsUtils.getStatusDetails(event.getError()).orElse(new StatusDetails()));
                        });
                        break;
                    default:
                        LOGGER.warn("Шаг завершился с неизвестным статусом {}", event.getStatus());
                }

                this.lifecycle.stopStep();
            });
        }

    }

    private static void customAttachment(String name, String fileExtension, byte[] body) {
        getInstance().lifecycle.addAttachment(name, null, fileExtension, body);
    }

    private boolean stepsShouldBeLogged(LogEvent event) {
        return this.includeSelenideLocatorsSteps || !(event instanceof SelenideLog);
    }
}
