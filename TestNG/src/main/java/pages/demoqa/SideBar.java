package pages.demoqa;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

public class SideBar extends BasePage {

    private static By elementList = By.className("element-list");
    @FindBy(xpath = "//div[@class='element-group' and .//*[contains(text(), 'Elements')]]")
    private WebElement itemElements;
    @FindBy(xpath = "//li[.//span[contains(text(), 'Web Tables')]]")
    public WebElement subitemWebTables;
    @FindBy(xpath = "//li[./span[normalize-space(text())='Links']]")
    private WebElement subitemLinks;
    @FindBy(xpath = "//div[@class='element-group' and .//*[contains(text(), 'Alerts, Frame')]]")
    private WebElement itemAlertsFrameWindows;
    @FindBy(xpath = "//span[contains(text(), 'Browser Windows')]//parent::li")
    private WebElement subitemBrowserWindows;
    @FindBy(xpath = "//span[contains(text(), 'Alerts')]//parent::*")
    private WebElement subitemAlerts;
    @FindBy(xpath = "//span[contains(text(), 'Frames') and not(contains(text(), 'Nested'))]//parent::li")
    private WebElement subitemFrames;

    public WebTablesPage itemElementsSubitemWebTables() {
        openItem(itemElements);
        clickElement(subitemWebTables);
        return new WebTablesPage();
    }

    public LinksPage itemElementsSubitemLinks() {
        openItem(itemElements);
        clickElement(subitemLinks);
        return new LinksPage();
    }

    public BrowserWindowsPage itemAlertsFrameWindowsSubitemBrowserWindows() {
        openItem(itemAlertsFrameWindows);
        clickElement(subitemBrowserWindows);
        return new BrowserWindowsPage();
    }

    public AlertsPage itemAlertsFrameWindowsSubitemAlerts() {
        openItem(itemAlertsFrameWindows);
        clickElement(subitemAlerts);
        return new AlertsPage();
    }

    public FramesPage itemAlertsFrameWindowsSubitemFrames() {
        openItem(itemAlertsFrameWindows);
        clickElement(subitemFrames);
        return new FramesPage();
    }

    private void openItem(WebElement item) {
        if (!item.findElement(elementList).getAttribute("class").contains("show")) {
            clickElement(item);
        }
    }
}
