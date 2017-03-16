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
    public void eachItemHasOneSticker(){
        driver.get(clientUrl);
        List<WebElement> productLists =  driver.findElements(By.cssSelector("ul.listing-wrapper.products"));
        for (WebElement listOfProducts : productLists) {
            for (int i = 1; i < listOfProducts.findElements(By.cssSelector("li.product")).size() + 1; i++) {

                List<WebElement> stickers = (listOfProducts.findElements(By.cssSelector("li.product:nth-child(" + i + ") div.sticker")));
                assertTrue(stickers.size() == 1);

                List<String> stickerClasses = Arrays.asList(stickers.get(0).getAttribute("class").split("\\s"));
                assertTrue(stickerClasses.size() == 2);
            }
        }
    }
}

