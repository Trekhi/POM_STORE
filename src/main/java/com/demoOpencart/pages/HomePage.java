package com.demoOpencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage{

    //Creación de selectores o elementos de la pagina Home
    private By categoryLink(String category){
        return By.linkText(category);
    }

    public HomePage(WebDriver driver){
        super(driver);
    }

    //Metodos o Acciones que puede ejecutar con los elementos de la Pag
    public void selectCategory(String category){
        driver.findElement(categoryLink(category)).click();
    }

}
