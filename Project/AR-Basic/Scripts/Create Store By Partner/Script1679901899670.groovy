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
import com.kms.katalon.entity.global.GlobalVariableEntity
import com.kms.katalon.keyword.excel.ExcelKeywords

import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys


WebUI.openBrowser(null)
//Navigate to link Partner
String link_partner="https://partners.shopify.com/"+GlobalVariable.partner_ID+"/stores/new"
WebUI.navigateToUrl(link_partner)

//Login Email + Password
WebUI.setText(findTestObject('Object Repository/Shopify/input_account_email'), GlobalVariable.account_email)
WebUI.click(findTestObject('Object Repository/Shopify/btn_continue_with_email'))
WebUI.setEncryptedText(findTestObject('Object Repository/Shopify/input_account_password'), GlobalVariable.account_password)
WebUI.click(findTestObject('Object Repository/Shopify/btn_login'))

//Create store with store build = Checkout Extensibility
String link_partner_checkout_extensibility="https://partners.shopify.com/"+GlobalVariable.partner_ID+"/stores/new?store_type=dev_preview_store&developer_preview_handle=checkout_extensibility"
WebUI.navigateToUrl(link_partner_checkout_extensibility)

//Input store name and Data configs
WebUI.setText(findTestObject('Object Repository/ShopifyPartner/input_store_name'), store_name)
WebUI.click(findTestObject('Object Repository/ShopifyPartner/span_data_and_configurations', ['store_data': store_data]))

//Click create store
WebUI.click(findTestObject('Object Repository/ShopifyPartner/btn_create_development_store'))

//Select user account
WebUI.waitForPageLoad(50,FailureHandling.OPTIONAL)
WebUI.click(findTestObject('Object Repository/Shopify/account_shopify', ['account_email' : GlobalVariable.account_email]),FailureHandling.OPTIONAL)

//Make sure that store is created success
WebUI.waitForElementPresent(findTestObject('Object Repository/Shopify/span_shopify_menu', ['text' : 'Home']), 50)
List<String> expectedTexts = Arrays.asList("Home", "Orders", "Products", "Customers", "Content", "Analytics", "Marketing", "Discounts");
for (String expectedText : expectedTexts) {
	WebUI.verifyElementPresent(findTestObject('Object Repository/Shopify/span_shopify_menu', ['text' : expectedText]), 15)
}

@com.kms.katalon.core.annotation.TearDownIfPassed
def teardownIfPassed() {
	String excelFilePath = 'Data Files\\StoreData\\AR-Basic_Store_Data.xlsx'
	CustomKeywords.'excelkeyword.excel.updateValueToSheet'(excelFilePath, "store", 4, store_name, 12, 'Success')
}

@com.kms.katalon.core.annotation.TearDownIfPassed
def teardownIfFailed() {
	String excelFilePath = 'Data Files\\StoreData\\AR-Basic_Store_Data.xlsx'
	CustomKeywords.'excelkeyword.excel.updateValueToSheet'(excelFilePath, "store", 4, store_name, 12, 'Fail')
}