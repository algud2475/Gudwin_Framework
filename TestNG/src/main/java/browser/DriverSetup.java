package browser;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

import java.time.Duration;
import java.util.logging.Level;

import static config.PropsTestNG.propsDb;
import static config.PropsTestNG.propsWeb;

/**
 * Класс настройки веб-драйверва
 */
public class DriverSetup {

    private static ChromeOptions options = new ChromeOptions();
    private static LoggingPreferences loggingPreferences = new LoggingPreferences();
    private static boolean initialized = false;

    public static void setup() {
        if (initialized) return;

        loggingPreferences.enable(LogType.BROWSER, Level.ALL);
        loggingPreferences.enable(LogType.PERFORMANCE, Level.ALL);

        System.setProperty("webdriver.chrome.driver", propsWeb.driverPath() + propsWeb.driverVersion() + ".exe");
        //System.setProperty("webdriver.chrome.driver", "D:\\QA Automation\\AQA Projects\\chromedriver130.exe"); //хардкод

        options.addArguments("enable-automation");
        options.addArguments("start-maximized");

        //options.setCapability("browserVersion", propsWeb.driverVersion());

        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        options.setPageLoadTimeout(Duration.ofSeconds(propsWeb.pageLoadTimeout())); //по-умолчанию - 300с
        options.setScriptTimeout(Duration.ofSeconds(propsWeb.scriptTimeout())); //по-умолчанию - 30с
        //options.setImplicitWaitTimeout(Duration.ofSeconds(propsWeb.implicitWaitTimeout())); //по-умолчанию - 0с

        addOptions();

        initialized = true;
    }

    public static ChromeOptions getOptions() {
        return options;
    }

    private static void addOptions() {
        String chromeOptionsProps = propsWeb.chromeOptions();
        if (!chromeOptionsProps.isEmpty()) {
            String[] chromeOptions = chromeOptionsProps.split(",");
            for (String option : chromeOptions) {
                options.addArguments(option);
            }
        }
    }
}


