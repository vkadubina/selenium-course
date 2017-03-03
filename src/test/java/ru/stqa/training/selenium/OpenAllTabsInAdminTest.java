package ru.stqa.training.selenium;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;

import java.util.Arrays;
import java.util.Collection;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlToBe;

/**
 * Created by kotenok on 03/03/2017.
 */

@RunWith(Parameterized.class)
public class OpenAllTabsInAdminTest extends MultiBrowserBaseTest {


    public OpenAllTabsInAdminTest(String browser) {
        super(browser);
    }
    @Parameterized.Parameters
    public static Collection<Object> data() {
        return Arrays.asList(new Object[]{
                "chrome"
        });
    }

    @Test
    public void clickAllTabsAndSubtabs() {
        driver.get(adminUrl);
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("not-so-secret-password");
        driver.findElement(By.name("login")).click();
        wait.until(urlToBe(adminUrl));

        String currentUrl = adminUrl;
        int categoriesCount = driver.findElements(By.cssSelector("ul#box-apps-menu > li")).size();
        for (int i = 1; i <= categoriesCount; i++) {

            String cssSelector = "ul#box-apps-menu > li:nth-child(" + i + ")";
            driver.findElement(By.cssSelector(cssSelector)).click();
            assertUrlChanged(currentUrl);
            currentUrl = driver.getCurrentUrl();
            assertPageHeaderExists();

            for (int j = 1; j < getSubcategoriesQty(cssSelector) + 1; j++) {
                driver.findElement(By.cssSelector(cssSelector + " li:nth-child(" + j + ")")).click();
                if(j != 1) {
                    assertUrlChanged(currentUrl);
                    currentUrl = driver.getCurrentUrl();
                } else {
                    assertUrlNotChanged(currentUrl);
                }
                assertPageHeaderExists();
            }

        }


    }

    private int getSubcategoriesQty(String cssSelector) {
        return driver.findElements(By.cssSelector(cssSelector + " li")).size();
    }

    private void assertUrlNotChanged(String oldUrl) {
        assertTrue(driver.getCurrentUrl().equals(oldUrl));
    }

    private void assertUrlChanged(String oldUrl) {
        assertFalse(driver.getCurrentUrl().equals(oldUrl));
    }

    private void assertPageHeaderExists() {
        assertTrue(driver.findElements(By.cssSelector("td#content > h1")).size() > 0);
    }

    private String adminUrl = "http://localhost:8080/admin/";
}
