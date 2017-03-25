package ru.stqa.training.selenium.client.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.openqa.selenium.support.ui.ExpectedConditions.urlToBe;

/**
 * @author Victoria Kadubina
 */
public class SiteMenuPage extends Page{

    public SiteMenuPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    @FindBy(css = "nav#site-menu li.general-0 a")
    private WebElement homeButton;

    public void goToMainPage() {
        if (!driver.getCurrentUrl().equals(System.getProperty("app.client.url"))) {
            homeButton.click();
            wait.until(urlToBe(System.getProperty("app.client.url")));
        }
    }

}
