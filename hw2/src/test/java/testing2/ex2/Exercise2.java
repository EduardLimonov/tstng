package testing2.ex2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.List;

public class Exercise2 {
    protected WebDriver driver;

    @BeforeClass
    public void initDriver() {
        System.setProperty("webdriver.chrome.driver","C:/Users/Юзверь/Documents/Study/Методы тестирования ПО/lab3/dz2/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test(testName = "Open site")
    public void openPage() {
        // 1. Open test site by URL
        String mainURL = "https://jdi-testing.github.io/jdi-light/index.html";
        driver.navigate().to(mainURL);

        //2. Assert Browser title
        Assert.assertEquals(driver.getTitle(), "Home Page");
    }

    @Test(testName = "Login",
            dependsOnMethods = {"openPage"})
    public void login() {
        // 3. Perform login
        driver.findElement(By.id("user-icon")).click();
        driver.findElement(By.id("name")).sendKeys("Roman");
        driver.findElement(By.id("password")).sendKeys("Jdi1234");
        driver.findElement(By.id("login-button")).click();

        // 4. Assert User name in the left-top side of screen that user is loggined
        WebElement name = driver.findElement(By.id("user-name"));
        String s = name.getAttribute("innerHTML");
        Assert.assertEquals(s.toUpperCase(), "ROMAN IOVLEV");
        Assert.assertTrue(name.isDisplayed());
    }

    @Test(testName = "DropDown",
            dependsOnMethods = {"login"},
            groups = {"firstPage"})
    public void dropDownTest() {
        // 5. Click on "Service" subcategory in the header and check that drop down contains options
        SoftAssert softAssert = new SoftAssert();
        WebElement dropDown = driver.findElement(By.className("dropdown"));
        dropDown.click();
        List<WebElement> menu = dropDown.findElements(By.tagName("li"));
        String[] options = {
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

        Assert.assertEquals(menu.size(), options.length);
        for (int i = 0; i < options.length; i++) {
            WebElement href = menu.get(i).findElement(By.tagName("a"));
            softAssert.assertTrue(href.isDisplayed());
            softAssert.assertEquals(href.getAttribute("innerHTML").toUpperCase().trim(),
                    options[i].toUpperCase());
        }
        softAssert.assertAll();
    }

    @Test(dependsOnMethods = {"login"},
            groups = {"firstPage"})
    public void sideMenuTest() {
        String[] options = {
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
        // 6. Click on Service subcategory in the left section and check that drop down contains options
        SoftAssert softAssert = new SoftAssert();
        WebElement service = driver.findElement(By.xpath("//*[@class='sidebar-menu']/li[3]"));
        service.click();

        List<WebElement> menu = service.findElements(By.tagName("li"));
        Assert.assertEquals(menu.size(), options.length);
        for (int i = 0; i < options.length; i++) {
            WebElement item = menu.get(i).findElement(By.tagName("span"));
            softAssert.assertTrue(item.isDisplayed());
            softAssert.assertEquals(item.getAttribute("innerHTML").trim(), options[i]);
        }
        softAssert.assertAll();
    }

    @Test(dependsOnGroups = {"firstPage"})
    public void elementsPage() {
        // 7. Open through the header menu Service -> Different Elements Page
        WebElement dropDown = driver.findElement(By.className("dropdown"));
        dropDown.click();
        dropDown.findElement(By.xpath("//li[8]")).click();
    }

    @Test(dependsOnMethods = {"elementsPage"},
            groups = {"secondPage"})
    public void elements4() {
        // 8. Check interface on Different elements page, it contains all needed elements
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(driver.findElement(By.name("navigation-sidebar")).isDisplayed());
        softAssert.assertTrue(driver.findElement(By.name("log-sidebar")).isDisplayed());

        String[] checkBoxes = {"Water", "Earth", "Wind", "Fire"};
        String[] radioButtons = {"Gold", "Silver", "Bronze", "Selen"};
        String[] optionsLabels = {"Red", "Green", "Blue", "Yellow"};

        List<WebElement> weCB = driver.findElements(By.className("label-checkbox"));
        List<WebElement> weRB = driver.findElements(By.className("label-radio"));

        softAssert.assertEquals(weCB.size(), checkBoxes.length);
        softAssert.assertEquals(weRB.size(), radioButtons.length);
        softAssert.assertTrue(driver.findElement(By.tagName("select")).isDisplayed());
        List<WebElement> weOp = driver.findElements(By.tagName("option"));
        softAssert.assertEquals(weOp.size(), optionsLabels.length);
        softAssert.assertAll();

        for (int i = 0; i < 4; i++) {
            WebElement cb = weCB.get(i);
            WebElement rb = weRB.get(i);

            softAssert.assertEquals(cb.getText().trim(), checkBoxes[i]);
            softAssert.assertEquals(rb.getText().trim(), radioButtons[i]);
            softAssert.assertEquals(weOp.get(i).getAttribute("innerHTML").trim(), optionsLabels[i]);
        }
        softAssert.assertEquals(driver.findElement(By.xpath("//main//button")).getAttribute("innerHTML").trim(),
                "Default Button");
        softAssert.assertEquals(driver.findElement(By.xpath("//main//input[@class='uui-button']")).getAttribute("value").trim(),
                "Button");
        softAssert.assertAll();
    }

    @Test(dependsOnMethods = {"elementsPage"},
            groups = {"secondPage"})
    public void clickElements() {
        List<WebElement> checkboxes = driver.findElements(By.xpath("//main//input[@type='checkbox']"));
        List<WebElement> radios = driver.findElements(By.xpath("//main//input[@type='radio']"));

        WebElement selen = radios.get(radios.size() - 1);
        WebElement logs = driver.findElement(By.className("panel-body-list"));
        SoftAssert softAssert = new SoftAssert();

        // 11. Select checkboxes
        checkboxes.get(0).click();
        checkboxes.get(2).click();
        softAssert.assertTrue(checkboxes.get(0).isSelected());
        softAssert.assertTrue(checkboxes.get(2).isSelected());

        String[] logsString = logs.getText().split("\n");

        // 12. Assert that for each checkbox there is an individual log row and value is corresponded to the status of checkbox
        Assert.assertEquals(logsString.length, 2);
        checkLogs(logsString[0], "Wind", "true", softAssert);
        checkLogs(logsString[1], "Water", "true", softAssert);
        softAssert.assertAll();

        // 13. Select radio
        selen.click();
        softAssert.assertTrue(selen.isSelected());

        // 14. Assert that for radiobutton there is a log row and value is corresponded to the status of radiobutton.
        logsString = logs.getText().split("\n");
        Assert.assertEquals(logsString.length, 3);
        checkLogs(logsString[0], "metal", "Selen", softAssert);
        softAssert.assertAll();

        // 15. Select in dropdown
        WebElement select = driver.findElement(By.xpath("//main//select"));
        select.click();
        select.findElement(By.xpath("*[last()]")).click();
        logsString = logs.getText().split("\n");
        Assert.assertEquals(logsString.length, 4);

        // 16. Assert that for dropdown there is a log row and value is corresponded to the selected value.
        checkLogs(logsString[0], "Colors", "Yellow", softAssert);
        softAssert.assertAll();

        // 17. Unselect and assert checkboxes
        checkboxes.get(0).click();
        checkboxes.get(2).click();

        // 18. Assert that for each checkbox there is an individual log row and value is corresponded to the status of checkbox
        softAssert.assertFalse(checkboxes.get(0).isSelected());
        softAssert.assertFalse(checkboxes.get(2).isSelected());

        logsString = logs.getText().split("\n");
        Assert.assertEquals(logsString.length, 6);
        checkLogs(logsString[0], "Wind", "false", softAssert);
        checkLogs(logsString[1], "Water", "false", softAssert);
        softAssert.assertAll();
    }

    protected void checkLogs(String log, String key, String value, SoftAssert softAssert) {
        String[] parsed = log.split("\\s");
        softAssert.assertEquals(parsed[1].substring(0, parsed[1].length() - 1), key);
        softAssert.assertEquals(parsed[parsed.length - 1], value);
    }


    @AfterClass
    public void closeSession() {
        driver.quit();
    }
}