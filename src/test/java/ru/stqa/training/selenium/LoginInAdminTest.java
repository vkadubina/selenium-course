package ru.stqa.training.selenium;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.*;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

/**
 * @author Victoria Kadubina
 */
public class LoginInAdminTest {
    private WebDriver driver;
    private WebDriverWait wait;

    private void initChormeDriver() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    private void initFFDriver() {
        FirefoxBinary bin = new FirefoxBinary(new File("/Applications/FirefoxNightly.app/Contents/MacOS/firefox"));
        driver = new FirefoxDriver(bin, new FirefoxProfile());
        wait = new WebDriverWait(driver, 10);
    }

    private void initSafariDriver() {
        driver = new SafariDriver();
        wait = new WebDriverWait(driver, 10);
    }


    @Test
    public void loginInAdminFirefoxTest() {
        initFFDriver();
        loginWithCorrectPassword();
    }

    @Test
    public void wrongPasswordFirefoxTest() {
        initFFDriver();
        loginWithWrongPassword();
    }

    @Test
    public void loginInAdminChromeTest() {
        initChormeDriver();
        loginWithCorrectPassword();
    }

    @Test
    public void wrongPasswordChromeTest() {
        initChormeDriver();
        loginWithWrongPassword();
    }

    @Test
    public void loginInAdminSafariTest() {
        initSafariDriver();
        loginWithCorrectPassword();
    }

    @Test
    public void wrongPasswordSafariTest() {
        initSafariDriver();
        loginWithWrongPassword();
    }

    private void loginWithCorrectPassword() {
        driver.get(adminUrl);
        assertTrue(driver.getCurrentUrl().contains("login.php"));
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("not-so-secret-password");
        driver.findElement(By.name("login")).click();
        wait.until(urlToBe(adminUrl));
        assertTrue(driver.getCurrentUrl().equals(adminUrl));
    }

    private void loginWithWrongPassword() {
        driver.get(adminUrl);
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("wrong-password");
        driver.findElement(By.name("login")).click();
        wait.until(visibilityOfElementLocated(By.className("errors")));
        assertTrue(driver.getCurrentUrl().contains("login.php"));
    }


    private String adminUrl = "http://localhost:8080/admin/";


    @After
    public void stop() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
