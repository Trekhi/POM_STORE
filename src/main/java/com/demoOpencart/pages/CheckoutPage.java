package com.demoOpencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class CheckoutPage extends BasePage{

    private By guestCheckoutInput = By.xpath("//*[@id=\"collapse-checkout-option\"]/div/div/div[1]/div[2]/label/input");
    private By continueButton = By.id("button-account");
    private By firstNameInput = By.id("input-payment-firstname");
    private By lastNameInput = By.id("input-payment-lastname");
    private By emailInput = By.id("input-payment-email");
    private By telephoneInput = By.id("input-payment-telephone");
    private By addressInput = By.id("input-payment-address-1");
    private By cityInput = By.id("input-payment-city");
    private By postCodeInput = By.id("input-payment-postcode");
    private By countrySelect = By.id("input-payment-country");
    private By regionSelect = By.id("input-payment-zone");
    private By guestButton = By.id("button-guest");
    private By shippingButton= By.id("button-shipping-method");
    private By checkInput = By.xpath("//*[@id=\"collapse-payment-method\"]/div/div[3]/div/input[1]");
    private By paymentButton = By.id("button-payment-method");
    private By confirmButton = By.id("button-confirm");
    private By messageSuccess = By.xpath("//*[@id=\"content\"]/h1");


    public CheckoutPage(WebDriver driver){
        super(driver);
    }

    public void selectGuestCheckout() {
        WebElement guestInput = driver.findElement(guestCheckoutInput);

        if (!guestInput.isSelected()) {
            guestInput.click();
            System.out.println("Guest Checkout seleccionado correctamente.");
        }
    }

    public void clickContinueButton() {
        driver.findElement(continueButton).click();
    }

    public void enterFirstName(String firstName) {
        driver.findElement(firstNameInput).sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        driver.findElement(lastNameInput).sendKeys(lastName);
    }

    public void enterEmail(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }

    public void enterTelephone(String telephone) {
        driver.findElement(telephoneInput).sendKeys(telephone);
    }

    public void enterAddress(String address) {
        driver.findElement(addressInput).sendKeys(address);
    }

    public void enterCity(String city) {
        driver.findElement(cityInput).sendKeys(city);
    }

    public void enterPostCode(String postCode) {
        driver.findElement(postCodeInput).sendKeys(postCode);
    }

    public void selectCountry(String country) {
        Select countryDropdown = new Select(driver.findElement(countrySelect));
        countryDropdown.selectByVisibleText(country);
    }

    public void selectRegion(String region) {
        Select regionDropdown = new Select(driver.findElement(regionSelect));
        regionDropdown.selectByVisibleText(region);
    }

    public void clickGuestButton() {
        driver.findElement(guestButton).click();
    }

    public void clickShippingButton() {
        driver.findElement(shippingButton).click();
    }

    public void clickPaymentButton() {
        driver.findElement(paymentButton).click();
    }

    public void acceptTermsAndConditions() {
        WebElement checkbox = driver.findElement(checkInput);
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    public void clickConfirmButton() {
        driver.findElement(confirmButton).click();
    }

    public String getSuccessMessage() {
        return driver.findElement(messageSuccess).getText();
    }


}
