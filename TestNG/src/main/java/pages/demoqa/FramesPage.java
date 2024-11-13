package pages.demoqa;

import browser.BrowserActions;
import helpers.CustomAssertions;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

public class FramesPage extends BasePage {

    private FramesPage framesPage;
    private UpperFrame upperFrame;
    private LowerFrame lowerFrame;

    public FramesPage() {
        framesPage = this;
        upperFrame = new UpperFrame();
        lowerFrame = new LowerFrame();
    }

    @FindBy(xpath = "//iframe[@id='frame1']")
    private WebElement upperFrameLocator;

    @FindBy(xpath = "//iframe[@id='frame2']")
    private WebElement lowerFrameLocator;

    public UpperFrame inUpperFrame() {
        BrowserActions.enableFrame(upperFrameLocator);
        return upperFrame;
    }

    public LowerFrame inLowerFrame() {
        BrowserActions.enableFrame(lowerFrameLocator);
        return lowerFrame;
    }

    public class UpperFrame extends BasePage {

        @FindBy(xpath = "//h1[@id='sampleHeading']")
        private WebElement labelUpperText;

        @Step("Проверка, что верхний iframe содержит текст: '{text}'")
        public UpperFrame checkText(String text) {
            CustomAssertions.assertTrue(labelUpperText.getText().contains(text),
                    "Верхний iframe не содержит требуемый текст");
            return this;
        }

        public FramesPage outUpperFrame() {
            BrowserActions.disableFrame();
            return framesPage;
        }
    }

    public class LowerFrame extends BasePage {

        @FindBy(xpath = "//h1[@id='sampleHeading']")
        private WebElement labelLowerText;

        @Step("Проверка, что нижний iframe содержит текст: '{text}'")
        public LowerFrame checkText(String text) {
            CustomAssertions.assertTrue(labelLowerText.getText().contains(text),
                    "Верхний iframe не содержит требуемый текст");
            return this;
        }

        public FramesPage outLowerFrame() {
            BrowserActions.disableFrame();
            return framesPage;
        }
    }
}
