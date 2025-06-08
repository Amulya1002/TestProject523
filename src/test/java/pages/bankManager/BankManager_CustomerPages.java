package pages.bankManager;

import framework.SeleniumUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;

@RequiredArgsConstructor
public class BankManager_CustomerPages {

    @NonNull
    SeleniumUtils seleniumUtils;

    private By txt_SearchCustomer= By.xpath("//input[@placeholder='Search Customer']");
    private By fld_Rows=By.xpath("//table/tbody/tr");

    /***************************************************************************************************************************/

    public void enterDataForTheSearchCustomer(String customerName)
    {
        seleniumUtils.enterText(txt_SearchCustomer,customerName,"Search Customer");
    }

    public int getNumberOfRows()
    {
        return seleniumUtils.getTotalNumberOfRecords(fld_Rows);
    }

}
