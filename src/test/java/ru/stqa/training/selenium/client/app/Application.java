package ru.stqa.training.selenium.client.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.training.selenium.client.pages.CheckoutPage;
import ru.stqa.training.selenium.client.pages.MainPage;
import ru.stqa.training.selenium.client.pages.ProductPage;
import ru.stqa.training.selenium.client.pages.SiteMenuPage;

import java.util.List;
import java.util.Random;

/**
 * @author Victoria Kadubina
 */
public class Application {

    WebDriver driver;

    private MainPage mainPage;
    private ProductPage productPage;
    private CheckoutPage checkoutPage;
    private SiteMenuPage siteMenuPage;

    private static final Random RANDOM = new Random();


    public Application(WebDriver driver) {
        this.driver = driver;
        mainPage = new MainPage(driver);
        siteMenuPage = new SiteMenuPage(driver);
        productPage = new ProductPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    public void addRandomProductsToCart(int amount) {
        for (int i = 0; i < amount; i++) {
            siteMenuPage.goToMainPage();
            mainPage.clickOnProductLink(Math.abs(RANDOM.nextInt()) % mainPage.getProductCount());
            addProductToCart();
        }
    }

    public MainPage getMainPage() {
        return mainPage;
    }

    public ProductPage getProductPage() {
        return productPage;
    }

    public CheckoutPage getCheckoutPage() {
        return checkoutPage;
    }

    public SiteMenuPage getSiteMenuPage() {
        return siteMenuPage;
    }

    private void addProductToCart() {

        int size = productPage.getSizes().size();
        if (size > 0)
            productPage.selectSize(0);
        productPage.addSelectedProductToCart();
    }

    public void deleteAllProductsFromCart() {

        siteMenuPage.goToMainPage();
        mainPage.getTradingComponent().goToCheckoutPage();
        while(checkoutPage.isCartEmpty()){
            checkoutPage.deleteItem();
        }
    }

    public int getQtyOfItemsInCart() {

        siteMenuPage.goToMainPage();
        return mainPage.getTradingComponent().getQtyOfItemsInCart();
    }
}
