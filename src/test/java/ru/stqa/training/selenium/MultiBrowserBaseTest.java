package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Properties;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author Victoria Kadubina
 */
@RunWith(Parameterized.class)
abstract public class MultiBrowserBaseTest {

    @Parameterized.Parameters(name = "{0}")
    public static Collection<SeleniumBrowser> data() {
        OS currentOS = OS.detect();
        return Stream.of(SeleniumBrowser.values())
                .filter(browser -> browser.isSupported(currentOS))
                .collect(toList());
    }

    static {
        loadTestEnvProperties();
    }

    protected String clientUrl;
    protected String adminUrl;

    private SeleniumBrowser browser;

    public MultiBrowserBaseTest(SeleniumBrowser browser) {
        this.browser = browser;
        clientUrl = System.getProperty("app.client.url");
        adminUrl = System.getProperty("app.admin.url");
    }

    protected WebDriver driver;
    protected WebDriverWait wait;


    @Before
    public void init() {
        driver = browser.getDriver();
        wait = new WebDriverWait(driver, 10);
    }


    @After
    public void stop() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    private static void loadTestEnvProperties() {
        Properties props = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("src/test/resources/test_env.properties");

            // load a properties file
            props.load(input);

            Properties sysProps = System.getProperties();
            props.putAll(sysProps);
            System.setProperties(props);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
