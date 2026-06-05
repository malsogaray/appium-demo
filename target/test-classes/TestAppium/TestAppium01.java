package TestAppium;

import java.io.PrintStream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import net.serenitybdd.annotations.Managed;
import net.serenitybdd.junit5.SerenityJUnit5Extension;

public class TestAppium01 {
    @Managed(
      driver = "appium"
   )
   WebDriver webdriver;

   @ExtendWith({SerenityJUnit5Extension.class})
   @Test
   public void verificarCreacionDeSesionAppium() throws Exception {
      Assertions.assertThat(this.webdriver).isNotNull();
      System.out.println("Esperando a que la aplicaci\u00f3n cargue la interfaz...");
      Thread.sleep(7000L);
      String pageSource = this.webdriver.getPageSource();
      System.out.println("=================================================");
      System.out.println("\u00a1SESI\u00d3N INICIADA CON \u00c9XITO!");
      int var10001 = pageSource != null ? pageSource.length() : 0;
      System.out.println("Longitud del XML obtenido: " + var10001);
      if (pageSource != null && !pageSource.isEmpty()) {
         PrintStream var10000 = System.out;
         String var2 = pageSource.substring(0, Math.min(pageSource.length(), 300));
         var10000.println("Fragmento del XML:\n" + var2 + "...");
      }

      System.out.println("=================================================");
      Assertions.assertThat(pageSource).isNotEmpty();
   }
}