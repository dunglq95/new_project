import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import javax.lang.model.element.VariableElement

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
import com.sun.net.httpserver.Authenticator.Failure

import internal.GlobalVariable

//Open URL
WebUI.openBrowser(null)

//Open store with URL https://accounts.shopify.com/lookup
WebUI.navigateToUrl("https://accounts.shopify.com/lookup")

//Login Email + Password
WebUI.setText(findTestObject('Object Repository/Shopify/input_account_email'), GlobalVariable.account_email)
WebUI.click(findTestObject('Object Repository/Shopify/btn_continue_with_email'))
WebUI.setEncryptedText(findTestObject('Object Repository/Shopify/input_account_password'), GlobalVariable.account_password)
WebUI.click(findTestObject('Object Repository/Shopify/btn_login'))

//Choose view all store if it need
WebUI.click(findTestObject('Object Repository/Shopify/div_view_all_store'),FailureHandling.OPTIONAL)

//Choose account and store if it need
WebUI.switchToWindowIndex(1)
WebUI.click(findTestObject('Object Repository/Shopify/account_shopify', ['account_email' : GlobalVariable.account_email]),FailureHandling.OPTIONAL)
WebUI.click(findTestObject('Object Repository/Shopify/span_store_name_selection', ['store_name' : GlobalVariable.store_name + ".myshopify.com"]),FailureHandling.OPTIONAL)
WebUI.click(findTestObject('Object Repository/Shopify/account_shopify', ['account_email' : GlobalVariable.account_email]),FailureHandling.OPTIONAL)

//Verify that login successfully
WebUI.waitForPageLoad(20)
List<String> expectedTexts = Arrays.asList("Home", "Orders", "Products", "Customers", "Content", "Analytics", "Marketing", "Discounts");

for (String expectedText : expectedTexts) {
	WebUI.verifyElementPresent(findTestObject('Object Repository/Shopify/span_shopify_menu', ['text' : expectedText]), 5)
}