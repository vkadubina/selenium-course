package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.urlToBe;

/**
 * @author Victoria Kadubina
 */
abstract public class MultiBrowserBaseTest {

    @Parameterized.Parameters
    public static Collection<Object> data() {
        return Arrays.asList(new Object[] {
               "chrome", "firefox", "firefox45","firefox-nightly", "safari"
        });
    }

    private String browser;

    public MultiBrowserBaseTest(String browser) {
        this.browser = browser;
    }


    protected WebDriver driver;
    protected WebDriverWait wait;


    @Before
    public void init() {
        switch (browser) {
            case "chrome":
                initChormeDriver();
                break;
            case "firefox":
                initFFDriver();
                break;
            case "firefox45":
                initFF45esrDriver();
                break;
            case "firefox-nightly":
                initFFNightlyDriver();
                break;
            case "safari":
                initSafariDriver();
                break;
            case "ie":
                initIEDriver();
                break;
            default:
                throw new IllegalArgumentException("Unknown browser id " + browser);
        }
    }



    @After
    public void stop() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    private void initChormeDriver() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    private void initFFDriver() {
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 10);
    }

    private void initFF45esrDriver() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(FirefoxDriver.MARIONETTE, false);
        FirefoxBinary bin = new FirefoxBinary(new File("/Applications/Firefox45.app/Contents/MacOS/firefox"));
        driver = new FirefoxDriver(bin, new FirefoxProfile(),caps);
        wait = new WebDriverWait(driver, 10);

    }

    private void initFFNightlyDriver() {
        FirefoxBinary bin = new FirefoxBinary(new File("/Applications/FirefoxNightly.app/Contents/MacOS/firefox"));
        driver = new FirefoxDriver(bin, new FirefoxProfile());
        wait = new WebDriverWait(driver, 10);

    }


    private void initSafariDriver() {
        driver = new SafariDriver();
        wait = new WebDriverWait(driver, 10);
    }

    private void initIEDriver(){
        File file = new File("C:/IEDriverServer.exe");
        System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
        driver = new InternetExplorerDriver();
        wait = new WebDriverWait(driver, 10);
    }

    public void loginInAdmin(WebDriver driver, String url){
        if (driver != null) {
            driver.get(url);
            driver.findElement(By.name("username")).clear();
            driver.findElement(By.name("username")).sendKeys("admin");
            driver.findElement(By.name("password")).sendKeys("not-so-secret-password");
            driver.findElement(By.name("login")).click();
            wait.until(urlToBe(url));
        }
    }
}
