package testing3.AbstractPageObjects;

import org.openqa.selenium.WebElement;

public class ClickableElement implements SelectableElement {
    WebElement element;

    public ClickableElement(WebElement webElement) {
        element = webElement;
    }

    @Override
    public void select(int what) {
        element.click();
    }

    @Override
    public boolean isSelected() {
        return element.isSelected();
    }

    @Override
    public boolean isVisible() {
        return element.isDisplayed();
    }

    @Override
    public String getText() {
        return element.getText();
    }
}
