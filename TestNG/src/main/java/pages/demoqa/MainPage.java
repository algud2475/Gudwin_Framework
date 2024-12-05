package pages.demoqa;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

public class MainPage extends BasePage {

    @FindBy(xpath = "//img[@class='banner-image' and @alt='Selenium Online Training']")
    protected WebElement pageLocator;
    @FindBy(xpath = "//h5[contains(text(), 'Alerts')]//ancestor::*[3]")
    protected WebElement buttonAlertsFrameWindows;
    @FindBy(xpath = "//h5[contains(text(), 'Elements')]//ancestor::*[3]")
    protected WebElement buttonElements;

    @Step("Нажать на кнопку 'Alerts, Frame & Windows'")
    public AlertsFrameWindowsPage clickButtonAlertsFrameWindows() {
        clickElement(buttonAlertsFrameWindows);
        return new AlertsFrameWindowsPage();
    }

    @Step("Нажать на кнопку 'Elements'")
    public ElementsPage clickButtonElements() {
        clickElement(buttonElements);
        return new ElementsPage();
    }

    @Step("Проверка, что главная страница открыта")
    public MainPage checkPageOpened() {
        checkElementVisible(pageLocator);
        return this;
    }
}
