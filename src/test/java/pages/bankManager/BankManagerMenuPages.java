package pages.bankManager;

import framework.SeleniumUtils;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;

//@AllArgsConstructor //Automatically it will generate a parmaterized constructor
////based on the variables below

@RequiredArgsConstructor //Generates a Constructor only for the required variables
public class BankManagerMenuPages {

    @NonNull //Mentioning it specfically that it should not be a null value
    SeleniumUtils seleniumUtils;

    private By btn_AddCustomer= By.xpath("//button[normalize-space(text())='Add Customer']");
    private By btn_OpenAccount=By.xpath("//button[normalize-space(text())='Open Account']");
    private By btn_Customers=By.xpath("//button[normalize-space(text())='Customers']");

    /***********************************************************************************************************************/

    public void clickOnAddCustomer()
    {
        seleniumUtils.clickOnElement(btn_AddCustomer,"Add Customer");
    }

    public void clickOnOpenAccount()
    {
        seleniumUtils.clickOnElement(btn_OpenAccount,"Open Account");
    }

    public void clickOnCustomers()
    {
        seleniumUtils.clickOnElement(btn_Customers,"Customers");
    }

}
