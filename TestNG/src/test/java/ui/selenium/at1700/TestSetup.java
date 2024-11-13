package ui.selenium.at1700;

import browser.DriverSetup;
import browser.DriverUtil;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;

public class TestSetup {

    protected DriverUtil driverUtil = DriverUtil.getInstance();

    @BeforeTest
    public void beforeTest() {
        System.out.println("BeforeTest");
        DriverSetup.setup();
        System.out.println("Options at TestSetup: " + DriverSetup.getOptions().toString());
    }

    /**
     * Подключение listener возможно как через @Before, так и через pom.xml
     */
    /*
    @BeforeMethod
    private void beforeEachTest() {
        String listenerName = "CustomListener";
        if (!(SelenideLogger.hasListener(listenerName))) {
            SelenideLogger.addListener(listenerName, new CustomListener(Allure.getLifecycle()));
        }
    }
    */

    @AfterMethod
    private void afterEachTest(ITestResult result) {
        if (result.getStatus() != ITestResult.FAILURE) {
            driverUtil.quit();
        }
    }
}
