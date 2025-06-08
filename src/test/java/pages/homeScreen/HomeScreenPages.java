package pages.homeScreen;

import framework.SeleniumUtils;
import org.openqa.selenium.By;

public class HomeScreenPages {

    SeleniumUtils seleniumUtils;
    public HomeScreenPages(SeleniumUtils seleniumUtils)
    {
        this.seleniumUtils=seleniumUtils;
    }

    //Page Object Model was founded by Martin Fowler
    //Page Object Model --> We will declare the elements here and the respective code for handling the elements
    //In a Page Object Model, we will never write any business logic or any assertions

    private By btn_BankManagerLogin= By.xpath("//button[text()='Bank Manager Login']");
    private By btn_CustomerLogin=By.xpath("//button[text()='Customer Login']");
    private By btn_Home=By.xpath("//button[text()='Home']");

    /***************************************************************************************************************************/

    public void clickOnBankManager()
    {
        seleniumUtils.clickOnElement(btn_BankManagerLogin,"Bank Manager Login");
    }

    public void clickOnCustomerLogin()
    {
        seleniumUtils.clickOnElement(btn_CustomerLogin,"Customer Login");
    }

    public void clickOnHome()
    {
        seleniumUtils.clickOnElement(btn_Home,"Home");
    }


}
