package ru.stqa.training.selenium;

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import java.util.Random;

import static org.openqa.selenium.support.ui.ExpectedConditions.urlToBe;

/**
 * @author Victoria Kadubina
 */
@RunWith(Parameterized.class)
public class NewUserRegistrationTest extends MultiBrowserBaseTest{

    private static final Fairy FAIRY = Fairy.create();


    public NewUserRegistrationTest(String browser) {
        super(browser);
    }

    @Test
    public void newUserRegistrationAndLoginTest(){
        driver.get(url);
        registration();

    }

    private void registration() {
        driver.findElement(By.cssSelector("[name=login_form] a")).click();
        wait.until(urlToBe("http://localhost:8080/en/create_account"));
        Person person = FAIRY.person();


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
        Random random = new Random();
        selectZone.selectByIndex(Math.abs(random.nextInt()) % zonesCount);

        driver.findElement(By.cssSelector("input[name=email]")).sendKeys(person.getEmail());
        driver.findElement(By.cssSelector("input[name=phone]")).sendKeys(person.getTelephoneNumber());
        driver.findElement(By.cssSelector("input[name=password]")).sendKeys(person.getPassword());
        driver.findElement(By.cssSelector("input[name=confirmed_password]")).sendKeys(person.getPassword());

        driver.findElement(By.cssSelector("button[name=create_account]")).click();

    }

    String url = "http://localhost:8080/";
}
