package ru.stqa.training.selenium.admin;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.stqa.training.selenium.MultiBrowserBaseTest;
import ru.stqa.training.selenium.SeleniumBrowser;

import static junit.framework.TestCase.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlToBe;

/**
 * @author Victoria Kadubina
 */
public class SortingOfCountriesInAdminTest extends MultiBrowserBaseTest {


    public SortingOfCountriesInAdminTest(SeleniumBrowser browser) {
        super(browser);
    }

    @Test
    public void isAllCountriesSorted() {
        adminApp.login();
        adminApp.openAdminSection("Countries");

        int countriesCount = adminApp.getDataTable().getRows().size();
        String prevCountryName = "";

        for (int i = 0; i < countriesCount; i++) {
            WebElement row = adminApp.getDataTable().getRow(i);
            String currentCountryName = adminApp.getDataTable().getCell(row, 5).getAttribute("innerText");

            if (i != 0) {
                assertTrue(currentCountryName.compareTo(prevCountryName) > 0);
            }
            prevCountryName = currentCountryName;
            checkZones(row);
        }
    }

    private void checkZones(WebElement row) {
        int zonesCount = Integer.parseInt(adminApp.getDataTable().getCell(row, 6).getAttribute("innerText"));

        if (zonesCount > 0) {
            String currentUrl = driver.getCurrentUrl();
            String countryURL = "http://localhost:8080/admin/?app=countries&doc=edit_country&country_code=" +
                    adminApp.getDataTable().getCell(row, 4).getAttribute("innerText");

            adminApp.getDataTable().getCell(row, 5).findElement(By.cssSelector("a")).click();
            wait.until(urlToBe(countryURL));

            String prevZone = "";

            for (int j = 0; j < zonesCount; j++) {

                String currentZone = adminApp.getDataTable().getCell("table#table-zones", (j + 1), 3)
                        .getAttribute("textContent");
                if (j != 0) {
                    assertTrue(currentZone.compareTo(prevZone) > 0);
                }
                prevZone = currentZone;
            }
            driver.navigate().back();
            wait.until(urlToBe(currentUrl));
        }
    }
}
