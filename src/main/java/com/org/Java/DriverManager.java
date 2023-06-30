package com.org.Java;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class DriverManager {

    private WebDriver driver;

    public WebDriver getDriver(String type) {
        if (type.equalsIgnoreCase("local")) {
            driver = getLocalDriver();
        } else {
            driver = getRemoteDriver();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        return driver;
    }

    public WebDriver getLocalDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        return driver;
    }

    public WebDriver getRemoteDriver() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        try {
            driver = new RemoteWebDriver(new URL("http://localhost:4444"), firefoxOptions);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return driver;
    }
}
