package auxiliary.reports;

import com.aventstack.extentreports.Status;
import org.apache.commons.lang3.StringUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    public void onStart(ITestContext context) {
        String browser = context.getCurrentXmlTest().getParameter("browserName");
        if (browser != null) {
            System.setProperty("browserName", browser);
        }
        System.out.println("*** Test Suite " + context.getSuite() + " started ***");
    }

    public void onFinish(ITestContext context) {
        System.out.println("*** Test Suite " + context.getName() + " ending ***");
        ExtentTestManager.endTest();
        ExtentManager.getInstance().flush();
    }

    public void onTestStart(ITestResult result) {
        System.out.println("*** Running test method " + result.getMethod().getMethodName());
        String testClass = result.getTestClass().getName();
        ExtentTestManager.startTest(StringUtils.substringAfterLast(testClass, ".") + " - " + result.getMethod().getMethodName(),result.getMethod().getDescription(),result.getTestContext().getName());
    }

    public void onTestSuccess(ITestResult result) {
        System.out.println("*** Executed " + result.getMethod().getMethodName() + " test successfully");
        ExtentTestManager.getTest().pass("Test passed");
    }

    public void onTestFailure(ITestResult result) {
        System.out.println("*** Test execution " + result.getMethod().getMethodName() + " failed");
        ExtentTestManager.getTest().fail(result.getThrowable());
    }

    public void onTestSkipped(ITestResult result) {
        System.out.println("*** Test " + result.getMethod().getMethodName() + " skipped");
        ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped");
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("*** Test failed but within percentage % " + result.getMethod().getMethodName());
    }
}