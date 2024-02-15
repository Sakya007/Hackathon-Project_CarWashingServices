package testCases;

import java.io.IOException;

import org.testng.annotations.Test;

import pageObjects.CarWashPage;
import pageObjects.GymandSearchPage;
import utilityFiles.ScreenShots;

public class TC002_SearchandFind extends TC001_Location{
	
	@Test(priority=3,groups= {"regression","master"})
	public void searchCarWash() throws InterruptedException, IOException {
		GymandSearchPage gs = new GymandSearchPage(driver);
		ScreenShots sc = new ScreenShots(driver);
		Thread.sleep(3000);
		sc.screenshot("SearchPage");
		gs.searchText();
	}
	
	@Test(priority=4,groups= {"regression","master"})
	public void carWashDetails() throws InterruptedException, IOException {
		CarWashPage cp = new CarWashPage(driver);
		Thread.sleep(3000);
		cp.displayDetails();
		logger.info("--//Top 5 car wash services displyed//--");
	}
	
	@Test(priority=5,groups= {"regression","master"})
	public void shareDetails() throws InterruptedException, IOException {
		CarWashPage cp = new CarWashPage(driver);
		cp.writeReviewReportShare();
		logger.info("--//Review given//--");
		logger.info("--//Shared and Reported//--");
		cp.clickBack();
	}
}
