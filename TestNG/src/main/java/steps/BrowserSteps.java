package steps;

import browser.DriverUtil;
import config.PropsManager;
import io.cucumber.java.ru.И;
import org.openqa.selenium.WebDriver;

public class BrowserSteps {

    @И("^Открываем страницу \"([^\"]*)\"$")
    public static void openPage(String pageName) {
        String url = PropsManager.getProperty(pageName);
        driver().get(url);
    }

    private static WebDriver driver() {
        return DriverUtil.getInstance().getDriver();
    }
}
