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

        data.forEach(dataProd -> {
            String email = dataProd[0];
            String password = dataProd[1];

            System.out.println("Intentando autenticarse con email: " + email + " y contraseña: " + password);

            homePage.selectCategory("My Account");
            homePage.selectCategory("Login");

            loginPage.login(email, password);

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            //Assert
            String actualTitle = loginPage.getWelcomeTitle();
            String expectedTitle = "My Account";
            Assertions.assertEquals(expectedTitle, actualTitle, "El titulo de bienvenida no es el esperado");

            try {
                Thread.sleep(5000);
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }

        });

    }
}
    

