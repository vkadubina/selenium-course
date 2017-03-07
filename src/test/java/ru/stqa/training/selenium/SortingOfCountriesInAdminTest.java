package ru.stqa.training.selenium;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.Collection;

import static junit.framework.TestCase.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlToBe;

/**
 * @author Victoria Kadubina
 */
@RunWith(Parameterized.class)
public class SortingOfCountriesInAdminTest extends MultiBrowserBaseTest{


    public SortingOfCountriesInAdminTest(String browser) {
        super(browser);
    }

    @Test
    public void isAllCountriesSorted() {
        loginInAdmin(driver,adminUrl);

        int countriesCount = driver.findElements(By.cssSelector("tr.row")).size();
        String prevCountryName = "";

        for (int i = 0; i < countriesCount; i++) {
            WebElement row = driver.findElement(By.cssSelector("table.dataTable tr:nth-child(" + (i + 2) + ")"));
            WebElement country = row.findElement(By.cssSelector("td:nth-child(5)"));
            String currentCountryName = country.getAttribute("innerText");

            if (i != 0) {
                assertTrue(currentCountryName.compareTo(prevCountryName) > 0);
            }
            prevCountryName = currentCountryName;
            checkZones(row);
        }

    }

    private void checkZones(WebElement row) {
        WebElement country = row.findElement(By.cssSelector("td:nth-child(5)"));
        int zonesCount = Integer.parseInt(row.findElement(By.cssSelector("td:nth-child(6)")).getAttribute("innerText"));
        if (zonesCount > 0) {
            String countryURL = "http://localhost:8080/admin/?app=countries&doc=edit_country&country_code=" +
                    row.findElement(By.cssSelector("td:nth-child(4)")).getAttribute("innerText");
            country.findElement(By.cssSelector("a")).click();
            wait.until(urlToBe(countryURL));

            String prevZone = "";

            for (int j = 0; j < zonesCount; j++) {
                String currentZone = driver.findElement(By.cssSelector("table#table-zones tr:nth-child(" + (j + 2) + ") td:nth-child(3)" ))
                        .getAttribute("textContent");
                if (j != 0) {
                    assertTrue(currentZone.compareTo(prevZone) > 0);
                }
                prevZone = currentZone;
            }
            driver.navigate().back();
            wait.until(urlToBe(adminUrl));
        }
    }

    private String adminUrl = "http://localhost:8080/admin/?app=countries&doc=countries";
}
