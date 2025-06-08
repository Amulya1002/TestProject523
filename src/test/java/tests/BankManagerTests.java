package tests;

import dataProviders.DataProviders;
import framework.ProjectUtils;
import lombok.SneakyThrows;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.homeScreen.HomeScreenPages;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class BankManagerTests extends BaseTest {

    //When the data provider returns an empty set, then that particular test case will not be executed
    @SneakyThrows
    @Test(description = "Navigate To Bank Manager Login Screen",priority = 1)
    public void navigateToBankManagerLoginScreen()
    {
        homeScreenPages.clickOnBankManager();

//        Thread.sleep(6000);
        //Assert Library is used during the validations
//        Assert.assertTrue((seleniumUtils.getBodyText().contains("Add Customer")));
        Assert.assertTrue(seleniumUtils.checkIfTextIsPresent("Add Customer"));

        excelUtils.removeCompleteDataFromExcelSheet(ProjectUtils.getExcelFilePath(),"Validate_Customers");
    }

    @Test(description = "Navigating to the Customer Creation",priority = 2,dataProvider = "fetchingDataForTestExecution",dataProviderClass = DataProviders.class)
    public void navigateToCustomerCreationScreen(Map<String,String> testData)
    {
        homeScreenPages.clickOnBankManager();

        bankManagerMenuPages.clickOnAddCustomer();

        bankManagerAddCustomerPages.enterDataIntoFirstName(testData.get("First_Name"));
        bankManagerAddCustomerPages.enterDataIntoLastName(testData.get("Last_Name"));
        bankManagerAddCustomerPages.enterDataIntoPostalCode(testData.get("Postal_Code"));
        bankManagerAddCustomerPages.clickOnAddCustomer();

        System.out.println(seleniumUtils.getTextFromAlert());

        Assert.assertTrue(seleniumUtils.getTextFromAlert().contains("Customer added successfully with customer id"));
        seleniumUtils.acceptAlert();

        excelUtils.writeDataToTheExcel(ProjectUtils.getExcelFilePath(),"Validate_Customers","First_Name",testData.get("First_Name"));
    }

    @Test(description = "Validate the Records for the Customer",priority = 3,dataProvider = "fetchingDataForTestExecution",dataProviderClass = DataProviders.class)
    public void validateTheRecordsForTheCustomer(Map<String,String> data)
    {
        homeScreenPages.clickOnBankManager();

        bankManagerMenuPages.clickOnCustomers();

        bankManagerCustomerPages.enterDataForTheSearchCustomer(data.get("First_Name"));
        int noOfRows=bankManagerCustomerPages.getNumberOfRows();

        //Only one record should be present
        Assert.assertEquals(noOfRows,1);
    }

    @Test(description = "Creating a new Account",priority = 4,dataProvider = "fetchingDataForTestExecution",dataProviderClass = DataProviders.class)
    public void createNewAccounts(Map<String,String> data)
    {
        excelUtils.writeDataToTheExcel(ProjectUtils.getExcelFilePath(),"Create_Accounts","Account_Numbers","","First_Name-"+data.get("First_Name").trim());

        homeScreenPages.clickOnBankManager();

        bankManagerMenuPages.clickOnOpenAccount();

        String customerName=data.get("First_Name").trim()+" "+data.get("Last_Name").trim();
        bankManagerOpenAccount.selectCustomer(customerName);

        double noOfAccounts=Double.parseDouble(data.get("No_Of_Accounts"));

        for(int i=0;i<noOfAccounts;i++) {
            bankManagerOpenAccount.selectCurrency(data.get("Currency").split(",")[i]);

            bankManagerOpenAccount.clickOnProcess();

            String msg = seleniumUtils.getTextFromAlert();
            String accountNumber = msg.split(":")[1];

            seleniumUtils.acceptAlert();

            //Getting the account Numbers, that is present in the excel sheet
            String existingAccountNumbers=excelUtils.readDataFromExcel(ProjectUtils.getExcelFilePath(),"Create_Accounts","Account_Numbers","First_Name-"+data.get("First_Name").trim());
            if(existingAccountNumbers.isBlank()) //If the data is not present in the cell, then write the data directly to the cell
            {
                excelUtils.writeDataToTheExcel(ProjectUtils.getExcelFilePath(),"Create_Accounts","Account_Numbers",accountNumber,"First_Name-"+data.get("First_Name").trim());
            }

            else
            {
                //If the data is present in the cell, then append the data to the existing data and write it to the cell
                excelUtils.writeDataToTheExcel(ProjectUtils.getExcelFilePath(),"Create_Accounts","Account_Numbers",existingAccountNumbers+","+accountNumber,"First_Name-"+data.get("First_Name").trim());
            }

            existingAccountNumbers=excelUtils.readDataFromExcel(ProjectUtils.getExcelFilePath(),"Create_Accounts","Account_Numbers","First_Name-"+data.get("First_Name").trim());

            List<String> listOfAccounts=Arrays.asList(existingAccountNumbers.split(","));

            String accountForTransactions=listOfAccounts.get(ThreadLocalRandom.current().nextInt(0,listOfAccounts.size()));

            excelUtils.writeDataToTheExcel(ProjectUtils.getExcelFilePath(),"Deposit_Amount","Account Number",accountForTransactions,"First_Name-"+data.get("First_Name").trim());
            excelUtils.writeDataToTheExcel(ProjectUtils.getExcelFilePath(),"Withdraw_Amount","Account Number",accountForTransactions,"First_Name-"+data.get("First_Name").trim());

            bankManagerOpenAccount.selectCustomer(customerName);
        }
    }

}
