package browser;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.time.Duration;
import java.util.logging.Level;

import static config.PropsJUnit.propsWeb;

/**
 * Класс настройки веб-драйверва
 */
public abstract class DriverSetup {

    private static ChromeOptions options = new ChromeOptions();
    protected static DesiredCapabilities capabilities = new DesiredCapabilities();
    private static LoggingPreferences loggingPreferences = new LoggingPreferences();
    private static boolean initialized = false;

    public static void setup() {
        if (initialized) return;

        loggingPreferences.enable(LogType.BROWSER, Level.ALL);
        loggingPreferences.enable(LogType.PERFORMANCE, Level.ALL);

        //System.setProperty("webdriver.chrome.driver", propsWeb.driverPath() + propsWeb.driverVersion());

        options.addArguments(
                "enable-automation",
                "start-maximized"
                );

        //options.setCapability("browserVersion", propsWeb.driverVersion());

        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        options.setPageLoadTimeout(Duration.ofSeconds(40)); //по-умолчанию - 30с
        options.setScriptTimeout(Duration.ofSeconds(30)); //по-умолчанию - 30с

        addOptions();

        capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        Configuration.browserCapabilities = capabilities;
        Configuration.timeout = 10L; //узнать про разницу этого таймаут и Implicity wait

        initialized = true;
    }

    public static ChromeOptions getOptions() {
        return options;
    }

    private static void addOptions() {
        String chromeOptionsProps = propsWeb.chromeOptions();
        if (chromeOptionsProps != null) {
            String[] chromeOptions = chromeOptionsProps.split(",");
            for (String option : chromeOptions) {
                options.addArguments(option);
            }
        }
    }
}


