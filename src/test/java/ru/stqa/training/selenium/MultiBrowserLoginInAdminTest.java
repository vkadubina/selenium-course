package ru.stqa.training.selenium;

import org.junit.Test;
import org.openqa.selenium.By;

import static junit.framework.TestCase.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * @author Victoria Kadubina
 */
public class MultiBrowserLoginInAdminTest extends MultiBrowserBaseTest {

    public MultiBrowserLoginInAdminTest(String browser) {
        super(browser);
    }

    @Test
    public void loginWithCorrectPassword() {
        driver.get(adminUrl);
        assertTrue(driver.getCurrentUrl().contains("login.php"));
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("not-so-secret-password");
        driver.findElement(By.name("login")).click();
        wait.until(urlToBe(adminUrl));
        assertTrue(driver.getCurrentUrl().equals(adminUrl));
    }

    @Test
    public void loginWithWrongPassword() {
        driver.get(adminUrl);
        assertTrue(driver.getCurrentUrl().contains("login.php"));
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("wrong-password");
        driver.findElement(By.name("login")).click();
        wait.until(visibilityOfElementLocated(By.className("errors")));
        assertTrue(driver.getCurrentUrl().contains("login.php"));
    }

    private String adminUrl = "http://localhost:8080/admin/";
}
