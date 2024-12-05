package helpers;

import browser.DriverUtil;
import io.qameta.allure.Attachment;
import io.qameta.allure.testng.AllureTestNg;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * Расширение обработчика событий AllureTestNg
 *
 * Данное расширение подключается посредством аннотирования нужного класса как @Listeners(CustomListener.class),
 * например в данном проекте аннотирован класс TestSetup
 *
 * p.s. подключение через pom почему-то не отрабатывает
 */
public class CustomListener extends AllureTestNg {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomListener.class);

    @Override
    public void onTestFailure(final ITestResult result) {
        //возможно прикреплять посредством метода, помеченного @Attachment
        if (DriverUtil.getInstance().hasWebDriverStarted()) {
            addScreenshot();
        }
        //либо вызывать AllureLifecycle и прикреплять напрямую в него
        getPageSource().ifPresent(bytes ->
            getLifecycle().addAttachment("HTML-код страницы", "text/html", "html", bytes)
        );
        super.onTestFailure(result);
    }

    @Attachment(value = "Скриншот", type = "image/png")
    public static byte[] addScreenshot() {
        return ((TakesScreenshot) DriverUtil.getInstance().getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    //@Attachment(value = "HTML-код страницы", type = "text/html")
    public static Optional<byte[]> getPageSource() {
        try {
            return DriverUtil.getInstance().hasWebDriverStarted() ? Optional.of(DriverUtil.getInstance().getDriver().getPageSource().getBytes(StandardCharsets.UTF_8)) : Optional.empty();
        } catch (NullPointerException ex) {
            LOGGER.warn("Не удалось получить HTML-код страницы", ex);
            return Optional.empty();
        }
    }
}
