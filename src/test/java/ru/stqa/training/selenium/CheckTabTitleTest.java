package ru.stqa.training.selenium;

import org.junit.Test;
import org.openqa.selenium.By;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

/**
 * @author Victoria Kadubina
 */
public class CheckTabTitleTest extends MultiBrowserBaseTest{

    public CheckTabTitleTest(SeleniumBrowser browser) {
        super(browser);
    }

    @Test
    public void checkGoogleSearchTabTitleTest() {
        driver.get("https://www.google.com/");
        driver.findElement(By.name("q")).sendKeys("webdriver");
        driver.findElement(By.name("btnG")).click();
        wait.until(titleIs("webdriver - Google Search"));
    }
}
