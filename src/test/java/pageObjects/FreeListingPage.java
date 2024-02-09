package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FreeListingPage extends BasePage {

	public FreeListingPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath = "//div[@id='flowEntry']//button") WebElement freeListingLogin;
	@FindBy(xpath = "//input[@id='loginEmail']") WebElement email;
	@FindBy(xpath = "//input[@id='loginPassword']") WebElement password;
	@FindBy(xpath = "//button[@id='loginRes']") WebElement loginButton;
	@FindBy(xpath = "//div[@id='errdiv']") WebElement freeListingErrormsg;
	@FindBy(xpath = "//div[@class='modal-header loginTitle']//button[@type='button']") WebElement crossButton;
	@FindBy(xpath = "//div[@id='pushengage-subscription-overlay-close-button']") WebElement popup;
	
	public void closePopup(){
		try {
		if(popup.isDisplayed()) {
			explicitWait(popup,5);
			popup.click();
		}
		}
		catch(Exception e) {
			e.getMessage();
		}		
	}
	
	
	public void clickFreeListingLogin() {
		highlightElement(freeListingLogin);
		freeListingLogin.click();
	}
	
	public void enterEmail() {
		email.sendKeys("abc@xyz");
	}
	
	public void enterPassword() {
		password.sendKeys("11111");
	}
	
	public void clickLoginButton() throws InterruptedException {
		highlightElement(loginButton);
		loginButton.click();
		Thread.sleep(2000);
	}
	
	public String freeListingErrorMsg() {
		String msg = freeListingErrormsg.getText();
		return msg;
	}
	
	public void cross() throws InterruptedException {
		highlightElement(crossButton);
		crossButton.click();
		Thread.sleep(1000);
		
	}
}