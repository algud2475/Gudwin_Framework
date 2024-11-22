package pages.demoqa;

import browser.BrowserActions;
import helpers.CustomAssertions;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

public class BrowserWindowsPage extends BasePage {

    private SideBar sideBar = new SideBar();
    @FindBy(xpath = "//button[@id='tabButton']")
    private WebElement buttonNewTab;

    public SideBar sideBar() {
        return sideBar;
    }

    @Step("Проверка, что кнопка 'New Tab' открывает страницу 'https://demoqa.com/sample' в новой вкладке в браузере")
    public BrowserWindowsPage checkButtonNewTab() {
        int before = BrowserActions.getNumberOfTabs();
        clickElement(buttonNewTab);
        int after = BrowserActions.getNumberOfTabs();
        CustomAssertions.assertTrue(after > before, "Количество вкладок не изменилось");
        BrowserActions.closeActiveTab();
        return this;
    }
}
