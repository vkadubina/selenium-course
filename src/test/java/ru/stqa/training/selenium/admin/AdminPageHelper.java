package ru.stqa.training.selenium.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlToBe;

/**
 * @author Victoria Kadubina
 */
public class AdminPageHelper {
    public static final String ADMIN_URL = "http://localhost:8080/admin/";

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
        String titleText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("td#content h1"))).getText().trim();
        assertEquals(sectionName, titleText);
        }

    public static void assertUrlNotChanged(WebDriver driver, String oldUrl) {
        assertEquals(oldUrl,driver.getCurrentUrl());
    }

    public static void assertUrlChanged(WebDriver driver, String oldUrl) {
        assertFalse(driver.getCurrentUrl().equals(oldUrl));
    }

}
