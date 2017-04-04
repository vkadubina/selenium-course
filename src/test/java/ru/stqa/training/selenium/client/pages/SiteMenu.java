package ru.stqa.training.selenium.client.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.stqa.training.selenium.Page;

/**
 * @author Victoria Kadubina
 */
public class SiteMenu extends Page {

    MainPage mainPage;

    public SiteMenu(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        mainPage = new MainPage(driver);
    }

    @FindBy(css = "nav#site-menu li.general-0 a")
    private WebElement homeButton;

    public void goToMainPage() {
        if (!mainPage.isOnThisPage()) {
            homeButton.click();
            wait.until(d -> d.findElement(By.id("slider-wrapper")));
        }
    }

}
