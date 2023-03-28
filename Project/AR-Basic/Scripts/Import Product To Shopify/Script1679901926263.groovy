import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

//WebUI.openBrowser(null)
String product_page="admin.shopify.com/store/"+store_name+"/products"
WebUI.navigateToUrl(product_page)

if(!WebUI.verifyElementPresent(findTestObject('Object Repository/Shopify/span_shopify_menu', ['text' : 'Products']), 5,FailureHandling.OPTIONAL)) {
	//Login to shopify
	WebUI.setText(findTestObject('Object Repository/Shopify/input_account_email'), GlobalVariable.account_email)
	WebUI.click(findTestObject('Object Repository/Shopify/btn_continue_with_email'))
	WebUI.setEncryptedText(findTestObject('Object Repository/Shopify/input_account_password'), GlobalVariable.account_password)
	WebUI.click(findTestObject('Object Repository/Shopify/btn_login'))
	WebUI.click(findTestObject('Object Repository/Shopify/span_shopify_menu', ['text' : 'Products']))

} else {
	//Add product by Import button
	WebUI.click(findTestObject('Object Repository/Shopify/span_shopify_menu', ['text' : 'Products']))
}

WebUI.click(findTestObject('Object Repository/Shopify/btn_import'))
WebUI.uploadFile(findTestObject('Object Repository/Shopify/file_import_product'), System.getProperty("user.dir") + product_file_source)
WebUI.click(findTestObject('Object Repository/Shopify/btn_upload_and_countinue'))
WebUI.click(findTestObject('Object Repository/Shopify/btn_import_products'))

//Make sure import progress is done
WebUI.delay(35)

//Verify product imported
//WebUI.verifyElementNotPresent(findTestObject('Object Repository/Shopify/div_message_product_import_in_progress'), 45)

@com.kms.katalon.core.annotation.TearDownIfPassed
def teardownIfPassed() {
	String excelFilePath = 'Data Files\\StoreData\\AR-Basic_Store_Data.xlsx'
	CustomKeywords.'excelkeyword.excel.updateValueToSheet'(excelFilePath, "store", 4, store_name, 15, 'Success')
}

@com.kms.katalon.core.annotation.TearDownIfPassed
def teardownIfFailed() {
	String excelFilePath = 'Data Files\\StoreData\\AR-Basic_Store_Data.xlsx'
	CustomKeywords.'excelkeyword.excel.updateValueToSheet'(excelFilePath, "store", 4, store_name, 15, 'Fail')
}