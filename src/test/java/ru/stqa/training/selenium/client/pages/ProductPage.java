package ru.stqa.training.selenium.client.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Victoria Kadubina
 */
public class ProductPage extends Page {

    TradingPage tradingPage;

    public ProductPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
        tradingPage = new TradingPage(driver);
    }

    @FindBy(css = "button[name=add_cart_product]")
    WebElement addToCartButton;

    public void addSelectedProductToCart(){
        int qtyOfProductsInCart = tradingPage.getQtyOfItemsInCart();
        String expectedQty = Integer.toString(++qtyOfProductsInCart);
        addToCartButton.click();
        wait.until(d -> d.findElement(
                By.cssSelector("div#cart span.quantity")).getText().trim().equals(expectedQty));
    }

    public void selectSize(int i) {
        List<String> sizes = getSizes();
        if (sizes.size() > 0) {
            if (i < 0 || i > sizes.size()) throw new IllegalArgumentException("unexpected option id");
            Select select = new Select(driver.findElement(
                    By.cssSelector("form[name=buy_now_form] select[name=options\\[Size\\]]")));
            select.selectByValue(sizes.get(i));
        }
    }

    public List<String> getSizes(){
        WebElement buyNowForm = driver.findElement(By.cssSelector("form[name=buy_now_form]"));
        List<WebElement> sizeSelectOptions = buyNowForm.findElements(By.cssSelector("select[name=options\\[Size\\]] > option"));
        List<String> sizes = sizeSelectOptions.stream()
                .map(wl -> wl.getAttribute("value"))
                .filter(optionValue -> !optionValue.isEmpty())
                .collect(Collectors.toList());
        return sizes;
    }
}
