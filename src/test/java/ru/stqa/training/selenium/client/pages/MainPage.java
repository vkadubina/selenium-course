package ru.stqa.training.selenium.client.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * @author Victoria Kadubina
 */
public class MainPage extends Page {

    TradingPage tradingPage;

    @FindBy(css = "li.product a.link")
    private List<WebElement> allProductsList;

    public MainPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
        tradingPage = new TradingPage(driver);
    }

    public void open(){
        driver.get(System.getProperty("app.client.url"));
        wait.until(visibilityOfElementLocated(By.cssSelector("body")));
    }

    public void openProductPage(int i){
        if (i >= getProductCount() || i < 0) throw new IllegalArgumentException("unexpected product id");
        allProductsList.get(i).click();
        wait.until(visibilityOfElementLocated(By.cssSelector("button[name=add_cart_product]")));
    }

    public int getProductCount(){
        return allProductsList.size();
    }


    public TradingPage getTradingComponent() {
        return tradingPage;
    }
}
