package ru.stqa.training.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.util.List;

import static org.junit.Assert.fail;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlContains;

/**
 * @author Victoria Kadubina
 */
public class AddNewProductTest extends MultiBrowserBaseTest{

    private static final String ADMIN_URL = "http://localhost:8080/admin/?app=catalog&doc=catalog";

    public AddNewProductTest(String browser) {
        super(browser);
    }

    @Test
    public void addNewProductTest(){
        loginInAdmin(driver, ADMIN_URL);
        String productName = "Bayern Duck " + System.currentTimeMillis();

        driver.findElement(By.cssSelector("div a.button:nth-child(2)")).click();
        wait.until(urlContains("doc=edit_product"));
        fillGeneralProductInfo(productName);
        switchToTab("a[href=\\#tab-information]");
        fillProductInformation();
        switchToTab("a[href=\\#tab-prices]");
        fillProductPrices();
        driver.findElement(By.cssSelector("button[name=save]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.notice.success")));
        List<WebElement> productList = driver.findElements(By.cssSelector("table.dataTable tr.row"));
        for (WebElement product : productList) {
            String nameInRow = product.findElement(By.cssSelector("a")).getText();
            if (productName.equals(nameInRow)) {
                return;
            }
        }
        fail("product not found");
    }

    private void fillProductPrices() {

        Select currencyCode = new Select(driver.findElement(By.cssSelector("select[name=purchase_price_currency_code]")));
        currencyCode.selectByValue("USD");

        driver.findElement(By.cssSelector("input[name=purchase_price")).clear();
        driver.findElement(By.cssSelector("input[name=purchase_price")).sendKeys("33");
        driver.findElement(By.cssSelector("input[name=prices\\[USD\\]")).sendKeys("35");

    }

    private void fillProductInformation() {

        Select manufactureId = new Select(driver.findElement(By.cssSelector("select[name=manufacturer_id]")));
        manufactureId.selectByIndex(1);
        driver.findElement(By.cssSelector("input[name=keywords]")).sendKeys("Rubber Duck");
        driver.findElement(By.cssSelector("input[name=short_description\\[en\\]]")).sendKeys("Beautiful Rubber Duck from Germany");
        driver.findElement(By.cssSelector("div.trumbowyg-editor")).sendKeys(DUCK_DESCRIPTION);

    }

    private void switchToTab(String cssSelector) {
        driver.findElement(By.cssSelector(cssSelector)).click();
        wait.until((WebDriver d) -> driver.findElement(By.cssSelector("li.active > " + cssSelector)));
    }

    private void fillGeneralProductInfo(String name) {
        WebElement generalForm = driver.findElement(By.cssSelector("div#tab-general"));

        generalForm.findElement(By.cssSelector("input[type=radio][value='1']")).click();
        generalForm.findElement(By.cssSelector("[name=name\\[en\\]]")).sendKeys(name);
        generalForm.findElement(By.cssSelector("[name=code]")).sendKeys("BD001");
        generalForm.findElement(By.cssSelector("input[type=checkbox][name=categories\\[\\]][data-name='Root']")).click();
        generalForm.findElement(By.cssSelector("input[type=checkbox][name=categories\\[\\]][data-name='Rubber Ducks']")).click();
        generalForm.findElement(By.cssSelector("input[type=checkbox][name=product_groups\\[\\]][value='1-3']")).click();
        generalForm.findElement(By.cssSelector("input[name=quantity]")).clear();
        generalForm.findElement(By.cssSelector("input[name=quantity]")).sendKeys("50");

        Select soldOutStateSelect = new Select(generalForm.findElement(By.cssSelector("select[name=sold_out_status_id]")));
        soldOutStateSelect.selectByValue("2");


        String imgPath = "src/test/resources/rubber_duck_img.png";
        String absolutePath = new File(imgPath).getAbsolutePath();
        generalForm.findElement(By.cssSelector("input[type=file]")).sendKeys(absolutePath);

    }

    private static final String DUCK_DESCRIPTION = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
            "Suspendisse sollicitudin ante massa, eget ornare libero porta congue." +
            " Cras scelerisque dui non consequat sollicitudin. " +
            "Sed pretium tortor ac auctor molestie. Nulla facilisi." +
            " Maecenas pulvinar nibh vitae lectus vehicula semper. " +
            "Donec et aliquet velit. Curabitur non ullamcorper mauris. " +
            "In hac habitasse platea dictumst. Phasellus ut pretium justo, sit amet bibendum urna." +
            " Maecenas sit amet arcu pulvinar, facilisis quam at, viverra nisi." +
            " Morbi sit amet adipiscing ante. Integer imperdiet volutpat ante, sed venenatis urna volutpat a." +
            " Proin justo massa, convallis vitae consectetur sit amet, facilisis id libero.";
}
