package com.demoOpencart.test;

import com.demoOpencart.pages.CheckoutPage;
import com.demoOpencart.pages.HomePage;
import com.demoOpencart.pages.ProductPage;
import com.demoOpencart.utils.Constants;
import com.demoOpencart.utils.ExcelReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class PurchaseTest extends BaseTest{

    @Test
    public void completePurchaseTest() {
        HomePage homePage = new HomePage(driver);
        ProductPage productPage = new ProductPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        ExcelReader excelReader = new ExcelReader(Constants.EXCEL_FILE_PATH);
        List<String[]> data = excelReader.readDataProducts();

        homePage.navigateTo(Constants.BASE_URL);

        data.forEach(product -> {
            String name = product[0];
            String quantity = product[1];
            productPage.searchFor(name);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement waitingProduct = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(name)));

            if (waitingProduct.isEnabled()) {
                if (!productPage.getNoProducts()) {
                    System.out.println("Producto existente: " + name);

                    productPage.selectProduct(name);
                    productPage.enterQuantity(quantity);
                    productPage.clickAddButton();

                    System.out.println("Producto añadido: " + name);

                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"product-product\"]/div[1]")));
                    homePage.selectCart();

                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

                    // Asserts
                    try {
                        // Example: Asserts to check the name and quantity (if needed)
                    /* String actualNameProduct = homePage.getNameProductAdded();
                    Assertions.assertEquals(name, actualNameProduct, "El nombre del producto agregado no es el mismo");
                    String actualQuantityProduct = homePage.getQuantityProductAdded();
                    Assertions.assertEquals(quantity, actualQuantityProduct, "La cantidad del producto agregado no es la misma"); */
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    System.out.println("El producto buscado no existe: " + name);
                }
            } else {
                System.out.println("Los productos no se pudieron cargar");
            }
        });

        homePage.selectCategory("Shopping Cart");

        data.forEach(product -> {
            String name = product[0];
            String quantity = product[1];
            boolean productFound = false;

            List<WebElement> rows = driver.findElements(By.xpath("//*[@id='content']/form/div/table/tbody/tr"));

            for (int i = 1; i <= rows.size(); i++) {
                String actualProduct = driver.findElement(By.xpath("//*[@id='content']/form/div/table/tbody/tr[" + i + "]/td[2]/a")).getText();
                String actualQuantity = driver.findElement(By.xpath("//*[@id=\"content\"]/form/div/table/tbody/tr[1]/td[4]/div/input")).getAttribute("value");

                if (actualProduct.equalsIgnoreCase(name)) {
                    System.out.println("Producto encontrado: " + name + " en la fila " + i);
                    System.out.println("Cantidad actual: " + actualQuantity);
                    System.out.println("Cantidad esperada: " + quantity);
                    if (actualQuantity.equalsIgnoreCase(quantity)) {
                        System.out.println("Producto con cantidad correcta");
                    } else {
                        System.out.println("Producto con cantidad incorrecta");
                    }

                    productFound = true;
                    break;
                }
            }

            if (!productFound) {
                System.out.println("Producto no encontrado en el carrito: " + name);
            }
        });

        homePage.selectCategory("Checkout");

        List<String[]> dataCheckout = excelReader.readDataCheckout();

        dataCheckout.forEach(checkdata -> {
            String firstName = checkdata[0];
            String lastName = checkdata[1];
            String email = checkdata[2];
            String telephone = checkdata[3];
            String address = checkdata[4];
            String city = checkdata[5];
            String postCode = checkdata[6];
            String country = checkdata[7];
            String region = checkdata[8];

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            checkoutPage.selectGuestCheckout();
            checkoutPage.clickContinueButton();
            checkoutPage.enterFirstName(firstName);
            checkoutPage.enterLastName(lastName);
            checkoutPage.enterEmail(email);
            checkoutPage.enterTelephone(telephone);
            checkoutPage.enterAddress(address);
            checkoutPage.enterCity(city);
            checkoutPage.enterPostCode(postCode);
            checkoutPage.selectCountry(country);
            checkoutPage.selectRegion(region);
            checkoutPage.clickGuestButton();

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            checkoutPage.clickShippingButton();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            checkoutPage.acceptTermsAndConditions();
            checkoutPage.clickPaymentButton();

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            checkoutPage.clickConfirmButton();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"content\"]/p[1]")));
            String actualMessage = checkoutPage.getSuccessMessage();
            String expectedMessage = "Your order has been placed!";
            Assertions.assertEquals(expectedMessage, actualMessage, "El mensaje de éxito no es el mismo");
        });
    }
}
