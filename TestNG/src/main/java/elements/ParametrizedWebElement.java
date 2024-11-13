package elements;

import browser.DriverUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class ParametrizedWebElement {

    private String xpath;

    public ParametrizedWebElement(String xpath) {
        this.xpath = xpath;
    }

    public WebElement parameterize(String... attributes) {
        xpath = String.format(xpath, attributes);
        try {
            return DriverUtil.getInstance().getDriver().findElement(By.xpath(xpath));
        } catch (NoSuchElementException e) {
            //Log.infp (элемент с xpath) не найден
            return null;
        }
    }
}
