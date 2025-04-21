package ListenersUtility;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.GenericUtilities.ClassObject_Utility;
import com.GenericUtilities.Java_Utility;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import BaseClassUtilitiy.BaseClass;

public class Listeners implements ITestListener, ISuiteListener {

	public ExtentReports report = null;
	public static ExtentTest test = null;

	@Override
	public void onStart(ISuite suite) {
		Reporter.log("Report configuration", true);
		// test.log(Status.INFO, "On Suite Execution started");
		String timeStamp = Java_Utility.getCurrentTimestamp();

		// Configure the report
		ExtentSparkReporter spark = new ExtentSparkReporter("./AdvanceReport/Report" + timeStamp + ".html");
		spark.config().setDocumentTitle("VTiger suite Execution Report");
		spark.config().setReportName("Vtiger Report");
		spark.config().setTheme(Theme.DARK);

		// Set Environment configuration
		report = new ExtentReports();
		report.attachReporter(spark);
		report.setSystemInfo("OS Version", "windows-11");
		report.setSystemInfo("Browser version", "chrome-135");
		test = report.createTest("VTiger RunTime Events");
		ClassObject_Utility.setTest(test);

	}

	@Override
	public void onFinish(ISuite suite) {
		Reporter.log("Report BackUp", true);
		report.flush();
	}

	@Override
	public void onTestStart(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		Reporter.log(testName + "--Started--", true);
		test = report.createTest(result.getMethod().getMethodName());
		test.log(Status.INFO, result.getMethod().getMethodName() + "---Started----");

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		Reporter.log(testName + "--Success--", true);
		test = report.createTest(testName);
		test.log(Status.INFO, "" + testName + "---Success----");

	}

	@Override
	public void onTestFailure(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		Reporter.log(testName + "----Failed----", true);

		TakesScreenshot ts = (TakesScreenshot) BaseClass.static_Driver;
		String filePath = ts.getScreenshotAs(OutputType.BASE64);
		// File src=ts.getScreenshotAs(OutputType.FILE);
		// String time=new Date().toString().replace(" ","-").replace(":","_");
		String timeStamp = Java_Utility.getCurrentTimestamp();
		test.addScreenCaptureFromBase64String(filePath, testName + "_" + timeStamp);

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		Reporter.log(result.getMethod().getMethodName() + "--Skipped--", true);
		test.log(Status.INFO, result.getMethod().getMethodName() + "--Skipped--");

	}

}
