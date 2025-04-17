package SystemTesting;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.GenericUtilities.Property_Utility;

public class UsingDataDrivenCreateOrg {
	
		public WebDriver driver;
		//@SuppressWarnings("deprecation")
		@Test
		
		public void createOrg() throws InterruptedException, IOException{
			
			
			
//			FileInputStream fis =new FileInputStream("./src/test/resources/Vtiger.properties");
//			
//			Properties p = new Properties();
//			
//			p.load(fis);
			
			//Using Utility 
			Property_Utility p=new Property_Utility();
			
			// Read browser type from properties file
	        String browser = p.FetchDataFromProFile("browser").toLowerCase();

	        // Initialize WebDriver based on browser type
	        switch (browser) {
	            case "chrome":
	                driver = new ChromeDriver();
	                break;
	            case "firefox":
	                driver = new FirefoxDriver();
	                break;
	            case "edge":
	                driver = new EdgeDriver();
	                break;
	            default:
	                throw new IllegalArgumentException("Unsupported browser: " + browser);
			
			
	        }
			
			String url=(p.FetchDataFromProFile("URL"));
			String un=(p.FetchDataFromProFile("USERNAME"));
			String pwd=(p.FetchDataFromProFile("PASSWORD"));
			//String time=(p.getProperty("TIME"));
			
			
			long wait = Long.parseLong((p.FetchDataFromProFile("TIME")));
			
			
			driver.manage().window().maximize();
			
			driver.get(url);
			
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(wait));
	        
	        driver.findElement(By.name("user_name")).sendKeys(un);
	        
	        
	        
	        driver.findElement(By.name("user_password")).sendKeys(pwd);
	     
	       
	        
	        driver.findElement(By.id("submitButton")).click();
	        
	        driver.findElement(By.linkText("Organizations")).click();
	        
	        driver.findElement(By.cssSelector("img[title='Create Organization...']")).click();
	        
	        
	        
	        //Fetch Data From Excel
	        FileInputStream eFile=new FileInputStream("./src/test/resources/Vtiger.xlsx");
	        Workbook wb = WorkbookFactory.create(eFile);
	        Sheet sh =wb.getSheet("Organization");
	        

	        
	     // Define column indexes
	        int[] rowIndexes = {1, 1, 1, 1};  // Row numbers where data is present
	        int[] colIndexes = {2, 3, 4, 5};  // Column numbers for OrgName, Phone, Industry, Type

	        // Array to store extracted values
	        String[] testData = new String[rowIndexes.length];

	        // Loop through row and column indexes
	        for (int i = 0; i < rowIndexes.length; i++) {
	            Row row = sh.getRow(rowIndexes[i]);
	            Cell cell = row.getCell(colIndexes[i]);
	            
	            // Convert numeric cell values (like phone numbers) properly
	            if (cell.getCellType() == CellType.NUMERIC) {
	                testData[i] = String.valueOf((long) cell.getNumericCellValue());
	            } else {
	                testData[i] = cell.getStringCellValue();
	            }
	        }

	        // Assign extracted values
	        String Name = testData[0];
	        String phoneNumber = testData[1];
	        String ind = testData[2];
	        String industryType = testData[3];

	        
	        //Enter Uniq Org name
	        String uniqueOrgName = Name + getCurrentTimestamp();
	        WebElement orgName = driver.findElement(By.name("accountname"));
	        orgName.click();
	        orgName.sendKeys(uniqueOrgName);
	        
	        //get OrgName
	        String actualText = orgName.getAttribute("value");
	        System.out.println(actualText);
	        
	        
	        //Enter Phone Number
	        WebElement phoneNumber1 = driver.findElement(By.id("phone"));
	        phoneNumber1.sendKeys(phoneNumber);
	        
	        //get OrgPhone Number
	        String actualPhNumber = phoneNumber1.getAttribute("value");
	        System.out.println(actualPhNumber);

	        
	        
	        //Select Industry
	        WebElement industry = driver.findElement(By.name("industry"));
	        
	        
	        Select s= new Select(industry);
	        s.selectByVisibleText(ind);
	        
	        
	        
	        //Select Industry type
	        WebElement indType = driver.findElement(By.name("accounttype"));
	        
	        
	        Select s1= new Select(indType);
	        s1.selectByVisibleText(industryType);
	        
	        
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
	        
	        
	        
	     // Fetch row to update status in Excel
	        Row statusRow = sh.getRow(1);
	        Cell statusCell = statusRow.createCell(6); // Assuming column 6 is for Status

	        if (actualText.equals(expectedText) && actualPhNumber.equals(expectedPhNumber)) {
	            System.out.println("Test Passed!");
	            statusCell.setCellValue("Pass");  // Write "Pass" to Excel
	        } else {
	            System.out.println("Test Failed!");
	            statusCell.setCellValue("Fail");  // Write "Fail" to Excel
	        }

	        // Save the updated Excel file
	        FileOutputStream fos = new FileOutputStream("./src/test/resources/Vtiger.xlsx");
	        wb.write(fos);
	        fos.close();
	        

	        
//	        //Delete the created org
//	        driver.findElement(By.linkText("Organizations")).click();
//	        
//	        driver.findElement(By.xpath("//a[text()='"+uniqueOrgName+"']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']")).click();
//	        
//	        //Handle Popup
//	        Thread.sleep(2000);
//	        driver.switchTo().alert().accept();
	        
	        
	        //Handle Sign out
	        WebElement administrator = driver.findElement(By.xpath("(//img[@src='themes/softed/images/user.PNG'])[1]"));
	        
	        Actions actions = new Actions(driver);

	        actions.moveToElement(administrator).perform();
	        
	        driver.findElement(By.xpath("//a[text()='Sign Out']")).click();
	        wb.close();
	        driver.quit();
			
			

		}
	        
	        

		private String getCurrentTimestamp() {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	        return LocalDateTime.now().format(dtf);
		}

	}
