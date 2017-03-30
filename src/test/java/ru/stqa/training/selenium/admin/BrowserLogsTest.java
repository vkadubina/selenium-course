package ru.stqa.training.selenium.admin;

import org.junit.Test;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.stqa.training.selenium.MultiBrowserBaseTest;
import ru.stqa.training.selenium.SeleniumBrowser;

import java.util.Collection;
import java.util.logging.Level;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertFalse;
import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;
import static ru.stqa.training.selenium.admin.AdminPageHelper.openAdminSection;

/**
 * @author Victoria Kadubina
 */
public class BrowserLogsTest extends MultiBrowserBaseTest {


    @Parameterized.Parameters(name = "{0}")
    public static Collection<SeleniumBrowser> data() {
        return Stream.of(SeleniumBrowser.CHROME)
                .collect(toList());
    }

    public BrowserLogsTest(SeleniumBrowser browser) {
        super(browser);
    }

    @Test
    public void thereIsNoMessagesInBrowserLogs() {
        adminApp.login();
        openAdminSection(driver, wait, "Catalog");
        openAllProductPagesOneByOne();
        checkLogsDoNotContainMessages();

    }

    private void checkLogsDoNotContainMessages() {
        assertFalse("expected to have no messages",
                driver.manage().logs().get("browser").getAll()
                        .stream()
                        // Assuming that only warning and severe level log messages mean that something is wrong
                        .filter(l -> l.getLevel().intValue() > Level.INFO.intValue()).findAny()
                        .isPresent()
        );
    }


    private void openAllProductPagesOneByOne() {

        int i = 0;
        while (i < driver.findElements(By.cssSelector("table.dataTable tr.row")).size()) {

            WebElement table = driver.findElement(By.cssSelector("table.dataTable"));
            driver.findElement(By.cssSelector("table.dataTable tr.row:nth-child(" + (i + 2) + ") a")).click();
            wait.until(stalenessOf(table));
            i++;

            String title = driver.findElement(By.cssSelector("h1")).getText().trim();
            if (!title.equals("Catalog")) {
                if (title.startsWith("Edit Product")) {
                    WebElement body = driver.findElement(By.cssSelector("body"));
                    driver.navigate().back();
                    wait.until(stalenessOf(body));
                } else throw new IllegalStateException("unexpected title");
            }
        }
    }
}
