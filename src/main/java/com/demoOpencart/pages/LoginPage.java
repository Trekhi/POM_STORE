package com.demoOpencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    // Selectores para los campos de login
    private By usernameField = By.id("input-email"); // Reemplaza con el ID real del campo de usuario
    private By passwordField = By.id("input-password"); // Reemplaza con el ID real del campo de contraseña
    private By loginButton = By.xpath("//*[@id='content']/div/div[2]/div/form/input"); // Reemplaza con la clase real

    // Constructor
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // Método para ingresar el usuario
    public void enterUsername(String username) {
        driver.findElement(usernameField).sendKeys(username);
    }

    // Método para ingresar la contraseña
    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    // Método para hacer clic en el botón de inicio de sesión
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    // Método general para realizar el login
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

}
