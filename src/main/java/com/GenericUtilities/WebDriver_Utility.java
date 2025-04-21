package com.GenericUtilities;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;


/**
 * @author : M.Yogesh
 * This is WebDriver Utility
 */
public class WebDriver_Utility {
	/**
	 * This is used for maximize the Browser
	 * @param driver
	 */
	public void maximizeTheWindow(WebDriver driver) {
		driver.manage().window().maximize();
	}
	/**
	 * This method is used for Implicit wait
	 * @param Timeouts
	 * @param driver
	 */
     public void waitTillElementFound(String Timeouts,WebDriver driver) {
    	 

    	 long time =Long.parseLong(Timeouts);
    	 driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(time));
	}
     
     public void navigateToApplication(String url,WebDriver driver) {
    	 driver.get(url);
     }
     
     public void handleAlertAndAccept(WebDriver driver) {
    	 driver.switchTo().alert().accept();
     }
     public void handleAlertAndACacel(WebDriver driver) {
    	 driver.switchTo().alert().dismiss();;
     }
     public String handleAlertAndFetchText(WebDriver driver) {
    	 String text=driver.switchTo().alert().getText();
    	 return text;
     }
     public void handleAlertAndPassText(WebDriver driver,String text) {
    	 driver.switchTo().alert().sendKeys(text);;
     }
	 public void handleDropdownUsingIndex(WebElement dropdown,int index) {
		 Select s=new Select(dropdown);
		 s.selectByIndex(index);	 
	 }
	 public void handleDropdownUsingValue(WebElement dropdown,String value) {
		 Select s=new Select(dropdown);
		 s.selectByValue(value);;	 
	 }
	 public void handleDropdownUsingVisibleText(WebElement dropdown,String text) {
		 Select s=new Select(dropdown);
		 s.selectByVisibleText(text);
	 }
	 public void switchToTabUsingUrl(WebDriver driver,String url) {
		 Set<String> wids=driver.getWindowHandles();
		 for(String s :wids) {
			 driver.switchTo().window(s);
			 if (driver.getCurrentUrl().contains(url)) {
				break;
			}
			 
		 }	 
	 }
	 public void switchToTabUsingTitle(WebDriver driver,String title) {
		 Set<String> wids=driver.getWindowHandles();
		 for(String s :wids) {
			 driver.switchTo().window(s);
			 if (driver.getCurrentUrl().contains(title)) {
				break;
			}
			 
		 }	 
	 }
	 public void switchToTabUsingParentWindow(WebDriver driver,String p_window) {
		 Set<String> wids=driver.getWindowHandles();
		 for(String s :wids) {
			 driver.switchTo().window(s);
			 if (driver.getCurrentUrl().contains(p_window)) {
				break;
			}
			 
		 }	 
	 }
	 
	 public void actionMouseHovering(WebDriver driver,WebElement element) {
		 Actions actions = new Actions(driver);
	        actions.moveToElement(element).perform();
	 }
	 
	 public void quitTheBrowser(WebDriver driver) {
		 driver.quit();
	 }
	 public void closeTheBrowser(WebDriver driver) {
		 driver.close();
	 }
	 
	 
}
