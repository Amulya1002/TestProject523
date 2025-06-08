package framework;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportUtil {

    public ExtentReports getExtentReport()
    {
        ExtentHtmlReporter extentHtmlReporter=new ExtentHtmlReporter("SampleHTMLReport.html");

        extentHtmlReporter.config().setTheme(Theme.DARK); //Setting the theme of the report to DARK
        extentHtmlReporter.config().setReportName("Test Automation Results"); //Setting the report Name to 'Test Automation Results'
        extentHtmlReporter.config().setDocumentTitle("Extent Reports Demo");  //Setting the Title of the tab as 'Extent Reports Demo'
        extentHtmlReporter.config().setTimeStampFormat("dd-MM-yyyy hh-mm-ss E"); //Enabling the time stamp in the report

        extentHtmlReporter.config().enableTimeline(true); //Enabling the timeline in the report

        ExtentReports reports=new ExtentReports();
        reports.attachReporter(extentHtmlReporter);

        return reports;
    }
}
