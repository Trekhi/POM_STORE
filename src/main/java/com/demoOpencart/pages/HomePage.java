package com.demoOpencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage{

    private By categoryLink(String category){
        return By.linkText(category);
    }
    private By cart = By.xpath("//*[@id=\"cart\"]/button");
    private By nameProductAdded = By.xpath("//*[@id=\"cart\"]/ul/li[1]/table/tbody/tr[1]/td[2]/a");
    private By quantityProductAdded = By.xpath("//*[@id=\"cart\"]/ul/li[1]/table/tbody/tr[1]/td[3]");

    public HomePage(WebDriver driver){
        super(driver);
    }

    public void selectCategory(String category){
        driver.findElement(categoryLink(category)).click();
    }

    public void selectCart(){
        driver.findElement(cart).click();
    }

    public String getNameProductAdded(){
        return driver.findElement(nameProductAdded).getText();
    }

    public String getQuantityProductAdded(){
        String quantityText = driver.findElement(quantityProductAdded).getText();
        return quantityText.replace("x ", "");
    }

}
