package com.appium_test;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;

import io.appium.java_client.android.AndroidDriver;
import jakarta.inject.Inject;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.webdriver.WebDriverFacade;
import net.thucydides.model.util.EnvironmentVariables;





@ExtendWith(SerenityJUnit5Extension.class)
public class TestAppium {
    
    @Managed(driver = "appium")
    WebDriver webdriver;

    @Inject
    EnvironmentVariables environmentVariables;


    @Test
    public void verificarCreacionDeSesionAppium() throws Exception {

        System.out.println(EnvironmentSpecificConfiguration
            .from(environmentVariables)
            .getProperty("myDebugProperty")
        );

        
        // 1. Validar que el objeto intermedio existe
        assertThat(webdriver).isNotNull();
        System.out.println("\n>>> [DIAGNÓSTICO DE SESIÓN]");

        WebDriver realDriver = null;
        SessionId sessionId = null;
        RemoteWebDriver remote = null;

        try {
            // Desenvolvemos el Driver que gestiona Serenity
            if (webdriver instanceof WebDriverFacade) {
                realDriver = ((WebDriverFacade) webdriver).getProxiedDriver();
                System.out.println("[OK] Driver extraído del Facade de Serenity.");
                System.out.println("Tipo del driver real: " + realDriver.getClass().getName());
            } else {
                realDriver = webdriver;
            }

            

            // Validamos si es un RemoteWebDriver válido y extraemos su Session ID
            if (realDriver instanceof RemoteWebDriver) {
                remote = (RemoteWebDriver) realDriver;
                sessionId = remote.getSessionId();
                System.out.println("[SESSION INFO] ID de Sesión de Appium: " + sessionId);
                
                if (sessionId == null) {
                    System.out.println("[ALERTA CRÍTICA] ¡El Session ID es NULL! La sesión en el servidor Appium no se creó o se cerró prematuramente.");
                }
            } else {
                System.out.println("[WARN] El driver real no es una instancia de RemoteWebDriver. Tipo: " + realDriver.getClass().getName());
            }

        } catch (Exception e) {
            System.out.println("[ERROR] Falló el desempaquetado de la sesión: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n>>> [INTENTO DE COMUNICACIÓN]");
        String pageSource = null;
        /*===AUXILIAR==== */
        Object proxiedDriver = (webdriver instanceof WebDriverFacade facade)
        ? facade.getProxiedDriver()
        : realDriver;
        System.out.println(proxiedDriver.getClass().getName());

        AndroidDriver driver = (AndroidDriver) proxiedDriver;
        
        if (driver != null && sessionId != null) {
            try {
                System.out.println("Enviando petición getPageSource a la sesión activa...");
                System.out.println(driver.getClass().getName());
                pageSource = driver.getPageSource();
                System.out.println("Respuesta recibida.");
            } catch (Exception e) {
                System.out.println("=================================================");
                System.out.println("[ERROR AL SOLICITAR XML]: La sesión rechazó la petición:");
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
                System.out.println("=================================================");
            }
        } else {
            System.out.println("[CANCELADO] No se intenta getPageSource porque no tenemos una sesión de Appium válida.");
        }

        System.out.println("\n>>> [RESULTADO FINAL]");
        System.out.println("Longitud del XML obtenido: " + (pageSource != null ? pageSource.length() : 0));
        
        assertThat(pageSource).isNotEmpty();
    }
}