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

import com.GenericUtilities.Java_Utility;

public class CreateOrganization {
	
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
        
        Java_Utility j_util=new Java_Utility();
        int random=j_util.getRandomNumber();
        
        
        String uniqueOrgName = "Moto_" + random;

        WebElement orgName = driver.findElement(By.name("accountname"));
        orgName.click();
        orgName.sendKeys(uniqueOrgName);

        
        String actualText = orgName.getAttribute("value");
        System.out.println(actualText);
        
        
        driver.findElement(By.xpath("(//input[@title='Save [Alt+S]'])[1]")).click();
        
        WebElement savedOrgElement = driver.findElement(By.id("dtlview_Organization Name"));

        
        String expectedText = savedOrgElement.getText();
        System.out.println("Saved Organization Name: " + expectedText);
        
        Assert.assertEquals(actualText, expectedText, "Organization Name Validation Failed!");
        
        driver.findElement(By.linkText("Organizations")).click();
        
        driver.findElement(By.name("search_text")).sendKeys(uniqueOrgName);
        
        WebElement name = driver.findElement(By.name("search_field"));
        Select s= new Select(name);
        s.selectByVisibleText("Organization Name");
        
        driver.findElement(By.name("submit")).click();
        
        //Delete the created org
        
        
        
        
        driver.findElement(By.xpath("//a[text()='"+uniqueOrgName+"']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']")).click();
        
        Thread.sleep(1);
        driver.switchTo().alert().accept();
        
        
        
        WebElement administrator = driver.findElement(By.xpath("(//img[@src='themes/softed/images/user.PNG'])[1]"));
        
        Actions actions = new Actions(driver);

        actions.moveToElement(administrator).perform();
        
        driver.findElement(By.xpath("//a[text()='Sign Out']")).click();

		driver.quit();
	}

}
