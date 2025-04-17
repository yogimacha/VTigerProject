package SystemTesting;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.GenericUtilities.Excel_Utility;
import com.GenericUtilities.Property_Utility;
import com.GenericUtilities.WebDriver_Utility;

public class CreateContactUsingUtilities {
    
    public WebDriver driver;

    @Test
    public void createOrg() throws InterruptedException, IOException {
        WebDriver_Utility w_util = new WebDriver_Utility();
        Property_Utility p_util = new Property_Utility();
        Excel_Utility e_util = new Excel_Utility();

        // Read browser type from properties file
        String browser = p_util.FetchDataFromProFile("browser").toLowerCase();

        // Initialize WebDriver
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

        // Fetch details from properties file
        String url = p_util.FetchDataFromProFile("URL");
        String username = p_util.FetchDataFromProFile("USERNAME");
        String password = p_util.FetchDataFromProFile("PASSWORD");
        String time = p_util.FetchDataFromProFile("TIME");

        // Open browser and login
        w_util.maximizeTheWindow(driver);
        w_util.navigateToApplication(url, driver);
        w_util.waitTillElementFound(time, driver);

        driver.findElement(By.name("user_name")).sendKeys(username);
        driver.findElement(By.name("user_password")).sendKeys(password);
        driver.findElement(By.id("submitButton")).click();

        // Navigate to Organizations
        driver.findElement(By.linkText("Organizations")).click();
        driver.findElement(By.cssSelector("img[title='Create Organization...']")).click();

        // Read data from Excel
        String orgName = e_util.getCellValue("Organization", 1, 2);
        String phoneNumber = e_util.getCellValue("Organization", 1, 3);
        String industry = e_util.getCellValue("Organization", 1, 4);
        String industryType = e_util.getCellValue("Organization", 1, 5);

        // Enter Unique Organization Name
        String uniqueOrgName = orgName + getCurrentTimestamp();
        driver.findElement(By.name("accountname")).sendKeys(uniqueOrgName);
        driver.findElement(By.id("phone")).sendKeys(phoneNumber);

        w_util.handleDropdownUsingVisibleText(driver.findElement(By.name("industry")), industry);
        w_util.handleDropdownUsingVisibleText(driver.findElement(By.name("accounttype")), industryType);

        driver.findElement(By.xpath("(//input[@title='Save [Alt+S]'])[1]")).click();
        Thread.sleep(3000);

        // Create Contact
        driver.findElement(By.xpath("//a[@href='index.php?module=Contacts&action=index']")).click();
        driver.findElement(By.xpath("//img[@title='Create Contact...']")).click();

        e_util.writeCellValue("Contacts", 1, 3, uniqueOrgName);

        String contactName = e_util.getCellValue("Contacts", 1, 2) + getCurrentTimestamp();
        String startDate = e_util.getCellValue("Contacts", 1, 4);
        String endDate = e_util.getCellValue("Contacts", 1, 5);

        driver.findElement(By.name("lastname")).sendKeys(contactName);

        driver.findElement(By.xpath("(//input[@class='crmButton small save'])[1]")).click();

        // Validate Contact Name
        String savedContactName = driver.findElement(By.id("dtlview_Last Name")).getText();
        Assert.assertEquals(contactName, savedContactName, "Contact Name Validation Failed!");

        e_util.writeCellValue("Contacts", 1, 6, "Pass");

        // Logout
        WebElement admin = driver.findElement(By.xpath("(//img[@src='themes/softed/images/user.PNG'])[1]"));
        w_util.actionMouseHovering(driver, admin);
        driver.findElement(By.xpath("//a[text()='Sign Out']")).click();

        w_util.quitTheBrowser(driver);
    }

    private String getCurrentTimestamp() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return LocalDateTime.now().format(dtf);
    }
}
