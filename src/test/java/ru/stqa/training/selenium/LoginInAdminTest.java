package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import static junit.framework.TestCase.assertFalse;

/**
 * Created by vkadubina on 2/23/2017.
 */
public class LoginInAdminTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void loginInAdminTest() {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("password")).sendKeys("not-so-secret-password");
        driver.findElement(By.name("login")).click();
        assertFalse(driver.getCurrentUrl().contains("login.php"));
    }


    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
