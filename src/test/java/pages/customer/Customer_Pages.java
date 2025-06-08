package pages.customer;

import framework.SeleniumUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;

@RequiredArgsConstructor
public class Customer_Pages {

    @NonNull
    public SeleniumUtils seleniumUtils;

    private By ddl_Customer= By.id("userSelect");
    private By btn_Login=By.xpath("//button[normalize-space(text())='Login']");
    private By ddl_Account=By.id("accountSelect");

    private By btn_Deposit=By.xpath("//button[@ng-click='deposit()']");
    private By btn_Withdrawal=By.xpath("//button[@ng-click='withdrawl()']");
    private By btn_Transactions=By.xpath("//button[@ng-click='transactions()']");

    private By txt_Amount=By.xpath("//input[@placeholder='amount']");
    private By btn_WithDrawAmount=By.xpath("//button[text()='Withdraw']");
    private By btn_DepositAmount=By.xpath("//button[text()='Deposit']");

    /***********************************************************************************************************************************************************/

    public void selectCustomer(String customerName)
    {
        seleniumUtils.selectOptionFromDropdown(ddl_Customer,customerName,"Customer Name");
    }

    public void selectAccount(String accountNumber)
    {
        seleniumUtils.selectOptionFromDropdown(ddl_Account,accountNumber,"Account Number");
    }

    public void clickOnLogin()
    {
        seleniumUtils.clickOnElement(btn_Login,"Login");
    }

    public void clickOnDeposit()
    {
        seleniumUtils.clickOnElement(btn_Deposit,"Deposit");
    }

    public void clickOnWithdrawal()
    {
        seleniumUtils.clickOnElement(btn_Withdrawal,"Withdrawal");
    }

    public void clickOnTransactions()
    {
        seleniumUtils.clickOnElement(btn_Transactions,"Transactions");
    }

    public void enterAmount(String amount)
    {
        seleniumUtils.enterText(txt_Amount,amount,"Amount");
    }

    public void clickOnWithdrawAmount()
    {
        seleniumUtils.clickOnElement(btn_WithDrawAmount,"Withdraw");
    }

    public void clickOnDepositAmount()
    {
        seleniumUtils.clickOnElement(btn_DepositAmount,"Deposit");
    }


}
