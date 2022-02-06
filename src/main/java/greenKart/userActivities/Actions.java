package greenKart.userActivities;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import greenKart.base.base;
import greenKart.utilties.ConfigReader;

public class Actions extends base {

	public static boolean checkPresentUrl(WebDriver driver,String ExpectedURL) {
		boolean result = false ;
		if (driver.getCurrentUrl().contains(ExpectedURL)) 
		
		{
			log.info("URL is already intialised");
			result = true;
			
		}
		
		else 
		{
			result = false;
		}
		
		return result;		
	}
	
	public static boolean getUrl(WebDriver driver,String projectURL) throws Exception
	{
		boolean result = false ;
		
		//	String projectURL = ConfigReader.readProjectConfiguration("URL");
			if (projectURL != null)
			{
				driver.get(projectURL);
				if (driver.getCurrentUrl().contains(projectURL)) 
				{
					log.info("URL is intialised");
					result = true;
				}
				else 
				{
					log.info("URL is not correctly intialised");
				}
				
			} else 
			{
				log.info("URL not found");
			}
	
			return result;
		
		}	
}
