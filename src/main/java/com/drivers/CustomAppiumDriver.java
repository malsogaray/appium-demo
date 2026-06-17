package com.drivers;

import java.net.URI;
import java.time.Duration;

import org.openqa.selenium.WebDriver;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import net.thucydides.core.webdriver.DriverSource;

public class CustomAppiumDriver implements DriverSource {

    static {
        System.out.println("=== CLASE CUSTOMAPPIUMDRIVER CARGADA ===");
    }
    

    @Override
    public WebDriver newDriver() {
        AndroidDriver driver;
        System.out.println("=== INSTANCIANDO LA CLASE  ===");
        
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setDeviceName("N910plus")
                .setApp("C:/Users/malsogaray.NET01/appium-demo/src/apps/platform_app_debug_3.5.0.0_newland_n910.apk")
                .setPlatformVersion("10")
                .setAppPackage("br.com.phoebus.paystore.platform.newland")
                .setAppActivity("br.com.phoebus.android.platform.ui.activity.MainActivity")
                .setUdid("0123456789ABCDEF")
                .setEnsureWebviewsHavePages(true)     
                .setNativeWebScreenshot(true)       
                .setNewCommandTimeout(Duration.ofSeconds(3600));
        try{
            System.out.println("=== CREANDO ANDROID DRIVER ===");
            driver = new AndroidDriver(URI.create("http://127.0.0.1:4723").toURL(), options);
            System.out.println("SESSION ID = " + driver.getSessionId());
            return driver;
        } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to create AndroidDriver", e);
        }
    }
    @Override
    public boolean takesScreenshots() {
        return true;
    }
}
