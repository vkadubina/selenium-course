package ru.stqa.training.selenium.client.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.stqa.training.selenium.Page;

import static org.openqa.selenium.support.ui.ExpectedConditions.urlContains;

/**
 * @author Victoria Kadubina
 */
public class CartMenu extends Page {

    public CartMenu(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    @FindBy(css = "div#cart-wrapper a.link")
    WebElement checkoutLink;

    @FindBy(css = "div#cart span.quantity")
    WebElement qtyOfProductsInCartLabel;

    public int getQtyOfItemsInCart(){
        return Integer.parseInt(qtyOfProductsInCartLabel.getText().trim());
    }

    public void goToCheckoutPage() {
        checkoutLink.click();
        wait.until(urlContains("checkout"));
    }
}
