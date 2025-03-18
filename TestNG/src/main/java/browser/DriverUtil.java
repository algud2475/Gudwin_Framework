package browser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverUtil {

    private static DriverUtil INSTANCE = null;
    private WebDriver driver;

    private DriverUtil() {}

    /*
    Узнать про вариант работы без использования INSTANCE за пределами класса, т.е.:

    public static WebDriver getDriver() {
        return getInstance().produceDriver();
    }

    private static MyChromDriver getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MyChromDriver();
        }
        return INSTANCE;
    }

    private WebDriver produceDriver() {
        if (chromeDriver == null) {
            initChromeDriver();
        }
        return chromeDriver;
    }

     */

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

    public boolean hasWebDriverStarted() {
        return driver != null;
    }

    public void quit() {
        if (driver != null) {
            driver.quit();
        }
        driver = null;
    }
}
