package Organizations;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.GenericUtilities.Excel_Utility;
import com.GenericUtilities.Java_Utility;
import com.GenericUtilities.WebDriver_Utility;

import BaseClassUtilitiy.BaseClass;
import POMpage.CreateNewOrgPompage;
import POMpage.HomePomPage;
import POMpage.OrgDetailPompage;
import POMpage.OrganizationPompage;


public class CreateOrganisationWithName extends BaseClass{
//	@Parameters("browser")
	@Test(groups="integration")

	public void organisationtest() throws Exception {

		// fetch the data from property file
//		Property_Utility pro = new Property_Utility();

		//String browser = pro.FetchDataFromProFile("browser");
//		String UrL = pro.FetchDataFromProFile("URL");
//		String un = pro.FetchDataFromProFile("USERNAME");
//		String pwsd = pro.FetchDataFromProFile("PASSWORD");
//		String timeouts = pro.FetchDataFromProFile("TIME");
		// fetch data from excel
		Excel_Utility ex_util = new Excel_Utility();
		Java_Utility j_util = new Java_Utility();
		int random = j_util.getRandomNumber();
		String orgname = ex_util.FetchDataFromExcel("Organization", 1, 2) + random;

//		// launch the browser
//
//		WebDriver driver = null;
//		if (Browser.equals("chrome")) {
//			driver = new ChromeDriver();
//		} else if (Browser.equals("edge")) {
//			driver = new EdgeDriver();
//		} else {
//			driver = new ChromeDriver();
//		}

//		// maximize the window
		WebDriver_Utility w_util = new WebDriver_Utility();
//		w_util.maximizeTheWindow(driver);
//
//		// implicity wait
//		w_util.waitTillElementFound(timeouts, driver);
//
//		// Navigate to an V-Tiger application
//		w_util.navigateToApplication(UrL, driver);
//
//		LoginPompage l = new LoginPompage(driver);
//		l.login(un, pwsd);

		// Identify username textfield and pass the text
		// driver.findElement(By.xpath("//input[@name='user_name']")).sendKeys(un);

		// Identify password textfield and pass the text
		// driver.findElement(By.xpath("//input[@name='user_password']")).sendKeys(pwsd);

		// Identify login button and click on it
		// driver.findElement(By.xpath("//input[@id='submitButton']")).click();

		// Identify org tab in home page and click
		// driver.findElement(By.linkText("Organizations")).click();
		HomePomPage home=new HomePomPage(driver);
		Object exp_home = home.getHeader().contains("Home");
		SoftAssert soft=new SoftAssert();
		soft.assertEquals(exp_home, true);
		home.getCont_tab();

		// identify plus button and click
		// driver.findElement(By.xpath("//img[@title='Create
		// Organization...']")).click();
		OrganizationPompage org = new OrganizationPompage(driver);
		org.getPlusicon();

		// Enter org name in create new org page and save

		// driver.findElement(By.name("accountname")).sendKeys(orgname);

		// driver.findElement(By.xpath("(//input[@title='Save [Alt+S]'])[1]")).click();
		CreateNewOrgPompage neworg = new CreateNewOrgPompage(driver);
		neworg.getOrg_name(orgname);
		neworg.getOrg_savebtn();

		// verify actual org name with expected org name
		// WebElement header =
		// driver.findElement(By.xpath("//span[contains(text(),'Organization
		// Information')]"));
		OrgDetailPompage orgdetail = new OrgDetailPompage(driver);
		boolean header = orgdetail.getHeader().contains(orgname);
		Assert.assertEquals(header, true);
		
		
		
//		if (header.contains(orgname)) {
//			Reporter.log(orgname + "Test Pass");
//		} else {
//			Reporter.log("org not created");
//		}
		// Click on org tab and delete the created org
		// driver.findElement(By.linkText("Organizations")).click();
		home.getOrg_tab();

		driver.findElement(
				By.xpath("//a[text()='" + orgname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();

  		Thread.sleep(2000);

		// Handle the popup
		w_util.handleAlertAndAccept(driver);

		// Logout of the appln
		// WebElement admin =
		// driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
//		WebElement admin = home.getAdmin_icon();
//
//		w_util.actionMouseHovering(driver, admin);
//		// driver.findElement(By.linkText("Sign Out")).click();
//		home.getSignout();
//
//		// close the browser
//		w_util.quitTheBrowser(driver);
		soft.assertAll();

	}

}