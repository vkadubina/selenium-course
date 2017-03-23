package ru.stqa.training.selenium;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

import static ru.stqa.training.selenium.OS.*;

/**
 * @author Victoria Kadubina
 */
public enum SeleniumBrowser implements SeleniumDriverProvider {
    CHROME(WINDOWS, MAC) {
        @Override
        public WebDriver getDriver() {

            DesiredCapabilities cap = new DesiredCapabilities();
            LoggingPreferences logPrefs = new LoggingPreferences();
            logPrefs.enable(LogType.BROWSER, Level.ALL);
            cap.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
            return new ChromeDriver(cap);
        }

        @Override
        public WebDriver getRemoteDriver() {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setBrowserName("chrome");
            caps.setPlatform(Platform.MAC);
            return createRemoteDriver(caps);
        }
    },
    FIREFOX(WINDOWS, MAC) {
        @Override
        public WebDriver getDriver() {
            return new FirefoxDriver();
        }

        @Override
        public WebDriver getRemoteDriver() {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setBrowserName("firefox");
            caps.setPlatform(Platform.MAC);
            return createRemoteDriver(caps);
        }
    },
    FFNIGHTLY(WINDOWS, MAC) {
        @Override
        public WebDriver getDriver() {
            OS os = OS.detect();
            File bin;
            if (os == WINDOWS) {
                bin = new File(System.getProperty("browser.binary.ffnightly.windows"));
            } else if (os == MAC) {
                bin = new File(System.getProperty("browser.binary.ffnightly.mac"));
            } else {
                throw new IllegalStateException("Unsupported OS " + os);
            }
            FirefoxOptions ffo = new FirefoxOptions();
            ffo.setProfile(new FirefoxProfile());
            ffo.setBinary(new FirefoxBinary(bin));
            return new FirefoxDriver(ffo);
        }

        @Override
        public WebDriver getRemoteDriver() {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setBrowserName("firefox");
            caps.setPlatform(Platform.MAC);
            return createRemoteDriver(caps);
        }
    },
    FF45(WINDOWS, MAC) {
        @Override
        public WebDriver getDriver() {
            OS os = OS.detect();
            File bin;
            if (os == WINDOWS) {
                bin = new File(System.getProperty("browser.binary.ff45.windows"));
            } else if (os == MAC) {
                bin = new File(System.getProperty("browser.binary.ff45.mac"));
            } else {
                throw new IllegalStateException("Unsupported OS " + os);
            }
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability(FirefoxDriver.MARIONETTE, false);
            FirefoxOptions ffo = new FirefoxOptions();
            ffo.setBinary(new FirefoxBinary(bin));
            ffo.setProfile(new FirefoxProfile());
            ffo.addDesiredCapabilities(caps);
            return new FirefoxDriver(ffo);
        }

        @Override
        public WebDriver getRemoteDriver() {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setBrowserName("firefox");
            caps.setPlatform(Platform.MAC);
            return createRemoteDriver(caps);
        }
    },
    IE(WINDOWS) {
        @Override
        public WebDriver getDriver() {
            return new InternetExplorerDriver();
        }

        @Override
        public WebDriver getRemoteDriver() {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setBrowserName("internet explorer");
            caps.setPlatform(Platform.WINDOWS);
            return createRemoteDriver(caps);
        }
    },
    SAFARI(MAC) {
        @Override
        public WebDriver getDriver() {
            return new SafariDriver();
        }

        @Override
        public WebDriver getRemoteDriver() {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setBrowserName("safari");
            caps.setPlatform(Platform.MAC);
            return createRemoteDriver(caps);
        }
    };

    private final Set<OS> supportedOS;

    SeleniumBrowser(OS... supportedOS) {
        this.supportedOS = new HashSet<>(Arrays.asList(supportedOS));
    }

    public boolean isSupported(OS os) {
        return supportedOS.contains(os);
    }

    public boolean isEnabled() {
        return !Boolean.parseBoolean(System.getProperty("browser." + this.toString().toLowerCase() + ".disabled", "false"));
    }

    public static final String USERNAME = "victoriakadubina1";
    public static final String AUTOMATE_KEY = "ztsq1KcXPiT8HbJk84qH";
    public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";


    private static WebDriver createRemoteDriver(DesiredCapabilities caps) {
        caps.setCapability("browserstack.debug", "true");
        URL remoteAddress = null;
        try {
            remoteAddress = new URL(URL);
        } catch (MalformedURLException e) {
            throw new IllegalStateException(e);
        }

        return new RemoteWebDriver(remoteAddress, caps);
    }
}
