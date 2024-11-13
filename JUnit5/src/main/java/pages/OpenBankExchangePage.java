package pages;

import com.codeborne.selenide.Condition;
import helpers.CustomAssertions;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class OpenBankExchangePage {
    private static String exchangeRow= "(//section[contains(@class, 'currency-exchange')])[1]//tr[contains(@class, 'card-rates-table__row')]";
    private static String purchaseRate = "card-rates-table__purchase";
    private static String saleRate = "card-rates-table__sale";
    private static String rate = "(//section[contains(@class, 'currency-exchange')])[1]" +
            "//span[contains(@class, 'currency__text') and contains(text(), '%1$s')]" +
            "//ancestor::tr[contains(@class, 'card-rates-table__row')]//td[contains(@class, '%2$s')]";

    @Step("Проверить, что количество валют > {number}")
    public OpenBankExchangePage checkNumberOfExchangeRows(int number) {
        CustomAssertions.assertTrue($$x(exchangeRow).size() > number);
        return this;
    }

    @Step("Проверить, что курс покупки {currency} ниже курса продажи")
    public OpenBankExchangePage checkNumberOfExchangeRows(String currency) {
        float currentPurchaseRate = getCurrentPurchaseRate(currency);
        float currentSaleRate = getCurrentSaleRate(currency);
        CustomAssertions.assertTrue(currentSaleRate < currentPurchaseRate);
        return this;
    }

    private float getCurrentPurchaseRate(String currency) {
        String currentRate = $x(String.format(rate, currency, purchaseRate)).as("Курс покупки " + currency)
                .shouldBe(Condition.visible).text();
        return Float.parseFloat(currentRate.replaceAll(",", "."));
    }

    private float getCurrentSaleRate(String currency) {
        String currentRate = $x(String.format(rate, currency, saleRate)).as("Курс продажи " + currency)
                .shouldBe(Condition.visible).text();
        return Float.parseFloat(currentRate.replaceAll(",", "."));
    }
}



