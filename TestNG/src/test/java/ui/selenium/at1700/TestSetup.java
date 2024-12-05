package ui.selenium.at1700;

import browser.DriverSetup;
import browser.DriverUtil;
import helpers.CustomListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;

@Listeners(CustomListener.class)
public class TestSetup {

    protected DriverUtil driverUtil = DriverUtil.getInstance();

    /**
     * Подготовка окружения перед прогоном тестов
     * <p>
     * При TestNG вызывается неявно при помощи @BeforeTest
     * При Cucumber вызывается неявно при помощи @Before (io.cucumber.java.Before)
     */
    @BeforeTest
    public void beforeTest() {
        DriverSetup.setup();
    }

    @BeforeMethod
    private void beforeEachTest() {
        /*
        Allure.getLifecycle()
        String listenerName = "CustomListener";
        if (!(SelenideLogger.hasListener(listenerName))) {
            SelenideLogger.addListener(listenerName, new CustomListener(Allure.getLifecycle()));
        }

         */
    }

    /**
     * Закрытие браузера после прогона тестов
     * <p>
     * При TestNG вызывается неявно при помощи @AfterMethod
     * При Cucumber вызывается неявно при помощи @After (io.cucumber.java.After)
     */
    @AfterMethod
    private void afterEachTest(ITestResult result) {
        if (result.getStatus() != ITestResult.FAILURE) {
            driverUtil.quit();
        }
    }
}
