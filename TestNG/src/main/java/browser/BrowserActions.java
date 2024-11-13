package browser;

import helpers.CustomAssertions;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;

import java.time.Duration;
import java.util.Set;

public class BrowserActions {

    private static WebDriverWait wait = new WebDriverWait(driver(), Duration.ofSeconds(10L), Duration.ofSeconds(1L));  //вынести в проперти

    /**
     * Методы для работы со вкладками
     */

    @Step("Перейти по ссылке {url}")
    public static void open(String url) {
        driver().get(url);
        makeCurrentTabActive();
    }

    @Step("Перейти по ссылке {url}")
    public static <PageObject> PageObject open(String url, Class<PageObject> pageObjectClass) {
        driver().get(url);
        makeCurrentTabActive();
        return pageObjectClass.cast(BasePage.getPageByClass(driver(), pageObjectClass));
    }

    @Step("Перейти на вновь открытую вкладку")
    public static void switchToNewTab() {
        makeCurrentTabActive();
    }

    @Step("Перейти на вновь открытую вкладку")
    public static <PageObject> PageObject switchToNewTab(Class<PageObject> pageObjectClass) {
        makeCurrentTabActive();
        return pageObjectClass.cast(BasePage.getPageByClass(driver(), pageObjectClass));
    }

    @Step("Закрыть активную вкладку")
    public static void closeActiveTab() {
        makeCurrentTabActive();
        driver().close();
        makeCurrentTabActive();
    }

    @Step("Закрыть браузер")
    public static void quit() {
        driver().quit();
    }

    public static int getNumberOfTabs() {
        return driver().getWindowHandles().size();
    }

    /**
     * Методы для работы со всплывающими окнами
     */

    @Step("Проверка наличия окна уведомления")
    public static boolean isAlertPresent() {
        return CustomAssertions.ifTrue(ExpectedConditions.alertIsPresent().apply(driver()) != null,
                "Окно уведомлений не появилось на странице");
    }

    @Step("Подтвердить прочтение уведомления")
    public static void acceptAlert() {
        wait.until(ExpectedConditions.alertIsPresent()).accept();
    }

    @Step("Закрыть окно уведомлений")
    public static void declineAlert() {
        wait.until(ExpectedConditions.alertIsPresent()).dismiss();
    }

    @Step("Получить текст уведомления")
    public static String getText() {
        return wait.until(ExpectedConditions.alertIsPresent()).getText();
    }

    @Step("Заполнить поле в окне уведомлений текстом: '{keys}'")
    public static void sendKeys(String keys) {
        wait.until(ExpectedConditions.alertIsPresent()).sendKeys(keys);
    }

    /**
     * Методы для работы со фреймами
     */

    @Step("Перейти в iframe")
    public static void enableFrame(WebElement frame) {
        driver().switchTo().frame(frame);
    }

    @Step("Выйти из iframe")
    public static void disableFrame() {
        driver().switchTo().defaultContent();;
    }

    private static WebDriver driver() {
        return DriverUtil.getInstance().getDriver();
    }

    private static void makeCurrentTabActive() {
        Set<String> tabs = driver().getWindowHandles();
        for (String tab : tabs) {
            driver().switchTo().window(tab);
        }
    }
}
