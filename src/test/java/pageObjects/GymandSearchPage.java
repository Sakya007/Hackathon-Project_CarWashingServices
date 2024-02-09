package pageObjects;

import java.io.IOException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utilityFiles.ScreenShots;

public class GymandSearchPage extends BasePage{

	public GymandSearchPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//input[@id='what']") WebElement searchbox;
	@FindBy(xpath = "//button[@class='btn btn-default']") WebElement findButton;
	@FindBy(xpath = "//div[@class='btn-group']//a[@class='btn cardFooterButtonBlue']") WebElement freeListingButton;
	@FindBy(xpath = "//div[@class='col-xs-6 col-sm-6 col-md-3 col-lg-3 pad0'][34]") WebElement gymGrid;
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

	
	public void searchText() throws InterruptedException, IOException {

		ScreenShots ss = new ScreenShots(driver);
		closePopup();
		Thread.sleep(2000);
		ss.screenshot("SearchPage");
		searchbox.sendKeys("Car Washing Services");
		highlightElement(findButton);
		findButton.click();
	}
	
	public void clickFreeListing() {
		highlightElement(freeListingButton);
		freeListingButton.click();
	}
	
	public void selectGym() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,1800)","");
		highlightElement(gymGrid);
		gymGrid.click();
	}
	
	
}
