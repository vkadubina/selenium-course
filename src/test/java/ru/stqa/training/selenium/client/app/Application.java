package ru.stqa.training.selenium.client.app;

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;
import org.openqa.selenium.WebDriver;
import ru.stqa.training.selenium.client.pages.*;

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
    private UserRegistrationPage registrationPage;

    private static final Random RANDOM = new Random();

    public Application(WebDriver driver) {
        this.driver = driver;
        mainPage = new MainPage(driver);
        siteMenuPage = new SiteMenuPage(driver);
        productPage = new ProductPage(driver);
        checkoutPage = new CheckoutPage(driver);
        registrationPage = new UserRegistrationPage(driver);
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

    public UserRegistrationPage getRegistrationPage() {
        return registrationPage;
    }

    private void addProductToCart() {

        int size = productPage.getSizes().size();
        if (size > 0)
            productPage.selectSize(0);
        productPage.addSelectedProductToCart();
    }

    public void deleteAllProductsFromCart() {

        siteMenuPage.goToMainPage();
        mainPage.getTradingPage().goToCheckoutPage();
        while(checkoutPage.isCartEmpty()){
            checkoutPage.deleteItem();
        }
    }

    public int getQtyOfItemsInCart() {

        siteMenuPage.goToMainPage();
        return mainPage.getTradingPage().getQtyOfItemsInCart();
    }

    public void registerUser() {
        registerUser(null, null, null);
    }

    public void registerUser(Person user) {
        registerUser(user, null, null);
    }

    public void registerUser(Person user, String country, String zone){
        if (country == null)
            country = "United States";
        if (user == null){
            user = Fairy.create().person();
        }
        registrationPage
                .inputFirstName(user.getFirstName())
                .inputLastName(user.getLastName())
                .inputAdress1(user.getAddress().getAddressLine1())
                .inputPostcode(user.getAddress().getPostalCode())
                .inputCity(user.getAddress().getCity())
                .selectCountry(country)
                .selectZone(zone)
                .inputEmail(user.getEmail())
                .inputPhone(user.getTelephoneNumber())
                .inputPassword(user.getPassword())
                .inputConfirmedPassword(user.getPassword())
                .clickCreateAccountButton();
    }

    public void logout() {
        siteMenuPage.goToMainPage();
        mainPage.getNavigationPage().clockLogoutLink();
    }

    public void loginAs(String email, String password){
        mainPage.getNavigationPage()
                .inputEmail(email)
                .inputPassword(password)
                .clickLoginLink();
    }
}
