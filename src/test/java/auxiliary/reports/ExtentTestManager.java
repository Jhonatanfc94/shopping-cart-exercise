package auxiliary.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import java.util.HashMap;
import java.util.Map;
public class ExtentTestManager {
    static Map<Integer, ExtentTest> extentTestMap = new HashMap<>();
    static ExtentReports extent = ExtentManager.getInstance();
    public static synchronized ExtentTest getTest() {
        return extentTestMap.get((int) Thread.currentThread().getId());
    }
    public static synchronized void endTest() {
        extent.flush();
    }
    public static synchronized ExtentTest startTest(String testName, String description, String category) {
        ExtentTest test = extent.createTest(testName,description).assignCategory(category);
        extentTestMap.put((int) Thread.currentThread().getId(), test);
        return test;
    }
}