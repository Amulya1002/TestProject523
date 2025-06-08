package pages.bankManager;

import framework.SeleniumUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;

@RequiredArgsConstructor
public class BankManager_AddCustomerPages {

    @NonNull //For the variables which has been mentioned as non null, those variables will be part of the constructors
    SeleniumUtils seleniumUtils;

    private By txt_FirstName= By.xpath("//input[@placeholder='First Name']");
    private By txt_LastName=By.xpath("//input[@placeholder='Last Name']");
    private By txt_PostCode=By.xpath("//input[@placeholder='Post Code']");
    private By btn_AddCustomer=By.xpath("//button[text()='Add Customer']");

    /***********************************************************************************************************/

    public void enterDataIntoFirstName(String firstName)
    {
        seleniumUtils.enterText(txt_FirstName,firstName,"First Name");
    }

    public void enterDataIntoLastName(String lastName)
    {
        seleniumUtils.enterText(txt_LastName,lastName,"Last Name");
    }

    public void enterDataIntoPostalCode(String postalCode)
    {
        seleniumUtils.enterText(txt_PostCode,postalCode,"Postal Code");
    }

    public void clickOnAddCustomer()
    {
        seleniumUtils.clickOnElement(btn_AddCustomer,"Add Customer");
    }
}
