package tests;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListeners implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Test Execution Started");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test Execution Completed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test Execution Failed");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Test Execution Skipped");
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        System.out.println("Test Execution failed as the execution is not completed within the time");
    }
}
