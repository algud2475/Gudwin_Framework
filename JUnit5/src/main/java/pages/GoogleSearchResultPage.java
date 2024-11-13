package pages;

import com.codeborne.selenide.*;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class GoogleSearchResultPage {
    private static String searchResult = "//div[@id='rso']";
    private static String searchResultSource = "//div[@id='rso']//a//cite";
    private static String searchResultTitle = "//div[@id='rso']//a//h3";

    @Step("Проверить, что на странице отображаются результаты поиска")
    public GoogleSearchResultPage searchResultsPresent() {
        $x(searchResult).shouldBe(Condition.exist);
        return this;
    }

    @Step("Проверить, что на первой странице есть ссылка на '{website}'")
    public GoogleSearchResultPage searchResultsContainsWebsite(String website) {
        /*
        find используется для общего поиска элемента на странице,
        а findBy — для поиска конкретного элемента из коллекции, соответствующего заданному условию
         */
        $$x(searchResultSource).findBy(text(website)).as("Результат поискового запроса").shouldBe(Condition.exist);
        return this;
    }

    @Step("Нажать на '{title}' в результатах поиска")
    public OpenBankMainPage clickOnLink(String title) {
        $x(searchResultTitle).shouldBe(Condition.visible).scrollIntoView(false).click();
        return page(OpenBankMainPage.class);
    }
}



