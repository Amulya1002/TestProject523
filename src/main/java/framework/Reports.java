package framework;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.google.common.io.Files;
import constants.LogStatus;
import lombok.SneakyThrows;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class Reports {

    WebDriver driver;
    public Reports(WebDriver driver)
    {
        this.driver=driver;
    }

    @SneakyThrows
    public String captureScreenshot(String... filePath)
    {
        File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        File dest=new File(ProjectUtils.getScreenshotPath(filePath)); //Image will be saved in the Project Folder

        Files.copy(src,dest);

        return dest.getPath();
    }

    public String captureScreenshotInTheFormOfBase64()
    {
        String base64Image=((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);

        return base64Image;
    }

    @SneakyThrows
    public void logReportsToTheHTMLFile(LogStatus logStatus,String content, ExtentTest extentTest)
    {
        switch (logStatus)
        {
            case PASS -> extentTest.log(Status.PASS,content);
            case PASS_SCREENSHOT -> extentTest.log(Status.PASS,content, MediaEntityBuilder.createScreenCaptureFromBase64String(captureScreenshotInTheFormOfBase64()).build());
            case FAIL -> extentTest.log(Status.FAIL,content);
            case FAIL_SCREENSHOT -> extentTest.log(Status.FAIL,content, MediaEntityBuilder.createScreenCaptureFromBase64String(captureScreenshotInTheFormOfBase64()).build());
            case SKIP -> extentTest.log(Status.SKIP,content);
            case SKIP_SCREENSHOT -> extentTest.log(Status.SKIP,content, MediaEntityBuilder.createScreenCaptureFromBase64String(captureScreenshotInTheFormOfBase64()).build());
            case INFO_BOLD -> extentTest.log(Status.INFO, MarkupHelper.createLabel(content, ExtentColor.AMBER));
            case INFO -> extentTest.log(Status.INFO,content);
            case INFO_SCREENSHOT -> extentTest.log(Status.INFO,content,MediaEntityBuilder.createScreenCaptureFromBase64String(captureScreenshotInTheFormOfBase64()).build());

            default -> throw new GenericExceptions(logStatus+" is not found, please check");
        }
    }
}
