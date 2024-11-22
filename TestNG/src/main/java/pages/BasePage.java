package pages;

import browser.DriverUtil;
import elements.ParametrizedWebElement;
import helpers.CustomAssertions;
import helpers.FindByParametrized;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.Duration;

import static config.PropsTestNG.propsWeb;

public class BasePage {

    protected DriverUtil driverUtil = DriverUtil.getInstance();
    protected PageManager pageManager = PageManager.getInstance();
    protected WebDriverWait wait = new WebDriverWait(driverUtil.getDriver(), Duration.ofSeconds(propsWeb.explicitWaitTimeout()), Duration.ofSeconds(1L));

    public BasePage() {
        initializeFields(this);
        PageFactory.initElements(driverUtil.getDriver(), this);
    }

    public static <PageObjectClass> PageObjectClass getPageByClass(WebDriver driver, Class<PageObjectClass> pageObjectClass) {
        return PageFactory.initElements(driver, pageObjectClass);
    }

    public WebElement scrollToElementJs(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driverUtil.getDriver();
        js.executeScript("arguments[0].scrollIntoView(false);", element);
        return element;
    }

    public void fillInputField(WebElement element, String value) {
        scrollToElementJs(element);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
        element.clear();
        element.sendKeys(value);
        boolean checkFlag = wait.until(ExpectedConditions.attributeContains(element, "value", value));
        CustomAssertions.assertTrue(checkFlag, "Поле было заполнено некорректно");
    }

    public void clickElement(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        scrollToElementJs(element);
        element.click();
    }

    public void checkElementVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    private static void initializeFields(Object page) {
        for (Field field : page.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(FindByParametrized.class)) {
                FindByParametrized createAnnotation = field.getAnnotation(FindByParametrized.class);
                String xpath = createAnnotation.xpath();

                // Создаем экземпляр SomeClass с переданным значением
                try {
                    boolean priv = false;
                    if (Modifier.isPrivate(field.getModifiers())) {
                        field.setAccessible(true);
                        priv = true;
                    }
                    field.setAccessible(true); // Делаем поле доступным, если оно приватное
                    field.set(page, new ParametrizedWebElement(xpath)); // Инициализируем поле
                    if (priv) {
                        field.setAccessible(false);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}