package ru.stqa.training.selenium.client;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.stqa.training.selenium.MultiBrowserBaseTest;
import ru.stqa.training.selenium.SeleniumBrowser;

import java.util.List;
import static org.junit.Assert.*;

/**
 * @author Victoria Kadubina
 */
public class NavigateToProductPageTest extends MultiBrowserBaseTest {

    public NavigateToProductPageTest(SeleniumBrowser browser) {
        super(browser);
    }

    @Test
    public void navigateToProductPageTest() {
        driver.get(clientUrl);
        List<WebElement> elements = driver.findElements(By.cssSelector("li.product"));
        assertTrue(elements.size() > 0);

        elements.get(0).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div#box-product")));
    }
}
