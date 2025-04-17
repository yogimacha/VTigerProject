package POMpage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.GenericUtilities.WebDriver_Utility;

public class HomePomPage {

	// Declare
	@FindBy(partialLinkText = "Home")
	private WebElement header;

	@FindBy(linkText = "Organizations")
	private WebElement org_tab;

	@FindBy(linkText = "Contacts")
	private WebElement cont_tab;

	@FindBy(xpath = "//span[text()=' Administrator']/../../descendant::img")
	private WebElement admin_icon;

	@FindBy(linkText = "Sign Out")
	private WebElement signout;

	// initialize
	public HomePomPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	// Utilize

	public String getHeader() {
		return header.getText();
	}

	public void getOrg_tab() {
		org_tab.click();
	}

	public void getCont_tab() {
		cont_tab.click();
	}

	public WebElement getAdmin_icon() {
		return admin_icon;
	}

	public void getSignout() {
		signout.click();
	}
	
	public void logout(WebDriver driver) {
		WebDriver_Utility w_util=new WebDriver_Utility();
		w_util.actionMouseHovering(driver, admin_icon);
		signout.click();
	}

}