package ru.stqa.training.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Victoria Kadubina
 */
public class Page {

        protected WebDriver driver;
        protected WebDriverWait wait;
        protected final String clientUrl = System.getProperty("app.client.url");
        protected final String adminUrl = System.getProperty("app.admin.url");

        public Page(WebDriver driver) {
            this.driver = driver;
            wait = new WebDriverWait(driver, 10);
        }
    }
