package ru.stqa.training.selenium;

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.Random;

import static junit.framework.TestCase.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlToBe;

/**
 * @author Victoria Kadubina
 */
public class NewUserRegistrationTest extends MultiBrowserBaseTest{

    private static final Fairy FAIRY = Fairy.create();
    private static final Random RANDOM = new Random();
    private Person person;
    private String url = "http://localhost:8080/";


    @Before
    public void init(){
        super.init();
        person = FAIRY.person();
    }

    public NewUserRegistrationTest(String browser) {
        super(browser);
    }

    @Test
    public void newUserRegistrationAndLoginTest(){
        driver.get(url);
        registration();
        logout();
        login();
        logout();

    }

    private void login() {

        WebElement loginForm = driver.findElement(By.cssSelector("[name=login_form]"));
        loginForm.findElement(By.cssSelector("input[name=email]")).sendKeys(person.getEmail());
        loginForm.findElement(By.cssSelector("input[name=password]")).sendKeys(person.getPassword());
        loginForm.findElement(By.cssSelector("button[name=login]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#box-account")));
        WebElement notice = driver.findElement(By.cssSelector("div#notices-wrapper div.notice.success"));

        assertTrue(notice.getText().startsWith("You are now logged in as"));

    }

    private void logout() {

        driver.findElement(By.cssSelector("div#box-account li:nth-child(4) a")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#box-account-login")));
        WebElement notice = driver.findElement(By.cssSelector("div#notices-wrapper div.notice.success"));

        assertTrue(notice.getText().equals("You are now logged out."));

    }

    private void registration() {
        driver.findElement(By.cssSelector("[name=login_form] a")).click();
        wait.until(urlToBe("http://localhost:8080/en/create_account"));

        driver.findElement(By.cssSelector("input[name=firstname]")).sendKeys(person.getFirstName());
        driver.findElement(By.cssSelector("input[name=lastname]")).sendKeys(person.getLastName());
        driver.findElement(By.cssSelector("input[name=address1]")).sendKeys(person.getAddress().getAddressLine1());
        driver.findElement(By.cssSelector("input[name=postcode]")).sendKeys(person.getAddress().getPostalCode());
        driver.findElement(By.cssSelector("input[name=city]")).sendKeys(person.getAddress().getCity());

        Select selectCountry = new Select(driver.findElement(By.cssSelector("select[name=country_code]")));
        selectCountry.selectByVisibleText("United States");

        Select selectZone = new Select(driver.findElement(By.cssSelector("select[name=zone_code]")));
        int zonesCount = selectZone.getOptions().size();
        // FF Nightly hack
        if(zonesCount == 0)
            zonesCount = 1;

        selectZone.selectByIndex(Math.abs(RANDOM.nextInt()) % zonesCount);

        driver.findElement(By.cssSelector("input[name=email]")).sendKeys(person.getEmail());
        driver.findElement(By.cssSelector("input[name=phone]")).sendKeys(person.getTelephoneNumber());
        driver.findElement(By.cssSelector("input[name=password]")).sendKeys(person.getPassword());
        driver.findElement(By.cssSelector("input[name=confirmed_password]")).sendKeys(person.getPassword());

        driver.findElement(By.cssSelector("button[name=create_account]")).click();

        WebElement notice = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#notices-wrapper div.notice.success")));
        assertTrue(notice.getText().equals("Your customer account has been created."));

    }
}
