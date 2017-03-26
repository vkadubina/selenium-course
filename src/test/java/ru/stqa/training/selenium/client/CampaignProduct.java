package ru.stqa.training.selenium.client;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @author Victoria Kadubina
 */
public class CampaignProduct {
    Price regularPrice;
    Price campaignPrice;
    String title;
    WebElement link;

    public CampaignProduct(WebElement product, String titleSelector) {

        WebElement priceWrapper = product.findElement(By.cssSelector("div.price-wrapper"));
        regularPrice = new Price(priceWrapper.findElement(By.cssSelector(".regular-price")));
        campaignPrice = new Price(priceWrapper.findElement(By.cssSelector(".campaign-price")));
        title = product.findElement(By.cssSelector(titleSelector)).getText();
        link = product.findElement(By.cssSelector("a"));
    }

    public Price getRegularPrice() {
        return regularPrice;
    }

    public Price getCampaignPrice() {
        return campaignPrice;
    }

    public String getTitle() {
        return title;
    }

    public WebElement getLink() {
        return link;
    }
}
