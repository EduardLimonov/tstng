package testing3;

import org.apache.commons.math3.util.Pair;
import testing3.AbstractPageObjects.SelectableElement;
import testing3.PageObject.ElementsPage;
import testing3.AbstractPageObjects.GroupedComponent;
import testing3.PageObject.HomePage;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;


public class StepDefinitions
{
    WebDriver driver;
    HomePage homePage;
    ElementsPage elementsPage = new ElementsPage();

    enum CurrentPage {
        HomePage,
        ElementPage
    }
    CurrentPage page;

    @Before
    public void initBrowser() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/Юзверь/Documents/Study/Методы тестирования ПО/lab3/dz2/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @After
    public void quit() {
        driver.quit();
    }

    @Given("I'm opened homepage")
    public void openHomepage() {
        driver.navigate().to("https://jdi-testing.github.io/jdi-light/index.html");
        homePage = new HomePage();
        homePage.initElements(driver);
        page = CurrentPage.HomePage;
    }

    @Given("I'm logged as {word} - {word}")
    public void login(String username, String password) {
        homePage.login(username, password);
    }

    @Then("Browser title is {string}")
    public void assertTitle(String title) {
        Assert.assertEquals(driver.getTitle(), title);
    }

    @Then("Username is {string}")
    public void assertUsername(String username) {
        Assert.assertEquals(homePage.getText(HomePage.Element.Username),
                username);
    }

    @Then("I see {int} {}")
    public void assertVisible(int number, String what) {
        if (page.equals(CurrentPage.HomePage)) {
            HomePage.ElementList list;
            switch (what) {
                case "images":
                    list = HomePage.ElementList.BenefitIcons;
                    break;
                case "texts":
                    list = HomePage.ElementList.BenefitTexts;
                    break;
                case "header items":
                    list = HomePage.ElementList.Header;
                    break;
                case "header list items":
                    list = HomePage.ElementList.HeaderDropdownList;
                    break;
                case "sidebar list items":
                    list = HomePage.ElementList.LeftSectionDropdownList;
                    break;
                default:
                    System.out.println("Wrong item: " + what);
                    return;
            }

            Assert.assertEquals(homePage.elementListSize(list),
                    number);
            Assert.assertTrue(homePage.isDisplayed(list));
        }
        if (page.equals(CurrentPage.ElementPage)) {
            GroupedComponent group;
            switch (what) {
                case "checkboxes":
                    group = elementsPage.getCheckboxes();
                    break;
                case "radio buttons":
                    group = elementsPage.getRadioButtons();
                    break;
                default:
                    System.out.println("Wrong item: " + what);
                    return;
            }
            Assert.assertEquals(group.numberOfElements(), number);
            Assert.assertTrue(group.isVisible());
        }
    }

    @Then("Header labels are proper")
    public void assertHeaderLabels() {
        SoftAssert softAssert = new SoftAssert();
        List<String> labels = homePage.getText(HomePage.ElementList.Header);
        String[] expected = {"HOME", "CONTACT FORM", "SERVICE", "METALS & COLORS"};
        for (int i = 0; i < 4; i++) {
            softAssert.assertEquals(labels.get(i), expected[i]);
        }
        softAssert.assertAll();
    }

    @Then("Text blocks have proper text")
    public void assertBlockText() {
        SoftAssert softAssert = new SoftAssert();
        String[] expected = {"To include good practices\nand ideas from successful\nEPAM project",
                "To be flexible and\ncustomizable",
                "To be multiplatform",
                "Already have good base\n(about 20 internal and\nsome external projects),\nwish to get more…"
        };
        List<String> labels = homePage.getText(HomePage.ElementList.BenefitTexts);
        for (int i = 0; i < 4; i++) {
            softAssert.assertEquals(labels.get(i), expected[i]);
        }
        softAssert.assertAll();
    }

    @Then("I see {string} as main header")
    public void assertHeader(String text) {
        Assert.assertEquals(homePage.getText(HomePage.Element.Title), text);
    }

    @Then("I see {string} as text below")
    public void assertText(String text) {
        if (text.equals("Lorem ipsum"))
            text = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.";
        Assert.assertEquals(homePage.getText(HomePage.Element.Text), text.toUpperCase());
    }

    @When("I switch to iFrame")
    public void switchToIFrame() {
        homePage = homePage.openIFrame(driver);
    }

    @Then("I see the {}")
    public void assertVisible(String what) {
        if (page.equals(CurrentPage.HomePage)) {
            HomePage.Element element;
            switch (what) {
                case "EPAM logo":
                    element = HomePage.Element.Logo;
                    break;
                case "left section":
                    element = HomePage.Element.LeftPanel;
                    break;
                case "footer":
                    element = HomePage.Element.Footer;
                    break;
                default:
                    System.out.println("Wrong item: " + what);
                    return;
            }
            Assert.assertTrue(homePage.isDisplayed(element));
        }
        if (page.equals(CurrentPage.ElementPage)) {
            SelectableElement element;
            switch (what) {
                case "dropdown":
                    element = elementsPage.getSelector();
                    break;
                case "button1":
                    element = elementsPage.getButton(1);
                    break;
                case "button2":
                    element = elementsPage.getButton(2);
                    break;
                case "right sidebar":
                    Assert.assertTrue(elementsPage.rightSectionIsVisible());
                    return;
                case "left sidebar":
                    Assert.assertTrue(elementsPage.leftSectionIsVisible());
                    return;
                default:
                    System.out.println("Wrong item: " + what);
                    return;
            }
            Assert.assertTrue(element.isVisible());
        }
    }

    @Then("I see {string} as sub header")
    public void assertSubHeader(String text) {
        Assert.assertEquals(homePage.getText(HomePage.Element.SubTitle), text);
    }

    @Then("The hyperref is proper")
    public void assertHref() {
        Assert.assertEquals(homePage.getHRef(HomePage.Element.SubTitle),
                "https://github.com/epam/JDI");
    }

    @When("I click on {}")
    public void clickOn(String what) {
        if (what.equals("service at header")) homePage.click(HomePage.Element.HeaderDropdown);
        if (what.equals("service at left sidebar")) homePage.click(HomePage.Element.LeftSectionDropdown);
    }

    @Then("{word} list items have proper text")
    public void serviceItems(String which) {
        String[] options;
        List<String> items;
        if (which.equals("Header")) {
            items = homePage.getText(HomePage.ElementList.HeaderDropdownList);
            options = new String[]{
                    "Support",
                    "Dates",
                    "Search",
                    "Complex Table",
                    "Simple Table",
                    "User Table",
                    "Table with pages",
                    "Different elements",
                    "Performance"
            };
        }
        else {
            items = homePage.getText(HomePage.ElementList.LeftSectionDropdownList);
            options = new String[]{
                    "Support",
                    "Dates",
                    "Complex Table",
                    "Simple Table",
                    "Search",
                    "User Table",
                    "Table with pages",
                    "Different elements",
                    "Performance"
            };
        }
        SoftAssert softAssert = new SoftAssert();
        for (int i = 0; i < options.length; i++) {
            softAssert.assertEquals(items.get(i).toUpperCase(), options[i].toUpperCase());
        }
        softAssert.assertAll();
    }

    @Given("I am on Element Page")
    public void goToElementPage() {
        homePage.click(HomePage.Element.HeaderDropdown);
        homePage.click(HomePage.ElementList.HeaderDropdownList, 7);
        elementsPage.initElements(driver);
        page = CurrentPage.ElementPage;
    }

    List<Integer> checkboxIndexes(String boxes) {
        String[] checked = boxes.split(", ");
        List<Integer> res = new ArrayList<>(checked.length);
        final String[] options = {"Water", "Earth", "Wind", "Fire"};
        for(String s: checked) {
            for (int i = 0; i < options.length; i++) {
                if (options[i].equals(s)) {
                    res.add(i);
                    break;
                }
            }
        }
        if (res.size() == 0)
            throw new IllegalArgumentException("Unknown checkboxes: " + boxes);
        return res;
    }

    int radioIndex(String radio) {
        final String[] options = {"Gold", "Silver", "Bronze", "Selen"};
        for (int i = 0; i < options.length; i++) {
            if(options[i].equals(radio))
                return i;
        }
        throw new IllegalArgumentException("Unknown radio: " + radio);
    }

    int dropdownIndex(String option) {
        final String[] options = {"Red", "Green", "Blue", "Yellow"};
        for (int i = 0; i < options.length; i++) {
            if(options[i].equals(option))
                return i;
        }
        throw new IllegalArgumentException("Unknown option: " + option);
    }

    @When("I check {string}")
    public void checkBoxes(String boxes) {
        for(Integer index: checkboxIndexes(boxes))
            elementsPage.getCheckboxes().select(index);
    }

    @Then("{string} {} checked")
    public void assertChecked(String boxes, String verb) {
        boolean state = verb.equals("are");
        SoftAssert softAssert = new SoftAssert();
        for(Integer index: checkboxIndexes(boxes))
            softAssert.assertEquals(elementsPage.getCheckboxes().getElement(index).isSelected(),
                    state);
        softAssert.assertAll();
    }

    @Then("{string} is logged as {string}")
    public void assertLogs(String k, String v) {
        String[] keys = k.split(", ");
        String[] values = v.split(", ");
        Assert.assertEquals(keys.length, elementsPage.getLogs().numberOfLogs());
        List<Pair<String, String>> logs = elementsPage.getLogs().elementValueLogs();
        for (int i = 0; i < keys.length; i++) {
            Assert.assertEquals(logs.get(i).getKey(), keys[keys.length-1-i]);
            Assert.assertEquals(logs.get(i).getValue(), values[keys.length-1-i]);
        }
    }

    @When("I select {string} at {word}")
    public void selectAt(String what, String where) {
        SelectableElement element;
        List<Integer> indexes;
        if (where.equals("dropdown")) {
            element = elementsPage.getSelector();
            indexes = new ArrayList<>(1);
            indexes.add(dropdownIndex(what));
        } else if (where.equals("radios")) {
            element = elementsPage.getRadioButtons();
            indexes = new ArrayList<>(1);
            indexes.add(radioIndex(what));
        }
        else {
            element = elementsPage.getCheckboxes();
            indexes = checkboxIndexes(what);
        }
        for(Integer i: indexes) {
            element.select(i);
        }
    }

    @Then("{string} is selected at {word}")
    public void optionIsSelected(String what, String where) {
        if (where.equals("radios")) {
            int index = radioIndex(what);
            Assert.assertTrue(elementsPage.getRadioButtons()
                    .getElement(index).isSelected());
        }
        else if (where.equals("dropdown")) {
            Assert.assertEquals(elementsPage.getSelector().getText(),
                    what);
        }
        else {
            throw new IllegalArgumentException("Illegal location: " + where);
        }
    }
}
