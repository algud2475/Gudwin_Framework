package tests.ui.selenide;

import browser.BrowserActions;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pages.GoogleMainPage;

@Epic(value = "UI tests")
public class BankTests extends TestsSetup {
    private static final String GOOGLE_URL = "https://www.google.com/";
    private static final String BANK_NAME_REQUEST = "Открытие";
    private static final String BANK_TITLE = "Банк Открытие: кредит наличными, ипотека, кредитные и ...";

    @Feature(value = "Проверка сайта банка 'Открытие'") //группировка тестов по проверяемому функционалу
    @DisplayName("Проверка текущих курсов покупки и продажи '{currency}' в банке 'Открытие'")
    @ParameterizedTest
    @ValueSource(strings = {"USD", "EUR"})
    public void checkCurrencyExchangeRate(String currency) {
        BrowserActions.open(GOOGLE_URL, GoogleMainPage.class)
                .search(BANK_NAME_REQUEST)
                .searchResultsPresent()
                .clickOnLink(BANK_TITLE)
                .acceptCookies()
                .openExchangePage()
        ;
    }
}
