package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlToBe;

/**
 * @author Victoria Kadubina
 */
@RunWith(Parameterized.class)
abstract public class MultiBrowserBaseTest {

    private final static String FF_NIGHTLY_WIN = "";
    private final static String FF_NIGHTLY_MAC = "/Applications/FirefoxNightly.app/Contents/MacOS/firefox";
    private final static String FF_45_WIN = "";
    private final static String FF_45_MAC = "/Applications/Firefox45.app/Contents/MacOS/firefox";

    private final static List<String> DEFAULT_BROWSERS = Stream.of("chrome", "firefox", "firefox-nightly").collect(toList());
    private final static List<String> WIN_ONLY_BROWSERS = Stream.of("ie").collect(toList());
    private final static List<String> MAC_ONLY_BROWSERS = Stream.of("safari").collect(toList());

    private String ffNightly;
    private String ff45;

    @Parameterized.Parameters
    public static Collection<String> data() {
        if (OSValidator.isWindows()) {
            return Stream.concat(DEFAULT_BROWSERS.stream(), WIN_ONLY_BROWSERS.stream()).collect(toList());
        } else if (OSValidator.isMac()) {
            return Stream.concat(DEFAULT_BROWSERS.stream(), MAC_ONLY_BROWSERS.stream()).collect(toList());
        } else {
            return DEFAULT_BROWSERS;
        }
    }

    private String browser;

    public MultiBrowserBaseTest(String browser) {
        this.browser = browser;
        if (OSValidator.isWindows()) {
            this.ffNightly = FF_NIGHTLY_WIN;
            this.ff45 = FF_45_WIN;
        } else if (OSValidator.isMac()) {
            this.ffNightly = FF_NIGHTLY_MAC;
            this.ff45 = FF_45_MAC;
        } else {
            // FIXME
        }
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
        FirefoxOptions ffo = new FirefoxOptions();
        ffo.setBinary(new FirefoxBinary(new File(ff45)));
        ffo.setProfile(new FirefoxProfile());
        ffo.addDesiredCapabilities(caps);
        driver = new FirefoxDriver(ffo);
        wait = new WebDriverWait(driver, 10);

    }

    private void initFFNightlyDriver() {
        FirefoxOptions ffo = new FirefoxOptions();
        ffo.setProfile(new FirefoxProfile());
        ffo.setBinary(new FirefoxBinary(new File(ffNightly)));
        driver = new FirefoxDriver(ffo);
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
