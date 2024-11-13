package tests.UI.selenide;

import browser.BrowserActions;
import browser.DriverSetup;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.AllureSelenideListener;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class TestsSetup {

    //должен быть public static void
    @BeforeAll
    public static void beforeAllTests() {
        DriverSetup.setup();
        SelenideLogger.addListener("AllureSelenide", new AllureSelenideListener().screenshotSteps(true));
    }

    //должен быть public void
    @BeforeEach
    public void beforeEachTest() {

    }

    @AfterEach
    public void afterEachTest() {
        BrowserActions.quit();
    }
}
