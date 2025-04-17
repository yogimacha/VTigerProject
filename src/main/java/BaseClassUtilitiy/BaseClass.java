package BaseClassUtilitiy;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.GenericUtilities.ClassObject_Utility;
import com.GenericUtilities.DataBase_Utility;
import com.GenericUtilities.Property_Utility;
import com.GenericUtilities.WebDriver_Utility;
import com.aventstack.extentreports.Status;

import POMpage.HomePomPage;
import POMpage.LoginPompage;

public class BaseClass {
	
	public WebDriver driver =null;
	public static WebDriver static_Driver=null;
	DataBase_Utility db=new DataBase_Utility();
	Property_Utility pro = new Property_Utility();
	WebDriver_Utility w_util=new WebDriver_Utility();
	@BeforeSuite
	public void beforeSuite(){
	Reporter.log("Configure the DB:Connect",true);
	ClassObject_Utility.getTest().log(Status.INFO, "Configure the DB:Connect");

	db.getDataBaseConnection();
	
	}
	@BeforeTest
	public void beforeTest(){
		Reporter.log("BT:parallel Exe",true);
		ClassObject_Utility.getTest().log(Status.INFO, "BT:parallel Exe");

		
	}
//	@Parameters("browser")
	@BeforeClass
	public void beforeClass() throws IOException{
		Reporter.log("Launch the browser",true);
		ClassObject_Utility.getTest().log(Status.INFO, "Launch the browser");

		String Browser=pro.FetchDataFromProFile("browser");
		if (Browser.equals("chrome")) {

			driver = new ChromeDriver();
		} else if (Browser.equals("edge")) {

			driver = new EdgeDriver();

		} else {

			driver = new ChromeDriver();
		}
		
		static_Driver=driver;
		ClassObject_Utility.setDrriver(driver);
		
	}
	@BeforeMethod
	public void beforeMethod() throws IOException{
		Reporter.log("logout of the application",true);
		ClassObject_Utility.getTest().log(Status.INFO, "logout of the application");

		String UrL = pro.FetchDataFromProFile("URL");
		String un = pro.FetchDataFromProFile("USERNAME");
		String pwsd = pro.FetchDataFromProFile("PASSWORD");
		String timeouts = pro.FetchDataFromProFile("TIME");
		w_util.waitTillElementFound(timeouts, driver);
		LoginPompage lp=new LoginPompage(driver);
		w_util.navigateToApplication(UrL, driver);
		w_util.maximizeTheWindow(driver);
		lp.login(un, pwsd);
		
		
	}
	@AfterMethod
	public void afterMethod(){
		Reporter.log("Logout of the appln",true);
		ClassObject_Utility.getTest().log(Status.INFO, "Logout of the appln");

		HomePomPage home=new HomePomPage(driver);
		home.logout(driver);
	}
	@AfterClass
	public void afterClass(){
		Reporter.log("Close the Browser",true);
		ClassObject_Utility.getTest().log(Status.INFO, "Close the Browser");

		WebDriver_Utility wb= new WebDriver_Utility();
		wb.quitTheBrowser(driver);
		
	}
	@AfterTest
	public void afterTest(){
		Reporter.log("AT:Parllel exe",true);
		ClassObject_Utility.getTest().log(Status.INFO, "AT:Parllel exe");

	}
	@AfterSuite
	public void afterSuite(){
		Reporter.log("Close the DataBase Connection",true);
		ClassObject_Utility.getTest().log(Status.INFO, "Close the DataBase Connection");

		db.closeDataBaseConnection();
	}

}
