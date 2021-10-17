package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

public class CommonMethods {

    public static WebDriver driver;
    public static WebDriverWait wait;

    @BeforeMethod(alwaysRun = true)
    public void openBrowser(){
        ConfigReader.readProperties(Constants.CONFIGURATION_FILEPATH);
        switch (ConfigReader.getPropertyValue("browser")) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            default:
                throw new RuntimeException("Invalid browser name");
        }
        driver.get(ConfigReader.getPropertyValue("url"));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Constants.IMPLICIT_WAIT, TimeUnit.SECONDS);
    }
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }
    public static void setUpChrome(String url) {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(url);
    }
    public static void setUpFirefox(String url) {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.get(url);
    }
    public static void maximizeWindow()  {
        driver.manage().window().maximize();
    }
    public static void navigateToURL(String url) {
        driver.navigate().to(url);
    }
    public static void navigateBack() {
        driver.navigate().back();
    }
    public static void navigateForward() {
        driver.navigate().forward();
    }
    public static void navigateRefresh() {
        driver.navigate().refresh();
    }
    public static void getUrl(String url) {
        driver.get(url);
    }
    public static String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    public static String getTitle() {
        return driver.getTitle();
    }
    public static void switchToWindow(String handle) {
        driver.switchTo().window(handle);
    }
    public static void implicitlyWait10sec() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    public static void explicitWait10secXpath(String xpath) {
        wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }
    public static void closeTab() {
        driver.close();
    }
    public static void quitBrowser() {
        driver.quit();
    }
}
