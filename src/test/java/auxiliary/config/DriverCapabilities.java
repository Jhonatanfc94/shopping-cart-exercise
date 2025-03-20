package auxiliary.config;

import auxiliary.LocalConfiguration;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DriverCapabilities {
    public static WebDriver chromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--user-data-dir=/tmp/chrome-user-data-" + System.currentTimeMillis());

        WebDriver driver = new ChromeDriver(options);
        windowDimension(driver);

        if (LocalConfiguration.web.enableLocation) {
            Map<String, Object> coordinates = new HashMap<String, Object>() {{
                put("latitude", LocalConfiguration.web.defaultLatitude);
                put("longitude", LocalConfiguration.web.defaultLength);
                put("accuracy", 1);
            }};
            ((ChromeDriver) driver).executeCdpCommand("Emulation.setGeolocationOverride", coordinates);
        }

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(300));
        return driver;
    }


    public static WebDriver edgeDriver() {
        WebDriver driver = new EdgeDriver();
        windowDimension(driver);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(300));
        return driver;
    }

    public static WebDriver firefoxDriver() {
        WebDriver driver = new FirefoxDriver();
        windowDimension(driver);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(300));
        return driver;
    }

    public static WebDriver safariDriver() {
        WebDriver driver = new SafariDriver();
        windowDimension(driver);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(300));
        return driver;
    }

    public static void windowDimension(WebDriver driver){
        if(LocalConfiguration.web.enableChangeableDisplay){
            driver.manage().window().setSize(new Dimension(LocalConfiguration.web.width, LocalConfiguration.web.height));
        }else if(LocalConfiguration.web.enableModifiableRandomDisplay){
            Random rand = new Random();
            int randomResolution = rand.nextInt(LocalConfiguration.web.resolutions.length);
            int randomWidth = LocalConfiguration.web.resolutions[randomResolution][0];
            int heightRandom = LocalConfiguration.web.resolutions[randomResolution][1];
            driver.manage().window().setSize(new Dimension(randomWidth, heightRandom));
        }else{
            driver.manage().window().maximize();
        }
    }
}