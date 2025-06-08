package concepts.february6th2025;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.IOException;

public class ExtentReportsConcepts {

    public static void main(String[] args) throws IOException {

        ExtentHtmlReporter extentHtmlReporter=new ExtentHtmlReporter("SampleHTMLReport.html");

        extentHtmlReporter.config().setTheme(Theme.DARK); //Setting the theme of the report to DARK
        extentHtmlReporter.config().setReportName("Test Automation Results"); //Setting the report Name to 'Test Automation Results'
        extentHtmlReporter.config().setDocumentTitle("Extent Reports Demo");  //Setting the Title of the tab as 'Extent Reports Demo'
        extentHtmlReporter.config().setTimeStampFormat("dd-MM-yyyy hh-mm-ss E"); //Enabling the time stamp in the report

        extentHtmlReporter.config().enableTimeline(true); //Enabling the timeline in the report

        //Extent
        ExtentReports reports=new ExtentReports();

        //Attaching the extentHtmlReporter to the reports
        //We are letting the extent reports know that we are using the extentHtmlReporter object to generate the HTML Report
        reports.attachReporter(extentHtmlReporter);

        //Creating the Test

        //ExtentTest --> Represents a test case in the report
        ExtentTest testCaseOne=reports.createTest("First Test Case");
        testCaseOne.pass("Test Case Passed"); //Logging to the report stating that the test case is passed

        ExtentTest testCaseTwo=reports.createTest("Second Test Case");
        testCaseTwo.fail("Test Case Failed");

        ExtentTest testCaseThree=reports.createTest("Third Test Case");

        //<b> --> Display the content in bold
        testCaseThree.skip("<b> Test Case Skipped </b>");

        ExtentTest testCaseFour=reports.createTest("Fourth Test Case");

        //Add some logs to the test case
        testCaseFour.log(Status.INFO,"Launched the application for the given URL");
        testCaseFour.log(Status.PASS,"Enter the UserName: abc");
        testCaseFour.log(Status.FAIL,"Unable to enter the password");
        testCaseFour.log(Status.SKIP,"Unable to login to the application, so skipping the execution");

        ExtentTest testCaseFive=reports.createTest("Fifth Test Case");

        //Printing JSON Data on to the report
        testCaseFive.log(Status.INFO, MarkupHelper.createCodeBlock("{\n" +
                "    \"fruit\": \"Apple\",\n" +
                "    \"size\": \"Large\",\n" +
                "    \"color\": \"Red\"\n" +
                "}", CodeLanguage.JSON));

        ExtentTest testCaseSix=reports.createTest("Sixth Test Case");

        //This is used when we want to attach the screenshot at a test level
//        testCaseSix.addScreenCaptureFromPath("SampleImages.jpg");


        //This is used when we want to add the screenshots at a log level
        testCaseSix.log(Status.INFO,"Attaching the screenshot", MediaEntityBuilder.createScreenCaptureFromPath("SampleImages.jpg").build());

        ExtentTest testCaseSeven=reports.createTest("Seventh Test Case");

        //Adding a Tabular Data to the reports

        String[][] tabularData=new String[3][3];
        tabularData[0][0]="Name";
        tabularData[0][1]="Age";
        tabularData[0][2] ="Dept";

        tabularData[1][0]="John";
        tabularData[1][1]="30";
        tabularData[1][2]="IT";

        tabularData[2][0]="Sam";
        tabularData[2][1]="25";
        tabularData[2][2]="HR";

        //.createTable will accept the data in a tabular format and it will display the data in the form of a table
        //Accepts a 2D Array as input
        testCaseSeven.log(Status.INFO,MarkupHelper.createTable(tabularData));

        ExtentTest testCaseEight=reports.createTest("Eighth Test Case");

        //.createLabel will accept the data in the form of a string and it will display the data in the form of a label
        testCaseEight.log(Status.PASS,MarkupHelper.createLabel("This is a normal test case", ExtentColor.GREEN));
        testCaseEight.log(Status.FAIL,MarkupHelper.createLabel("This is a failed test case", ExtentColor.RED));


        reports.flush(); //Generate the HTML Report
    }
}
