package utilityFiles;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import testBase.BaseClass;

 public class ExtentReportManager extends BaseClass implements ITestListener{
	
	public ExtentSparkReporter sparkReporter;  // UI of the report
	public ExtentReports extent;  //Populate common info on the report
	public ExtentTest test; // Creating test case entries in the report and update status of the test methods

	public void onStart(ITestContext context) {
		
		//Specify location of the report

		String timeStamp =new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String rep="Test-Report"+timeStamp+".html";
		sparkReporter=new ExtentSparkReporter(".\\reports\\"+rep);
		sparkReporter.config().setDocumentTitle("Automation Report"); // Title of report
		sparkReporter.config().setReportName("Functional Testing"); // Name of the report
		sparkReporter.config().setTheme(Theme.DARK);				// Theme of the report 
		extent=new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Computer Name","LTIN200789"); 
		extent.setSystemInfo("Environment","AskLaila Automation!");
		extent.setSystemInfo("Tester Name","Sakyasinha Roy(2303535)");
		extent.setSystemInfo("os","Windows11");
		extent.setSystemInfo("Browser name","Chrome,Edge");
	}
 
 
	public void onTestSuccess(ITestResult result) {

		test = extent.createTest(result.getName()); // Create a new enty in the report
		test.log(Status.PASS, "Test case PASSED is:" + result.getName()); // update status p/f/s
		try {
			String s = new ScreenShots(driver).screenshot(result.getName());
			test.addScreenCaptureFromPath(s);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
 
	public void onTestFailure(ITestResult result) {

		test = extent.createTest(result.getName());
		test.log(Status.FAIL, "Test case FAILED is:" + result.getName());
		test.log(Status.FAIL, "Test Case FAILED cause is: " + result.getThrowable()); 
		try {
			String s = new ScreenShots(driver).screenshot(result.getName());
			test.addScreenCaptureFromPath(s);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
 
	public void onTestSkipped(ITestResult result) {
		
 		test = extent.createTest(result.getName());
		test.log(Status.SKIP, "Test case SKIPPED is:" + result.getName());
	}
 
	
	public void onFinish(ITestContext context) {

		extent.flush();
	}
}