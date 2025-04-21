package POMpage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @author M.Yogesh
 */
public class ContactPomPage {
	// Declare
	@FindBy(linkText = "Contacts")
	private WebElement header;

	@FindBy(xpath = "//img[@alt='Create Contact...']")
	private WebElement plusicon;

	// Intialization
	public ContactPomPage(WebDriver driver) {

		PageFactory.initElements(driver, this);
	}

	// Utilization
	public String getHeader() {
		return header.getText();
	}

	public void getPlusicon() {
		plusicon.click();
	}

}