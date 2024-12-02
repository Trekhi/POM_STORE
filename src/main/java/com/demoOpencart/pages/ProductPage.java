package com.demoOpencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class ProductPage extends BasePage{

    private By quantityField = By.id("input-quantity");
    private By addButton = By.id("button-cart");
    private By searchBar = By.xpath("//*[@id=\"search\"]/input");
    private By searchButton = By.xpath("//*[@id=\"search\"]/span/button");
    private By resultNoProducts = By.xpath("//*[@id=\"content\"]/p[2]");

    public ProductPage(WebDriver driver){
        super(driver);
    }

    public void selectProduct(String productName){
        driver.findElement(By.linkText(productName)).click();
    }

    public void enterQuantity(String quantity) {
        driver.findElement(quantityField).clear();
        driver.findElement(quantityField).sendKeys(quantity);
    }

    public void clickAddButton() {
        driver.findElement(addButton).click();
    }

    public void searchFor(String query){
        driver.findElement(searchBar).clear();
        driver.findElement(searchBar).sendKeys(query);
        driver.findElement(searchButton).click();
    }

    public Boolean getNoProducts(){
        try {
            return driver.findElement(resultNoProducts).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

}
