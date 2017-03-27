package ru.stqa.training.selenium.client.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Victoria Kadubina
 */
public class Page {

        protected WebDriver driver;
        protected WebDriverWait wait;
        protected final String clientUrl = System.getProperty("app.client.url");

        public Page(WebDriver driver) {
            this.driver = driver;
            wait = new WebDriverWait(driver, 10);
        }
    }
