package ru.stqa.training.selenium.client.app;

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;
import org.openqa.selenium.WebDriver;
import ru.stqa.training.selenium.client.pages.*;

import java.util.Optional;
import java.util.Random;

/**
 * @author Victoria Kadubina
 */
public class ClientApplication {

    WebDriver driver;

    private MainPage mainPage;
    private ProductPage productPage;
    private CheckoutPage checkoutPage;
    private SiteMenu siteMenuPage;
    private UserRegistrationPage registrationPage;

    private static final Random RANDOM = new Random();

    public ClientApplication(WebDriver driver) {
        this.driver = driver;
        mainPage = new MainPage(driver);
        siteMenuPage = new SiteMenu(driver);
        productPage = new ProductPage(driver);
        checkoutPage = new CheckoutPage(driver);
        registrationPage = new UserRegistrationPage(driver);
    }

    public void addRandomProductsToCart(int amount) {
        for (int i = 0; i < amount; i++) {
            siteMenuPage.goToMainPage();
            mainPage.clickOnProductLink(Math.abs(RANDOM.nextInt()) % mainPage.getAllProductsLinksList().size());
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

    public SiteMenu getSiteMenuPage() {
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
        mainPage.getCartMenu().goToCheckoutPage();
        while(checkoutPage.isCartEmpty()){
            checkoutPage.deleteItem();
        }
    }

    public int getQtyOfItemsInCart() {

        siteMenuPage.goToMainPage();
        return mainPage.getCartMenu().getQtyOfItemsInCart();
    }

    public void registerUser() {
        registerUser(null, null, null);
    }

    public void registerUser(Person user) {
        registerUser(user, null, null);
    }

    public void registerUser(Person user, String country, String zone){
        if (user == null){
            user = Fairy.create().person();
        }
        registrationPage
                .inputFirstName(user.getFirstName())
                .inputLastName(user.getLastName())
                .inputAdress1(user.getAddress().getAddressLine1())
                .inputPostcode(user.getAddress().getPostalCode())
                .inputCity(user.getAddress().getCity())
                .selectCountry(Optional.ofNullable(country).orElse("United States"))
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
