package greenKart.cucumber.runner;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;




@RunWith(Cucumber.class)
@CucumberOptions(features="Feature",
				monochrome=true,
				glue="greenKart.cucumber.stepDefinition",
				plugin={"html:target/cucumber-report.html/",
						"json:target/cucumber.json",
						"pretty:target/cucumber-pretty.txt",
						"junit:target/cucumber-results.xml/",
						}
				)



public class TestRunner 
{


	

	
}
