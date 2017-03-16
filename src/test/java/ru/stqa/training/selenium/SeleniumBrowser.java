package ru.stqa.training.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static ru.stqa.training.selenium.OS.*;

/**
 * @author Victoria Kadubina
 */
public enum SeleniumBrowser implements SeleniumDriverProvider{
    CHROME(WINDOWS, MAC) {
        @Override
        public WebDriver getDriver() {
            return new ChromeDriver();
        }
    },
    FIREFOX(WINDOWS, MAC) {
        @Override
        public WebDriver getDriver() {
            return new FirefoxDriver();
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
    },
    IE(WINDOWS) {
        @Override
        public WebDriver getDriver() {
            return new InternetExplorerDriver();
        }
    },
    SAFARI(MAC) {
        @Override
        public WebDriver getDriver() {
            return new SafariDriver();
        }
    };

    private final Set<OS> supportedOS;

    SeleniumBrowser(OS... supportedOS) {
        this.supportedOS = new HashSet<>(Arrays.asList(supportedOS));
    }

    public boolean isSupported(OS os){
        return supportedOS.contains(os);
    }
}
