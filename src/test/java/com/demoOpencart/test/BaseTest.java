package com.demoOpencart.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;

    @BeforeEach
    public void setup(){

        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();


    }

    @AfterEach//Limpiar estado incicial de información FALTA!!!!
    public void tearDown(){
        if(driver != null){
            //driver.quit();
        }
    }

}
