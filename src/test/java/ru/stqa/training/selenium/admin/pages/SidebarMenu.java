package ru.stqa.training.selenium.admin.pages;

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
public class SidebarMenu extends Page {
    public SidebarMenu(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "ul#box-apps-menu > li")
    private List<WebElement> categories;

    public List<WebElement> getCategories() {
        return categories;
    }

    public WebElement getLogotype() {
        return driver.findElement(By.cssSelector("div.logotype"));
    }

    public WebElement getCategory(int i) {
        return driver.findElement(By.cssSelector("ul#box-apps-menu > li:nth-child(" + i + ")"));
    }

    public WebElement getSubCategory(WebElement category, int i) {
        return category.findElement(By.cssSelector(" li:nth-child(" + i + ")"));
    }

    public List<WebElement> getSubcategories(WebElement category) {
        return category.findElements(By.cssSelector(" li"));
    }

    public List<WebElement> getPageHeaders() {
        return driver.findElements(By.cssSelector("td#content > h1"));
    }

    public WebElement getCategoryByName(String name) {
        String xpath = "//span[.='" + name + "']/..";
        return driver.findElement(By.xpath(xpath));
    }

}
