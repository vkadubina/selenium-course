package ru.stqa.training.selenium;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlToBe;

/**
 * @author Victoria Kadubina
 */
@RunWith(Parameterized.class)
public class SortingOfGeoZonesInAdminTest extends MultiBrowserBaseTest{
    public SortingOfGeoZonesInAdminTest(String browser) {
        super(browser);
    }

    @Test
    public void isAllGeoZonesSorted() {
        loginInAdmin(driver,adminUrl);
        int zonesCount = driver.findElements(By.cssSelector("tr.row")).size();

        for (int i = 0; i < zonesCount; i++) {
            WebElement row = driver.findElement(By.cssSelector("table.dataTable tr:nth-child(" + (i + 2) + ")"));
            row.findElement(By.cssSelector("td:nth-child(3) a")).click();
            checkGeoZones();
            driver.navigate().back();
            wait.until(urlToBe(adminUrl));
        }
    }

    private void checkGeoZones() {
        assertUrlChanged(adminUrl);

        int zonesCount = Integer.parseInt(row.findElement(By.cssSelector("td:nth-child(6)")).getAttribute("innerText"));
        String prevZone = "";

        for (int j = 0; j < zonesCount; j++) {
            String currentZone = driver.findElement(By.cssSelector("table#table-zones tr:nth-child(" + (j + 2) + ") td:nth-child(3)" ))
                    .getAttribute("textContent");
            if (j != 0) {
                assertTrue(currentZone.compareTo(prevZone) > 0);
            }
            prevZone = currentZone;
        }

    }

    private void assertUrlChanged(String oldUrl) {
        assertFalse(driver.getCurrentUrl().equals(oldUrl));
    }
    private String adminUrl = "http://localhost:8080/admin/?app=geo_zones&doc=geo_zones";
}
