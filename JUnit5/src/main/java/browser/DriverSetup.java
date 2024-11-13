package browser;

import com.codeborne.selenide.Configuration;
import config.Props;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.time.Duration;
import java.util.logging.Level;

/**
 * Класс настройки веб-драйверва
 */
public abstract class DriverSetup {
    protected static Props props = Props.props;
    private static ChromeOptions options = new ChromeOptions();
    protected static DesiredCapabilities capabilities = new DesiredCapabilities();
    private static LoggingPreferences loggingPreferences = new LoggingPreferences();
    private static boolean initialized = false;

    public static void setup() {
        if (initialized) return;

        loggingPreferences.enable(LogType.BROWSER, Level.ALL);
        loggingPreferences.enable(LogType.PERFORMANCE, Level.ALL);

        //System.setProperty("webdriver.chrome.driver", props.driverPath() + props.driverVersion());

        options.addArguments(
                "enable-automation",
                "start-maximized"
                );

        options.setCapability("browserName", "chrome");
        //options.setCapability("browserVersion", props.driverVersion());

        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        //options.setPageLoadTimeout(Duration.of(40, ChronoUnit.SECONDS));
        options.setPageLoadTimeout(Duration.ofSeconds(40)); //по-умолчанию - 30с
        //options.setScriptTimeout(Duration.ofSeconds(5)); //по-умолчанию - 30с
        //options.setImplicitWaitTimeout(Duration.of(30, ChronoUnit.SECONDS));
        //options.setImplicitWaitTimeout(Duration.ofSeconds(30));


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
        String chromeOptionsProps = props.chromeOptionsJUnit();
        if (chromeOptionsProps != null) {
            String[] chromeOptions = chromeOptionsProps.split(",");
            for (String option : chromeOptions) {
                options.addArguments(option);
            }
        }
    }
}


