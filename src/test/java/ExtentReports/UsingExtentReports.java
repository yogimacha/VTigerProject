package ExtentReports;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class UsingExtentReports {
	@Test
	public void Demo() {
		
		//Configure the report
		ExtentSparkReporter spark=new ExtentSparkReporter("./AdvanceReport/demo.html");
		spark.config().setDocumentTitle("Demo test");
		spark.config().setReportName("Demo Report");
		spark.config().setTheme(Theme.DARK);
		
		//Set Environment configuration
		ExtentReports report=new ExtentReports();
		report.attachReporter(spark);
		report.setSystemInfo("OS Version", "windows-11");
		report.setSystemInfo("Browser version", "chrome-135");
		ExtentTest test=report.createTest("Demo Test Created");
		
		
		
		
		
		WebDriver driver= new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.get("https://www.facebook.com/");
		 test.log(Status.INFO,"Navigated to FB application");

			driver.findElement(By.xpath("//input[@id='email']")).sendKeys("Hello");
			test.log(Status.PASS, "UserName Entered");
			
			driver.findElement(By.xpath("//input[@id='pass']")).sendKeys("Hello@123");
			test.log(Status.FAIL, "Password Entered");
			
			
			driver.quit();
			report.flush();
	}

}
