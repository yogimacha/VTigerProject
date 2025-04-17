package SystemTesting;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateOrgWithPhoneNumber {
	@SuppressWarnings("deprecation")
	@Test
	
	public void createOrg() throws InterruptedException{
		WebDriver driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		
		driver.get("http://localhost:8888/");
		
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        driver.findElement(By.name("user_name")).sendKeys("admin");
        
        
        
        driver.findElement(By.name("user_password")).sendKeys("12345678");
     
       
        
        driver.findElement(By.id("submitButton")).click();
        
        driver.findElement(By.linkText("Organizations")).click();
        
        driver.findElement(By.cssSelector("img[title='Create Organization...']")).click();
        
        //Enter Uniq Org name
        String uniqueOrgName = "Moto_" + getCurrentTimestamp();
        WebElement orgName = driver.findElement(By.name("accountname"));
        orgName.click();
        orgName.sendKeys(uniqueOrgName);
        
        //get OrgName
        String actualText = orgName.getAttribute("value");
        System.out.println(actualText);
        
        
        //Enter Phone Number
        WebElement phoneNumber = driver.findElement(By.id("phone"));
        phoneNumber.sendKeys("9876543210");
        
        //get OrgPhone Number
        String actualPhNumber = phoneNumber.getAttribute("value");
        System.out.println(actualPhNumber);

        
        
        //Select Industry
        WebElement industry = driver.findElement(By.name("industry"));
        
        
        Select s= new Select(industry);
        s.selectByVisibleText("Electronics");
        
        
        
        //Select Industry type
        WebElement indType = driver.findElement(By.name("accounttype"));
        
        
        Select s1= new Select(indType);
        s1.selectByVisibleText("Reseller");
        
        
        //save the org
        driver.findElement(By.xpath("(//input[@title='Save [Alt+S]'])[1]")).click();
        
        
        //verify orgName
        WebElement savedOrgElement = driver.findElement(By.id("dtlview_Organization Name"));
        String expectedText = savedOrgElement.getText();
        System.out.println("Saved Organization Name: " + expectedText);
        
        Assert.assertEquals(actualText, expectedText, "Organization Name Validation Failed!");
        
        //verify phone Number
        WebElement savedOrgPhNumbe = driver.findElement(By.id("dtlview_Phone"));
        String expectedPhNumber= savedOrgPhNumbe.getText();
        System.out.println("Saved Organization Name: " + expectedPhNumber);
        
        Assert.assertEquals(actualPhNumber, expectedPhNumber, "Organization Phone Number Validation Failed!");
        
        
        
        //Delete the created org
        driver.findElement(By.linkText("Organizations")).click();
        
        driver.findElement(By.xpath("//a[text()='"+uniqueOrgName+"']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']")).click();
        
        //Handle Popup
        Thread.sleep(1);
        driver.switchTo().alert().accept();
        
        
        //Handle Sign out
        WebElement administrator = driver.findElement(By.xpath("(//img[@src='themes/softed/images/user.PNG'])[1]"));
        
        Actions actions = new Actions(driver);

        actions.moveToElement(administrator).perform();
        
        driver.findElement(By.xpath("//a[text()='Sign Out']")).click();

		driver.quit();
	}

	private String getCurrentTimestamp() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return LocalDateTime.now().format(dtf);
	}

}
