package ru.stqa.training.selenium;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlToBe;

/**
 * @author Victoria Kadubina
 */
@RunWith(Parameterized.class)
public class EachItemHasOneStickerTest extends MultiBrowserBaseTest{


    public EachItemHasOneStickerTest(String browser) {
        super(browser);
    }

    @Test
    public void eachItemHasOneSticker(){
        driver.get(clientAppUrl);
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

    private String clientAppUrl = "http://localhost:8080";
}

