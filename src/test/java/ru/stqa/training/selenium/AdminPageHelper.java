package ru.stqa.training.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlToBe;

/**
 * @author Victoria Kadubina
 */
public class AdminPageHelper {

    public static void loginInAdmin(WebDriver driver, String url){

        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.get(url);
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("not-so-secret-password");
        driver.findElement(By.name("login")).click();
        wait.until(urlToBe(url));

    }

    public static void openAdminSection(WebDriver driver, String sectionName){
        String oldUrl = driver.getCurrentUrl();
        List<WebElement> adminSectionsList = driver.findElements(By.cssSelector("ul#box-apps-menu li"));
        for (WebElement we:adminSectionsList) {
            String text = we.findElement(By.cssSelector("span.name")).getText();
            if (text.equals(sectionName)){
                if (!we.getAttribute("className").equals("selected")) {
                    we.findElement(By.cssSelector("a")).click();
                    assertUrlChanged(driver,oldUrl);
                }

            }

        }
        throw new IllegalArgumentException("Section not found");
    }
    public static void assertUrlNotChanged(WebDriver driver, String oldUrl) {
        assertTrue(driver.getCurrentUrl().equals(oldUrl));
    }

    public static void assertUrlChanged(WebDriver driver, String oldUrl) {
        assertFalse(driver.getCurrentUrl().equals(oldUrl));
    }

}
