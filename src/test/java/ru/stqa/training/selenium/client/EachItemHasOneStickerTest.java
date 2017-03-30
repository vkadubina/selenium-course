package ru.stqa.training.selenium.client;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.stqa.training.selenium.MultiBrowserBaseTest;
import ru.stqa.training.selenium.SeleniumBrowser;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

/**
 * @author Victoria Kadubina
 */
public class EachItemHasOneStickerTest extends MultiBrowserBaseTest {


    public EachItemHasOneStickerTest(SeleniumBrowser browser) {
        super(browser);
    }

    @Test
    public void eachItemHasOneSticker() {
        driver.get(clientUrl);
        List<WebElement> productGroups = clientApp.getMainPage().getAllProductGroups();
        for (WebElement listOfProducts : productGroups) {
            List<WebElement> products = clientApp.getMainPage().getAllProductsInGroup(listOfProducts);
            for (WebElement product : products) {
                List<WebElement> stickers = clientApp.getMainPage().getProductStickers(product);
                assertTrue("product expected to have only one sticker", stickers.size() == 1);
            }
        }
    }
}

