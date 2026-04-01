package com.deno.pages;

import org.openqa.selenium.By; 
import org.openqa.selenium.WebDriver; 
 
public class InternetAppPage { 
 
    private WebDriver driver; 
 
    private By usernameTextBox = By.id("username"); 
    private By passwordTextBox = By.id("password"); 
    private By loginButton = By.cssSelector("button[type='submit']"); 
    private By flashMessage = By.id("flash"); 
    private By logoutButton = By.cssSelector("a.button.secondary.radius"); 
 
    public InternetAppPage(WebDriver driver) { 
        this.driver = driver; 
    } 
 
    public void openLoginPage() { 
        driver.get("https://the-internet.herokuapp.com/login"); 
    } 
 
    public void login(String username, String password) { 
        driver.findElement(usernameTextBox).clear(); 
        driver.findElement(usernameTextBox).sendKeys(username); 
 
        driver.findElement(passwordTextBox).clear(); 
        driver.findElement(passwordTextBox).sendKeys(password); 
 
        driver.findElement(loginButton).click(); 
    } 
 
    public boolean isSecureAreaMessageDisplayed() { 
        return driver.findElement(flashMessage) 
                     .getText() 
                     .contains("You logged into a secure area!"); 
    } 
 
    public void clickLogout() { 
        driver.findElement(logoutButton).click(); 
    } 
 
    public boolean isLogoutMessageDisplayed() { 
        return driver.findElement(flashMessage) 
                     .getText() 
                     .contains("You logged out of the secure area!"); 
    } 
} 
