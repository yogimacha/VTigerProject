package POMpage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateNewOrgPompage {
	@FindBy(xpath = "//span[text()='Creating New Organization']")
	private WebElement newCreateheader;

	@FindBy(name = "accountname")
	private WebElement Org_name;

	@FindBy(id = "phone")
	private WebElement Org_phno;

	@FindBy(name = "industry")
	private WebElement Org_ind;

	@FindBy(name = "accounttype")
	private WebElement Org_type;

	@FindBy(xpath = "(//input[@title='Save [Alt+S]'])[1]")
	private WebElement Org_savebtn;

	// Initalization
	public CreateNewOrgPompage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	// Utilization

	public String getNewCreateheader() {
		return newCreateheader.getText();
	}

	public void getOrg_name(String orgname) {
		Org_name.sendKeys(orgname);
	}

	public void getOrg_phno(String orgphno) {
		Org_phno.sendKeys(orgphno);
	}

	public WebElement getOrg_ind() {
		return Org_ind;
	}

	public WebElement getOrg_type() {
		return Org_type;
	}

	public void getOrg_savebtn() {
		Org_savebtn.click();
	}

}