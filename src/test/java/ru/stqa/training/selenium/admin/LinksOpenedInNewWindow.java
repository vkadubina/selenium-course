package ru.stqa.training.selenium.admin;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import ru.stqa.training.selenium.MultiBrowserBaseTest;

import java.util.List;
import java.util.Set;

import static org.openqa.selenium.support.ui.ExpectedConditions.urlContains;
import static ru.stqa.training.selenium.admin.AdminPageHelper.loginInAdmin;
import static ru.stqa.training.selenium.admin.AdminPageHelper.openAdminSection;

/**
 * @author Victoria Kadubina
 */
public class LinksOpenedInNewWindow extends MultiBrowserBaseTest{

    public LinksOpenedInNewWindow(String browser) {
        super(browser);
    }

    @Test
    public void testLinksAreOpenedInNewWindow(){
        loginInAdmin(driver);
        openAdminSection(driver, wait, "Countries");
        clickAddNewCountryButton();
        openAllExternalLinks(findAllExternalLinks());

    }

    private void openAllExternalLinks(List<WebElement> allExternalLinks) {
        String mainWindow = driver.getWindowHandle();
        Set<String> oldWindows = driver.getWindowHandles();
        for (WebElement link: allExternalLinks){
            link.click();
            String newWindow = wait.until(d -> thereIsWindowOtherThan(oldWindows));

            driver.switchTo().window(newWindow);
            driver.close();
            driver.switchTo().window(mainWindow);

        }

    }

    private String thereIsWindowOtherThan(final Set<String> oldWindows) {

        Set<String> newWindows = driver.getWindowHandles();
        newWindows.removeAll(oldWindows);

        return newWindows.stream().findFirst().orElse(null);

    }

    private List<WebElement> findAllExternalLinks() {
        return driver.findElements(By.xpath("//*[contains(@class,'fa-external-link')]/.."));
    }

    private void clickAddNewCountryButton() {
        driver.findElement(By.xpath("//a[normalize-space(text())='Add New Country']")).click();
        wait.until(urlContains("edit_country"));
    }
}