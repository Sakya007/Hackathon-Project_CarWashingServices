package testBase;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

public class BaseClass {

	public static WebDriver driver;
	public Properties p;
	public int choice;
	public Logger logger;
	Scanner sc;
	
	@BeforeTest(groups= {"sanity","regression","Master"})
	@Parameters({"os","browser"})
	public void setup(String os,String br) throws IOException {
		//loading properties file
		FileReader file = new FileReader(".//src//test//resources//config.properties");
		p = new Properties();
		p.load(file);
		ChromeOptions chromeOptions=new ChromeOptions();
		chromeOptions.setExperimentalOption("excludeSwitches", new String[] {"enable-automation"});
		chromeOptions.addArguments("--disable-notifications");
		EdgeOptions edgeOptions=new EdgeOptions();
		edgeOptions.setExperimentalOption("excludeSwitches", new String[] {"enable-automation"});
		edgeOptions.addArguments("--disable-notifications");
		
		logger = LogManager.getLogger(this.getClass());
		
		
		if(p.getProperty("execution_env").equals("local")) {
			// Launching browser based on choice
			if(br.equalsIgnoreCase("chrome")) {
				driver = new ChromeDriver(chromeOptions);
			}else if(br.equalsIgnoreCase("edge")) {
				driver = new EdgeDriver(edgeOptions);
			}else {
				System.out.println("No matching browser");
				return;
			}
		}
		else if(p.getProperty("execution_env").equals("remote")) {
			String url = "http://localhost:4444/wd/hub";
			DesiredCapabilities cap = new DesiredCapabilities();
			if(os.equalsIgnoreCase("windows")) {
				cap.setPlatform(Platform.WIN11);
			}
			else if(os.equalsIgnoreCase("mac")) {
				cap.setPlatform(Platform.MAC);
			}
			else if(os.equalsIgnoreCase("linux")) {
				cap.setPlatform(Platform.LINUX);
			}
			if(br.equalsIgnoreCase("chrome")) {
				cap.setBrowserName("chrome");
			}
			else if(br.equalsIgnoreCase("edge")) {
				cap.setBrowserName("edge");
			}
			else {
				System.out.println("No matching browser");
				return;
			}
			driver = new RemoteWebDriver(new URL(url),cap);
		}
	
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(p.getProperty("appURL"));
		driver.manage().window().maximize();
	}
	
	@AfterTest(groups= {"sanity","regression","master"})
	public void tearDown() {
		driver.quit();
	}
		
}
