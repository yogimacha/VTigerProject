package Contacts;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.GenericUtilities.ClassObject_Utility;
import com.GenericUtilities.Excel_Utility;
import com.GenericUtilities.Java_Utility;
import com.GenericUtilities.WebDriver_Utility;
import com.aventstack.extentreports.Status;

import BaseClassUtilitiy.BaseClass;
import POMpage.ContactDetailPompage;
import POMpage.ContactPomPage;
import POMpage.CreateNewContactPompage;
import POMpage.HomePomPage;
@Listeners(ListenersUtility.Listeners.class)

public class CreateContactWithNameTest extends BaseClass {
//	@Parameters("browser")
	@Test(groups="smoke")

	public void contacttest() throws Exception {
		// fetch the data from property file
		//Property_Utility pro = new Property_Utility();

//		String browser = pro.FetchDataFromProFile("browser");
//		String UrL = pro.FetchDataFromProFile("URL");
//		String un = pro.FetchDataFromProFile("USERNAME");
//		String pwsd = pro.FetchDataFromProFile("PASSWORD");
//		String timeouts = pro.FetchDataFromProFile("TIME");

		// fetch data from excel
		ClassObject_Utility.getTest().log(Status.INFO, "Fetch data from Excel");

		Excel_Utility ex_util = new Excel_Utility();
		Java_Utility j_util = new Java_Utility();
		int random = j_util.getRandomNumber();
		String conname = ex_util.FetchDataFromExcel("Contacts", 1, 2) + random;

		// launch the browser
//		WebDriver driver = null;
//		if (Browser.equals("chrome")) {
//
//			driver = new ChromeDriver();
//		} else if (Browser.equals("edge")) {
//
//			driver = new EdgeDriver();
//		} else {
//
//			driver = new ChromeDriver();
//		}

		// maximize the window

		WebDriver_Utility w_util = new WebDriver_Utility();
//		w_util.maximizeTheWindow(driver);
//
//		// implicity wait
//		w_util.waitTillElementFound(timeouts, driver);
//		
//		// Navigate to an V-Tiger application
//		w_util.navigateToApplication(UrL, driver);
//		
//		//Login to Vtiger appln
//		LoginPompage l = new LoginPompage(driver);
//		l.login(un, pwsd);
		
		// Identify username textfield and pass the text
		//driver.findElement(By.xpath("//input[@name='user_name']")).sendKeys(un);

		// Identify password textfield and pass the text
		//driver.findElement(By.xpath("//input[@name='user_password']")).sendKeys(pwsd);

		// Identify login button and click on it
		//driver.findElement(By.xpath("//input[@id='submitButton']")).click();

		// Identify contact tab in home page and click
		//driver.findElement(By.linkText("Contacts")).click();
		HomePomPage home=new HomePomPage(driver);
		Object exp_home = home.getHeader().contains("Home");
		SoftAssert soft=new SoftAssert();
		soft.assertEquals(exp_home, true);
		home.getCont_tab();

		// identify plus button and click
		//driver.findElement(By.xpath("//img[@title='Create Contact...']")).click();
		ContactPomPage con=new ContactPomPage(driver);
		con.getPlusicon();

		//driver.findElement(By.name("lastname")).sendKeys(conname);

		//driver.findElement(By.xpath("(//input[@title='Save [Alt+S]'])[1]")).click();
		CreateNewContactPompage newcon = new CreateNewContactPompage(driver);
		newcon.getLastname_TF(conname);
		newcon.getSaveBtn();

		
//		//WebElement header = driver.findElement(By.xpath("//span[contains(text(),' Contact Information')]"));
		
		// verify actual contact name with expected contact name
		ContactDetailPompage con_details =new ContactDetailPompage(driver);
		boolean exp_result = con_details.getHeader().contains(conname);
		Assert.assertEquals(exp_result, true);
		
//		ContactDetailPompage condetail= new ContactDetailPompage(driver);
//		String header=condetail.getHeader();
//		if (header.contains(conname)) {
//			Reporter.log(conname + "Test Pass");
//		} else {
//			Reporter.log("orgname not created");
//		}
		// Click on contact tab and delete the created contacts
		//driver.findElement(By.linkText("Contacts")).click();
		
		
		home.getCont_tab();
  driver.findElement(
				By.xpath("//a[text()='" + conname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();
  
  		Thread.sleep(2000);
		// Handle the popup
		w_util.handleAlertAndAccept(driver);
		// Logout of the appln
		//WebElement admin = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
//		WebElement admin =home.getAdmin_icon();
//		
//
//		w_util.actionMouseHovering(driver, admin);
//		//driver.findElement(By.linkText("Sign Out")).click();
//		home.getSignout();
//
//		// close the browser
//		w_util.quitTheBrowser(driver);
		
		//To fail the Script 
		//Assert.fail();
		soft.assertAll();
	}

}