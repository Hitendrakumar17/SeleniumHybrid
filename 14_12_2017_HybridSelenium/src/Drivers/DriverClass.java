package Drivers;

import java.util.Properties;

import org.openqa.selenium.By;

import commonLibs.ExcelDriver;
import commonLibs.KeywordUtility;
import commonLibs.Utils;

public class DriverClass {

	private static KeywordUtility keywordUtilityDriver;
	private static ExcelDriver excelDriver;
	private static String driverPropertyFile = "C:\\Development\\Eclipse\\ProjectHybridFramework\\14_12_2017\\src\\conf\\AutomationInput.properties";
	private static Properties driverProperties;
	private static String inputFileFolder;
	private static String resultFolder;
	private static String mainDriverInputFile;
	private static String currentTestCaseStatus;

	public static void main(String[] args) {

		driverProperties = Utils.getProperties(driverPropertyFile);

		inputFileFolder = driverProperties.getProperty("InputFileFolder")
				.trim();
		mainDriverInputFile = driverProperties.getProperty("DriverInputFile")
				.trim();
		resultFolder = driverProperties.getProperty("ResultFolder").trim();
		
		testSuiteDriver();
		
		exportToExcel();

	}

	private static void testSuiteDriver() {

		String testCaseSheetName, runFlag, runStatus, comment;
		String driverExcelFile;
		int currentSuiteRowIndex, rowCount;

		driverExcelFile = inputFileFolder + "\\" + mainDriverInputFile;
		excelDriver = new ExcelDriver();
		excelDriver.openExcelWorkbook(driverExcelFile);

		rowCount = excelDriver.getRowCountOfSheet("TestSuite"); // 7

		for (currentSuiteRowIndex = 2; currentSuiteRowIndex <= rowCount + 1; currentSuiteRowIndex++) {
			testCaseSheetName = "";
			runFlag = "";
			runStatus = "";
			comment = "";
			currentTestCaseStatus = "Pass";

			testCaseSheetName = excelDriver
					.getCellCData("TestSuite", currentSuiteRowIndex, 2);
			runFlag = excelDriver.getCellCData("TestSuite", currentSuiteRowIndex, 3);

			testCaseSheetName = testCaseSheetName.trim();

			runFlag = runFlag.toLowerCase().trim();

			if (runFlag.equals("yes")) {
				keywordUtilityDriver = null;
				runStatus = testCaseDriver(testCaseSheetName);

				if (runStatus == "") {
					if (currentTestCaseStatus == "Pass") {
						runStatus = "Pass";
					} else {
						runStatus = "Fail";
						comment = "One or more steps got Failed";
					}

				} else {
					comment = runStatus;
					runStatus = "Fail";
				}

			} else {
				runStatus = "Skipped";
				comment = "Because, Run Flag was set to " + runFlag;
			}

			excelDriver.setCellCData("testSuite", currentSuiteRowIndex, 4, runStatus);
			excelDriver.setCellCData("testSuite", currentSuiteRowIndex, 5, comment);
		}

	}

	private static String testCaseDriver(String sSheetName) {
		int iRow, rowCount;

		String testCaseDriverreturnvalue = "";

		String actionKeyword;
		String objectLocator;
		String argumentValue;
		String runStatus;
		String comment;
		String returnValue;
		By by;

		try {
			keywordUtilityDriver = new KeywordUtility();
			rowCount = excelDriver.getRowCountOfSheet(sSheetName);
			System.out.println("The row count is: " + rowCount);

			for (iRow = 2; iRow <= rowCount + 1; iRow++) {
				actionKeyword = "";
				objectLocator = "";
				argumentValue = "";
				runStatus = "";
				comment = "";
				returnValue = "";
				by = null;

				actionKeyword = excelDriver.getCellCData(sSheetName, iRow, 2)
						.trim();
				objectLocator = excelDriver.getCellCData(sSheetName, iRow, 3)
						.trim();
				argumentValue = excelDriver.getCellCData(sSheetName, iRow, 4)
						.trim();

				if (objectLocator != "" && !objectLocator.equals("")) {
					by = Utils.getLocatorBy(objectLocator);
				}

				if (actionKeyword == "") {
					runStatus = "Skipped";
					comment = "No Action Keyword";
				} else {
					try {

						returnValue = keywordUtilityDriver.performAction(actionKeyword,
								by, argumentValue);

						if (returnValue.toLowerCase().contains("error")) {
							runStatus = "Fail";
							comment = returnValue;
							returnValue = "Failure";
						} 
						else if(returnValue.equals("File already exists")){
							runStatus = "fail";
							comment = "File Already exists";
							testCaseDriverreturnvalue = "Fail";
						}
						else if(returnValue.contains("is present")){
							runStatus = "Pass";
							comment = "Element is present";							
						}
						else {
							runStatus = "pass";
							returnValue = "Pass";
							comment = "The test step passed";
						}

					} catch (Exception e) {
						runStatus = "Exception";
						comment = e.getMessage();
						currentTestCaseStatus = "Fail";
					}
				}

				excelDriver.setCellCData(sSheetName, iRow, 5, runStatus);
				excelDriver.setCellCData(sSheetName, iRow, 6, returnValue);
				excelDriver.setCellCData(sSheetName, iRow, 7, comment);

			}

		} catch (Exception e) {
			testCaseDriverreturnvalue = e.getMessage();
			currentTestCaseStatus = "Fail";
		}

		return testCaseDriverreturnvalue;
	}

	private static void exportToExcel() {
		String outputFileName;
		String dateTimeStamp;

		dateTimeStamp = Utils.getDateTimeStamp();

		outputFileName = resultFolder + "\\Result as on " + dateTimeStamp
				+ ".xlsx";

		excelDriver.saveAs(outputFileName);
	}
}
