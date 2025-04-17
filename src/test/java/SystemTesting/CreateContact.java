package SystemTesting;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.GenericUtilities.Property_Utility;
import com.GenericUtilities.WebDriver_Utility;

public class CreateContact {
	
	
	public WebDriver driver;
	@Test
	
	public void createOrg() throws InterruptedException, IOException{
		
		WebDriver_Utility w_util= new WebDriver_Utility();
		
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
		String time=(p.FetchDataFromProFile("TIME"));
		
		
		//long wait = Long.parseLong((p.FetchDataFromProFile("TIME")));
		
		w_util.maximizeTheWindow(driver);
		//driver.manage().window().maximize();
		w_util.navigateToApplication(url, driver);
		//driver.get(url);
		
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(wait));
        w_util.waitTillElementFound(time, driver);
        //Login
        driver.findElement(By.name("user_name")).sendKeys(un);
        driver.findElement(By.name("user_password")).sendKeys(pwd);
        driver.findElement(By.id("submitButton")).click();
        
     // Create an object of CreateOrganization
        
        
        
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
        
        w_util.handleDropdownUsingVisibleText(industry, ind);
        

        //Select Industry type
        WebElement indType = driver.findElement(By.name("accounttype"));
        
        w_util.handleDropdownUsingVisibleText(indType, industryType);

        
        //save the org
        driver.findElement(By.xpath("(//input[@title='Save [Alt+S]'])[1]")).click();
        
        Thread.sleep(3000);
     

        // Re-locate the element after refresh
        WebElement orgLink = driver.findElement(By.xpath("//a[@href='index.php?module=Contacts&action=index']"));
        orgLink.click();
        
        
        
        //Create Contact
        //driver.findElement(By.xpath("//a[@href='index.php?module=Contacts&action=index']")).click();
        driver.findElement(By.xpath("//img[@title='Create Contact...']")).click();
        
      //Fetch Data From Excel
        FileInputStream cFile=new FileInputStream("./src/test/resources/Vtiger.xlsx");
        Workbook wb1 = WorkbookFactory.create(cFile);
        Sheet sh1 =wb1.getSheet("Contacts");
        
       
        
        
        // Fetch row to update orgName in Excel
        Row statusRow = sh1.getRow(1);
        Cell statusCell = statusRow.createCell(3); 
        statusCell.setCellValue(uniqueOrgName);
        

        // Save the updated Excel file
        FileOutputStream fos = new FileOutputStream("./src/test/resources/Vtiger.xlsx");
        wb.write(fos);
        fos.close();
        //wb.close();
        
        // Define column indexes
        int[] rowIndexes1 = {1, 1, 1, 1};  // Row numbers where data is present
        int[] colIndexes2 = {2, 3, 4, 5};  // Column numbers for OrgName, Phone, Industry, Type

        // Array to store extracted values
        String[] testData1 = new String[rowIndexes1.length];


        // Date formatter
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // Loop through row and column indexes
        for (int i = 0; i < rowIndexes1.length; i++) {
            Row row = sh1.getRow(rowIndexes1[i]);
            Cell cell = row.getCell(colIndexes2[i]);

            if (cell != null) {
                switch (cell.getCellType()) {
                    case STRING:
                    	testData1[i] = cell.getStringCellValue();
                        break;
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                        	testData1[i] = sdf.format(cell.getDateCellValue()); // Convert date to yyyy-MM-dd format
                        } else {
                        	testData1[i] = String.valueOf((long) cell.getNumericCellValue()); // Convert numeric to string
                        }
                        break;
                    case BOOLEAN:
                    	testData1[i] = String.valueOf(cell.getBooleanCellValue());
                        break;
                    default:
                    	testData1[i] = "";
                        break;
                }
            }
        }
        
        // Assign extracted values
        String contactName = testData1[0];
        String organization = testData1[1];
        String sDate = testData1[2];
        String eDate = testData1[3];
        
     
        
        System.out.println(organization);
        
        
        //Create uniq Contact
        String uniqContact=contactName+getCurrentTimestamp();
        WebElement lastName = driver.findElement(By.name("lastname"));
        lastName.sendKeys(uniqContact);
        
        //get Contact Name
        @SuppressWarnings("deprecation")
		String actualContact = lastName.getAttribute("value");
        System.out.println(actualContact);
        
        
       
        
        driver.findElement(By.xpath("(//img[@title='Select'])[1]")).click();
        
        // Get the main window handle
        String mainWindow = driver.getWindowHandle();

        // Get all window handles
        Set<String> allWindows = driver.getWindowHandles();

        // Switch to the new window
        for (String window : allWindows) {
            if (!window.equals(mainWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }
        
        driver.findElement(By.id("search_txt")).sendKeys(organization);
        driver.findElement(By.name("search")).click();
        Thread.sleep(3000);
        driver.findElement(By.id("1")).click();
        


        // Switch back to the main window
        driver.switchTo().window(mainWindow);
        
        //Enter Start date
        WebElement startDate = driver.findElement(By.name("support_start_date"));
        startDate.clear();
        startDate.sendKeys(sDate);
        
        //Enter End Date
        WebElement endDate = driver.findElement(By.name("support_end_date"));
        endDate.clear();
        endDate.sendKeys(eDate);
        
        //save the Contact
        driver.findElement(By.xpath("(//input[@class='crmButton small save'])[1]")).click();
        
        
      //verify ContactName
        WebElement savedContactName = driver.findElement(By.id("dtlview_Last Name"));
        String expectedContact = savedContactName.getText();
        System.out.println("Saved Contact Name: " + expectedContact);
        
        Assert.assertEquals(actualContact, expectedContact, "Contact Name Validation Failed!");
        
     // Fetch row to update status in Excel
        Row r= sh1.getRow(1);
        Cell c = r.createCell(6); 
        c.setCellValue("Pass");
        // Save the updated Excel file
        FileOutputStream fos1 = new FileOutputStream("./src/test/resources/Vtiger.xlsx");
        wb.write(fos1);
        fos1.close();
        
        
        //Handle Sign out
        WebElement administrator = driver.findElement(By.xpath("(//img[@src='themes/softed/images/user.PNG'])[1]"));
        
        w_util.actionMouseHovering(driver, administrator);
        
        driver.findElement(By.xpath("//a[text()='Sign Out']")).click();
        
        wb.close();

        //Close the browser
        w_util.quitTheBrowser(driver);
        

	}
	private String getCurrentTimestamp() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return LocalDateTime.now().format(dtf);
	}
}
