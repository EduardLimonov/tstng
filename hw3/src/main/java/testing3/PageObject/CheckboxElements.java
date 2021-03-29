package testing3.PageObject;

import testing3.AbstractPageObjects.ClickableElement;
import testing3.AbstractPageObjects.GroupedComponent;
import testing3.AbstractPageObjects.PageComponent;
import testing3.AbstractPageObjects.SelectableElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class CheckboxElements implements SelectableElement, PageComponent, GroupedComponent {
    @FindBy(xpath = "//main//input[@type='checkbox']")
    List<WebElement> checkboxes;
    List<ClickableElement> checkboxElements;

    @Override
    public void select(int what) {
        checkboxElements.get(what).select(0);
    }

    @Override
    public boolean isSelected() {
        for (ClickableElement clickableElement : checkboxElements) {
            if (clickableElement.isSelected()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isVisible() {
        for (ClickableElement clickableElement : checkboxElements) {
            if (!clickableElement.isVisible()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getText() {
        StringBuilder builder = new StringBuilder();
        for (ClickableElement element : checkboxElements) {
            builder.append(element.getText()).append("\n");
        }
        return builder.toString();
    }

    @Override
    public void initElements(WebDriver driver) {
        PageFactory.initElements(driver, this);
        checkboxElements = new ArrayList<>(checkboxes.size());
        for(WebElement webElement: checkboxes) {
            checkboxElements.add(new ClickableElement(webElement));
        }
    }

    @Override
    public int numberOfElements() {
        return checkboxElements.size();
    }

    @Override
    public SelectableElement getElement(int i) {
        return checkboxElements.get(i);
    }
}