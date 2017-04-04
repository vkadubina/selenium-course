package ru.stqa.training.selenium.admin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
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

            //need to wait until page is updated
            WebElement oldLogo = adminApp.getSidebarMenu().getLogotype();

            adminApp.getSidebarMenu().getCategory(i).click();
            assertPageHeaderExists(oldLogo);

            WebElement category = adminApp.getSidebarMenu().getCategory(i);
            for (int j = 1; j < (adminApp.getSidebarMenu().getSubcategories(category).size() + 1); j++) {

                //need to wait until page is updated
                oldLogo = adminApp.getSidebarMenu().getLogotype();

                adminApp.getSidebarMenu().getSubCategory(category, j).click();
                assertPageHeaderExists(oldLogo);

                category = adminApp.getSidebarMenu().getCategory(i);
            }
        }
    }

    private void assertPageHeaderExists(WebElement oldLogo) {
        wait.until(ExpectedConditions.stalenessOf(oldLogo));
        assertTrue(adminApp.getSidebarMenu().getPageHeaders().size() > 0);
    }
}
