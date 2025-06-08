package concepts.january27th2025;

public class WaitConcepts {

    public static void main(String[] args) {

        //In General --> Selenium does not wait even a single second for that element to load

        //There are Two types of Waits:
        //1. Static Wait --> Thread.sleep(2000); --> It waits for 2 seconds irrespective of whatever happens
        //2. Dynamic Wait --> Implicit Wait, Explicit Wait, Fluent Wait

        //Implicit Wait -->
        // It is used at a global level
        // It is automatically applicable for all the web elements that we are trying to find
        // It is applicable only for the findElement() and findElements() methods
        // In general we use 10 seconds as the implicit wait

        //Disadvantages of Implicit Wait:
        //Cannot have separate wait times for different web elements

        //Syntax of implicit wait:
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        //Explicit wait: (We use this wait in 90 percent of the cases)
        //It is used at a specific element level
        //We have to mention which kind of condition we are waiting for
        //We have to mention the time for which we are waiting

        //Syntax of Explicit Wait:
        //WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));

        //Fluent Wait: (Less than 5 percent of the cases)
        //It is used to wait for a specific condition
        //It is used to wait for a specific element
        //It is used to wait for a specific time
        //But we can have a control over ignoring the exceptions and we can customize the polling time

        //Syntax of Fluent Wait:
        //Wait wait=new FluentWait(driver).withTimeout(Duration.ofSeconds(10)).pollingEvery(Duration.ofSeconds(2)).ignoring(NoSuchElementException.class);
    }
}
