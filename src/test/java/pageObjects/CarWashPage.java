package pageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utilityFiles.ExcelUtils;
import utilityFiles.ScreenShots;

public class CarWashPage extends BasePage {

	public CarWashPage(WebDriver driver) {
		super(driver);
	}
    
	
	@FindBy(xpath = "//span[@class='ratevalue']//ancestor::div[@class='col-xs-12 card']") List<WebElement> carCards;
    @FindBy(xpath = "//h2[@class='resultTitle']") List<WebElement> carWashName;
    @FindBy(xpath = "//span[@class='ratevalue']") List<WebElement> carWashRatings;
    @FindBy(xpath = "//label[@class='phonedisplay']") List<WebElement> carWashPhone;
    
    @FindBy(xpath = "//textarea[@class='commentBox']")WebElement reviewTextBox;
    @FindBy(xpath = "//div[@id='modalFixListing']//div[@class='modal-content']//textarea")WebElement reportTextArea;
    @FindBy(xpath = "//div[@id='modalFixListing']//div[@class='modal-content']//button[@class='close']")WebElement reportCloseButton;
    @FindBy(xpath = "//div[@id='pushengage-subscription-overlay-close-button']") WebElement popup;
    @FindBy(xpath = "//input[@id='email']")WebElement FBEmail;
    @FindBy(xpath = "//input[@id='pass']")WebElement FBPass;
    @FindBy(xpath = "//label[@id='loginbutton']")WebElement FBLogin;
    @FindBy(xpath="//div[@class='_li']//div[contains(@class,'pam login_error_box')]") WebElement error;
    
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

    
    String before_review = "(//button[@class='hidden-xs btn btn-default cardFooterButtonBlue'])[";
    String after_review = "]";
    String before_share = "(//button[@title='Share'])[";
    String after_share = "]";
    String before_fb = "(//ul[@role='menu']//li//a[@data-original-title='Share on Facebook'])[";
    String after_fb = "]";
    String before_tw = "(//ul[@role='menu']//li//a[@data-original-title='Share on Twitter'])[";
    String after_tw = "]";
    String before_report = "(//button[@title='Report'])[";
    String after_report = "]";
    
    Map<String, String> nameRating = new HashMap<String, String>();
    Map<String, String> nameNumber = new HashMap<String, String>();
    List<String> numReal = new ArrayList<String>();
	List<String> rateReal = new ArrayList<String>();
	
	
    int size = carWashRatings.size();
    int randNum = (randomNumber(size))+1;
    ScreenShots ss;
    
    public void displayDetails() throws InterruptedException, IOException {
    	ExcelUtils excel = new ExcelUtils();
		String xlfile = System.getProperty("user.dir") + "\\testData\\testdataexcel.xlsx";
    	ss = new ScreenShots(driver);
    	closePopup();
    	Thread.sleep(3000);
    	ss.screenshot("CarWash");
    	for(int i=0; i<carCards.size(); i++) 
    	{
    		String name = carWashName.get(i).getText();
    		String rating = carWashRatings.get(i).getText();
    		String number1 = carWashPhone.get(i).getText();
    		nameRating.put(name,rating);
    		nameNumber.put(name,number1);
    	}
    	
		numReal = getMaps(nameNumber);
		rateReal = getMaps(nameRating);
		int i =0;
		int j=1;
		String Name="";
		for(String k : nameNumber.keySet())
		{
			String rate1 = rateReal.get(i);
			if(Double.parseDouble(rate1)>4){
				Name= "Name:" +k+","+"Rating: "+rate1+" Phone Number: "+ numReal.get(i);
				System.out.println(Name);
				excel.setCellData(xlfile, "CarWash", j, 0, Name);
				j++;
        	
			}
		
			i++;
		}
    }
    
    public void writeReviewReportShare() throws InterruptedException, IOException {
    	
    	ScreenShots sc = new ScreenShots(driver);
    	
    	WebElement reviewButton= driver.findElement(By.xpath(before_review +randNum +after_review));
    	highlightElement(reviewButton);
    	reviewButton.click();
    	explicitWait(reviewTextBox,5);
    	reviewTextBox.sendKeys("Satisfactory");
    	Thread.sleep(3000);
    	sc.screenshot("ReviewPage");
		driver.navigate().back();
    
    	WebElement shareButton = driver.findElement(By.xpath(before_share +randNum +after_share));
    	highlightElement(shareButton);
    	shareButton.click();
    	Thread.sleep(3000);
    	
    	WebElement fbShareButton = driver.findElement(By.xpath(before_fb + randNum + after_fb));
    	highlightElement(fbShareButton);
    	fbShareButton.click();
    	Set<String> wid = driver.getWindowHandles();
    	List<String> winid = new ArrayList<String>(wid);
    	String parent = winid.get(0);
    	String child = winid.get(1);
    	String title = driver.switchTo().window(child).getTitle();
    	Thread.sleep(3000);

    		if(title.equals("Facebook")) {
    			driver.switchTo().window(child);
    			Thread.sleep(3000);
    			sc.screenshot("FB");
    			Thread.sleep(2000);
    			FBEmail.sendKeys("abc@xyz");
    			FBPass.sendKeys("akkscj1234");
    			FBLogin.click();
    			Thread.sleep(2000);
    			sc.screenshot("FBError");
    			String err_msg = error.getText();
    			System.out.println("Error msg : " + err_msg);
    			driver.close();
    		}
    		driver.switchTo().window(parent);
    	
    	shareButton.click();
    	Thread.sleep(2000);
    	WebElement twShareButton = driver.findElement(By.xpath(before_tw + randNum + after_tw));
    	highlightElement(twShareButton);
    	twShareButton.click();
    	
    	Set<String> wid2 = driver.getWindowHandles();
    	List<String> winid2 = new ArrayList<String>(wid2);
    	String parent2 = winid2.get(0);
    	String child2 = winid2.get(1);

    	Thread.sleep(2000);

	    driver.switchTo().window(child2);
	    Thread.sleep(1000);
	    sc.screenshot("Twitter");
	    Thread.sleep(1000);
	    driver.close();

    	driver.switchTo().window(parent2);
    	
    	
    	WebElement reportButton = driver.findElement(By.xpath(before_report + randNum + after_report));
    	highlightElement(reportButton);
    	reportButton.click();
    	Thread.sleep(1000);
    	reportTextArea.sendKeys("Report");
    	Thread.sleep(1000);
    	sc.screenshot("Report");
    	Thread.sleep(1000);
    	reportCloseButton.click();
    }
    

    public static List<String>  getMaps(Map<String, String> m )	{
    	Map<String, String> map = m;	
    
    	List<String> val = new ArrayList<String>();
    	for(String k : map.keySet())
    	{    		
    		val.add(map.get(k));    	
    	}
    	return val;
    }

    public static int randomNumber(int num) {
    	Random random = new Random();
    	int randomNo = (random.nextInt(num))+1;
    	return randomNo;

    }
    
    public void clickBack() {
    	driver.navigate().back();
    }


}
