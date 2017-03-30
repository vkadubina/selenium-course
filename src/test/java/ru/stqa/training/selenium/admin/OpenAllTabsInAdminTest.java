package ru.stqa.training.selenium.admin;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.stqa.training.selenium.MultiBrowserBaseTest;
import ru.stqa.training.selenium.SeleniumBrowser;

import static org.junit.Assert.assertTrue;

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
        adminApp.login();

        int categoriesCount = adminApp.getSidebarMenu().getCategories().size();

        for (int i = 1; i <= categoriesCount; i++) {

            //need to understand that the page is updated
            WebElement logo = adminApp.getSidebarMenu().getLogotype();

            SelenideElement category = adminApp.getSidebarMenu().getCategory(i);
            category.click();
            wait.until(ExpectedConditions.stalenessOf(logo));

            assertPageHeaderExists();

            category = adminApp.getSidebarMenu().getCategory(i);
            for (int j = 1; j < adminApp.getSidebarMenu().getSubcategories(category).size() + 1; j++) {

                //need to understand that the page is updated
                logo = adminApp.getSidebarMenu().getLogotype();

                adminApp.getSidebarMenu().getSubCategory(category,j).click();
                wait.until(ExpectedConditions.stalenessOf(logo));

                assertPageHeaderExists();
                category = adminApp.getSidebarMenu().getCategory(i);
            }
        }
    }

    private void assertPageHeaderExists() {
        assertTrue(driver.findElements(By.cssSelector("td#content > h1")).size() > 0);
    }

}
