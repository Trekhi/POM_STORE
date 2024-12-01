package com.demoOpencart.test;


import com.demoOpencart.pages.HomePage;
import com.demoOpencart.pages.LoginPage;
import com.demoOpencart.utils.Constants;
import com.demoOpencart.utils.ExcelReader;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

//Clase con todas las pruebas
public class FullTest extends BaseTest{

    @Test
    public void completeFullTest(){
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);

        ExcelReader excelReader = new ExcelReader(Constants.EXCEL_FILE_PATH);
        List<String[]> data = excelReader.readDataLogin();

        homePage.navigateTo(Constants.BASE_URL);

        data.forEach(dataProd -> {
            String email = dataProd[0];
            String contrasena = dataProd[1];

            System.out.println("Intentando con email: " + email + " y contraseña: " + contrasena);

            homePage.selectCategory("My Account");
            homePage.selectCategory("Login");

            loginPage.login(email, contrasena);

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            try {
                Thread.sleep(5000);
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }

        });

    }

}
