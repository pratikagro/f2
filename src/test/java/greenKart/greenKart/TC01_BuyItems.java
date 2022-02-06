package greenKart.greenKart;

import static org.testng.Assert.fail;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import greenKart.assertions.Assertions;
import greenKart.base.base;
import greenKart.datagenerators.*;
import greenKart.userActivities.KartPage;
import greenKart.utilties.ConfigReader;
import greenKart.utilties.ExtentReportNG;

public class TC01_BuyItems extends base 
{
	private WebDriver driver;
	private ITestContext context = null;
	private String KartItem = "";
	private String dummyData = "";
	private String random;
	ExtentTest mainNode;
	ExtentTest test;
	

	
	/*@Factory(dataProvider = "KartPage")
	public TC01_BuyItems(String KartItem, String dummyData) 
	{
		this.KartItem = KartItem;
		this.dummyData = dummyData;
		log.info("Factory is intialised");
	}*/

	@DataProvider(name = "KartPage")
	public static Object[][] testDataGenerator() throws Exception {

		FileInputStream file = new FileInputStream("./TestData/TestData.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet currentSheet = workbook.getSheet("KartPage");
		int numberOfData = currentSheet.getPhysicalNumberOfRows();
		XSSFRow row = currentSheet.getRow(0);
		int numberOfAttributes = row.getPhysicalNumberOfCells();
		Object testData[][] = new Object[numberOfData][numberOfAttributes];

		for (int i = 0; i <= numberOfData - 1; i++) {
			XSSFRow row1 = currentSheet.getRow(i);
			for (int j = 0; j <= numberOfAttributes - 1; j++) {
				testData[i][j] = row1.getCell(j).getStringCellValue();
			}
		}
	//	log.info("dataprovider is intialised");
		return testData;

	}
	
	@BeforeClass
	@DataProvider(name = "KartPage")
	public void startDriver (String KartItem, String dummyData) 
	{
		try {
			
		test = extent.createTest(TC01_BuyItems.class.getName()+KartItem);
		extentTest.set(test);
		this.driver = initializeDriver(test.createNode("Step 1 driver start"));
		 mainNode = test.createNode("step 3 buy kart");
		//context.setAttribute("WebDriver", driver);
		random = ExtentReportNG.getScreenShotPath(this.getClass().getName(),driver);
		mainNode.log(Status.INFO, "Step1 driver started succesfully").addScreenCaptureFromPath(random, this.getClass().getName()+random.substring(15));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			mainNode.log(Status.FAIL, e);
		}
	}

	
	@Test
	public void tc11_BuyAtGreenKart()  {
		try {
			
		boolean result = KartPage.openPage(driver,test.createNode("Step 2 openPage"));
		if (result) 
		{
			log.info("KartPage opened succesfully");
			random = ExtentReportNG.getScreenShotPath(this.getClass().getName(),driver);
			// test.log().addScreenCaptureFromPath(random, this.getClass().getName()+random.substring(15));
			mainNode.log(Status.INFO, "Step2 Page started succesfully", MediaEntityBuilder.createScreenCaptureFromPath(random).build());
			
		} 
		else 
		{
			Assert.fail("KartPage opening failed");
			mainNode.log(Status.FAIL, "KartPage opening failed").addScreenCaptureFromPath(random, this.getClass().getName()+random.substring(15));
		}
	
		 result = KartPage.searchItems(KartItem, driver);
		if (result) {
			log.info("search operation 1 of tc12_searchItems is passed");
			
		} else {
			Assert.fail("search operation 1 of tc12_searchItems is failed");
		}

		boolean result1 = KartPage.clickSearch(driver);
		if (result1) {
			log.info("click operation 1 of tc12_searchItems is passed");
		} else {
			Assert.fail("click operation 1 of tc12_searchItems is  failed");
		}

		Thread.sleep(5000);
		boolean result3 = Assertions.validateFirstIndex(driver, KartItem);
		Assert.assertEquals(result3, true);
		if (result3) {
			log.info("item visible at first index is correct");
		} else {
			log.info("item visible at first index is incorrect");
			Assert.fail("item visible at first index is incorrect");
		}
		
		
		result = KartPage.addToKart(driver, KartItem);
		if (result) {
			log.info("tc13_addToKartItems is passed");
			mainNode.log(Status.PASS,"test passed");
		} else {
			Assert.fail("Add to kart operation failed");
		}
		}
		catch (Exception e)
		{
			mainNode.log(Status.FAIL, e);
		}
	}
	
	@AfterClass
	public void closeeDriver() 
	{
		try {
			driver.quit();
			extent.flush();
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
		}

	}
	
}
