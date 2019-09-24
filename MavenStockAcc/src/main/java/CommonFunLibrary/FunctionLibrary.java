package CommonFunLibrary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Utilities.PropertyFileUtil;

public class FunctionLibrary {

	WebDriver driver;
	// Start Browser

	public static  WebDriver startBrowser(WebDriver driver) throws Throwable {
		System.out.println("start browser");
		if (PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver",
					"C:\\Users\\manvendra.s\\workspace\\MavenStockAcc\\ExecutableFiles\\chromedriver.exe");
			driver = new FirefoxDriver();
		} else if (PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("ie")) {

			System.setProperty("webdriver.ie.driver",
					"C:\\Users\\manvendra.s\\workspace\\MavenStockAcc\\ExecutableFiles\\chromedriver.exe");
			driver = new InternetExplorerDriver();
		} else if (PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("chrome")) {

			System.setProperty("webdriver.chrome.driver",
					"C:\\Users\\manvendra.s\\workspace\\MavenStockAcc\\ExecutableFiles\\chromedriver.exe");
			driver = new ChromeDriver();
		}

		return driver;
	}

	// Open Application
	public static void openApplication(WebDriver driver) throws Throwable {
        System.out.println("open app");
		driver.get(PropertyFileUtil.getValueForKey("URL"));
		driver.manage().window().maximize();
	}

	// Close Application

	public static void closeApplication(WebDriver driver) {
        System.out.println("close");
		driver.close();
	}

	// Click Action

	public static void clickAction(WebDriver driver, String locatorType, String locatorValue) {
		System.out.println("click");
		if (locatorType.equalsIgnoreCase("id")) {
			driver.findElement(By.id(locatorValue)).click();
		} else if (locatorType.equalsIgnoreCase("name")) {
			driver.findElement(By.name(locatorValue)).click();
		} else if (locatorType.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(locatorValue)).click();
		}

	}

	// Type Action

	public static void typeAction(WebDriver driver, String locatorType, String locatorValue, String data) {
System.out.println("type");
		if (locatorType.equalsIgnoreCase("id")) {
			driver.findElement(By.id(locatorValue)).clear();
			driver.findElement(By.id(locatorValue)).sendKeys(data);
		} else if (locatorType.equalsIgnoreCase("name")) {
			driver.findElement(By.name(locatorValue)).clear();
			driver.findElement(By.name(locatorValue)).sendKeys(data);
		} else if (locatorType.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(locatorValue)).clear();
			driver.findElement(By.xpath(locatorValue)).sendKeys(data);
		}
	}

	// Wait For Element

	public static void waitForElement(WebDriver driver, String locatorType, String locatorValue,String waittime) {
		System.out.println("wait");
		WebDriverWait myWait = new WebDriverWait(driver, Integer.parseInt(waittime));
		if (locatorType.equalsIgnoreCase("id")) {
			myWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
		} else if (locatorType.equalsIgnoreCase("name")) {
			myWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorValue)));
		} else if (locatorType.equalsIgnoreCase("xpath")) {
			myWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
		}
	}
	
	
	/*public static void main(String[] args) throws Throwable {
		
		WebDriver driver=null;
		driver=FunctionLibrary.startBrowser(driver); // we need a variable to run as the method has a return type
		FunctionLibrary.openApplication(driver);
		FunctionLibrary.typeAction(driver, "id", "username", "manvendra");
		
	}*/
	
	// page down
	public static void keyDown(WebDriver driver)
	{
		System.out.println("keyDown");
		Actions pageDown=new Actions(driver);
		pageDown.sendKeys(Keys.PAGE_DOWN).build().perform();
	}

	// Capture Data
	public static void captureData(WebDriver driver,String locatorType, String locatorValue) throws Throwable
	{
		System.out.println("capture");
		String data="";
		if (locatorType.equalsIgnoreCase("id")) {
			data=driver.findElement(By.id(locatorValue)).getAttribute("value");
		} else if (locatorType.equalsIgnoreCase("name")) {
			data=driver.findElement(By.name(locatorValue)).getAttribute("value");
		} else if (locatorType.equalsIgnoreCase("xpath")) {
			data=driver.findElement(By.xpath(locatorValue)).getAttribute("value");
		}
		
		FileWriter fw=new FileWriter("C:\\Users\\manvendra.s\\Testing\\NewAutomation\\StockAccounting\\CaptureData\\Data.txt");
		BufferedWriter bw=new BufferedWriter(fw);
		bw.write(data);
		bw.flush();
		bw.close();
		
		
	}
	
	// Table Validation
	
	public static void tableValidation(WebDriver driver,String column) throws Throwable
	{
		FileReader fr=new FileReader("C:\\Users\\manvendra.s\\Testing\\NewAutomation\\StockAccounting\\CaptureData\\Data.txt");
		BufferedReader br=new BufferedReader(fr);
		
		String exp_data=br.readLine();
		
		int colNum=Integer.parseInt(column);
		
		if(driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search.Panel"))).isDisplayed())
		{
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search.Panel"))).click();
			Thread.sleep(3000);
			driver.findElement(By.id(PropertyFileUtil.getValueForKey("Search.Box"))).clear();
			driver.findElement(By.id(PropertyFileUtil.getValueForKey("Search.Box"))).sendKeys(exp_data);
			driver.findElement(By.id(PropertyFileUtil.getValueForKey("Search.Button"))).click();
		}else
		{
			Thread.sleep(3000);
			driver.findElement(By.id(PropertyFileUtil.getValueForKey("Search.Box"))).clear();
			driver.findElement(By.id(PropertyFileUtil.getValueForKey("Search.Box"))).sendKeys(exp_data);
			driver.findElement(By.id(PropertyFileUtil.getValueForKey("Search.Button"))).click();
		}
		
		//web table
		WebElement webTable=driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("WebTable")));
	    
		//row count
	List<WebElement> rows=webTable.findElements(By.tagName("tr"));
	
	for (int i = 1; i <=rows.size(); i++) {
		//capturing supplier number
	String	act_data=driver.findElement(By.xpath("//*[@id='ewContentColumn']/div[3]/form/div//table[@id='tbl_a_supplierslist']/tbody/tr["+i+"]/td["+colNum+"]/div/span")).getText();
	
	System.out.println(act_data);
	
	// validation
	
	Assert.assertEquals(exp_data, act_data);
	break;
	}
	}
	
	// Mouse click
	
	public static void mouseEvent(WebDriver driver,String locatorType, String locatorValue)
	{
		Actions mouse=new Actions(driver);
		mouse.moveToElement(driver.findElement(By.id("mi_a_stock_items"))).build().perform();
		mouse.moveToElement(driver.findElement(By.id("mi_a_stock_categories"))).click().build().perform();
		/*if (locatorType.equalsIgnoreCase("id")) {
			mouse.moveToElement(driver.findElement(By.id(locatorValue))).build().perform();
			mouse.moveToElement(driver.findElement(By.id(locatorValue))).click().build().perform();
		} else if (locatorType.equalsIgnoreCase("name")) {
			mouse.moveToElement(driver.findElement(By.name(locatorValue))).build().perform();
			mouse.moveToElement(driver.findElement(By.name(locatorValue))).click().build().perform();
		} else if (locatorType.equalsIgnoreCase("xpath")) {
			mouse.moveToElement(driver.findElement(By.xpath(locatorValue))).build().perform();
			mouse.moveToElement(driver.findElement(By.xpath(locatorValue))).click().build().perform();
		}*/
		
	}

	
	// Generate date
	public static String generateDate()
	{
	Date date=new Date();
	SimpleDateFormat sdf=new SimpleDateFormat("YYYY_MM_dd_ss");
	return sdf.format(date);
	}
	
// Table Validation Stock category
	
	public void tableValidationCategory(WebDriver driver,String column) throws Throwable
	{
		FileReader fr=new FileReader("C:\\Users\\manvendra.s\\Testing\\NewAutomation\\StockAccounting\\CaptureData\\Data.txt");
		BufferedReader br=new BufferedReader(fr);
		
		String exp_data=br.readLine();
		
		int colNum=Integer.parseInt(column);
		
		if (driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search.Panel.Cat"))).isDisplayed())
		{
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search.Panel.Cat"))).click();
			driver.findElement(By.id(PropertyFileUtil.getValueForKey("Search.Box.Cat"))).sendKeys(exp_data);
			driver.findElement(By.id(PropertyFileUtil.getValueForKey("Search.Button.Cat"))).click();
		}else
		{
			driver.findElement(By.id(PropertyFileUtil.getValueForKey("Search.Box.Cat"))).sendKeys(exp_data);
			driver.findElement(By.id(PropertyFileUtil.getValueForKey("Search.Button.Cat"))).click();
		}
		
WebElement webTable=driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("WebTable.Cat")));
	    
		//row count
	List<WebElement> rows=webTable.findElements(By.tagName("tr"));
	
	for (int i = 1; i <=rows.size(); i++) {
		//capturing supplier number
	String	act_data=driver.findElement(By.xpath("//*[@id='ewContentColumn']/div[3]/form/div//table[@id='tbl_a_stock_categorieslist']]/tbody/tr["+i+"]/td["+colNum+"]/div/span")).getText();
	
	System.out.println(act_data);
	
	// validation
	
	Assert.assertEquals(exp_data, act_data);
	break;
	}
		
		
	}
}
