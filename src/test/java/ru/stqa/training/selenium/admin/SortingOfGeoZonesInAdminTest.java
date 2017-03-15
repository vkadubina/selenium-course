package ru.stqa.training.selenium.admin;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.stqa.training.selenium.MultiBrowserBaseTest;

import static junit.framework.TestCase.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlToBe;
import static ru.stqa.training.selenium.admin.AdminPageHelper.loginInAdmin;
import static ru.stqa.training.selenium.admin.AdminPageHelper.openAdminSection;

/**
 * @author Victoria Kadubina
 */
public class SortingOfGeoZonesInAdminTest extends MultiBrowserBaseTest {
    public SortingOfGeoZonesInAdminTest(String browser) {
        super(browser);
    }

    @Test
    public void isAllGeoZonesSorted() {
        loginInAdmin(driver);
        openAdminSection(driver,wait,"Geo Zones");
        int zonesCount = driver.findElements(By.cssSelector("tr.row")).size();

        for (int i = 0; i < zonesCount; i++) {
            String currentUrl = driver.getCurrentUrl();
            WebElement row = driver.findElement(By.cssSelector("table.dataTable tr:nth-child(" + (i + 2) + ")"));
            row.findElement(By.cssSelector("td:nth-child(3) a")).click();
            wait.until(urlContains("geo_zone_id"));

            assertGeoZonesAreSorted();
            driver.navigate().back();
            wait.until(urlToBe(currentUrl));
        }
    }

    public void assertGeoZonesAreSorted() {
        int zonesCount = driver.findElements(By.cssSelector("table#table-zones td:nth-child(3) select:not([disabled=disabled])")).size();
        String prevZone = "";

        for (int j = 0; j < zonesCount; j++) {
            String currentZone = driver.findElement(By.cssSelector("table#table-zones tr:nth-child(" + (j + 2) + ") select:not(.select2-hidden-accessible) [selected=selected]"))
                    .getText().trim();
            if (j != 0) {
                assertTrue(currentZone.compareTo(prevZone) > 0);
            }
            prevZone = currentZone;
        }

    }

}
