package POMpage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @author M.Yogesh
 */
public class LoginPompage {

	// declaration

	@FindBy(linkText = "vtiger")
	private WebElement header;

	@FindBy(name = "user_name")
	private WebElement un;

	@FindBy(name = "user_password")
	private WebElement pwd;

	@FindBy(id = "submitButton")
	private WebElement loginbtn;

	// initialization
	public LoginPompage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	// Utilization
	public String getHeader() {
		return header.getText();
	}

	public void getUn(String user) {
		un.sendKeys(user);
	}

	public void getPwd(String password) {
		pwd.sendKeys(password);
	}

	public void getLoginbtn() {
		loginbtn.click();
	}

	// Business login
	public void login(String user, String pass) {
		un.sendKeys(user);
		pwd.sendKeys(pass);
		loginbtn.click();

	}

}