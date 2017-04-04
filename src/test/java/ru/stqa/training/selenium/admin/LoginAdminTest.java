package ru.stqa.training.selenium.admin;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.stqa.training.selenium.MultiBrowserBaseTest;
import ru.stqa.training.selenium.SeleniumBrowser;

import static junit.framework.Assert.fail;
import static junit.framework.TestCase.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * @author Victoria Kadubina
 */
public class LoginAdminTest extends MultiBrowserBaseTest {

    public LoginAdminTest(SeleniumBrowser browser) {
        super(browser);
    }

    @Test
    public void loginWithCorrectPassword() {
        adminApp.login();
        assertTrue(adminApp.getSidebarMenu().getCategories().size() > 0);
    }

    @Test(expected = TimeoutException.class)
    public void loginWithWrongPassword() {
        adminApp.login("admin", "wrong-password");
    }
}