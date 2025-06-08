package pages.bankManager;

import framework.SeleniumUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;

@RequiredArgsConstructor
public class BankManager_OpenAccount {

    @NonNull
    public SeleniumUtils seleniumUtils;

    private By ddl_Customer= By.id("userSelect");
    private By ddl_Currency=By.id("currency");
    private By btn_Process=By.xpath("//button[text()='Process']");

    /******************************************************************************************************************************************************/

    public void selectCurrency(String currency)
    {
        seleniumUtils.selectOptionFromDropdown(ddl_Currency,currency,"Currency");
    }

    public void selectCustomer(String customerName)
    {
        seleniumUtils.selectOptionFromDropdown(ddl_Customer,customerName,"Customer");
    }

    public void clickOnProcess()
    {
        seleniumUtils.clickOnElement(btn_Process,"Process");
    }


}
