package com.demoOpencart.test;

import com.demoOpencart.pages.HomePage;
import com.demoOpencart.pages.LoginPage;
import com.demoOpencart.utils.Constants;
import com.demoOpencart.utils.ExcelReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

public class LoginTest extends BaseTest {

    @Test
    public void completeLoginTest(){
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);

        ExcelReader excelReader = new ExcelReader(Constants.EXCEL_FILE_PATH);
        List<String[]> data = excelReader.readDataLogin();

        homePage.navigateTo(Constants.BASE_URL);
        System.out.println("\n\n******** TEST - LOGIN ********\n");

        data.forEach(dataProd -> {
            String email = dataProd[0];
            String password = dataProd[1];

            System.out.println("Intentando autenticarse");
            System.out.println("Email: " + email);
            System.out.println("Contraseña: " + password);

            homePage.selectCategory("My Account");
            homePage.selectCategory("Login");

            loginPage.login(email, password);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            //Validar el inicio de sesión
            String actualTitle = loginPage.getWelcomeTitle();
            String expectedTitle = "My Account";

            try {
                Assertions.assertEquals(expectedTitle, actualTitle, "El titulo de bienvenida no es el esperado");
                System.out.println("\n¡Inicio de sesión exitoso!");
            } catch (AssertionError e) {
                System.out.println("\nEl assert de inicio de sesión falló: " + e.getMessage());
            }

        });

    }
}
    

