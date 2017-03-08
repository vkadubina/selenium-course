package ru.stqa.training.selenium;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;

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
    public void clickOnProductInCampaginsOpenCorrectPage(){
        driver.get(url);
        assertCampaginsContainsProducts();
        WebElement productOnMainPage = driver.findElement((By.cssSelector("div#box-campaigns ul.products li:first-child")));
        ArrayList<String> mainPageProductParameters = getProductParameters(productOnMainPage);


        productOnMainPage.findElement(By.cssSelector("a")).click();
        wait.until(urlContains("rubber-ducks"));
        WebElement productOnSelfPage = driver.findElement((By.cssSelector("div#box-product")));
        ArrayList<String> selfPageProductParameters = getProductParameters(productOnSelfPage);



    }

    private ArrayList<String> getProductParameters(WebElement productOnMainPage) {
        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(productOnMainPage.findElement(By.cssSelector("div.name")).getText());
        parameters.add(productOnMainPage.findElement(By.cssSelector("div.price-wrapper s.regular-price")).getText().replace("$",""));
        parameters.add(productOnMainPage.findElement(By.cssSelector("div.price-wrapper strong.campaign-price")).getText().replace("$",""));


    }

    private void assertCampaginsContainsProducts(){
        int campaginsProductsCount = driver.findElements(By.cssSelector("div#box-campaigns ul.products li")).size();
        assertTrue(campaginsProductsCount > 0);
    }

    String url="http://localhost:8080/";
}
