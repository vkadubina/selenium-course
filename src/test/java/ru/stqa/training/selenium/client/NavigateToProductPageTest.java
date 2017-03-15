package ru.stqa.training.selenium.client;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.stqa.training.selenium.MultiBrowserBaseTest;

import java.util.List;

import static org.junit.Assert.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;


/**
 * @author Victoria Kadubina
 */
public class NavigateToProductPageTest extends MultiBrowserBaseTest {

    public NavigateToProductPageTest(String browser) {
        super(browser);
    }

    @Test
    public void navigateToProductPageTest() {
        driver.get(CLIENT_APP_URL);
        List<WebElement> elements = driver.findElements(By.cssSelector("li.product"));
        assertTrue(elements.size() > 0);

        elements.get(0).click();
        wait.until(urlContains("rubber-ducks-c-1/subcategory-c-2"));
    }

}
