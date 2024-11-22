package pages.demoqa;

import browser.BrowserActions;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

public class LinksPage extends BasePage {

    @FindBy(xpath = "//a[@id='simpleLink' and contains(text(), 'Home')]")
    private WebElement linkHome;

    @Step("Нажать на ссылку 'Link home'")
    public MainPage clickLinkHome() {
        clickElement(linkHome);
        return BrowserActions.switchToNewTab(MainPage.class);
    }
}
