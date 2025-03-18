package ui.selenium.at1700;

import browser.BrowserActions;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.demoqa.MainPage;
import pojo.User;
import utils.FilesUtil;
import utils.JsonUtil;

import java.util.List;

import static config.PropsTestNG.propsTestData;

public class SeleniumTests extends TestSetup {
    private static final String PATH_TO_FILE_WITH_USERS = "src/test/resources/at1700/Users.json";
    private static final String MAIN_PAGE_URL = propsTestData.mainPageUrl();

    @DataProvider(name = "dp")
    public static Object[][] dataProvider() {
        List<User> users = JsonUtil.getListObjects(FilesUtil.readFileToString(PATH_TO_FILE_WITH_USERS), User.class);
        return new Object[][]{
                {users.get(0)},
                {users.get(1)}
        };
    }

    @Test(dataProvider = "dp")
    @Story("Тест добавления пользователя с DDT подходом")
    void simpleTestWithDDT(User user) {

        BrowserActions.open(MAIN_PAGE_URL, MainPage.class)
                .clickButtonElements()
                .sideBar().itemElementsSubitemWebTables()
                .clickButtonAdd()
                .checkRegistrationFormOpened()
                .fillUserFormAndAdd(user)
                .checkUserExist(user.getEmail())
                .deleteUser(user.getEmail())
                .checkUserNotExist(user.getEmail());
    }

    @Test
    @Story("Тест всплывающих окон (alerts)")
    void alertsTest() {
        String testMessage = "TestMessage";

        BrowserActions.open(MAIN_PAGE_URL, MainPage.class)
                .clickButtonAlertsFrameWindows()
                .sideBar().itemAlertsFrameWindowsSubitemAlerts()
                .checkButtonAlert()
                .checkButtonConfirm()
                .checkTextLabelConfirm("You selected Ok")
                .checkButtonPrompt(testMessage)
                .checkSentTextLabelPrompt(testMessage);
    }

    @Test
    @Story("Тест фреймов (iframe)")
    void framesTest() {
        String expectedText = "This is a sample page";

        BrowserActions.open(MAIN_PAGE_URL, MainPage.class)
                .clickButtonAlertsFrameWindows()
                .sideBar().itemAlertsFrameWindowsSubitemFrames()
                .inUpperFrame()
                .checkText(expectedText)
                .outUpperFrame()
                .inLowerFrame()
                .checkText(expectedText);
    }

    @Test
    @Story("Тест вкладок и окон (tabs & windows)")
    void tabsWindowsTest() {

        BrowserActions.open(MAIN_PAGE_URL, MainPage.class)
                .clickButtonAlertsFrameWindows()
                .sideBar().itemAlertsFrameWindowsSubitemBrowserWindows()
                .checkButtonNewTab()
                .sideBar().itemElementsSubitemLinks()
                .clickLinkHome()
                .checkPageOpened();
    }

    @Test
    public void someTest() {
        System.out.println(getClass());
        Assert.fail("Просто без причины");
    }
}
