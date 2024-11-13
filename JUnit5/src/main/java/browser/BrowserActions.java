package browser;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;

public class BrowserActions {
    @Step("Перейти по ссылке {url}")
    public static void open(String url) {
        Selenide.open(url);
    }

    @Step("Перейти по ссылке {url}")
    public static <PageObjectClass> PageObjectClass open(String url, Class<PageObjectClass> pageObjectClassClass) {
        return Selenide.open(url, pageObjectClassClass);
    }

    @Step("Закрыть браузер")
    public static void quit() {
        WebDriverRunner.clearBrowserCache();
        WebDriverRunner.closeWebDriver();
    }
}
