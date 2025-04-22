package Contacts;

import java.util.Set;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
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
import POMpage.CreateNewOrgPompage;
import POMpage.HomePomPage;
import POMpage.OrgDetailPompage;
import POMpage.OrganizationPompage;

//Create Branch and push this code to vtiger_2.0
@Listeners(ListenersUtility.Listeners.class)
public class ContractsScenariosTest extends BaseClass{

	@Test
	public void contacttest() throws Exception {
		
		// fetch data from excel
		ClassObject_Utility.getTest().log(Status.INFO, "Fetch data from Excel");
		Excel_Utility ex_util = new Excel_Utility();
		Java_Utility j_util = new Java_Utility();
		int random = j_util.getRandomNumber();
		String conname = ex_util.FetchDataFromExcel("Contacts", 1, 2) + random;

		
		// maximize the window

		WebDriver_Utility w_util = new WebDriver_Utility();
		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to application");

		HomePomPage home=new HomePomPage(driver);
		Object exp_home = home.getHeader().contains("Home");
		SoftAssert soft=new SoftAssert();
		soft.assertEquals(exp_home, true);
		home.getCont_tab();

		//Click on plus icon
		ClassObject_Utility.getTest().log(Status.INFO, "Click on plus icon");

		ContactPomPage con=new ContactPomPage(driver);
		con.getPlusicon();

	
		//Create new contact
		ClassObject_Utility.getTest().log(Status.INFO, "Create new contact");

		CreateNewContactPompage newcon = new CreateNewContactPompage(driver);
		newcon.getLastname_TF(conname);
		newcon.getSaveBtn();

		
		
		// verify actual contact name with expected contact name
		ClassObject_Utility.getTest().log(Status.INFO, "verify actual contact name with expected contact name");

		ContactDetailPompage con_details =new ContactDetailPompage(driver);
		boolean exp_result = con_details.getHeader().contains(conname);
		Assert.assertEquals(exp_result, true);
	
		
		//Navigate to contact tab and delete the contact
		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to contact tab and delete the contact");

		home.getCont_tab();
  driver.findElement(
				By.xpath("//a[text()='" + conname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();
  
  		Thread.sleep(2000);
		// Handle the popup
		w_util.handleAlertAndAccept(driver);
		
		soft.assertAll();
	}
	
	@Test(retryAnalyzer =ListenersUtility.RetryAnalyzer_utility.class)
	public void createConwithorgTest() throws Exception {

		

		// Fetch data from excel file
		ClassObject_Utility.getTest().log(Status.INFO, "Fetch data from Excel");

		Excel_Utility ex_util = new Excel_Utility();
		Java_Utility j_util = new Java_Utility();
		int random = j_util.getRandomNumber();
		String conname = ex_util.FetchDataFromExcel("Contacts", 1, 2) + random;
		String orgname = ex_util.FetchDataFromExcel("Contacts", 1, 2) + random;


		// maximize the window
		driver.manage().window().maximize();
		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to Application");

		HomePomPage home=new HomePomPage(driver);
		Object exp_home = home.getHeader().contains("Home");
		SoftAssert soft=new SoftAssert();
		soft.assertEquals(exp_home, true);
		home.getOrg_tab();

		ClassObject_Utility.getTest().log(Status.INFO, "Click on plus icon");

		OrganizationPompage org = new OrganizationPompage(driver);
		org.getPlusicon();

		
		CreateNewOrgPompage neworg = new CreateNewOrgPompage(driver);
		neworg.getOrg_name(orgname);
		neworg.getOrg_savebtn();

		
		OrgDetailPompage orgdetail = new OrgDetailPompage(driver);
		String header = orgdetail.getHeader();

		if (header.contains(orgname)) {
			Reporter.log(orgname + "Test Pass");
		} else {
			Reporter.log("org not created");
		}

		
		home.getCont_tab();

		
		ContactPomPage con = new ContactPomPage(driver);
		con.getPlusicon();

		CreateNewContactPompage newcon = new CreateNewContactPompage(driver);
		newcon.getLastname_TF(conname);
		String pwid = driver.getWindowHandle();

		newcon.getOrgplusicon();

		Set<String> wids = driver.getWindowHandles();
		for (String s : wids) {
			driver.switchTo().window(s);
			if (driver.getCurrentUrl().contains("module=Accounts&action")) {

				newcon.getOrgsearchTF(orgname);
				newcon.getOrgsearchBtn();
				driver.findElement(By.xpath("//a[text()= '" + orgname + "']")).click();
			}
		}
		driver.switchTo().window(pwid);
		newcon.getSaveBtn();
		
		ContactDetailPompage condetail = new ContactDetailPompage(driver);
		boolean header1 = condetail.getHeader().contains(conname);
		Assert.assertEquals(header1, true);


		home.getCont_tab();
		driver.findElement(
				By.xpath("//a[text()='" + conname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();
		
		// Handle the popup
		driver.switchTo().alert().accept();

		// Click on org tab and delete the created org
		home.getOrg_tab();
//		driver.findElement(By.linkText("Organizations")).click();

		driver.findElement(
				By.xpath("//a[text()='" + orgname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();
  		Thread.sleep(3000);

		// Handle the popup
		driver.switchTo().alert().accept();

		
		soft.assertAll();

	}
	@Test
	public void createConwithSupportdateTest() throws Exception {
		

		ClassObject_Utility.getTest().log(Status.INFO, "Fetch data from Excel");

		Excel_Utility ex_util = new Excel_Utility();
		Java_Utility j_util = new Java_Utility();
		int random = j_util.getRandomNumber();
		String conname = ex_util.FetchDataFromExcel("Contacts", 1, 2) + random;

		// maximize the window
		driver.manage().window().maximize();
		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to the application");

		HomePomPage home=new HomePomPage(driver);
		Object exp_home = home.getHeader().contains("Home");
		SoftAssert soft=new SoftAssert();
		soft.assertEquals(exp_home, true);
		home.getCont_tab();

		// identify plus button and click
		ClassObject_Utility.getTest().log(Status.INFO, "Click on plus icon");

		ContactPomPage con = new ContactPomPage(driver);
		con.getPlusicon();

		String startdate = j_util.getCurrentDate();

		CreateNewContactPompage newcon = new CreateNewContactPompage(driver);
		newcon.getLastname_TF(conname);
		newcon.getConStartDate_TF().clear();
		newcon.getConStartDate_TF().sendKeys(startdate);

		// specify start and end support date
		ClassObject_Utility.getTest().log(Status.INFO, "specify start and end support date");

		String enddate = j_util.getDateAftergivenDays(30);

		newcon.getConEndDate_TF().clear();
		newcon.getConEndDate_TF().sendKeys(enddate);

		

		newcon.getSaveBtn();


		ContactDetailPompage condetail = new ContactDetailPompage(driver);
		boolean header1 = condetail.getHeader().contains(conname);
		Assert.assertEquals(header1, true);
	

		// verify start supp date and end support date
		ClassObject_Utility.getTest().log(Status.INFO, "verify start supp date and end support date");

		boolean actstrtdate = condetail.getVerifyStartdate().contains(startdate);
		Assert.assertEquals(actstrtdate, true);
		
		boolean actenddate = condetail.getVerifyEnddate().contains(enddate);
		Assert.assertEquals(actenddate, true);
		
	

		// Click on contact tab and delete the created contacts
		ClassObject_Utility.getTest().log(Status.INFO, "Click on contact tab and delete the created contacts");

		home.getCont_tab();
		driver.findElement(
				By.xpath("//a[text()='" + conname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();

  		Thread.sleep(2000);

		// Handle the popup
		ClassObject_Utility.getTest().log(Status.INFO, "Handle the popup");

		driver.switchTo().alert().accept();
		
		soft.assertAll();

	}
}
