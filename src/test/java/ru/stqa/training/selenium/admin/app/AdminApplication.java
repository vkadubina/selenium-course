package ru.stqa.training.selenium.admin.app;

import org.openqa.selenium.WebDriver;
import ru.stqa.training.selenium.admin.pages.LoginPage;
import ru.stqa.training.selenium.admin.pages.SidebarMenu;

import java.util.Optional;

import static org.openqa.selenium.support.ui.ExpectedConditions.urlToBe;

/**
 * @author Victoria Kadubina
 */
public class AdminApplication {

    WebDriver driver;
    LoginPage loginPage;
    SidebarMenu sidebarMenu;

    public AdminApplication(WebDriver driver) {
        this.driver = driver;
        loginPage = new LoginPage(driver);
        sidebarMenu = new SidebarMenu(driver);
    }

    public void login(){
        login(null,null);
    }

    public void login(String login, String password) {
        loginPage.open();
        loginPage.usernameInput.clear();
        loginPage.usernameInput.sendKeys(Optional.ofNullable(login).orElse("admin"));
        loginPage.passwordInput.sendKeys(Optional.ofNullable(password).orElse("not-so-secret-password"));
        loginPage.clickLoginAndWait();
    }

    public LoginPage getLoginPage() {
        return loginPage;
    }

    public SidebarMenu getSidebarMenu() {
        return sidebarMenu;
    }
}
