package pages.demoqa;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

public class AlertsFrameWindowsPage extends BasePage {

    private SideBar sideBar = new SideBar();
    @FindBy(xpath = "//span[contains(text(), 'Alerts')]//parent::*")
    private WebElement buttonAlerts;
    @FindBy(xpath = "//span[contains(text(), 'Frames') and not(contains(text(), 'Nested'))]//parent::li")
    private WebElement buttonFrames;
    @FindBy(xpath = "//span[contains(text(), 'Browser Windows')]//parent::li")
    private WebElement buttonBrowserWindows;

    public SideBar sideBar() {
        return sideBar;
    }

    public AlertsPage goToAlerts() {
        clickElement(buttonAlerts);
        return new AlertsPage();
    }

    public FramesPage goToFrames() {
        clickElement(buttonFrames);
        return new FramesPage();
    }
}
