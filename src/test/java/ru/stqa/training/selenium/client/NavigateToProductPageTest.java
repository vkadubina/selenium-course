package ru.stqa.training.selenium.client;

import org.junit.Test;
import ru.stqa.training.selenium.MultiBrowserBaseTest;
import ru.stqa.training.selenium.SeleniumBrowser;

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
        clientApp.getMainPage().open();
        assertTrue(clientApp.getMainPage().isOnThisPage());

        clientApp.getMainPage().clickOnProductLink(0);
        assertTrue(clientApp.getProductPage().isOnThisPage());
    }
}
