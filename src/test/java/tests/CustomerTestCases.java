package tests;

import com.aventstack.extentreports.ExtentTest;
import constants.LogStatus;
import dataProviders.DataProviders;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class CustomerTestCases extends BaseTest {

    @Test(description = "Navigated to Customer Login Screen",priority = 1)
    public void checkIfCustomerLoginScreenIsNavigated()
    {
        ExtentTest customerLoginNavigation=extentReports.createTest("Customer Login Navigation");

        homeScreenPages.clickOnCustomerLogin();

        reports.logReportsToTheHTMLFile(LogStatus.INFO_SCREENSHOT,"Navigated to Customer Login Screen",customerLoginNavigation);

        Assert.assertTrue(seleniumUtils.checkIfTextIsPresent("Your Name :"));
    }

    @Test(description = "Login to the Customer",priority = 2,dataProvider = "fetchingDataForTestExecution",dataProviderClass = DataProviders.class)
    public void loginToTheCustomer(Map<String,String> data)
    {
        ExtentTest customerLogin=extentReports.createTest("Login to the Customer Screen");

        homeScreenPages.clickOnCustomerLogin();
        reports.logReportsToTheHTMLFile(LogStatus.INFO_SCREENSHOT,"Navigated to Customer Login Screen",customerLogin);

        customerPages.selectCustomer(data.get("First_Name")+" "+data.get("Last_Name"));
        reports.logReportsToTheHTMLFile(LogStatus.INFO_SCREENSHOT,"Selecting the Customer",customerLogin);

        customerPages.clickOnLogin();
        reports.logReportsToTheHTMLFile(LogStatus.INFO_SCREENSHOT,"Clicked on Login button",customerLogin);

        Assert.assertTrue(seleniumUtils.checkIfTextIsPresent("Account Number :"));
        Assert.assertTrue(seleniumUtils.checkIfTextIsPresent("Balance :"));
        Assert.assertTrue(seleniumUtils.checkIfTextIsPresent("Currency :"));
    }

    @Test(description = "Deposit the Amount",priority = 3,dataProvider = "fetchingDataForTestExecution",dataProviderClass = DataProviders.class)
    public void depositTheAmount(Map<String,String> data)
    {
        ExtentTest depositAmount=extentReports.createTest("Depositing the Amount");

        homeScreenPages.clickOnCustomerLogin();
        reports.logReportsToTheHTMLFile(LogStatus.INFO_SCREENSHOT,"Navigated to Customer Login Screen",depositAmount);

        customerPages.selectCustomer(data.get("First_Name")+" "+data.get("Last_Name"));
        reports.logReportsToTheHTMLFile(LogStatus.INFO_SCREENSHOT,"Selecting the Customer",depositAmount);

        customerPages.clickOnLogin();
        reports.logReportsToTheHTMLFile(LogStatus.INFO_SCREENSHOT,"Clicked on Login Button",depositAmount);

        customerPages.clickOnDeposit();
        reports.logReportsToTheHTMLFile(LogStatus.INFO_SCREENSHOT,"Clicked on Deposit Button",depositAmount);

        customerPages.selectAccount(data.get("Account Number"));
        reports.logReportsToTheHTMLFile(LogStatus.INFO_SCREENSHOT,"Selected the Account Number as: "+data.get("Account Number"),depositAmount);

        customerPages.enterAmount(data.get("Deposit_Amount"));
        reports.logReportsToTheHTMLFile(LogStatus.INFO_SCREENSHOT,"Depositing the amount of 400000",depositAmount);

        customerPages.clickOnDepositAmount();
        reports.logReportsToTheHTMLFile(LogStatus.INFO_SCREENSHOT,"Clicked on Deposit Amount",depositAmount);

        Assert.assertTrue(seleniumUtils.checkIfTextIsPresent("Deposit Successful"));
    }


    @Test(description = "Withdraw the Amount",priority = 4,dataProvider = "fetchingDataForTestExecution",dataProviderClass = DataProviders.class)
    public void withdrawTheAmount(Map<String,String> data)
    {
        ExtentTest withdrawAmount=extentReports.createTest("Withdraw the Amount");

        homeScreenPages.clickOnCustomerLogin();
        reports.logReportsToTheHTMLFile(LogStatus.INFO_SCREENSHOT,"Navigated to Customer Login Screen",withdrawAmount);

        customerPages.selectCustomer(data.get("First_Name")+" "+data.get("Last_Name"));
        reports.logReportsToTheHTMLFile(LogStatus.INFO_SCREENSHOT,"Selecting the Customer",withdrawAmount);

        customerPages.clickOnLogin();
        reports.logReportsToTheHTMLFile(LogStatus.INFO_SCREENSHOT,"Clicked on Login Button",withdrawAmount);

        customerPages.clickOnWithdrawal();
        reports.logReportsToTheHTMLFile(LogStatus.INFO_SCREENSHOT,"Clicked on Withdrawal",withdrawAmount);

        customerPages.selectAccount(data.get("Account Number"));
        reports.logReportsToTheHTMLFile(LogStatus.INFO_SCREENSHOT,"Selected the Account Number as: "+data.get("Account Number"),withdrawAmount);

        customerPages.enterAmount(data.get("Withdraw_Amount"));
        reports.logReportsToTheHTMLFile(LogStatus.INFO_SCREENSHOT,"Withdrawing the amount of 4000",withdrawAmount);

        customerPages.clickOnWithdrawAmount();
        reports.logReportsToTheHTMLFile(LogStatus.INFO_SCREENSHOT,"Clicked on WithDraw Amount",withdrawAmount);

        Assert.assertTrue(seleniumUtils.checkIfTextIsPresent("Transaction successful"));
    }
}
