package ru.stqa.training.selenium;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.remote.CapabilityType.PROXY;

/**
 * @author Victoria Kadubina
 */
public class CheckTabTitleTest {

    WebDriver driver;
    WebDriverWait wait;
    BrowserMobProxy proxy;

    @Before
    public void init() {
        // start the proxy
        proxy = new BrowserMobProxyServer();
        proxy.start(0);

        // get the Selenium proxy object
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
        proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(PROXY, seleniumProxy);


        driver = new ChromeDriver(capabilities);
        wait = new WebDriverWait(driver, 10);

        proxy.newHar();
    }


    @Test
    public void checkGoogleSearchTabTitleTest() {
        driver.get("https://www.google.com/");
        wait.until(d -> d.getTitle().contains("Google"));
        driver.findElement(By.name("q")).sendKeys("webdriver");
        driver.findElement(By.name("btnG")).click();
        wait.until(d -> d.getTitle().startsWith("webdriver - Google"));
    }

    @After
    public void stop() {
        if (proxy != null) {
            Har har = proxy.endHar();
            har.getLog().getEntries().forEach(e -> {
                System.out.println(e.getResponse().getStatus() +
                        " / " + e.getResponse().getStatusText() +
                        " / " + e.getRequest().getUrl() +
                        " / " + e.getTime(TimeUnit.MILLISECONDS));
            });
            proxy.stop();
            proxy = null;
        }

        if (driver != null) {
            driver.quit();
            wait = null;
            driver = null;
        }
    }
}
