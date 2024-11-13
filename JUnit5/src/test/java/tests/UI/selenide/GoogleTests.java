package tests.UI.selenide;

import browser.BrowserActions;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import pages.GoogleMainPage;
import tests.annotations.UITestAnnotation;

@DisplayName("GOOGLE TESTS")
@Owner(value = "Гудини")
public class GoogleTests extends TestsSetup {
    private static final String GOOGLE_URL = "https://www.google.com/";
    private static final String GOOGLE_SEARCH_TEST_REQUEST = "Гладиолус";
    private static final String GOOGLE_SEARCH_TEST_RESULT_SOURCE = "wikipedia";

    @Epic("THIS IS EPIC AND IT DOESN`T WORK =(")
    @Feature("THIS IS FEATURE AND IT DOESN`T WORK =(") //группировка тестов по проверяемому функционалу
    @Story("THIS IS STORY AND IT DOESN`T WORK =(")
    @DisplayName("Поиск '" + GOOGLE_SEARCH_TEST_REQUEST + "' в поисковике Google")
    @UITestAnnotation
    public void searchRequestAndCheckResults() {
        BrowserActions.open(GOOGLE_URL, GoogleMainPage.class)
                .search(GOOGLE_SEARCH_TEST_REQUEST)
                .searchResultsPresent()
                .searchResultsContainsWebsite(GOOGLE_SEARCH_TEST_RESULT_SOURCE);
    }
}
