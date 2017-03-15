package ru.stqa.training.selenium.client;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.stqa.training.selenium.MultiBrowserBaseTest;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlContains;

/**
 * @author Victoria Kadubina
 */
public class OpenProductPageTest extends MultiBrowserBaseTest {

    private static final Color RED = new Color(204, 0, 0);
    private static final Color GREY_ON_MAIN_PAGE = new Color(119, 119, 119);
    private static final Color GREY_ON_PROD_PAGE = new Color(102, 102, 102);

    private static final String TITLE_ON_MAIN_PAGE = "div.name";
    private static final String TITLE_ON_PROD_PAGE = "[itemprop=name]";


    public OpenProductPageTest(String browser) {
        super(browser);
    }

    @Test
    public void clickOnProductInCampaignsOpenCorrectPage() {
        driver.get(CLIENT_APP_URL);
        assertCampaignsContainsProducts();
        WebElement productOnMainPage = driver.findElement((By.cssSelector("div#box-campaigns ul.products li:first-child")));
        ArrayList<String> mainPageProductParameters = checkProductAndCollectParams(productOnMainPage, GREY_ON_MAIN_PAGE, TITLE_ON_MAIN_PAGE);

        productOnMainPage.findElement(By.cssSelector("a")).click();
        wait.until(urlContains("rubber-ducks"));

        WebElement productOnSelfPage = driver.findElement((By.cssSelector("div#box-product")));
        ArrayList<String> selfPageProductParameters = checkProductAndCollectParams(productOnSelfPage, GREY_ON_PROD_PAGE, TITLE_ON_PROD_PAGE);

        assertEquals("parameters on main and product pages should be equal",mainPageProductParameters, selfPageProductParameters);

    }

    private ArrayList<String> checkProductAndCollectParams(WebElement product, Color grey, String titleSelector) {
        ArrayList<String> parameters = new ArrayList<>();

        WebElement priceWrapper = product.findElement(By.cssSelector("div.price-wrapper"));

        WebElement regularPrice = priceWrapper.findElement(By.cssSelector(".regular-price"));
        WebElement campaignPrice = priceWrapper.findElement(By.cssSelector(".campaign-price"));

        assertEquals("regular price expected to be struck out","line-through",regularPrice.getCssValue("text-decoration"));
        assertEquals("campaign price expected to be strong","strong", campaignPrice.getTagName());

        Color regularPriceColor = getColor(regularPrice);
        String name = product.findElement(By.cssSelector(titleSelector)).getText();
        assertEquals("regular price expected to be grey", grey, regularPriceColor);

        Color campaignPriceColor = getColor(campaignPrice);
        assertEquals("campaign price expected to be red", RED, campaignPriceColor);

        assertRegularPriceSizeLessThanCampaign(regularPrice, campaignPrice);

        parameters.add(name);
        parameters.add(regularPrice.getText().replace("$", ""));
        parameters.add(campaignPrice.getText().replace("$", ""));

        return parameters;
    }

    private Color getColor(WebElement element) {
        Pattern pattern = Pattern.compile("\\d+");
        String color = element.getCssValue("color");

        Matcher matcher = pattern.matcher(color);

        List<Integer> colors = new ArrayList<>();

        while (matcher.find())
            colors.add(Integer.parseInt(matcher.group()));

        if (colors.size() < 3)
            throw new IllegalArgumentException("Illegal color style");

        return new Color(colors.get(0), colors.get(1), colors.get(2));
    }

    private void assertRegularPriceSizeLessThanCampaign(WebElement price1, WebElement price2) {
        double size1 = Double.parseDouble(price1.getCssValue("font-size").replaceAll("[a-zA-Z]", ""));
        double size2 = Double.parseDouble(price2.getCssValue("font-size").replaceAll("[a-zA-Z]", ""));
        assertTrue(size1 < size2);
    }

    private void assertCampaignsContainsProducts() {
        int campaignsProductsCount = driver.findElements(By.cssSelector("div#box-campaigns ul.products li")).size();
        assertTrue("Campaigns section expected to have products", campaignsProductsCount > 0);
    }

}


class Color {
    private int r;
    private int g;
    private int b;

    public Color(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Color color = (Color) o;

        if (r != color.r) return false;
        if (g != color.g) return false;
        return b == color.b;

    }

    @Override
    public int hashCode() {
        int result = r;
        result = 31 * result + g;
        result = 31 * result + b;
        return result;
    }

    @Override
    public String toString() {
        return "rgb(" + r + ", " + g + ", " + b + ")";
    }
}