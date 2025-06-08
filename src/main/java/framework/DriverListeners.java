package framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

public class DriverListeners implements WebDriverListener {
    @Override
    public void beforeGet(WebDriver driver, String url) {
        System.out.println("Launching the application for the URL: "+url);
    }

    @Override
    public void afterGet(WebDriver driver, String url) {
        System.out.println("Launched the Application for the URL: "+url);
    }

    @Override
    public void beforeClick(WebElement element) {
        System.out.println("Clicking on the element: "+element);
    }

    @Override
    public void afterClick(WebElement element) {
        System.out.println("Clicked on the element: "+element);
    }

    @Override
    public void beforeSendKeys(WebElement element, CharSequence... keysToSend) {
        System.out.println("Entering the data: "+keysToSend+" in the element: "+element);
    }

    @Override
    public void afterSendKeys(WebElement element, CharSequence... keysToSend) {
        System.out.println("Entered the data: "+keysToSend+" in the element: "+element);
    }
}
