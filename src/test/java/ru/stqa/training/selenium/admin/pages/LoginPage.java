package ru.stqa.training.selenium.admin.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.stqa.training.selenium.Page;

import static org.openqa.selenium.support.ui.ExpectedConditions.urlToBe;


/**
 * @author Victoria Kadubina
 */
public class LoginPage extends Page {

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    @FindBy(name = "username")
    public WebElement usernameInput;

    @FindBy(name = "password")
    public WebElement passwordInput;

    @FindBy(name = "login")
    public WebElement loginButton;

    public void open() {
        driver.get(adminUrl);
    }

    public void clickLoginAndWait(){
        loginButton.click();
        wait.until(urlToBe(adminUrl));
    }

}
