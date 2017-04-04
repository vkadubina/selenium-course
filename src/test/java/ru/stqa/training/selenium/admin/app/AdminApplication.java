package ru.stqa.training.selenium.admin.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.stqa.training.selenium.admin.pages.DataTableComponent;
import ru.stqa.training.selenium.admin.pages.LoginPage;
import ru.stqa.training.selenium.admin.pages.SidebarMenu;

import javax.annotation.Nonnull;

import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;

/**
 * @author Victoria Kadubina
 */
public class AdminApplication {

    WebDriver driver;
    WebDriverWait wait;
    LoginPage loginPage;
    SidebarMenu sidebarMenu;
    DataTableComponent dataTable;

    public AdminApplication(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        loginPage = new LoginPage(driver);
        sidebarMenu = new SidebarMenu(driver);
        dataTable = new DataTableComponent(driver);
    }

    public void login() {
        login("admin", "not-so-secret-password");
    }

    public void login(@Nonnull String login, @Nonnull String password) {
        loginPage.open();
        loginPage.usernameInput.clear();
        loginPage.usernameInput.sendKeys(login);
        loginPage.passwordInput.sendKeys(password);
        loginPage.clickLoginAndWait();
    }

    public LoginPage getLoginPage() {
        return loginPage;
    }

    public SidebarMenu getSidebarMenu() {
        return sidebarMenu;
    }

    public void openAdminSection(String sectionName) {
        WebElement oldLogo = sidebarMenu.getLogotype();
        sidebarMenu.getCategoryByName(sectionName).click();
        wait.until(stalenessOf(oldLogo));
    }

    public DataTableComponent getDataTable() {
        return dataTable;
    }

}
