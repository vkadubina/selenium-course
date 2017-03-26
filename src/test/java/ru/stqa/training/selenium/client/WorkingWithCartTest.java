package ru.stqa.training.selenium.client;

import org.junit.Test;
import ru.stqa.training.selenium.MultiBrowserBaseTest;
import ru.stqa.training.selenium.SeleniumBrowser;

import static org.junit.Assert.assertTrue;

/**
 * @author Victoria Kadubina
 */
public class WorkingWithCartTest extends MultiBrowserBaseTest {


    public WorkingWithCartTest(SeleniumBrowser browser) {
        super(browser);
    }

    @Test
    public void workingWithCartTest() {
        driver.get(clientUrl);
        app.addRandomProductsToCart(3);
        assertTrue(app.getQtyOfItemsInCart() == 3);

        app.deleteAllProductsFromCart();
        assertTrue(app.getQtyOfItemsInCart() == 0);
    }
}
