package testing3.PageObject;

import testing3.AbstractPageObjects.ClickableElement;
import testing3.AbstractPageObjects.PageComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ElementsPage implements PageComponent {
    LogElement logs = new LogElement();
    CheckboxElements checkboxes = new CheckboxElements();
    RadioButtons radioButtons = new RadioButtons();
    SelectorElement selector = new SelectorElement();
    ClickableElement button1;
    ClickableElement button2;

    @FindBy(xpath = "//main//button")
    WebElement buttonWE1;
    @FindBy(xpath = "//main//input[@class='uui-button']")
    WebElement buttonWE2;
    @FindBy(name = "navigation-sidebar")
    WebElement leftSection;
    @FindBy(name = "log-sidebar")
    WebElement rightSection;

    @Override
    public void initElements(WebDriver driver) {
        PageFactory.initElements(driver, this);
        PageComponent[] components = {logs, checkboxes, radioButtons, selector};
        for(PageComponent component: components)
            component.initElements(driver);
        button1 = new ClickableElement(buttonWE1);
        button2 = new ClickableElement(buttonWE2);
    }

    public LogElement getLogs() {
        return logs;
    }

    public CheckboxElements getCheckboxes() {
        return checkboxes;
    }

    public RadioButtons getRadioButtons() {
        return radioButtons;
    }

    public SelectorElement getSelector() {
        return selector;
    }

    public ClickableElement getButton(int which) {
        if (which == 1) return button1;
        if (which == 2) return button2;
        throw new IllegalArgumentException("Do not have such button");
    }

    public boolean leftSectionIsVisible() {
        return leftSection.isDisplayed();
    }

    public boolean rightSectionIsVisible() {
        return rightSection.isDisplayed();
    }
}