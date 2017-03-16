package ru.stqa.training.selenium.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * @author Victoria Kadubina
 */
public class AdminPageHelper {
    public static final String ADMIN_URL = System.getProperty("app.admin.url");

    public static void loginInAdmin(WebDriver driver){

        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.get(ADMIN_URL);
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("not-so-secret-password");
        driver.findElement(By.name("login")).click();
        wait.until(urlToBe(ADMIN_URL));
    }

    public static void openAdminSection(WebDriver driver, WebDriverWait wait, String sectionName) {

        String xpath = "//span[.='" + sectionName + "']/..";
        driver.findElement(By.xpath(xpath)).click();
        String titleText = wait.until(visibilityOfElementLocated(By.cssSelector("td#content h1"))).getText().trim();
        assertEquals(sectionName, titleText);
        }

}
