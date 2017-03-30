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
        }

        @Test
        public void loginWithWrongPassword() {
            try {
                adminApp.login("admin", "wrong-password");
                Assert.fail();
            } catch (TimeoutException e){
                e.printStackTrace();
            }
        }
    }