package com.demoOpencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private By usernameField = By.id("input-email");
    private By passwordField = By.id("input-password");
    private By loginButton = By.xpath("//*[@id='content']/div/div[2]/div/form/input");
    private By welcomeTitle = By.xpath("//*[@id=\"content\"]/h2[1]");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void enterUsername(String username) {
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public String getWelcomeTitle(){
        return driver.findElement(welcomeTitle).getText();
    }


}
