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
    NavigationPage navigationPage;

    public MainPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
        tradingPage = new TradingPage(driver);
        navigationPage = new NavigationPage(driver);
    }

    @FindBy(css = "li.product a.link")
    private List<WebElement> allProductsLinksList;

    @FindBy(css = "ul.listing-wrapper.products")
    private List<WebElement> productGroups;

    @FindBy(css = "div#notices-wrapper div.notice")
    WebElement notice;

    public WebElement getNotice() {
        return notice;
    }

    public void clickOnProductLink(int i){
        if (i >= getProductCount() || i < 0) throw new IllegalArgumentException("unexpected product id");
        getAllProductsLinksList().get(i).click();
        wait.until(visibilityOfElementLocated(By.cssSelector("button[name=add_cart_product]")));
    }

    public int getProductCount(){
        return getAllProductsLinksList().size();
    }


    public TradingPage getTradingPage() {
        return tradingPage;
    }

    public NavigationPage getNavigationPage() {
        return navigationPage;
    }

    public void open(){
        driver.get(clientUrl);
    }

    public boolean isOnThisPage(){
        return driver.findElements(By.id("slider-wrapper")).size() > 0;
    }

    public List<WebElement> getAllProductsLinksList(){
        return allProductsLinksList;
    }

    public List<WebElement> getAllProductGroups(){
        return productGroups;
    }

    public List<WebElement> getAllProductsInGroup(WebElement group){
        return group.findElements(By.cssSelector("li.product"));
    }

    public List<WebElement> getProductStickers(WebElement product){
        return product.findElements(By.cssSelector("div.sticker"));
    }

    public List<WebElement> getCampaignProducts(){
        return driver.findElements(By.cssSelector("div#box-campaigns ul.products li"));
    }
}
