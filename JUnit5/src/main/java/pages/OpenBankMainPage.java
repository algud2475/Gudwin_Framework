package pages;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.page;

public class OpenBankMainPage {
    private static String acceptCookieButton = "//button[contains(@class, 'CookieWarning_cookie-warning-button')]";

    private static String exchangeBlock = "//section[@data-block-id='exchange']";

    private static String exchangeLink = "//div[contains(@class, 'CurrencyExchangeFilterContent_currency-exchange-filter-content-link')]//a";

    @Step("Принять cookie")
    public OpenBankMainPage acceptCookies() {
        $x(acceptCookieButton).as("Кнопка 'Принять' cookies").shouldBe(Condition.visible).click();
        return this;
    }

    @Step("Перейти на страницу 'Все курсы'")
    public OpenBankExchangePage openExchangePage() {
        $x(exchangeLink).as("Ссылка 'Все курсы'").shouldBe(Condition.visible).scrollIntoView(false).click();
        return page(OpenBankExchangePage.class);
    }
}



