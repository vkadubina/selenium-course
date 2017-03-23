package ru.stqa.training.selenium.grid;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

/**
 * @author Victoria Kadubina
 */
public class SeleniumGridTest {

    private WebDriver driver = null;
    private WebDriverWait wait = null;

    @Before
    public void init() throws MalformedURLException {

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setBrowserName("internet explorer");
        caps.setPlatform(Platform.WINDOWS);

        driver = new RemoteWebDriver(new URL("http://192.168.178.21:4444/wd/hub"), caps);
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void checkGoogleSearchTabTitleTest() {
        driver.get("https://www.google.com/");
        driver.findElement(By.name("q")).sendKeys("webdriver");
        driver.findElement(By.name("btnG")).click();
        wait.until(titleIs("webdriver - Google-Suche"));
    }

    @After
    public void stop() {
        if (driver != null) {
            driver.quit();
            driver = null;
            wait = null;
        }
    }

}
