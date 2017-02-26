package ru.stqa.training.selenium;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * Created by kotenok on 26/02/2017.
 */
@RunWith(Parameterized.class)
public class MultiBrowserLoginInAdminTest extends MultiBrowserBaseTest {

    /*@Parameterized.Parameters
    public static Collection<Object> localData() {
        List<String> colors = Arrays.asList("red", "green", "blue");
        return data().stream().flatMap(
                browser -> colors.stream().map(color -> new Object[] {browser, color})
        ).collect(Collectors.toList());
    }*/

    /*@Parameterized.Parameters
    public static Collection<Object> data() {
        return Arrays.asList(new Object[] {
                "firefox45"
        });
    }*/

    public MultiBrowserLoginInAdminTest(String browser) {
        super(browser);
    }

    @Test
    public void loginWithCorrectPassword() {
        driver.get(adminUrl);
        assertTrue(driver.getCurrentUrl().contains("login.php"));
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("not-so-secret-password");
        driver.findElement(By.name("login")).click();
        wait.until(urlToBe(adminUrl));
        assertTrue(driver.getCurrentUrl().equals(adminUrl));
    }

    @Test
    public void loginWithWrongPassword() {
        driver.get(adminUrl);
        assertTrue(driver.getCurrentUrl().contains("login.php"));
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("wrong-password");
        driver.findElement(By.name("login")).click();
        wait.until(visibilityOfElementLocated(By.className("errors")));
        assertTrue(driver.getCurrentUrl().contains("login.php"));
    }

    private String adminUrl = "http://localhost:8080/admin/";
}
