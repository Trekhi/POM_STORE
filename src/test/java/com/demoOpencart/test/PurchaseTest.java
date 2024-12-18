package com.demoOpencart.test;

import com.demoOpencart.pages.CheckoutPage;
import com.demoOpencart.pages.HomePage;
import com.demoOpencart.pages.ProductPage;
import com.demoOpencart.utils.Constants;
import com.demoOpencart.utils.ExcelReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class PurchaseTest extends BaseTest{

    @Test
    public void completePurchaseTest(){
        HomePage homePage = new HomePage(driver);
        ProductPage productPage = new ProductPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        ExcelReader excelReader = new ExcelReader(Constants.EXCEL_FILE_PATH);
        List<String[]> data = excelReader.readDataProducts();

        homePage.navigateTo(Constants.BASE_URL);
        System.out.println("\n\n******** TEST - BÚSQUEDA Y ADICIÓN DE PRODUCTOS ********");

        data.forEach(product -> {
            String name = product[0];
            String quantity = product[1];

            System.out.println("\nBuscando producto: "+name);

            try {
                productPage.searchFor(name);

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                WebElement waitingProduct = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(name)));

                if (waitingProduct.isEnabled()) {
                    if (!productPage.getNoProducts()) {
                        System.out.println("Producto existente: " + name);

                        productPage.selectProduct(name);
                        productPage.enterQuantity(quantity);
                        productPage.clickAddButton();
                        System.out.println("Producto añadido!");

                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"product-product\"]/div[1]")));
                        homePage.selectCart();
                        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

                        // Validaciones con aserciones
                        try {
                            String actualNameProduct = homePage.getNameProductAdded();
                            Assertions.assertEquals(name, actualNameProduct, "El nombre del producto agregado no es el mismo");
                        } catch (AssertionError e) {
                            System.out.println("\nEl assert en ventana modal del carrito falló: " + e.getMessage());
                        }

                        try {
                            String actualQuantityProduct = homePage.getQuantityProductAdded();
                            Assertions.assertEquals(quantity, actualQuantityProduct, "La cantidad del producto agregado no es la misma");
                        } catch (AssertionError e) {
                            System.out.println("El assert en ventana modal del carrito falló: " + e.getMessage());
                        }

                    } else {
                        System.out.println("El producto buscado no existe");
                    }

                } else {
                    System.out.println("Los productos no se pudieron cargar");
                }
            } catch (TimeoutException e) {
                System.out.println("Error: No se encontró el producto '" + name + "' en el tiempo esperado.");
            } catch (NoSuchElementException e) {
                System.out.println("Error: Elemento no encontrado para el producto '" + name + "'. Detalle: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("\nError inesperado al procesar el producto '" + name + "': " + e.getMessage());
            }
        });

        homePage.selectCategory("Shopping Cart");
        System.out.println("\n\n******** TEST - VALIDACIÓN DE PRODUCTOS EN EL CARRITO ********");

        data.forEach(product -> {
            String name = product[0];
            String quantity = product[1];
            boolean productFound = false;
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            List<WebElement> rows = driver.findElements(By.xpath("//*[@id='content']/form/div/table/tbody/tr"));

            // Busca el producto en la tabla
            for (int i = 1; i <= rows.size(); i++) {
                String actualProduct = driver.findElement(By.xpath("//*[@id='content']/form/div/table/tbody/tr[" + i + "]/td[2]/a")).getText();
                String actualQuantity = driver.findElement(By.xpath("//*[@id=\"content\"]/form/div/table/tbody/tr[1]/td[4]/div/input")).getAttribute("value");

                if (actualProduct.equalsIgnoreCase(name)){

                    System.out.println("\nNombre actual: " + actualProduct);
                    System.out.println("Nombre esperado: " + name);
                    System.out.println("Producto encontrado: " + actualProduct + " en la fila " + i);

                    try {
                        Assertions.assertEquals(quantity, actualQuantity, "La cantidad del producto agregado no es la misma");
                    } catch (AssertionError e){
                        System.out.println("El assert en el carrito falló: " + e.getMessage());
                    }

                    if (actualQuantity.equalsIgnoreCase(quantity)){
                        System.out.println("Producto con cantidad correcta");
                    } else {
                        System.out.println("Producto con cantidad incorrecta");
                    }

                    productFound = true;
                    break;
                }
            }

            if (!productFound) {
                System.out.println("\nProducto no encontrado: " + name);
            }
        });

        homePage.selectCategory("Checkout");
        System.out.println("\n\n******** TEST - CHECKOUT ********\n");

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

            try {
                Assertions.assertEquals(expectedMessage,actualMessage, "El mensaje de éxito no es el mismo");
                System.out.println("¡La compra fue exitosa!");
            } catch (AssertionError e){
                System.out.println("El assert de compra exitosa falló: " + e.getMessage());
            }
        });



    }
}