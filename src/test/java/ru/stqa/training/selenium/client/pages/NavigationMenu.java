package ru.stqa.training.selenium.client.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.stqa.training.selenium.Page;

import static org.openqa.selenium.support.ui.ExpectedConditions.urlToBe;

/**
 * @author Victoria Kadubina
 */
public class NavigationMenu extends Page {

    public NavigationMenu(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "[name=login_form] a")
    private WebElement newUserRegistrationLink;

    public void clickNewUserRegistrationLink() {
        newUserRegistrationLink.click();
        wait.until(urlToBe(clientUrl + "create_account"));
    }

    @FindBy(css = "div#box-account li:nth-child(4) a")
    private WebElement logoutLink;

    public void clockLogoutLink() {
        logoutLink.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#box-account-login")));
    }

    @FindBy(name = "login_form")
    private WebElement loginForm;

    public NavigationMenu inputEmail(String email) {
        loginForm.findElement(By.cssSelector("input[name=email]")).sendKeys(email);
        return this;
    }

    public NavigationMenu inputPassword(String password) {
        loginForm.findElement(By.cssSelector("input[name=password]")).sendKeys(password);
        return this;
    }

    public void clickLoginLink() {
        loginForm.findElement(By.cssSelector("button[name=login]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#box-account")));
    }

    public boolean isLoggedIn() {
        return driver.findElements(By.cssSelector("#box-account-login")).size() == 0;
    }

}
