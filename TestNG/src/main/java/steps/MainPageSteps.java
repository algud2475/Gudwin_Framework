package steps;

import io.cucumber.java.ru.И;
import pages.PageManager;
import pages.demoqa.MainPage;

public class MainPageSteps extends MainPage {

    private PageManager pageManager = PageManager.getInstance();

    //При передаче в метод параметра через регулярку - вначале и в конце ставим ^ и $
    //В метод можно передать параметр через {string}, но тогда исключается возможность передать в этот же метод регулярку
    @И("^Выбираем раздел \"(Alerts, Frame & Windows|Elements)\"$")
    public void clickButtonOnMainPageStep(String buttonName) {
        pageManager.getMainPage();
        switch (buttonName) {
            case "Alerts, Frame & Windows":
                clickElement(buttonAlertsFrameWindows);
                break;
            case "Elements":
                clickElement(buttonElements);
                break;
        }
    }
}
