package pages.demoqa;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

public class MainPage extends BasePage {

    @FindBy(xpath = "//img[@class='banner-image' and @alt='Selenium Online Training']")
    private WebElement pageLocator;
    @FindBy(xpath = "//h5[contains(text(), 'Alerts')]//ancestor::*[3]")
    private WebElement buttonAlertsFrameWindows;
    @FindBy(xpath = "//h5[contains(text(), 'Elements')]//ancestor::*[3]")
    private WebElement buttonElements;

    public AlertsFrameWindowsPage clickButtonAlertsFrameWindows() {
        clickElement(buttonAlertsFrameWindows);
        return new AlertsFrameWindowsPage();
    }

    public ElementsPage clickButtonElements() {
        clickElement(buttonElements);
        return new ElementsPage();
    }

    public MainPage checkPageOpened() {
        checkElementVisible(pageLocator);
        return this;
    }
}
