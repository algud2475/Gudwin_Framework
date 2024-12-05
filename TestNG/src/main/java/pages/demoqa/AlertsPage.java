package pages.demoqa;

import browser.BrowserActions;
import helpers.CustomAssertions;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

public class AlertsPage extends BasePage {

    @FindBy(xpath = "//button[@id='alertButton']")
    protected WebElement buttonAlert;
    @FindBy(xpath = "//button[@id='confirmButton']")
    protected WebElement buttonConfirm;
    @FindBy(xpath = "//button[@id='promtButton']")
    protected WebElement buttonPrompt;
    @FindBy(xpath = "//span[@id='confirmResult']")
    protected WebElement labelConfirm;
    @FindBy(xpath = "//span[@id='promptResult']")
    protected WebElement labelPrompt;

    @Step("Проверка всплывающего окна 'Уведомление от сайта - закрыть'")
    public AlertsPage checkButtonAlert() {
        clickElement(buttonAlert);
        if (BrowserActions.isAlertPresent()) {
            BrowserActions.acceptAlert();
        }
        return this;
    }

    @Step("Проверка всплывающего окна 'Подтвердите действие - да/нет'")
    public AlertsPage checkButtonConfirm() {
        clickElement(buttonConfirm);
        if (BrowserActions.isAlertPresent()) {
            BrowserActions.acceptAlert();
        }
        return this;
    }

    @Step("Проверка всплывающего окна 'Ввод данных на сайте'")
    public AlertsPage checkButtonPrompt(String text) {
        clickElement(buttonPrompt);
        if (BrowserActions.isAlertPresent()) {
            BrowserActions.sendKeys(text);
            BrowserActions.acceptAlert();
        }
        return this;
    }

    @Step("Проверка, что страница содержит сообщение: {text}")
    public AlertsPage checkTextLabelConfirm(String text) {
        CustomAssertions.assertTrue(labelConfirm.getText().equals(text),
                "Следущее сообщение не появилось на странице: " + text);
        return this;
    }

    @Step("Проверка, что страница содержит сообщение: {text}")
    public AlertsPage checkSentTextLabelPrompt(String text) {
        CustomAssertions.assertTrue(labelPrompt.getText().contains(text),
                "На странице не появилось сообщение, содержащее: " + text);
        return this;
    }
}
