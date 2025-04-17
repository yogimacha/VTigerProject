package Contacts;

import java.util.Set;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.GenericUtilities.Excel_Utility;
import com.GenericUtilities.Java_Utility;
import BaseClassUtilitiy.BaseClass;
import POMpage.ContactDetailPompage;
import POMpage.ContactPomPage;
import POMpage.CreateNewContactPompage;
import POMpage.CreateNewOrgPompage;
import POMpage.HomePomPage;
import POMpage.OrgDetailPompage;
import POMpage.OrganizationPompage;

public class CreateContactWithOrg extends BaseClass{
//	@Parameters("browser")
	@Test(groups="retesting")

	public void createConwithorgTest() throws Exception {
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
		String orgname = ex_util.FetchDataFromExcel("Contacts", 1, 2) + random;

//		// launch the browser
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

		// Identify org tab in home page and click
		// driver.findElement(By.linkText("Organizations")).click();
		HomePomPage home=new HomePomPage(driver);
		Object exp_home = home.getHeader().contains("Home");
		SoftAssert soft=new SoftAssert();
		soft.assertEquals(exp_home, true);
		home.getOrg_tab();

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
		String header = orgdetail.getHeader();

		if (header.contains(orgname)) {
			Reporter.log(orgname + "Test Pass");
		} else {
			Reporter.log("org not created");
		}

		// Identify contact tab in home page and click
		// driver.findElement(By.linkText("Contacts")).click();
		home.getCont_tab();

		// identify plus button and click
		// driver.findElement(By.xpath("//img[@title='Create Contact...']")).click();
		ContactPomPage con = new ContactPomPage(driver);
		con.getPlusicon();

		CreateNewContactPompage newcon = new CreateNewContactPompage(driver);
		newcon.getLastname_TF(conname);
		String pwid = driver.getWindowHandle();

		newcon.getOrgplusicon();

		// driver.findElement(By.name("lastname")).sendKeys(conname);

		// select org name

//		driver.findElement(By.xpath("//img[@alt='Select']")).click();
		Set<String> wids = driver.getWindowHandles();
		for (String s : wids) {
			driver.switchTo().window(s);
			if (driver.getCurrentUrl().contains("module=Accounts&action")) {

				newcon.getOrgsearchTF(orgname);
				newcon.getOrgsearchBtn();
//				driver.findElement(By.id("search_txt")).sendKeys(orgname);
//				driver.findElement(By.name("search")).click();
				driver.findElement(By.xpath("//a[text()= '" + orgname + "']")).click();
			}
		}
		driver.switchTo().window(pwid);
		newcon.getSaveBtn();
		// driver.findElement(By.xpath("(//input[@title='Save [Alt+S]']) [1]")).click();
		// verify actual contact name with expected contact name
		// WebElement conheader = driver.findElement(By.xpath("//span[contains(text(),'
		// Contact Information')]"));
		ContactDetailPompage condetail = new ContactDetailPompage(driver);
		boolean header1 = condetail.getHeader().contains(conname);
		Assert.assertEquals(header1, true);

//		if (header1.contains(conname)) {
//			Reporter.log(conname + "Test Pass");
//		} else {
//			Reporter.log("org not created");
//		}
		// Click on org tab and delete the created org
		// driver.findElement(By.linkText("Contacts")).click();

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

		// Logout of the appln
		// driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
//		WebElement admin = home.getAdmin_icon();
//
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