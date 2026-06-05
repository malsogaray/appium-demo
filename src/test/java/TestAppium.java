import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import net.serenitybdd.annotations.Managed;
import net.serenitybdd.junit5.SerenityJUnit5Extension;

public class TestAppium {
   @Managed(driver = "appium")
    WebDriver webdriver;

    @ExtendWith(SerenityJUnit5Extension.class)
    @Test
    public void verificarCreacionDeSesionAppium() throws Exception {
        // 1. Verificamos que Serenity no nos devuelva un driver nulo
        assertThat(webdriver).isNotNull();

        System.out.println("Esperando a que la aplicación cargue la interfaz...");
        Thread.sleep(7000); 

        // 3. Intentamos capturar el árbol de componentes de nuevo
        String pageSource = webdriver.getPageSource();
        
        System.out.println("=================================================");
        System.out.println("¡SESIÓN INICIADA CON ÉXITO!");
        System.out.println("Longitud del XML obtenido: " + (pageSource != null ? pageSource.length() : 0));
        if (pageSource != null && !pageSource.isEmpty()) {
            System.out.println("Fragmento del XML:\n" + pageSource.substring(0, Math.min(pageSource.length(), 300)) + "...");
        }
        System.out.println("=================================================");
        
        // 4. Esta validación ahora debería pasar
        assertThat(pageSource).isNotEmpty();
    }
}
