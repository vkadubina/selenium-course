package ru.stqa.training.selenium.client.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.stqa.training.selenium.Page;

import java.util.List;

/**
 * @author Victoria Kadubina
 */
public class CheckoutPage extends Page {

    public CheckoutPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "div#order_confirmation-wrapper td.item")
    List<WebElement> productsInTable;

    @FindBy(name = "cart_form")
    WebElement cartForm;

    public void deleteItem() {
        if (productsInTable.size() > 0) {
            int qtyOfProductRowsInTable = productsInTable.size();
            cartForm.click();
            WebElement removeProductButton = wait.until(d -> d.findElement(By.cssSelector("button[name=remove_cart_item]")));
            removeProductButton.click();
            wait.until(d -> d.findElements(
                    (By.cssSelector("div#order_confirmation-wrapper td.item"))).size() < qtyOfProductRowsInTable);
        }
    }

    public boolean isCartEmpty() {
        List<WebElement> webElements = driver.findElements(By.cssSelector("div#checkout-cart-wrapper em"));
        if (webElements.isEmpty()) return true;
        String label = webElements.get(0).getText();
        return !label.equals("There are no items in your cart.");
    }
}

