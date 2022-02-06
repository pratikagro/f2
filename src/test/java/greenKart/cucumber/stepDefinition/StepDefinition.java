package greenKart.cucumber.stepDefinition;

import java.io.File;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.ITestContext;

import greenKart.assertions.Assertions;
import greenKart.base.base;
import greenKart.userActivities.KartPage;
import greenKart.utilties.ConfigReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinition {
	
	public static WebDriver driver = null;
	public Properties prop;
	public int d = 0;
	
	String KartItem = "Cucumber";
	public boolean resultClick;
	
	public static Logger log = LogManager.getLogger(StepDefinition.class.getName());
	
	@Given(": Browser is already open")
	public void browser_is_already_open() 
	{
		try {
			if (driver == null) 
			{
				String browserName = ConfigReader.readProjectConfiguration("browser");
				System.out.println(browserName);

				if (browserName.equals("chrome")) {
					System.setProperty("webdriver.chrome.driver", ".\\driver\\chromedriver.exe");
					driver = new ChromeDriver();
					// execute in chrome driver
					log.info("ChromeDriver is initialised");

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
					options.addArguments("headless");
					driver = new ChromeDriver(options);
				}

				driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				//context.setAttribute("WebDriver", driver);
				
				
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

		}
	}

	@When(": User types URL")
	public void user_types_url() 
	{
		if (driver.getCurrentUrl().contains(ConfigReader.readProjectConfiguration("URL"))) {
			log.info("URL is already intialised");
			boolean alreadyPresent = true;
			Assert.assertTrue(alreadyPresent);
		} else {

			try {

				String projectURL = ConfigReader.readProjectConfiguration("URL");
				if (projectURL != null) {
					driver.get(projectURL);
					log.info("URL is intialised");
				} else {
					Assert.fail("URL not found");
					log.info("URL not found");
				}
			} catch (Exception e) {
				e.printStackTrace();
				Assert.fail("URL navigation failed");

			}
		}
	}
	
	@When(": navigates to greenKart URL")
	public void navigates_to_green_kart_url() 
	{
	    
		

			try {

				String projectURL = ConfigReader.readProjectConfiguration("URL");
				if (projectURL != null) {
					driver.get(projectURL);
					//log.info("URL is intialised");
				} else {
					Assert.fail("URL not found");
					log.info("URL not found");
				}
			} catch (Exception e) {
				e.printStackTrace();
				Assert.fail("URL navigation failed");

			}
		}
		
		
	
	@Then(": GreenKart website opens up")
	public void green_kart_website_opens_up() 
	{
		if (driver.getCurrentUrl().contains(ConfigReader.readProjectConfiguration("URL"))) 
		{
			log.info("URL is already intialised");
			boolean alreadyPresent = true;
			Assert.assertTrue(alreadyPresent);
		}	
	}

	@Given(": URL is already open")
	public void url_is_already_open() 
	{
		if (driver.getCurrentUrl().contains(ConfigReader.readProjectConfiguration("URL"))) 
		{
			log.info("URL is already intialised");
			boolean alreadyPresent = true;
			Assert.assertTrue(alreadyPresent);
		}
		else
		{
			try {

				String projectURL = ConfigReader.readProjectConfiguration("URL");
				if (projectURL != null) {
					driver.get(projectURL);
					//log.info("URL is intialised");
				} else {
					Assert.fail("URL not found");
					log.info("URL not found");
				}
			} catch (Exception e) {
				e.printStackTrace();
				Assert.fail("URL navigation failed");

			}
		}
	   
	}


	

	@When("/^User types (.*?)$/")
    public void user_types_something(String KartItem) throws Throwable  
	{
		boolean result = KartPage.searchItems(KartItem, driver);
		if (result) {
			log.info("search operation 1 of tc12_searchItems is passed");
		} else {
			Assert.fail("search operation 1 of tc12_searchItems is failed");
		}

	    
	}
	@When(": User clicks on search items")
	public void user_clicks_on_search_items() throws Exception 
	{
		
		boolean result1 = KartPage.clickSearch(driver);
		if (result1) {
			log.info("click operation 1 of tc12_searchItems is passed");
		} else {
			Assert.fail("click operation 1 of tc12_searchItems is  failed");
		}
	   
	}
	 @Then("/^(.*?) gets listed$/")
	    public void something_gets_listed(String KartItem1) throws Throwable 
	{
	    
		boolean result3 = Assertions.validateFirstIndex(driver, KartItem1);
		Assert.assertEquals(result3, true);
		
	}

	@Given(": items is listed")
	public void items_is_listed() throws Exception 
	{
		boolean result3 = Assertions.validateFirstIndex(driver, KartItem);
		Assert.assertEquals(result3, true);
	    
	}


	

	@When(": User clicks on add to kart button")
	public void user_clicks_on_add_to_kart_button() throws Exception 
	{
		resultClick = KartPage.addToKart(driver, KartItem);
	}
	
	@Then(": item is added to the kart")
	public void item_is_added_to_the_kart() 
	{
		if (resultClick) {
			log.info("tc13_addToKartItems is passed");
		} else {
			Assert.fail("Add to kart operation failed");
		}
	    
	}




}
