package framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;

import java.time.Duration;

//It is used to maintain the factory of the browsers
public class BrowserUtil {

    public static void killExistingBrowsers()
    {
        //Runtime is the library present in JAVA to execute the runtime commands

        try {
            Runtime.getRuntime().exec("TASKKILL -f -im msedgedriver.exe /T");
            Runtime.getRuntime().exec("TASKKILL -f -im chromedriver.exe /T");
            Runtime.getRuntime().exec("TASKKILL -f -im geckodriver.exe /T");
        }

        catch (Exception c4)
        {
            c4.printStackTrace();
        }
    }

    public static WebDriver getDriver(String browserName)
    {
        WebDriver driver;
        return switch (browserName.toUpperCase())
        {
            case "CHROME" -> {

                //ChromeOptions are used to customize the chrome browser as per our requirements
                ChromeOptions options=new ChromeOptions();

                //Disabling the notifications
                options.addArguments("--disable-notifications");

                //Enabling the code to run in headless mode
                //Execute the test cases without opening the browser, it will run as a background process
//                options.addArguments("--headless");

                //Disable "Chrome is being controlled by automated test software" infobar
                options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

                driver=new ChromeDriver(options);

                //EventFiringDecorator helps the driver object to understand there is a class that is ready to listen to the events
                //or the actions that are performed on the driver object/ browser
                EventFiringDecorator<WebDriver> eventFiringDecorator=new EventFiringDecorator(new DriverListeners());
                driver=eventFiringDecorator.decorate(driver);

                driver.manage().window().maximize();

                //Applies a wait of 10 seconds for all the web elements that we are trying to find
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

                //Applies a wait of 300 seconds for the page to load completely
                driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(300));

                //Delete all the existing cookies
                driver.manage().deleteAllCookies();

                yield driver;
            }

            case "FIREFOX" -> {

                driver=new FirefoxDriver();

                driver.manage().window().maximize();

                //Applies a wait of 10 seconds for all the web elements that we are trying to find
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

                //Applies a wait of 300 seconds for the page to load completely
                driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(300));

                //Delete all the existing cookies
                driver.manage().deleteAllCookies();

                yield driver;
            }

            case "EDGE" ->
            {

                //Edgeoptions are used to customize the edge browser as per our requirements
                EdgeOptions options=new EdgeOptions();

                //Disabling the notifications
                options.addArguments("--disable-notifications");

                //Enabling the code to run in headless mode
                //Execute the test cases without opening the browser, it will run as a background process
                options.addArguments("--headless");

                //Disable "Chrome is being controlled by automated test software" infobar
                options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

                driver=new EdgeDriver(options);

                driver.manage().window().maximize();

                //Applies a wait of 10 seconds for all the web elements that we are trying to find
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

                //Applies a wait of 300 seconds for the page to load completely
                driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(300));

                //Delete all the existing cookies
                driver.manage().deleteAllCookies();

                yield driver;
            }

//            case "SAFARI" -> new SafariDriver();

            default -> throw new RuntimeException("Invalid Browser Name, Please check the browser name that you have passed");
        };
    }
}
