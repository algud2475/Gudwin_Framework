package steps;

import browser.DriverSetup;
import browser.DriverUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.testng.ITestResult;

public class TestSetupSteps {

    protected DriverUtil driverUtil = DriverUtil.getInstance();

    @Before
    public void beforeTest() {
        DriverSetup.setup();
    }

    /**
     * Закрытие браузера после прогона тестов
     *
     * При TestNG вызывается неявно при помощи @AfterMethod
     * При Cucumber вызывается неявно при помощи @After (io.cucumber.java.After)
     */
    @After
    private void afterEachTest(ITestResult result) {
        if (result.getStatus() != ITestResult.FAILURE) {
            driverUtil.quit();
        }
    }
}
