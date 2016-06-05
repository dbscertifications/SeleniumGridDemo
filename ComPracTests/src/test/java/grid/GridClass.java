package com.test.grid;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.Thread.sleep;

/**
 * Created by bhattacharyad on 9/03/2016.
 */
public class GridClass {

    WebDriver driver = null;

    @Parameters({ "browser", "url" })
    @BeforeClass
    public void setUp(String browser, String url) throws MalformedURLException{
        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (browser.equalsIgnoreCase("Internet Explorer")) { capabilities = DesiredCapabilities.internetExplorer(); }
        if (browser.equalsIgnoreCase("Chrome")) { capabilities = DesiredCapabilities.chrome(); }

        driver = new RemoteWebDriver(new URL("http://192.168.0.12:4444/wd/hub"), capabilities);

        // Open the RedBook Application
        driver.get(url);
        System.out.println("beforetest"+browser);
    }

    @Test
    public void gridTest() throws InterruptedException {
        //driver.findElement(By.name("entered_login")).sendKeys("bhattacharyad");
        //driver.findElement(By.name("entered_password")).sendKeys("Luxbet123");
        //driver.findElement(By.name("login")).click();
        sleep(5);
        System.out.println("gridtest");
    }

    @AfterTest
    public void afterTest() {
        //Close the browser
        System.out.println("aftertest");
        driver.quit();
    }
}
