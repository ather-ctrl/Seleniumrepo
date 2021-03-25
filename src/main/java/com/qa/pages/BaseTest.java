package com.qa.pages;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
//import com.beust.jcommander.Parameters;
import com.qa.utilities.ReadConfig;

public class BaseTest {
	ReadConfig readconfig = new ReadConfig();
	public String baseURL = readconfig.getApplicationURL();
	public String username = readconfig.getUsername();
	public String password = readconfig.getPassword();
	public static WebDriver driver;
	public static Logger logger;
	
	@BeforeClass()
	@Parameters("browser")
	public void setup (String browser)
	{
		logger = Logger.getLogger("linkedin");
		PropertyConfigurator.configure("log4j.properties");
		if(browser.contentEquals("edge"))
		{
		System.setProperty("webdriver.edge.driver", readconfig.getMSedgePath());
		driver = new EdgeDriver();
		}
		else if(browser.equals("chrome"))
		{
		System.setProperty("webdriver.chrome.driver", readconfig.getChromePath());
		driver = new ChromeDriver();
		}
		driver.manage().window().maximize();
		driver.get(baseURL);
	}
	@AfterClass
	public void tearDown()
	{
		driver.quit();
	}
	
	public static void captureScreen(WebDriver driver, String tname) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File target = new File(System.getProperty("user.dir") + "/Screenshots/" + tname + ".png");
		FileUtils.copyFile(source, target);
		System.out.println("Screenshot taken");

	}
}
