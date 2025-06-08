package tests;

import com.aventstack.extentreports.ExtentReports;
import framework.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pages.bankManager.BankManagerMenuPages;
import pages.bankManager.BankManager_AddCustomerPages;
import pages.bankManager.BankManager_CustomerPages;
import pages.bankManager.BankManager_OpenAccount;
import pages.customer.Customer_Pages;
import pages.homeScreen.HomeScreenPages;

public class BaseTest {

    protected int testCounter = 0;
    protected static WebDriver driver;
    protected SeleniumUtils seleniumUtils;
    protected Reports reports;
    protected ExtentReports extentReports;
    protected ExcelUtils excelUtils;
    private ExtentReportUtil extentReportUtil=new ExtentReportUtil();

    //Pages Objects
    protected HomeScreenPages homeScreenPages;
    protected BankManagerMenuPages bankManagerMenuPages;
    protected BankManager_OpenAccount bankManagerOpenAccount;
    protected BankManager_AddCustomerPages bankManagerAddCustomerPages;
    protected BankManager_CustomerPages bankManagerCustomerPages;
    protected Customer_Pages customerPages;


    @BeforeSuite
    @Parameters("browser")
    public void beforeExecutionOfTheCurrentSuite(String browser)
    {
        BrowserUtil.killExistingBrowsers();
        driver=BrowserUtil.getDriver(browser);
    }

    @BeforeClass
    public void beforeExecutionOfTheCurrentClass()
    {
        reports=new Reports(driver);
        seleniumUtils=new SeleniumUtils(driver,reports);
        extentReports=extentReportUtil.getExtentReport();
        excelUtils=new ExcelUtils();
    }

    @Parameters("URL")
    @BeforeMethod
    public void launchAppURL(String url)
    {
        seleniumUtils.launchApplication(url);
        homeScreenPages=new HomeScreenPages(seleniumUtils);

        bankManagerAddCustomerPages=new BankManager_AddCustomerPages(seleniumUtils);
        bankManagerMenuPages=new BankManagerMenuPages(seleniumUtils);
        bankManagerCustomerPages=new BankManager_CustomerPages(seleniumUtils);
        customerPages=new Customer_Pages(seleniumUtils);
        bankManagerOpenAccount=new BankManager_OpenAccount(seleniumUtils);
    }

    @AfterClass
    public void afterExecutionOfTheCurrentClass()
    {
//        driver.quit();
    }

    @AfterSuite
    public void afterExecutionOfTheCurrentSuite()
    {
        System.out.println("Total Number of Test Cases Executed: "+testCounter);
        extentReports.flush();
    }
}
