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
        clientApp.addRandomProductsToCart(3);
        assertTrue(clientApp.getQtyOfItemsInCart() == 3);

        clientApp.deleteAllProductsFromCart();
        assertTrue(clientApp.getQtyOfItemsInCart() == 0);
    }
}
