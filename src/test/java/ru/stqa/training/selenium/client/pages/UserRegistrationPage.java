package ru.stqa.training.selenium.client.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * @author Victoria Kadubina
 */
public class UserRegistrationPage extends Page{

    public UserRegistrationPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(name = "firstname")
    private WebElement firstnameInput;

    public UserRegistrationPage inputFirstName(String name){
        firstnameInput.sendKeys(name);
        return this;
    }

    @FindBy(name = "lastname")
    private WebElement lastnameInput;

    public UserRegistrationPage inputLastName(String name){
        lastnameInput.sendKeys(name);
        return this;
    }

    @FindBy(name = "address1")
    private WebElement adress1Input;

    public UserRegistrationPage inputAdress1(String adress){
        adress1Input.sendKeys(adress);
        return this;
    }

    @FindBy(name = "postcode")
    private WebElement postcodeInput;

    public UserRegistrationPage inputPostcode(String postcode){
        postcodeInput.sendKeys(postcode);
        return this;
    }

    @FindBy(name = "city")
    private WebElement cityInput;

    public UserRegistrationPage inputCity(String city){
        cityInput.sendKeys(city);
        return this;
    }

    @FindBy(name = "email")
    private WebElement emailInput;

    public UserRegistrationPage inputEmail(String email){
        emailInput.sendKeys(email);
        return this;
    }

    @FindBy(name = "phone")
    private WebElement phoneInput;

    public UserRegistrationPage inputPhone(String phone){
        phoneInput.sendKeys(phone);
        return this;
    }

    @FindBy(name = "password")
    private WebElement passwordInput;

    public UserRegistrationPage inputPassword(String password){
        passwordInput.sendKeys(password);
        return this;
    }

    @FindBy(name = "confirmed_password")
    private WebElement confirmedPasswordInput;

    public UserRegistrationPage inputConfirmedPassword(String confirmedPassword){
        confirmedPasswordInput.sendKeys(confirmedPassword);
        return this;
    }

    @FindBy(name = "create_account")
    private WebElement createAccountButton;

    public void clickCreateAccountButton(){
        createAccountButton.click();
        wait.until(visibilityOfElementLocated(By.cssSelector("div#notices-wrapper div.notice.success")));
    }


    public UserRegistrationPage selectCountry(String country){
        Select selectCountry = new Select(driver.findElement(By.cssSelector("select[name=country_code]")));
        selectCountry.selectByVisibleText(country);
        return this;
    }

    public UserRegistrationPage selectZone(String zone){
        if (zone != null){
        Select selectZone = new Select(driver.findElement(By.cssSelector("select[name=zone_code]")));
        selectZone.deselectByVisibleText(zone);
        }
        return this;
    }

    public boolean isOnThisPage(){
        return driver.getCurrentUrl().contains("create_account");
    }

    public void open(){
        driver.get(clientUrl + "create_account");
    }
}
