package browser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverUtil {

    private static DriverUtil INSTANCE = null;
    private WebDriver driver;

    private DriverUtil() {}

    public static DriverUtil getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DriverUtil();
        }
        return INSTANCE;
    }

    public WebDriver getDriver() {
        if (driver == null) {
            driver = new ChromeDriver(DriverSetup.getOptions());
        }
        return driver;
    }

    public void quit() {
        if (driver != null) {
            driver.quit();
        }
        driver = null;
    }
}
