package framework;

import lombok.SneakyThrows;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

//Performing All Selenium Related Actions
public class SeleniumUtils {

    WebDriver driver;
    ElementUtils elementUtils;
    Actions a1;
    Reports reports;

    public SeleniumUtils(WebDriver driver, Reports reports)
    {
        this.driver=driver;
        elementUtils=new ElementUtils(driver);
        a1=new Actions(driver);
        this.reports=reports;
    }

    public boolean checkIfElementIsClickable(By by,int waitTime)
    {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));

            //Return Type of elementToBeClickable is WebElement
            wait.until(ExpectedConditions.elementToBeClickable(by));

            return true;
        }

        catch (Exception e)
        {
            return false;
        }
    }

    public boolean checkIfElementIsClickable(WebElement element,int waitTime)
    {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));

            //Return Type of elementToBeClickable is WebElement
            wait.until(ExpectedConditions.elementToBeClickable(element));

            return true;
        }

        catch (Exception e)
        {
            return false;
        }
    }

    public void clickOnElement(WebElement element,String labelName)
    {
        if(checkIfElementIsClickable(element,10))
        {
            element.click();
        }

        else
        {
            throw new GenericExceptions(labelName+" is not clickable on the page");
        }
    }

    public void clickOnElement(By by,String labelName)
    {
        if(checkIfElementIsClickable(by,10))
            elementUtils.findElement(by).click();

        else
        {
            reports.captureScreenshot();
            throw new GenericExceptions(labelName+" is not clickable on the page");
        }

    }


    public boolean checkIfElementIsPresent(WebElement element,int waitTime)
    {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));

            //Return Type of presenceOfElementLocated is WebElement
            wait.until(ExpectedConditions.visibilityOf(element));

            return true;
        }

        catch (Exception e)
        {
            return false;
        }
    }

    public boolean checkIfElementIsPresent(By by,int waitTime)
    {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));

            //Return Type of presenceOfElementLocated is WebElement
            wait.until(ExpectedConditions.presenceOfElementLocated(by));

            return true;
        }

        catch (Exception e)
        {
            return false;
        }
    }

    public void enterText(WebElement element,String text,String labelName)
    {
        if(checkIfElementIsPresent(element,10))
            element.sendKeys(text);

        else
        {
            reports.captureScreenshot();
            throw new GenericExceptions("Unable to enter text in "+labelName+" as the element is not present on the page");
        }
    }

    public void enterText(By by, String text,String labelName) {

        if(checkIfElementIsPresent(by,10))
            elementUtils.findElement(by).sendKeys(text);

        else
        {
            reports.captureScreenshot();
            throw new GenericExceptions("Unable to enter text in "+labelName+" as the element is not present on the page");
        }
    }

    public void enterText(WebElement element, Keys keys) {
        element.sendKeys(keys);
    }

    public void enterText(By by,Keys keys) {
        elementUtils.findElement(by).sendKeys(keys);
    }

    public String launchApplication(String url)
    {
        if(url==null)
            throw new GenericExceptions("URL cannot be null, please check the value that you have passed");

        else if(url.indexOf("http") == -1)
            throw new GenericExceptions("Invalid URL, please check the URL that you have passed");

        else if(url.startsWith("http")==false)
            throw new GenericExceptions("URL is not starting with https or http, please check the URL that you have passed");

        //Always remember that the URL should start with either http or https
        //else it will throw an invalid argument exception

        driver.manage().window().maximize(); //Maximizing the browser window
        driver.get(url);
        return driver.getWindowHandle();
    }

    public void checkIfElementIsDisplayed(WebElement element,String labelName)
    {
        if(element.isDisplayed())
            System.out.println(labelName+" is displayed on the page");

        else
            System.out.println(labelName+" is not displayed on the page");
    }

    public void checkIfElementIsDisplayed(By by,String labelName)
    {
        if(driver.findElement(by).isDisplayed())
            System.out.println(labelName+" is displayed on the page");

        else
            System.out.println(labelName+" is not displayed on the page");
    }

    public void switchToParticularWindow(String windowRefID)
    {
        driver.switchTo().window(windowRefID);
    }

    public void switchToParticularWindow(int windowIndex)
    {
        List<String> listOfWindowHandles=new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(listOfWindowHandles.get(windowIndex));
    }

    public void switchToParticularWindowAndCloseThatTabOrWindow(int windowIndex)
    {
        List<String> listOfWindowHandles=new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(listOfWindowHandles.get(windowIndex)).close();
    }

    public String createNewTab(String url)
    {
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(url);

        return driver.getWindowHandle();
    }

    public String createNewWindow(String url)
    {
        driver.switchTo().newWindow(WindowType.WINDOW);
        driver.get(url);

        return driver.getWindowHandle();
    }

    @SneakyThrows
    public void switchToNewWindow()
    {
        Set<String> handles=driver.getWindowHandles();

        for(String s:handles)
        {
            driver.switchTo().window(s);
        }
    }

    @SneakyThrows
    public void closeWindow(String urlOrTitle)
    {
        Set<String> handles=driver.getWindowHandles();

        for(String s:handles)
        {
            driver.switchTo().window(s);
            Thread.sleep(2000);

            if(driver.getCurrentUrl().contains(urlOrTitle) || driver.getTitle().contains(urlOrTitle))
            {
                Thread.sleep(2000);
                driver.close();
            }
        }
    }

    public String getElementText(WebElement element,String labelName)
    {
        if(checkIfElementIsPresent(element,10))
            return element.getText();

        else
        {
            reports.captureScreenshot();
            throw new GenericExceptions(labelName+" is not present on the page");
        }
    }

    public String getElementText(By by)
    {
        if(checkIfElementIsPresent(by,10))
            return elementUtils.findElement(by).getText();

        else
        {
            reports.captureScreenshot();
            throw new GenericExceptions("Element is not present on the page");
        }
    }

    public void performMouseHover(WebElement element,String labelName)
    {
        if(checkIfElementIsPresent(element,10))
            a1.moveToElement(element).build().perform();
        else
        {
            reports.captureScreenshot();
            throw new GenericExceptions(labelName+" is not present on the page");
        }
    }

    public void performMouseHover(By by)
    {
        if(checkIfElementIsPresent(by,10)) {
            a1.moveToElement(elementUtils.findElement(by)).build().perform();
        }

        else
        {
            reports.captureScreenshot();
            throw new GenericExceptions("Unable to perform Mouse Hover Actions");
        }
    }

    public void performMouseHover(String locator, String value)
    {
        a1.moveToElement(elementUtils.findElement(locator,value)).build().perform();
    }

    public void performDragAndDrop(WebElement source, WebElement target)
    {
        a1.dragAndDrop(source,target).build().perform();
    }

    public void performDragAndDrop(By source, By target)
    {
        a1.dragAndDrop(elementUtils.findElement(source), elementUtils.findElement(target)).build().perform();
    }

    public void performRightClick(WebElement element)
    {
        a1.contextClick(element).build().perform();
    }

    public void performRightClick(By by)
    {
        a1.contextClick(elementUtils.findElement(by)).build().perform();
    }

    public void acceptAlert()
    {
        driver.switchTo().alert().accept();
    }

    public void dismissAlert()
    {
        driver.switchTo().alert().dismiss();
    }

    public void sendKeysToAlert(String text)
    {
        driver.switchTo().alert().sendKeys(text);
    }

    public String getTextFromAlert()
    {
        return driver.switchTo().alert().getText();
    }

    public void scrollPageDown(int pixels)
    {
        ((JavascriptExecutor)driver).executeScript("window.scrollBy(0,"+pixels+")");
    }

    public void scrollPage(int x, int y)
    {
        ((JavascriptExecutor)driver).executeScript("window.scrollBy("+x+","+y+")");
    }

    public void scrollPageUp(int pixels)
    {
        ((JavascriptExecutor)driver).executeScript("window.scrollBy(0,-"+pixels+")");
    }

    public void scrollPageToTheBottom()
    {
        ((JavascriptExecutor)driver).executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }

    public void scrollPageToTheTop()
    {
        ((JavascriptExecutor)driver).executeScript("window.scrollBy(0,-document.body.scrollHeight)");
    }

    public boolean checkIfTheGivenFrameIsPresent(String nameOrID,int waitTime)
    {
        try {
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(waitTime));
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(nameOrID));
            return true;
        }

        catch (Exception e)
        {
            return false;
        }
    }

    public void switchToFrame(String nameOrID)
    {
        if(checkIfTheGivenFrameIsPresent(nameOrID,4))
            driver.switchTo().frame(nameOrID);
        else
            throw new GenericExceptions("Frame with name or ID "+nameOrID+" is not present on the page");
    }

    public void switchToFrame(int index)
    {
        driver.switchTo().frame(index);
    }

    public void switchToParentFrame()
    {
        driver.switchTo().parentFrame();
    }

    public void switchOutOfAllFrames()
    {
        driver.switchTo().defaultContent();
    }

    public String getBodyText()
    {
        return driver.findElement(By.tagName("body")).getText();
    }

    public boolean checkIfTextIsPresent(String expectedText)
    {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            //It will match for the exact text for the given webelement --> ExpectedConditions.textToBe
            //What is the use case : we need to check whether the text is present inside the given web element

            //Function is a Functional Interface which has only one method to implement that is apply
            //We are writing our own waiting mechanism instead of using the predefined waiting mechanism

            Function<WebDriver, Boolean> containsText = (d) -> d.findElement(By.tagName("body")).getText().contains(expectedText);
            return wait.until(containsText);
        }

        catch (TimeoutException e2)
        {
            throw new GenericExceptions("Unable to find the given text in the web page for 10 seconds");
        }
    }

    public int getTotalNumberOfRecords(By by)
    {
        return elementUtils.findElements(by).size();
    }

    public void selectOptionFromDropdown(By by,String option, String label)
    {
        if(!checkIfElementIsPresent(by,5))
        {
            throw new GenericExceptions("Element: "+label+" is not found after waiting for 5 seconds");
        }
        Select s1=new Select(elementUtils.findElement(by));

        if(option.isBlank()) //If we are passing an empty option then randomly select any value
        {
            List<WebElement> optionsList=s1.getOptions();

            //Selecting a random option on the basis of index position
            s1.selectByIndex(ThreadLocalRandom.current().nextInt(optionsList.size()-1));
        }

        else {
        try {
            //Selecting a dropdown value on the basis of text:
            s1.selectByVisibleText(option);
        }

        catch (Exception e4) {
            try {
                //Selecting a dropdown value on the basis of partial text value
                s1.selectByContainsVisibleText(option);
            } catch (Exception e1) {
                try {
                    //Selecting the dropdown value on the basis of value attribute
                    s1.selectByValue(option);
                } catch (Exception e2) {
                    //Selecting the dropdown value on the basis of index position
                    s1.selectByIndex(Integer.parseInt(option));
                }
             }
          }
        }
    }
}

