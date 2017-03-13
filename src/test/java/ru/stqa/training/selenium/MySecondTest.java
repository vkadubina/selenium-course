package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.junit.Assert.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;


/**
 * @author Victoria Kadubina
 */
public class MySecondTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void myFirstTest() {
        driver.get("http://localhost:8080/");
        List<WebElement> elements = driver.findElements(By.cssSelector("li.product"));
        assertTrue(elements.size() > 0);

        elements.get(0).click();
        wait.until(urlContains("rubber-ducks-c-1/subcategory-c-2"));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
