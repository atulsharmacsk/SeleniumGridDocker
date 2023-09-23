package com.org.Tests;

import com.org.Docker.DockerManager;
import com.org.Drivers.DriverManager;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginTest {

    WebDriver driver;
    DockerManager dockerManager = new DockerManager();

    @BeforeSuite
    public void startDocker() {
        dockerManager.actionDocker("START");
    }

    @Parameters({"browser"})
    @BeforeTest
    public void setup(String browser) {
        driver = new DriverManager().getDriver(browser);
        driver.get("https://opensource-demo.orangehrmlive.com/");
    }

    @SneakyThrows
    @Test
    public void loginTest() {
        driver.findElement(By.name("username")).sendKeys("Admin");
        driver.findElement(By.name("password")).sendKeys("admin123$$");
        driver.findElement(By.xpath("//*[@id='app']/div[1]/div/div[1]/div/div[2]/div[2]/form/div[3]/button")).submit();

        Thread.sleep(20000);
        String actualErrorMessage = driver.findElement(By.xpath("//*[@id='app']/div[1]/div/div[1]/div/div[2]/div[2]/div/div[1]/div[1]/p")).getText();

        Assert.assertEquals(actualErrorMessage, "Invalid credentials");
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

    @AfterSuite
    public void stopDocker() {
        dockerManager.actionDocker("STOP");
    }
}
