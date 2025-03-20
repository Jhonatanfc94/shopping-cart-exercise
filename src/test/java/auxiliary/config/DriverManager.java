package auxiliary.config;

import org.openqa.selenium.WebDriver;

public class DriverManager {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void setDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                driver.set(DriverCapabilities.chromeDriver());
                break;
            case "firefox":
                driver.set(DriverCapabilities.firefoxDriver());
                break;
            case "edge":
                driver.set(DriverCapabilities.edgeDriver());
                break;
            default:
                throw new IllegalArgumentException("Navegador no soportado: " + browser);
        }
        System.out.println("Driver inicializado para el navegador: " + browser);
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
            System.out.println("Driver cerrado correctamente.");
        }
    }
}