package ru.stqa.training.selenium;

import org.openqa.selenium.WebDriver;

/**
 * @author Victoria Kadubina
 */
public interface SeleniumDriverProvider {
    WebDriver getDriver();

    WebDriver getRemoteDriver();
}
