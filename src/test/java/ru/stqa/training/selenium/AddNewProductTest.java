package ru.stqa.training.selenium;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.File;

import static org.openqa.selenium.support.ui.ExpectedConditions.urlContains;

/**
 * @author Victoria Kadubina
 */
@RunWith(Parameterized.class)
public class AddNewProductTest extends MultiBrowserBaseTest{

    String adminUrl = "http://localhost:8080/admin/?app=catalog&doc=catalog";

    public AddNewProductTest(String browser) {
        super(browser);
    }

    @Test
    public void addNewProductTest(){
        loginInAdmin(driver,adminUrl);
        driver.findElement(By.cssSelector("div a.button:nth-child(2)")).click();
        wait.until(urlContains("doc=edit_product"));
        fillGeneralProductInfo();
    }

    private void fillGeneralProductInfo() {
        WebElement generalForm = driver.findElement(By.cssSelector("div#tab-general"));
        generalForm.findElement(By.cssSelector("[name=name\\[en\\]]")).sendKeys("Bayern Duck");
        generalForm.findElement(By.cssSelector("[name=code]")).sendKeys("BD001");
        generalForm.findElement(By.cssSelector("input[type=checkbox][name=categories\\[\\]][data-name='Rubber Ducks']")).click();
        generalForm.findElement(By.cssSelector("input[type=checkbox][name=product_groups\\[\\]][value='1-3']")).click();
        generalForm.findElement(By.cssSelector("input[name=quantity]")).sendKeys("50");


        String imgPath = "src/test/resources/rubber_duck_img.png";
        String absolutePath = new File(imgPath).getAbsolutePath();
        generalForm.findElement(By.cssSelector("input[type=file]")).sendKeys(absolutePath);

    }
}
