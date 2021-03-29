package testing2.ex1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class Exercise1 {
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
        driver.navigate().to("https://jdi-testing.github.io/jdi-light/index.html");

        // 2. Assert Browser title
        Assert.assertEquals(driver.getTitle(), "Home Page");
    }

    @Test(testName = "Login",
            dependsOnMethods = {"openPage"})
    public void login() {
        // Perform login
        driver.findElement(By.id("user-icon")).click();
        driver.findElement(By.id("name")).sendKeys("Roman");
        driver.findElement(By.id("password")).sendKeys("Jdi1234");
        driver.findElement(By.id("login-button")).click();

        // 4. Assert User name in the left-top side of screen that user is loggined
        WebElement name = driver.findElement(By.id("user-name"));
        String s = name.getAttribute("innerHTML");
        Assert.assertEquals(s.toUpperCase(), "ROMAN IOVLEV");
        Assert.assertTrue(name.isDisplayed());

        // 5. Assert Browser title
        Assert.assertEquals(driver.getTitle(), "Home Page");
    }

    @Test(testName = "Header",
            dependsOnMethods = {"login"})
    public void headerTest() {
        // 6. Assert that there are 4 items on the header section are displayed and they have proper texts
        List<WebElement> list = driver.findElements(By.xpath("//header/div/nav/ul[1]/li/a"));
        Assert.assertEquals(list.size(), 4);
        String[] headers = {"HOME", "CONTACT FORM", "SERVICE", "METALS &AMP; COLORS"};
        for (int i = 0; i < 4; i++) {
            String s = list.get(i).getAttribute("innerHTML").split("<")[0].trim().toUpperCase();
            Assert.assertTrue(list.get(i).isDisplayed());
            Assert.assertEquals(s, headers[i]);
        }
    }

    @Test(testName = "Icons",
            dependsOnMethods = {"login"})
    public void iconsTest() {
        // 7. Assert that there are 4 images on the Index Page and they are displayed
        // 8. Assert that there are 4 texts on the Index Page under icons and they have proper text
        List<WebElement> list1 = driver.findElements(By.className("benefit-icon"));
        List<WebElement> list2 = driver.findElements(By.className("benefit-txt"));
        Assert.assertEquals(list1.size(), 4);
        Assert.assertEquals(list2.size(), 4);

        String[] texts = {"To include good practices\nand ideas from successful\nEPAM project",
                "To be flexible and\ncustomizable",
                "To be multiplatform",
                "Already have good base\n(about 20 internal and\nsome external projects),\nwish to get more…"
        };
        for(WebElement e : list1) {
            Assert.assertTrue(e.isDisplayed());
        }
        for (int i = 0; i < 4; i++) {
            Assert.assertTrue(list2.get(i).isDisplayed());
            String s = list2.get(i).getAttribute("innerHTML").replaceAll("\\s*<br>\\s*", "\n").trim();
            Assert.assertEquals(s, texts[i]);
        }
    }

    @Test(testName = "Text",
            dependsOnMethods = {"login"})
    public void textTest() {
        // 9. Assert a text of the main headers
        List<WebElement> headers = driver.findElements(By.tagName("h3"));
        WebElement title = headers.get(0);
        WebElement subHeader = headers.get(1).findElement(By.tagName("a"));
        WebElement text = driver.findElement(By.name("jdi-text"));

        Assert.assertTrue(title.isDisplayed());
        Assert.assertTrue(text.isDisplayed());
        Assert.assertEquals(title.getAttribute("innerHTML").trim(), "EPAM framework Wishes…");

        String displayedText = text.getAttribute("innerHTML").replaceAll("\\s+|\n", " ").trim();
        Assert.assertEquals(displayedText,
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.");
        Assert.assertTrue(headers.get(1).isDisplayed());

        // 13. Assert a text of the sub header
        Assert.assertEquals(subHeader.getAttribute("innerHTML").toUpperCase().trim(), "JDI GITHUB");
        Assert.assertEquals(subHeader.getAttribute("href"), "https://github.com/epam/JDI");
    }

    @Test(testName = "IFrame",
            dependsOnMethods = {"login"})
    public void iFrameTest() {
        // 10. Assert that there is the iframe in the center of page
        WebElement iframe = driver.findElement(By.id("second_frame"));
        Assert.assertTrue(iframe.isDisplayed());

        // 11. Switch to the iframe and check that there is Epam logo in the left top conner of iframe
        driver.switchTo().frame(iframe);
        Assert.assertTrue(driver.findElement(By.id("epam-logo")).isDisplayed());

        // 12. Switch to original window back
        driver.switchTo().parentFrame();
    }

    @Test(testName = "Sections",
            dependsOnMethods = {"login"})
    public void sectionsTest() {
        Assert.assertTrue(driver.findElement(By.id("mCSB_1")).isDisplayed());
        // 16. Assert that there is Footer
        Assert.assertTrue(driver.findElement(By.tagName("footer")).isDisplayed());
    }

    @AfterClass
    public void closeSession() {
        // 17. Close Browser
        driver.quit();
    }
}