package ru.stqa.training.selenium.client;

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import ru.stqa.training.selenium.MultiBrowserBaseTest;
import ru.stqa.training.selenium.SeleniumBrowser;

import static junit.framework.TestCase.assertTrue;

/**
 * @author Victoria Kadubina
 */
public class NewUserRegistrationTest extends MultiBrowserBaseTest {

    private static final Fairy FAIRY = Fairy.create();
    private Person person;

    @Before
    public void init(){
        super.init();
        person = FAIRY.person();
    }

    public NewUserRegistrationTest(SeleniumBrowser browser) {
        super(browser);
    }

    @Test
    public void newUserRegistrationAndLoginTest(){
        clientApp.getMainPage().open();
        userRegistration();
        logout();
        login();
        logout();
    }

    private void userRegistration(){
        clientApp.getMainPage().getNavigationPage().clickNewUserRegistrationLink();
        clientApp.registerUser(person, "United States", "Arizona");
        WebElement notice = clientApp.getMainPage().getNotice();
        assertTrue(notice.getText().equals("Your customer account has been created."));
    }

    private void logout(){
        clientApp.logout();
        WebElement notice = clientApp.getMainPage().getNotice();
        assertTrue(notice.getAttribute("class").contains("success"));
        assertTrue(notice.getText().equals("You are now logged out."));
    }

    private void login(){
        clientApp.loginAs(person.getEmail(),person.getPassword());
        WebElement notice = clientApp.getMainPage().getNotice();
        Assert.assertTrue(notice.getAttribute("class").contains("success"));
        Assert.assertTrue(notice.getText().startsWith("You are now logged in as"));
    }
}
