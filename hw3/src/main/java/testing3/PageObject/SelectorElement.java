package testing3.PageObject;

import testing3.AbstractPageObjects.PageComponent;
import testing3.AbstractPageObjects.SelectableElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class SelectorElement implements SelectableElement, PageComponent {
    @FindBy(xpath = "//main//select")
    WebElement forSelector;
    Select selector;

    @Override
    public void select(int what) {
        selector.selectByIndex(what);
    }

    @Override
    public boolean isSelected() {
        return true;
    }

    @Override
    public boolean isVisible() {
        return true;
    }

    @Override
    public String getText() {
        return selector.getFirstSelectedOption().getText();
    }

    /*public String getSelected() {
        List<WebElement> list = selector.findElements(By.xpath("*[@selected]"));
        if (list.size() == 0)
            return null;
        return list.get(0).getText();
    }*/

    public List<String> optionTexts() {
        List<WebElement> options = selector.getOptions();
        List<String> res = new ArrayList<>(options.size());
        for(WebElement option: options) {
            res.add(option.getText());
        }
        return res;
    }

    @Override
    public void initElements(WebDriver driver) {
        PageFactory.initElements(driver, this);
        selector = new Select(forSelector);
    }
}
