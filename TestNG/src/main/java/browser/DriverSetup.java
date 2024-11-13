package browser;

import config.Props;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

import java.time.Duration;
import java.util.logging.Level;

/**
 * Класс настройки веб-драйверва
 */
public abstract class DriverSetup {
    protected static Props props = Props.props;
    private static ChromeOptions options = new ChromeOptions();
    private static LoggingPreferences loggingPreferences = new LoggingPreferences();
    private static boolean initialized = false;

    public static void setup() {
        if (initialized) return;

        loggingPreferences.enable(LogType.BROWSER, Level.ALL);
        loggingPreferences.enable(LogType.PERFORMANCE, Level.ALL);

        System.setProperty("webdriver.chrome.driver", props.driverPath() + props.driverVersion() + ".exe");
        //System.setProperty("webdriver.chrome.driver", "D:\\QA Automation\\AQA Projects\\chromedriver130.exe"); //хардкод

        options.addArguments("enable-automation");
        options.addArguments("start-maximized");

        //options.setCapability("browserVersion", props.driverVersion());

        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        options.setPageLoadTimeout(Duration.ofSeconds(30)); //по-умолчанию - 300с  //вынести в проперти
        options.setScriptTimeout(Duration.ofSeconds(30)); //по-умолчанию - 30с
        //options.setImplicitWaitTimeout(Duration.ofSeconds(27)); //по-умолчанию - 0с

        addOptions();

        initialized = true;
    }

    public static ChromeOptions getOptions() {
        return options;
    }

    private static void addOptions() {
        String chromeOptionsProps = props.chromeOptionsTestNG();
        if (!chromeOptionsProps.isEmpty()) {
            String[] chromeOptions = chromeOptionsProps.split(",");
            for (String option : chromeOptions) {
                options.addArguments(option);
            }
        }
    }
}


