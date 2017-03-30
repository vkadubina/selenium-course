package ru.stqa.training.selenium.admin.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.stqa.training.selenium.Page;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;

/**
 * @author Victoria Kadubina
 */
public class SidebarMenu extends Page {
    public SidebarMenu(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    @FindBy(css = "ul#box-apps-menu > li")
    private List<WebElement> categories;

    public List<WebElement> getCategories(){
        return categories;
    }

    public WebElement getLogotype(){
        return $("div.logotype");
    }

    public SelenideElement getCategory(int i){
        return $("ul#box-apps-menu > li:nth-child(" + i + ")");
    }

    public SelenideElement getSubCategory(SelenideElement category, int i){
        return category.find(" li:nth-child(" + i + ")");
    }

    public List<WebElement> getSubcategories(WebElement category){
        return category.findElements(By.cssSelector(" li"));
    }

}
