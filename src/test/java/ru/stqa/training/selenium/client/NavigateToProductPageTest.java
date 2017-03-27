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
        app.getMainPage().open();
        assertTrue(app.getMainPage().isOnThisPage());

        app.getMainPage().clickOnProductLink(0);
        assertTrue(app.getProductPage().isOnThisPage());
    }
}
