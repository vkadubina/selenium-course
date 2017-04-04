package ru.stqa.training.selenium.admin.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.stqa.training.selenium.Page;

import java.util.List;

/**
 * @author Victoria Kadubina
 */
public class DataTableComponent extends Page {

    public DataTableComponent(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "table.dataTable")
    WebElement dataTable;

    public List<WebElement> getRows() {
        return dataTable.findElements(By.cssSelector("tr.row"));
    }

    public WebElement getRow(int n) {
        return getRows().get(n);
    }

    public WebElement getCell(WebElement row, int n) {
        return row.findElement(By.cssSelector("td:nth-child(" + n + ")"));
    }

    public WebElement getRow(String tableCSS, int n) {
        return getRows(tableCSS).get(n);
    }

    public List<WebElement> getRows(String tableCSS) {
        return driver.findElements(By.cssSelector(tableCSS + " tr"));
    }

    public WebElement getCell(String tableCSS, int i, int j) {
        return getRow(tableCSS, i).findElement(By.cssSelector("td:nth-child(" + j + ")"));
    }
}
