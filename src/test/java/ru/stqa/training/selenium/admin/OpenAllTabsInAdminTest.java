package ru.stqa.training.selenium.admin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.stqa.training.selenium.MultiBrowserBaseTest;
import ru.stqa.training.selenium.SeleniumBrowser;

import static org.junit.Assert.assertTrue;
import static ru.stqa.training.selenium.admin.AdminPageHelper.*;

/**
 * @author Victoria Kadubina
 */
@RunWith(Parameterized.class)
public class OpenAllTabsInAdminTest extends MultiBrowserBaseTest {


    public OpenAllTabsInAdminTest(SeleniumBrowser browser) {
        super(browser);
    }

    @Test
    public void clickAllTabsAndSubtabs() {
        loginInAdmin(driver);

        int categoriesCount = driver.findElements(By.cssSelector("ul#box-apps-menu > li")).size();

        for (int i = 1; i <= categoriesCount; i++) {

            //need to understand that the page is updated
            WebElement logo = driver.findElement(By.cssSelector("div.logotype"));

            String cssSelector = "ul#box-apps-menu > li:nth-child(" + i + ")";
            driver.findElement(By.cssSelector(cssSelector)).click();
            wait.until(ExpectedConditions.stalenessOf(logo));

            assertPageHeaderExists();

            for (int j = 1; j < getSubcategoriesQty(cssSelector) + 1; j++) {

                //need to understand that the page is updated
                logo = driver.findElement(By.cssSelector("div.logotype"));

                driver.findElement(By.cssSelector(cssSelector + " li:nth-child(" + j + ")")).click();
                wait.until(ExpectedConditions.stalenessOf(logo));

                assertPageHeaderExists();
            }
        }
    }

    private int getSubcategoriesQty(String cssSelector) {
        return driver.findElements(By.cssSelector(cssSelector + " li")).size();
    }

    private void assertPageHeaderExists() {
        assertTrue(driver.findElements(By.cssSelector("td#content > h1")).size() > 0);
    }

}
