package ru.stqa.training.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlToBe;

/**
 * @author Victoria Kadubina
 */
public class WorkingWithCartTest extends MultiBrowserBaseTest {

    private static final String CLIENT_URL = "http://localhost:8080/en/";
    private static final Random RANDOM = new Random();

    public WorkingWithCartTest(String browser) {
        super(browser);
    }

    @Test
    public void workingWithCartTest() {

        driver.get(CLIENT_URL);
        addRandomProductsToCart(3);
        goToCheckoutPage();
        deleteAllProductsOneByOne();

    }

    private void addRandomProductsToCart(int amount) {
        for (int i = 0; i < amount; i++) {
            addRandomProductToCart();
        }
    }

    private void addRandomProductToCart() {
        goToMainPage();
        openRandomProductPage();

        int qtyOfProductsInCart = Integer.parseInt(driver.findElement(By.cssSelector("div#cart span.quantity")).getText().trim());
        WebElement buyNowForm = driver.findElement(By.cssSelector("form[name=buy_now_form]"));
        List<WebElement> sizeSelects = buyNowForm.findElements(By.cssSelector("select[name=options\\[Size\\]]"));
        if (sizeSelects.size() > 0) {
            Select select = new Select(sizeSelects.get(0));
            select.selectByIndex(1);
        }
        driver.findElement(By.cssSelector("button[name=add_cart_product]")).click();

        String expectedQty = Integer.toString(++qtyOfProductsInCart);
        wait.until(d -> d.findElement(By.cssSelector("div#cart span.quantity")).getText().trim().equals(expectedQty));
    }

    private void goToMainPage() {
        driver.findElement(By.cssSelector("nav#site-menu li.general-0 a")).click();
        wait.until(urlToBe(CLIENT_URL));
    }

    private void openRandomProductPage() {
        List<WebElement> allProductsList = driver.findElements(By.cssSelector("li.product a.link"));
        int productsCount = allProductsList.size();
        allProductsList.get(Math.abs(RANDOM.nextInt()) % productsCount).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[name=add_cart_product]")));
    }

    private void deleteAllProductsOneByOne() {
        WebElement productsTable = driver.findElement(By.cssSelector("div#order_confirmation-wrapper"));

        int qtyOfProductRowsInTable = productsTable.findElements(By.cssSelector("td.item")).size();
        List<WebElement> shortcuts = driver.findElements(By.cssSelector("div#box-checkout-cart ul.shortcuts"));

        while (shortcuts.size() > 0) {
            shortcuts.get(0).click();
            waitAndClickRemoveButton();
            wait.until(ExpectedConditions.stalenessOf(productsTable));
            int currentQty = driver.findElements(By.cssSelector("div#order_confirmation-wrapper td.item")).size();
            assertTrue(currentQty < qtyOfProductRowsInTable);

            qtyOfProductRowsInTable = currentQty;
            shortcuts = driver.findElements(By.cssSelector("div#box-checkout-cart ul.shortcuts"));
            productsTable = driver.findElement(By.cssSelector("div#order_confirmation-wrapper"));
        }

        waitAndClickRemoveButton();
        wait.until(ExpectedConditions.stalenessOf(productsTable));
        String noItemsInCartText = driver.findElement(By.cssSelector("div#checkout-cart-wrapper em")).getText();
        assertTrue(noItemsInCartText.equals("There are no items in your cart."));

    }

    private void waitAndClickRemoveButton() {
        WebElement removeProductButton = wait.until(d -> d.findElement(By.cssSelector("button[name=remove_cart_item]")));
        removeProductButton.click();
    }


    private void goToCheckoutPage() {
        driver.findElement(By.cssSelector("div#cart-wrapper a.link")).click();
        wait.until(urlToBe("http://localhost:8080/en/checkout"));
    }

}
