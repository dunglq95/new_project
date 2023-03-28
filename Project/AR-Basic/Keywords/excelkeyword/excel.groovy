package excelkeyword
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testcase.TestCaseFactory
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords
import com.kms.katalon.keyword.excel.ExcelKeywords
import internal.GlobalVariable
import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.mobile.helper.MobileElementCommonHelper
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.exception.WebElementNotFoundException


class excel {
	@Keyword
	public void updateValueToCell(String file, String sheetname, int colnumtosearch, String rowvaluetosearch, int coltoupdate, String status, int coltoupdate2, String status2) {
		def workbook = ExcelKeywords.getWorkbook(file)
		def sheet = ExcelKeywords.getExcelSheet(workbook, sheetname)
		int lastRowIndex = sheet.getLastRowNum()
		int newRowIndex = lastRowIndex + 1 // add 1 to the last row index to get the new row index
	
		ExcelKeywords.setValueToCellByIndex(sheet, newRowIndex, coltoupdate, status) // set the new value in the new row
		ExcelKeywords.setValueToCellByIndex(sheet, newRowIndex, coltoupdate2, status2) // set the new value in the new row
		ExcelKeywords.saveWorkbook(file, workbook)
	}
	
	@Keyword
	public void updateValueToSheet(String file,String sheetname,int colnumtosearch, String rowvaluetosearch, int coltoupdate,String status) {
		def	workbook = ExcelKeywords.getWorkbook(file)
		def	sheet = ExcelKeywords.getExcelSheet(workbook, sheetname)

		int rowIndex = ExcelKeywords.getRowIndexByCellContent(sheet, rowvaluetosearch, colnumtosearch)
		ExcelKeywords.setValueToCellByIndex(sheet, rowIndex, coltoupdate, status)
		ExcelKeywords.saveWorkbook(file, workbook)
	}

}