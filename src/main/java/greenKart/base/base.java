package greenKart.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.functors.CatchAndRethrowClosure;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.sl.draw.geom.Path;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import greenKart.utilties.ConfigReader;
import greenKart.utilties.ExtentReportNG;

public class base {

	public WebDriver driver = null;
	public Properties prop;
	public int d = 0;
	public static Logger log = LogManager.getLogger(base.class.getName());
	
	protected ExtentReports extent;
	protected ThreadLocal<ExtentTest> extentTest =new ThreadLocal<ExtentTest>();
	protected ExtentTest test;
	
	public WebDriver initializeDriver(ExtentTest startDriver) 
	{
		try {
			if (driver == null) 
			{
				String browserName = ConfigReader.readProjectConfiguration("browser");
				System.out.println(browserName);
				startDriver.info("browser" +browserName+ "started");

				if (browserName.equals("chrome")) {
					System.setProperty("webdriver.chrome.driver", ".\\driver\\chromedriver.exe");
					driver = new ChromeDriver();
					// execute in chrome driver
					log.info("ChromeDriver is initialised");
					startDriver.info("browser" +browserName+ "started");

				} else if (browserName.equals("firefox")) {
					System.setProperty("webdriver.geckoDriver.driver", ".\\driver\\firefoxDriver.exe");
					driver = new FirefoxDriver();
					// firefox code
				} else if (browserName.equals("IE")) {
//		IE code
					System.setProperty("webdriver.ie.driver", ".\\driver\\IEDriverServer.exe");
					driver = new InternetExplorerDriver();

				} else if (browserName.trim().equalsIgnoreCase("chromeHeadless")) {
					System.setProperty("webdriver.chrome.driver", ".\\driver\\chromedriver.exe");
					ChromeOptions options = new ChromeOptions();
					//options.addArguments("headless");
					options.addArguments("headless");
					driver = new ChromeDriver(options);
					startDriver.info("chromerdriver started");
				}

				driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				
				
				
				
			}

			else 
			{
				log.info("ChromeDriver already is initialised");
			}
			
			if (d == 0)
			{
				d = 2;
				FileUtils.cleanDirectory(new File("./ExtentReport"));
				FileUtils.cleanDirectory(new File("./logs"));
				log.info("cleaned directories");
			}
			else
			{
				log.info("No need to clean directories");
			}
			
		} catch (Exception e) {
				System.out.println("Exception in driver initialiasation");
		}
		return driver;
	}
	
	@BeforeSuite
	public void before() {
		extent = ExtentReportNG.getReportObject();
	}
	
	@BeforeMethod
	public void setUp(Method method) throws Exception {
	    test = extent.createTest(method.getClass().getEnclosingMethod().getName());
	    test.assignAuthor("Pratik");
	//Rest code will be generic for browser initiliazion.

	}
	
	
	@AfterSuite
	public void tearDownSuite() {
	    // reporter.endReport();
	    extent.flush();
	    //extent.close();
	}
}
