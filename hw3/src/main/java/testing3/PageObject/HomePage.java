package testing3.PageObject;

import testing3.AbstractPageObjects.PageComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;


public class HomePage implements PageComponent {

    @FindBy(id = "user-icon")
    WebElement userIcon;
    @FindBy(id = "name")
    WebElement nameField;
    @FindBy(id = "password")
    WebElement passwordField;
    @FindBy(id = "login-button")
    WebElement loginButton;
    @FindBy(id = "user-name")
    WebElement username;
    @FindBy(xpath = "//header/div/nav/ul[1]/li/a")
    List<WebElement> headerLinks;
    @FindBy(className = "benefit-icon")
    List<WebElement> benefitIcons;
    @FindBy(className = "benefit-txt")
    List<WebElement> benefitText;
    @FindBy(tagName = "h3")
    WebElement title;
    @FindBy(xpath = "//main//a")
    WebElement subTitle;
    @FindBy(name = "jdi-text")
    WebElement text;
    @FindBy(id = "second_frame")
    WebElement iFrame;
    @FindBy(id = "epam-logo")
    WebElement logo;
    @FindBy(id = "mCSB_1")
    WebElement leftPanel;
    @FindBy(tagName = "footer")
    WebElement footer;
    @FindBy(className = "dropdown")
    WebElement headerDropdown;
    @FindBy(xpath = "//ul[@class='dropdown-menu']//a")
    List<WebElement> headerDropdownList;
    @FindBy(xpath = "//*[@class='sidebar-menu']/li[3]")
    WebElement leftSectionDropdown;
    @FindBy(xpath = "//*[@class='sidebar-menu']/li[3]//li")
    List<WebElement> leftSectionDropdownList;

    @Override
    public void initElements(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public enum Element {
        Username,
        Title,
        SubTitle,
        Text,
        Logo,
        LeftPanel,
        Footer,
        HeaderDropdown,
        LeftSectionDropdown
    }


    public enum ElementList {
        Header,
        BenefitIcons,
        BenefitTexts,
        HeaderDropdownList,
        LeftSectionDropdownList
    }


    public void login(String name, String password) {
        userIcon.click();
        nameField.sendKeys(name);
        passwordField.sendKeys(password);
        loginButton.click();
    }

    public boolean isDisplayed(Element element) {
        return (WE(element)).isDisplayed();
    }

    private List<WebElement> WEList(ElementList elementList) {
        /*return switch (elementList) {
            case Header -> headerLinks;
            case BenefitTexts -> benefitText;
            case BenefitIcons -> benefitIcons;
        };*/
        if (elementList.equals(ElementList.Header)) return headerLinks;
        else if (elementList.equals(ElementList.BenefitTexts)) return benefitText;
        else if (elementList.equals(ElementList.HeaderDropdownList)) return headerDropdownList;
        else if (elementList.equals(ElementList.LeftSectionDropdownList)) return leftSectionDropdownList;
        else /*if (elementList.equals(ElementList.BenefitIcons))*/ return benefitIcons;
    }

    private WebElement WE(Element element) {
        /*return switch (element) {
            case Username -> username;
            case Text -> text;
            case Title -> title;
            case SubTitle -> subTitle;
            case Logo -> logo;
            case Footer -> footer;
            case LeftPanel -> leftPanel;
        };*/
        if (element.equals(Element.Username)) return username;
        else if (element.equals(Element.Text)) return text;
        else if (element.equals(Element.Title)) return title;
        else if (element.equals(Element.SubTitle)) return subTitle;
        else if (element.equals(Element.Logo)) return logo;
        else if (element.equals(Element.Footer)) return footer;
        else if (element.equals(Element.LeftPanel)) return leftPanel;
        else if (element.equals(Element.HeaderDropdown)) return headerDropdown;
        else /*if (element.equals(Element.LeftPanel))*/ return leftSectionDropdown;


    }

    public boolean isDisplayed(ElementList elementList) {
        List<WebElement> list = WEList(elementList);
        boolean allVisible = true;
        for(WebElement element: list) {
            if (!element.isDisplayed()) {
                allVisible = false;
                break;
            }
        }
        return allVisible;
    }

    public String getText(Element element) {
        return WE(element).getText();
    }

    public List<String> getText(ElementList elementList) {
        List<WebElement> list = WEList(elementList);
        List<String> res = new ArrayList<>();
        for(WebElement element: list)
            res.add(element.getText());
        return res;
    }

    public int elementListSize(ElementList elementList) {
        return WEList(elementList).size();
    }

    public String getHRef(Element element) {
        return WE(element).getAttribute("href");
    }

    public HomePage openIFrame(WebDriver driver) {
        driver.switchTo().frame(iFrame);
        HomePage frame = new HomePage();
        frame.initElements(driver);
        return frame;
    }

    public void click(Element element) {
        WE(element).click();
    }

    public void click(ElementList list, int which) {
        WEList(list).get(which).click();
    }

}