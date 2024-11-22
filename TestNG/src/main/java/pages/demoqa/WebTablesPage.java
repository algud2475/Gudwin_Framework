package pages.demoqa;

import elements.ParametrizedWebElement;
import helpers.CustomAssertions;
import helpers.FindByParametrized;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;
import pojo.User;

public class WebTablesPage extends BasePage {

    @FindBy(xpath = "//button[@id='addNewRecordButton']")
    private WebElement buttonAdd;
    @FindByParametrized(xpath = "//div[contains(text(),'%s')]")
    private ParametrizedWebElement userData;
    @FindByParametrized(xpath = "//div[contains(text(),'%s')]//parent::div//span[contains(@id, 'delete-record')]")
    private ParametrizedWebElement buttonDeleteUser;
    @FindBy(xpath = "//form[@id='userForm']")
    private WebElement registrationForm;
    @FindBy(xpath = "//input[@id='firstName']")
    private WebElement textBoxFirstName;
    @FindBy(xpath = "//input[@id='lastName']")
    private WebElement textBoxLastName;
    @FindBy(xpath = "//input[@id='userEmail']")
    private WebElement textBoxEmail;
    @FindBy(xpath = "//input[@id='age']")
    private WebElement textBoxAge;
    @FindBy(xpath = "//input[@id='salary']")
    private WebElement textBoxSalary;
    @FindBy(xpath = "//input[@id='department']")
    private WebElement textBoxDepartment;
    @FindBy(xpath = "//button[@id='submit']")
    private WebElement buttonSubmit;

    @Step("Нажать кнопку 'Add'")
    public WebTablesPage clickButtonAdd() {
        clickElement(buttonAdd);
        return this;
    }

    @Step("Проверить, что форма регистрации пользователя открылась")
    public WebTablesPage checkRegistrationFormOpened() {
        CustomAssertions.assertTrue(registrationForm.isDisplayed(), "Форма регистрации пользователя не открылась");
        return this;
    }

    @Step("Заполнить форму регистрации пользователя")
    public WebTablesPage fillUserFormAndAdd(User user) {
        fillInputField(textBoxFirstName, user.getFirstName());
        fillInputField(textBoxLastName, user.getLastName());
        fillInputField(textBoxEmail, user.getEmail());
        fillInputField(textBoxAge, user.getAge());
        fillInputField(textBoxSalary, user.getSalary());
        fillInputField(textBoxDepartment, user.getDepartment());
        clickElement(buttonSubmit);
        return this;
    }

    @Step("Проверить, что пользователь присутствует в таблице")
    public WebTablesPage checkUserExist(String user) {
        CustomAssertions.assertTrue(userData.parameterize(user).isDisplayed(), "Заданный юзер не найден: " + user);
        return this;
    }

    @Step("Проверить, что пользователь отсутствует в таблице")
    public WebTablesPage checkUserNotExist(String user) {
        CustomAssertions.assertTrue(userData.parameterize(user) == null, "Заданный юзер найден: " + user);
        return this;
    }

    @Step("Удалить пользователя из таблицы")
    public WebTablesPage deleteUser(String user) {
        clickElement(buttonDeleteUser.parameterize(user));
        return this;
    }
}
