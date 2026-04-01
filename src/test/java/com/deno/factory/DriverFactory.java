package com.deno.factory;

import java.time.Duration; 

import org.openqa.selenium.WebDriver; 
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.RemoteWebDriver; 
 
public class DriverFactory { 
 
    private static WebDriver driver; 
 
    public static void initDriver() { 
        if (driver == null) { 
            driver = new EdgeDriver(); 
            driver.manage().window().maximize(); 
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); 
        } 
    } 
 
    public static WebDriver getDriver() { 
        return driver; 
    } 
 
    public static void printSessionId() { 
        if (driver instanceof RemoteWebDriver) { 
            System.out.println("Current Browser Session ID: " 
                    + ((RemoteWebDriver) driver).getSessionId()); 
        } 
    } 
    
 
    public static void quitDriver() { 
        if (driver != null) { 
            driver.quit(); 
            driver = null; 
        } 
    } 
} 
