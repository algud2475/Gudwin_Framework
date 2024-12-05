package steps;

import io.cucumber.java.ru.И;
import pages.PageManager;
import pages.demoqa.AlertsFrameWindowsPage;

public class AlertsFrameWindowsPageSteps extends AlertsFrameWindowsPage {

    private PageManager pageManager = PageManager.getInstance();

    @И("^Нажимаем на кнопку \"(Alerts|Frames)\"$")
    public void clickButtonOnAlertsFrameWindowsPageStep(String buttonName) {
        pageManager.getAlertsFrameWindowsPage();
        switch (buttonName) {
            case "Alerts":
                clickElement(buttonAlerts);
                break;
            case "Frames":
                clickElement(buttonFrames);
                break;
        }
    }
}
