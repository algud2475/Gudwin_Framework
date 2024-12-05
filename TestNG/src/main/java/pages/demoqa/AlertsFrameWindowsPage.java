package pages.demoqa;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

public class AlertsFrameWindowsPage extends BasePage {

    protected SideBar sideBar = pageManager.getSideBar();
    @FindBy(xpath = "//span[contains(text(), 'Alerts')]//parent::*")
    protected WebElement buttonAlerts;
    @FindBy(xpath = "//span[contains(text(), 'Frames') and not(contains(text(), 'Nested'))]//parent::li")
    protected WebElement buttonFrames;
    @FindBy(xpath = "//span[contains(text(), 'Browser Windows')]//parent::li")
    protected WebElement buttonBrowserWindows;

    public SideBar sideBar() {
        return sideBar;
    }

    /**
     * Метод для клика по кнопке в боковом меню с последующим переходом на страницу AlertsPage в текущей вкладке
     *
     * @return экземпляр AlertsPage
     */
    @Deprecated(since =  "Боковое меню вынесено в отдельный класс")
    public AlertsPage goToAlerts() {
        clickElement(buttonAlerts);
        return pageManager.getAlertsPage();
    }

    @Deprecated(since =  "Боковое меню вынесено в отдельный класс")
    public FramesPage goToFrames() {
        clickElement(buttonFrames);
        return pageManager.getFramesPage();
    }
}
