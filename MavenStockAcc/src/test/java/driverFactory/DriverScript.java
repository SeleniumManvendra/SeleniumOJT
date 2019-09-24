package driverFactory;



import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import CommonFunLibrary.FunctionLibrary;
import Utilities.ExcelFileUtil;

public class DriverScript {

	WebDriver driver;
	ExtentReports report;
	ExtentTest logger;
	public void startTest() throws Throwable 
	{
		ExcelFileUtil excel=new ExcelFileUtil();
		System.out.println(excel.rowCount("MasterTestCases"));
		//Master Test case sheet
		for (int i = 1; i <=excel.rowCount("MasterTestCases"); i++) {
			
			String ModuleStatus="";
			
			if (excel.getData("MasterTestCases", i, 2).equalsIgnoreCase("Y")) {
				
				String TC_Module=excel.getData("MasterTestCases", i, 1);
				System.out.println(TC_Module);
				report=new ExtentReports("C:\\Users\\manvendra.s\\workspace\\MavenStockAcc\\Reports\\"+TC_Module +"_"+FunctionLibrary.generateDate()+".html");
				logger=report.startTest(TC_Module);
				
				
				int rowCount=excel.rowCount(TC_Module);
				// TC MOdule sheet
				for (int j = 1; j <=rowCount; j++) {
					
					String Description=excel.getData(TC_Module, j, 0);
					String Object_Type=excel.getData(TC_Module, j, 1);
					String Locator_Type=excel.getData(TC_Module, j, 2);
					String Locator_Value=excel.getData(TC_Module, j, 3);
					String Test_Data=excel.getData(TC_Module, j, 4);
					
					
					try {
						if (Object_Type.equalsIgnoreCase("startBrowser")) {
							driver=FunctionLibrary.startBrowser(driver);
							logger.log(LogStatus.INFO, Description);
						}else
							if (Object_Type.equalsIgnoreCase("openApplication")) {
								FunctionLibrary.openApplication(driver);
								logger.log(LogStatus.INFO, Description);
							}else
								if (Object_Type.equalsIgnoreCase("typeAction")) {
									FunctionLibrary.typeAction(driver, Locator_Type, Locator_Value, Test_Data);
									logger.log(LogStatus.INFO, Description);
								}else
									if (Object_Type.equalsIgnoreCase("clickAction")) {
										FunctionLibrary.clickAction(driver, Locator_Type, Locator_Value);
										logger.log(LogStatus.INFO, Description);
									}else
										if (Object_Type.equalsIgnoreCase("waitForElement")) {
											FunctionLibrary.waitForElement(driver, Locator_Type, Locator_Value, Test_Data);
											logger.log(LogStatus.INFO, Description);
										}else
											if (Object_Type.equalsIgnoreCase("closeApplication")) {
												FunctionLibrary.closeApplication(driver);
												logger.log(LogStatus.INFO, Description);
											}else
												if (Object_Type.equalsIgnoreCase("keyDown")) {
													FunctionLibrary.keyDown(driver);
													logger.log(LogStatus.INFO, Description);
												}else
													if (Object_Type.equalsIgnoreCase("captureData")) {
														FunctionLibrary.captureData(driver, Locator_Type, Locator_Value);
														logger.log(LogStatus.INFO, Description);
													}else
														if (Object_Type.equalsIgnoreCase("tableValidation")) {
															FunctionLibrary.tableValidation(driver, Test_Data);
															logger.log(LogStatus.INFO, Description);
														}
														else
															if (Object_Type.equalsIgnoreCase("mouseEvent")) {
																FunctionLibrary.mouseEvent(driver, Locator_Type, Locator_Value);
																logger.log(LogStatus.INFO, Description);
															}
						
					
						excel.setData(TC_Module, j, 5, "PASS");
						logger.log(LogStatus.PASS, Description +" "+"PASS");
						ModuleStatus="true";
					} catch (Exception e) {
						excel.setData(TC_Module, j, 5, "FAIL");
						logger.log(LogStatus.FAIL, Description +" "+"FAIL");
						ModuleStatus="false";
						File srcFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
						FileUtils.copyFile(srcFile,new File("C:\\Users\\manvendra.s\\workspace\\MavenStockAcc\\ScreenShot\\"+TC_Module +" " + FunctionLibrary.generateDate()));
						break;
						
					}
					
				}
				
				report.endTest(logger);
				report.flush();
				
				if (ModuleStatus.equalsIgnoreCase("true")) {
					excel.setData("MasterTestCases", i, 3, "PASS");
				}else
					if (ModuleStatus.equalsIgnoreCase("false")) {
						excel.setData("MasterTestCases", i, 3, "FAIL");
					}
					else{
						
						excel.setData("MasterTestCases", i, 3, "NOT EXECUTED");
					}
			}
			
			
			
		}
		
		
	}
	
	
}
