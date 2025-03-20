package auxiliary.config;

import auxiliary.LocalConfiguration;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

    @Before
    public void setUp() {
        String browser = System.getProperty("browserName", "chrome");
        DriverManager.setDriver(browser);
    }

    @After
    public void tearDown() {
        DriverManager.quitDriver(); // Cierra el driver
    }
}
