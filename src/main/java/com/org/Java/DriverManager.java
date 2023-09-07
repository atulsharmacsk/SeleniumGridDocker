package com.org.Java;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.SneakyThrows;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.time.Duration;

public class DriverManager {

    private WebDriver driver;

    public WebDriver getDriver(String browser) {
        String type = System.getProperty("EXEC");
        if (type.equalsIgnoreCase("local")) {
            driver = getLocalDriver(browser);
        } else {
            driver = getRemoteDriver(browser);
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        return driver;
    }

    public WebDriver getLocalDriver(String browser) {
        return getLocalBrowser(browser);
    }

    @SneakyThrows
    public WebDriver getRemoteDriver(String browser) {
        driver = new RemoteWebDriver(new URL("http://localhost:4444"), getRemoteBrowser(browser));
        return driver;
    }

    public MutableCapabilities getRemoteBrowser(String browser) {
        MutableCapabilities option;
        switch (browser.toLowerCase()) {
            case "firefox":
                option = new FirefoxOptions();
                break;
            case "chrome":
                option = new ChromeOptions();
                break;
            case "edge":
                option = new EdgeOptions();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + browser.toLowerCase());
        }
        option.setCapability("se:recordVideo",true);
        return option;
    }

    public WebDriver getLocalBrowser(String browser) {
        WebDriver driver;
        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver= new EdgeDriver();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + browser.toLowerCase());
        }
        return driver;
    }

}
