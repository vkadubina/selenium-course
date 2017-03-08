package ru.stqa.training.selenium;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static junit.framework.TestCase.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlContains;

/**
 * Created by vkadubina on 3/8/2017.
 */
@RunWith(Parameterized.class)
public class OpenCorrectProductPageTest extends MultiBrowserBaseTest{

    public OpenCorrectProductPageTest(String browser) {
        super(browser);
    }

    @Test
    public void clickOnProductInCampaignsOpenCorrectPage(){
        driver.get(url);
        assertCampaginsContainsProducts();
        WebElement productOnMainPage = driver.findElement((By.cssSelector("div#box-campaigns ul.products li:first-child")));
        ArrayList<String> mainPageProductParameters = getProductParameters(productOnMainPage, true);


        productOnMainPage.findElement(By.cssSelector("a")).click();
        wait.until(urlContains("rubber-ducks"));

        WebElement productOnSelfPage = driver.findElement((By.cssSelector("div#box-product")));
        ArrayList<String> selfPageProductParameters = getProductParameters(productOnSelfPage,false);

        assertTrue(mainPageProductParameters.equals(selfPageProductParameters));

    }

    private ArrayList<String> getProductParameters(WebElement product, boolean isMainPage) {
        ArrayList<String> parameters = new ArrayList<>();
        String name;

        if (isMainPage) {
            name = product.findElement(By.cssSelector("div.name")).getText();}
        else {
            name = product.findElement(By.cssSelector("[itemprop=name]")).getText();
        }

        WebElement regularPrice = product.findElement(By.cssSelector("div.price-wrapper s.regular-price"));
        WebElement campaignPrice = product.findElement(By.cssSelector("div.price-wrapper strong.campaign-price"));

        assertTrue(regularPrice.getCssValue("text-decoration").equals("line-through"));
        assertTrue(campaignPrice.getCssValue("font-weight").equals("bold"));

        System.out.println("regularPriceColor = " + regularPrice.getCssValue("color"));
        System.out.println("campaignPriceColor = " + campaignPrice.getCssValue("color"));

        assertRegularPriceSizeLessThanCampaign(regularPrice,campaignPrice);

        parameters.add(name);
        parameters.add(regularPrice.getText().replace("$",""));
        parameters.add(campaignPrice.getText().replace("$",""));

        return parameters;
    }

    private void assertRegularPriceSizeLessThanCampaign(WebElement price1, WebElement price2) {
        double size1 = Double.parseDouble(price1.getCssValue("font-size").replaceAll("[a-zA-Z]", ""));
        double size2 = Double.parseDouble(price2.getCssValue("font-size").replaceAll("[a-zA-Z]", ""));
        assertTrue(size1 < size2);
    }

    private void assertCampaginsContainsProducts(){
        int campaginsProductsCount = driver.findElements(By.cssSelector("div#box-campaigns ul.products li")).size();
        assertTrue(campaginsProductsCount > 0);
    }

    //String url="http://localhost:8080/";
    String url="http://192.168.99.100:8080/";
}
