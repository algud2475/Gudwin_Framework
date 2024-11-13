package pages;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.page;

public class GoogleMainPage {
    private static String searchField = "//textarea[contains(@class,'gLFyf')]";

    @Step("Ввести в поисковой строке запрос: {request}")
    public GoogleSearchResultPage search(String request) {
        $x(searchField).shouldBe(Condition.visible).sendKeys(request + Keys.RETURN);
        return page(GoogleSearchResultPage.class);
    }
}



