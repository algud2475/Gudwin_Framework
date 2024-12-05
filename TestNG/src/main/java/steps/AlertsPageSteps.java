package steps;

import browser.BrowserActions;
import helpers.CustomAssertions;
import io.cucumber.java.ru.И;
import pages.PageManager;
import pages.demoqa.AlertsPage;

public class AlertsPageSteps extends AlertsPage {

    private PageManager pageManager = PageManager.getInstance();

    @И("^Проверяем всплывающее окно \"(Уведомление от сайта - закрыть|Подтвердите действие - да/нет)\"$")
    public void checkButtonOnAlertsPageStep(String buttonName) {
        pageManager.getAlertsPage();
        switch (buttonName) {
            case "Уведомление от сайта - закрыть":
                clickElement(buttonAlert);
                break;
            case "Подтвердите действие - да/нет":
                clickElement(buttonConfirm);
                break;
        }
        if (BrowserActions.isAlertPresent()) {
            BrowserActions.acceptAlert();
        }
    }

    @И("^Проверяем всплывающее окно \"Ввод данных на сайте\" с текстом \"(.+)\"$")
    public void checkButtonPromptStep(String text) {
        pageManager.getAlertsPage();
        clickElement(buttonPrompt);
        if (BrowserActions.isAlertPresent()) {
            BrowserActions.sendKeys(text);
            BrowserActions.acceptAlert();
        }
    }

    @И("^Проверяем, что на странице присутствует текст \"(.+)\"$")
    public void checkTextExistStep(String text) {
        pageManager.getAlertsPage();
        CustomAssertions.assertTrue(labelConfirm.getText().equals(text) | labelPrompt.getText().contains(text),
                "На странице не появилось сообщение, содержащее: " + text);
    }
}
