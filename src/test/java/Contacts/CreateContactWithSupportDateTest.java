package Contacts;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.GenericUtilities.Excel_Utility;
import com.GenericUtilities.Java_Utility;
import BaseClassUtilitiy.BaseClass;
import POMpage.ContactDetailPompage;
import POMpage.ContactPomPage;
import POMpage.CreateNewContactPompage;
import POMpage.HomePomPage;
@Listeners(ListenersUtility.Listeners.class)

public class CreateContactWithSupportDateTest extends BaseClass{
	//@Parameters("browser")
	@Test(groups="smoke")

	public void createConwithSupportdateTest() throws Exception {
		// Fetch Data from Property File
//		Property_Utility pro = new Property_Utility();
//		//String browser = pro.FetchDataFromProFile("browser");
//		String UrL = pro.FetchDataFromProFile("URL");
//		String un = pro.FetchDataFromProFile("USERNAME");
//		String pwsd = pro.FetchDataFromProFile("PASSWORD");
//		String timeouts = pro.FetchDataFromProFile("TIME");

		// Fetch data from excel file
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
//			driver = new ChromeDriver();
//		}

		// maximize the window
		driver.manage().window().maximize();

//		// implicity wait
//		long time = Long.parseLong(timeouts);
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(time));
//
//		// navigate to Vtiger appln
//		driver.get(UrL);
//
//		// Login to Vtiger appln
//		LoginPompage l = new LoginPompage(driver);
//		l.login(un, pwsd);

		// Identify username textfield and pass the text
		// driver.findElement(By.xpath("//input[@name='user_name']")).sendKeys(un);

		// Identify password textfield and pass the text
		// driver.findElement(By.xpath("//input[@name='user_password']")).sendKeys(pwsd);

		// Identify login button and click on it
		// driver.findElement(By.xpath("//input[@id='submitButton']")).click();

		// Identify contact tab in home page and click
		// driver.findElement(By.linkText("Contacts")).click();
		HomePomPage home=new HomePomPage(driver);
		Object exp_home = home.getHeader().contains("Home");
		SoftAssert soft=new SoftAssert();
		soft.assertEquals(exp_home, true);
		home.getCont_tab();

		// identify plus button and click
		// driver.findElement(By.xpath("//img[@title='Create Contact...']")).click();
		ContactPomPage con = new ContactPomPage(driver);
		con.getPlusicon();

		String startdate = j_util.getCurrentDate();

		// driver.findElement(By.name("lastname")).sendKeys(conname);
		CreateNewContactPompage newcon = new CreateNewContactPompage(driver);
		newcon.getLastname_TF(conname);
		newcon.getConStartDate_TF().clear();
		newcon.getConStartDate_TF().sendKeys(startdate);

		// specify start and end support date

		// WebElement start_dateTF = driver.findElement(By.name("support_start_date"));
		String enddate = j_util.getDateAftergivenDays(30);

		// start_dateTF.sendKeys(startdate);
		newcon.getConEndDate_TF().clear();
		newcon.getConEndDate_TF().sendKeys(enddate);

		// WebElement end_dateTF = driver.findElement(By.name("support_end_date"));
		// end_dateTF.clear();
		// end_dateTF.sendKeys(enddate);

		newcon.getSaveBtn();

		// driver.findElement(By.xpath("(//input[@title='Save [Alt+S]']) [1]")).click();
		// verify actual contact name with expected contact name
		// WebElement header = driver.findElement(By.xpath("//span[contains(text(),'
		// Contact Information')]"));
		ContactDetailPompage condetail = new ContactDetailPompage(driver);
		boolean header1 = condetail.getHeader().contains(conname);
		Assert.assertEquals(header1, true);
		
		
		
//		if (header.contains(conname)) {
//			Reporter.log(conname + "Test Pass");
//		} else {
//			Reporter.log("org not created");
//		}

		// verify start supp date and end support date
		boolean actstrtdate = condetail.getVerifyStartdate().contains(startdate);
		Assert.assertEquals(actstrtdate, true);
		
		
		
		
		
		
		
		
//		 WebElement actstrtdate = driver.findElement(By.id("dtlview_Support Start
//		 Date"));
//		if (actstrtdate.contains(startdate)) {
//			Reporter.log("contact created with start date");
//		} else {
//			Reporter.log("contact not created with start date");
//		}
		boolean actenddate = condetail.getVerifyEnddate().contains(enddate);
		Assert.assertEquals(actenddate, true);
		
		
		
		
		
		
//		 WebElement actenddate = driver.findElement(By.id("dtlview_Support End
//		 Date"));
//		if (actenddate.contains(enddate)) {
//			Reporter.log("contact created with end date");
//		} else {
//			Reporter.log("contact not created with end date");
//		}

		// Click on contact tab and delete the created contacts
		// driver.findElement(By.linkText("Contacts")).click();
		home.getCont_tab();
		driver.findElement(
				By.xpath("//a[text()='" + conname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();

  		Thread.sleep(2000);

		// Handle the popup
		driver.switchTo().alert().accept();
		// Logout of the appln
		// WebElement admin =
		// driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
//		WebElement admin = home.getAdmin_icon();
//		// Actions act = new Actions(driver);
//		// act.moveToElement(admin).perform();
//		WebDriver_Utility w_util = new WebDriver_Utility();
//		w_util.actionMouseHovering(driver, admin);
//		// driver.findElement(By.linkText("Sign Out")).click();
//		home.getSignout();
//
//		// close the browser
//		w_util.quitTheBrowser(driver);
		soft.assertAll();

	}

}