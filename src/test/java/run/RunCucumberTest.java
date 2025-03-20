package run;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/java/features", // Ruta a los archivos .feature
        glue = {"stepdefinition", "auxiliary.config"}, // Paquete donde están las step definitions
        plugin = {
                "pretty", // Formato legible en la consola
                "html:target/cucumber-reports.html", // Genera un reporte HTML
                "json:target/cucumber-reports.json" // Genera un reporte JSON
        }
)
public class RunCucumberTest extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false) // Para ejecutar pruebas en paralelo, cambia a true
    public Object[][] scenarios() {
        return super.scenarios();
    }
}