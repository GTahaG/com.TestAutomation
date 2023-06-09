package pages;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import modules.Browser;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utilities.Common;
import utilities.LogWriter;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class basePage extends Common {

    public enum identifierType {
        xpath, name, id, lnktext, partiallinktext, classname, cssselector, tagname
    }

    public enum elementState {
        present,
        clickable,
        visible,
    }

    public String identifier, locator, locatorDescription;
    Browser browser = Browser.getInstance();

    // [Start] Tag names
    public static final String inputTag = "input";
    public static final String buttonTag = "button";
    public static final String aTag = "a";
    public static final String liTag = "li";
    public static final String spanTag = "span";
    public static final String textareaTag = "textarea";
    public static final String pTag = "p";
    // [End] Tag names

    // [Start] Attribute names
    public static final String idAttribute = "id";
    public static final String classAttribute = "class";
    public static final String nameAttribute = "name";
    public static final String textAttribute = "text()";
    public static final String titleAttribute = "title";
    public static final String typeAttribute = "type";
    // [End] Attribute names

    // [Start] Locators
    public static final String createAnAccountButtonLocator = "Create an account button locator#xpath=//button[@id='SubmitCreate']";
    public static final String logOffButtonLocator = "Log off button locator#xpath=//a[@class='logout']";
    // [End] Locators

    /**
     * Parse identify By and locator
     *
     * @param identifyByAndLocator identifyByAndLocator
     */
    public void parseidentifyByAndlocator(String identifyByAndLocator) {
        LogWriter.writeToLogFile(LogWriter.logLevel.INFO, "Parsing Locator: " + identifyByAndLocator);
        try {
            locatorDescription = identifyByAndLocator.substring(0, identifyByAndLocator.indexOf("#"));
            identifyByAndLocator = identifyByAndLocator.substring(identifyByAndLocator.indexOf("#") + 1);
            identifier = identifyByAndLocator.substring(0, identifyByAndLocator.indexOf("=")).toLowerCase();
            locator = identifyByAndLocator.substring(identifyByAndLocator.indexOf("=") + 1);
            LogWriter.writeToLogFile(LogWriter.logLevel.INFO, "Locator Description : " + locatorDescription);
            LogWriter.writeToLogFile(LogWriter.logLevel.INFO, "Identify Type: " + identifier);
            LogWriter.writeToLogFile(LogWriter.logLevel.INFO, "Locator: " + locator);
        } catch (Exception e) {
            LogWriter.writeToLogFile(LogWriter.logLevel.ERROR, "Locator : '" + identifyByAndLocator + "' is not valid due to \n" + e.getMessage());
        }
    }

    /**
     * Form locator using By functionality
     *
     * @param fullLocator full Locator
     * @return formed locator using By
     */
    public By formLocatorBy(String fullLocator) {
        By formedLocator;
        parseidentifyByAndlocator(fullLocator);
        identifierType i = identifierType.valueOf(identifier);
        formedLocator = switch (i) {
            case xpath -> By.xpath(locator);
            case id -> By.id(locator);
            case name -> By.name(locator);
            case lnktext -> By.linkText(locator);
            case partiallinktext -> By.partialLinkText(locator);
            case classname -> By.className(locator);
            case cssselector -> By.cssSelector(locator);
            case tagname -> By.tagName(locator);
        };
        return formedLocator;
    }

    /**
     * Driver wait
     *
     * @param timeout timeout
     * @return WebDriverWait
     */
    public WebDriverWait driverWait(int timeout) {
        return browser.getWebDriverWait(timeout);
    }

    /**
     * Implementing navigate To URL functionality
     *
     * @param url URl for navigation
     */
    public void navigateToURL(String url) {
        browser.navigateTo(url);
        testStepInfo("Navigated to URL: " + url);
    }

    /**
     * Move to given element
     *
     * @param element element to hover
     */
    public void moveToElement(WebElement element) {
        browser.moveToElement(element);
    }

    /**
     * Get element By locator
     *
     * @param state                state
     * @param locator              locator
     * @param timeout              timeout
     * @param useDriverFindElement useDriverFindElement
     * @return WebElement
     */
    public WebElement getElementBy(elementState state, String locator, int timeout, boolean useDriverFindElement) {
        WebElement element = null;
        if (useDriverFindElement) {
            element = browser.getDriver().findElement(formLocatorBy(locator));
        } else {
            if (state.equals(elementState.visible))
                element = driverWait(timeout).until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(formLocatorBy(locator))));
            if (state.equals(elementState.clickable))
                element = driverWait(timeout).until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(formLocatorBy(locator))));
            if (state.equals(elementState.present))
                element = driverWait(timeout).until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(formLocatorBy(locator))));
        }
        return element;
    }

    /**
     * Get element By locator
     *
     * @param state                state
     * @param locator              locator
     * @param useDriverFindElement useDriverFindElement
     * @return WebElement
     */
    public WebElement getElementBy(elementState state, String locator, boolean useDriverFindElement) {
        return getElementBy(state, locator, 0, useDriverFindElement);
    }

    /**
     * click on element
     *
     * @param locator locator
     */
    public void clickElement(String locator) {
        getElementBy(elementState.present, locator, 0, false).click();
        waitForLoadingSpinner(20, 3);
    }

    /**
     * Set value into specific element
     *
     * @param locator locator
     * @param value   Input value
     */
    public void setValueIntoSpecificElement(String locator, String value) {
        WebElement element = null;
        element = getElementBy(elementState.present, locator, 0, false);
        element.clear();
        element.sendKeys(value);
    }

    /**
     * click on element
     *
     * @param locator locator
     */
    public String getTextFromSpecificElement(String locator) {
        WebElement element = null;
        String text;
        element = getElementBy(elementState.present, locator, 0, false);
        text = element.getText();
        return text;
    }

    /**
     * Wait for loading spinner visibility in application
     *
     * @param timeout time out
     */
    public void waitForLoadingSpinner(long timeout) {
        List<WebElement> spinner_control;
        String loadingSpinner = "Loading spinner locator#xpath=//img[@class=' ls-is-cached lazyloaded']";
        try {
            WebDriverWait wait = driverWait(5);
            spinner_control = wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElementsLocatedBy(formLocatorBy(loadingSpinner))));
            long end_time = System.currentTimeMillis() + timeout * 1000;
            while (spinner_control.size() > 0) {
                spinner_control = wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElementsLocatedBy(formLocatorBy(loadingSpinner))));
                if (System.currentTimeMillis() > end_time) {
                    break;
                }
            }
        } catch (Exception e) {
            //Not handling any exception
        }
    }

    /**
     * Wait for loading spinner visibility in application
     *
     * @param timeout               Time out
     * @param waitForLoadingSpinner Wait For Loading Spinner
     */
    public void waitForLoadingSpinner(long timeout, int waitForLoadingSpinner) {
        List<WebElement> spinner_control;
        String loadingSpinner = "Loading spinner locator#xpath=//img[@class=' ls-is-cached lazyloaded']";
        try {
            WebDriverWait wait = driverWait(waitForLoadingSpinner);
            spinner_control = wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElementsLocatedBy(formLocatorBy(loadingSpinner))));
            long end_time = System.currentTimeMillis() + timeout * 1000;
            while (spinner_control.size() > 0) {
                spinner_control = wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElementsLocatedBy(formLocatorBy(loadingSpinner))));
                if (System.currentTimeMillis() > end_time) {
                    break;
                }
            }
        } catch (Exception e) {
            //Not handling any exception
        }
    }

    /**
     * Get random string
     *
     * @param lengthOfString Length of random string
     * @return randomString
     */
    public String getRandomString(int lengthOfString) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvwxyz";
        StringBuilder randomString = new StringBuilder(lengthOfString);
        for (int i = 0; i < lengthOfString; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            randomString.append(AlphaNumericString.charAt(index));
        }
        return randomString.toString().toUpperCase();
    }

    /**
     * Get random string
     *
     * @param lengthOfString Length of random string
     * @return randomString
     */
    public String getRandomNumericStringValue(int lengthOfString) {
        String AlphaNumericString = "0123456789";
        StringBuilder randomString = new StringBuilder(lengthOfString);
        for (int i = 0; i < lengthOfString; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            randomString.append(AlphaNumericString.charAt(index));
        }
        return randomString.toString();
    }

    /**
     * Get element status By locator
     *
     * @param state                state
     * @param locator              locator
     * @param timeout              timeout
     * @param useDriverFindElement useDriverFindElement
     * @return element status
     */
    public boolean getElementStatusBy(elementState state, String locator, int timeout, boolean useDriverFindElement) {
        boolean elementStatus = false;
        try {
            if (useDriverFindElement) {
                browser.getDriver().findElement(formLocatorBy(locator));
            } else {
                if (state.equals(elementState.visible))
                    driverWait(timeout).until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(formLocatorBy(locator))));
                if (state.equals(elementState.clickable))
                    driverWait(timeout).until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(formLocatorBy(locator))));
                if (state.equals(elementState.present))
                    driverWait(timeout).until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(formLocatorBy(locator))));
            }
            elementStatus = true;
        } catch (WebDriverException ignored) {

        } catch (Exception e) {
            LogWriter.writeToLogFile(LogWriter.logLevel.ERROR, "Failed to get Status of '" + locator + " locator due to \n" + e.getMessage());
        }
        return elementStatus;
    }

    /**
     * Get element status By locator
     *
     * @param state                state
     * @param locator              locator
     * @param useDriverFindElement useDriverFindElement
     * @return element status
     */
    public boolean getElementStatusBy(elementState state, String locator, boolean useDriverFindElement) {
        return getElementStatusBy(state, locator, 0, useDriverFindElement);
    }

    /**
     * Verify element is exist in page
     *
     * @param objectLocator object locator
     * @return true/false depending on element present
     */
    public boolean isElementExist(String objectLocator) {
        return getElementStatusBy(elementState.present, objectLocator, true);
    }

    /**
     * assertEquals with boolean for pass fail
     *
     * @param actualValue   actualValue
     * @param expectedValue expectedValue
     * @param message       message
     */
    public void assertEquals(boolean actualValue, boolean expectedValue, String message) {
        if (Objects.equals(actualValue, expectedValue)) {
            LogWriter.writeToLogFile(LogWriter.logLevel.INFO, message + ". Actual value: '" + actualValue + "' is same as expected value: '" + expectedValue + "'");
            testStepPassed("Actual value: '" + actualValue + "' is same as expected value: '" + expectedValue + "'");
        } else {
            LogWriter.writeToLogFile(LogWriter.logLevel.ERROR, "Actual value: '" + actualValue + "' is not same as expected value: '" + expectedValue + "'");
            testStepFailed("Actual value: '" + actualValue + "' is not same as expected value: '" + expectedValue + "'. " + message + "");
        }
    }

    /**
     * Get current method name
     *
     * @return current method name
     */
    public static String getMethodName() {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        return stackTrace[1].getMethodName();
    }

    /**
     * Test step passed
     *
     * @param passMessage Pass Message
     */
    public void testStepPassed(String passMessage) {
        LogWriter.writeToLogFile(LogWriter.logLevel.INFO, passMessage);
        extentTest.log(Status.PASS, MarkupHelper.createLabel("[PASSED] " + passMessage, ExtentColor.GREEN));
    }

    /**
     * Test step failed
     *
     * @param failedMessage failed Message
     */
    public void testStepFailed(String failedMessage) {
        try {
            totalFailureInCurrentTestCase++;
            LogWriter.writeToLogFile(LogWriter.logLevel.ERROR, failedMessage);
            browser.captureScreenShot();
            extentTest.log(Status.FAIL, MarkupHelper.createLabel("[FAILURE] " + failedMessage, ExtentColor.RED));
            extentTest.fail("Screenshot of failure", MediaEntityBuilder.createScreenCaptureFromBase64String(encodeFileToBase64String(currentScreenShotPath.toString())).build());

            if (getTestConfigurationProperty("ExecuteRemainingStepsOnFailure(Yes/No)").equalsIgnoreCase("No")) {
                LogWriter.writeToLogFile(LogWriter.logLevel.FATAL, "ExecuteRemainingStepsOnFailure value was set to 'No', hence stopping the execution");
                extentTest.log(Status.FATAL, "ExecuteRemainingStepsOnFailure value was set to 'No', hence stopping the execution");
                Assert.fail("ExecuteRemainingStepsOnFailure value was set to 'No', hence stopping the execution");
            }
            if (totalFailureInCurrentTestCase >= maxNoOfFailurePerTestCase) {
                LogWriter.writeToLogFile(LogWriter.logLevel.FATAL, "MaxNoOfFailurePerTestCase value was set to '" + maxNoOfFailurePerTestCase + "', hence stopping the execution");
                extentTest.log(Status.FATAL, "MaxNoOfFailurePerTestCase value was set to '" + maxNoOfFailurePerTestCase + "', hence stopping the execution");
                Assert.fail("MaxNoOfFailurePerTestCase value was set to '" + maxNoOfFailurePerTestCase + "', hence stopping the execution");
            }
        } catch (Exception e) {
            LogWriter.writeToLogFile(LogWriter.logLevel.ERROR, "Failed to add screenshot in extend report due to\n" + getStackTrackAsString(e));
        } finally {
            currentScreenShotPath.setLength(0);
        }
    }

    /**
     * Test step information
     *
     * @param infoMessage Info Message
     */
    public void testStepInfo(String infoMessage) {
        LogWriter.writeToLogFile(LogWriter.logLevel.INFO, infoMessage);
        extentTest.log(Status.INFO, infoMessage);
    }

    /**
     * Encode file to base64 string
     *
     * @return Base64 string
     */
    public String encodeFileToBase64String(String filePath) {
        String base64String = "";
        try {
            base64String = Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get(filePath)));
        } catch (Exception e) {
            LogWriter.writeToLogFile(LogWriter.logLevel.ERROR, "Failed to convert screenshot to Base64 string due to \n'" + getStackTrackAsString(e));
        }
        return base64String;
    }

    /**
     * Get Current Web Driver
     *
     * @return Web driver
     */
    public WebDriver getCurrentWebDriver() {
        return browser.getDriver();
    }

    /**
     * Select/Deselect checkbox with tag attribute
     *
     * @param tagAttribute      Tag attribute
     * @param tagAttributeValue Tag attribute value
     * @param selectStatus      Checkbox select status
     */
    public void selectDeselectCheckboxWithTagAttribute(String tagAttribute, String tagAttributeValue, boolean selectStatus) {
        testStepInfo("[Start]: " + getMethodName());
        String checkboxLocator = "";
        String partialLocator = "";
        try {
            partialLocator = "[@" + tagAttribute + "='" + tagAttributeValue + "']";
            checkboxLocator = "Field locator #xpath=//input" + partialLocator;
            WebElement checkbox = getElementBy(elementState.present, checkboxLocator, 0, false);
            if (checkbox.isSelected() == selectStatus) {
                if (checkbox.isSelected()) {
                    testStepPassed("'" + partialLocator + "' tagged Checkbox was already selected");
                } else {
                    testStepPassed("'" + partialLocator + "' tagged Checkbox was already unselected");
                }
            } else {
                clickElement(checkboxLocator);
                testStepPassed("Changing Status of '" + partialLocator + "' tagged Checkbox.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            testStepFailed("'" + partialLocator + "' tagged Checkbox is not there");
        }
        testStepInfo("[End]: " + getMethodName());
    }

    /**
     * Click On Element With Tag Attribute
     *
     * @param tagName           tagName
     * @param tagAttribute      tagAttribute
     * @param tagAttributeValue tagAttributeValue
     */
    public void clickOnElementWithTagAttribute(String tagName, String tagAttribute, String tagAttributeValue) {
        testStepInfo("[Start]: " + getMethodName());
        String partialLocator = "";
        try {
            if (tagAttribute.equalsIgnoreCase("text()"))
                partialLocator = tagName + "[normalize-space()='" + tagAttributeValue + "']";
            else
                partialLocator = tagName + "[@" + tagAttribute + "='" + tagAttributeValue + "']";
            String elementXpath = "Field locator #xpath=//" + partialLocator;
            clickElement(elementXpath);
            testStepPassed("Successfully clicked on the '" + partialLocator + "' tag");
        } catch (Exception e) {
            testStepFailed("Failed to click on the '" + partialLocator + "' tag due to \n" + e.getMessage());
        }
        testStepInfo("[End]: " + getMethodName());
    }

    /**
     * Click On Element With Tag Attribute
     *
     * @param tagName           tagName
     * @param tagAttribute      tagAttribute
     * @param tagAttributeValue tagAttributeValue
     * @param index             index
     */
    public void clickOnElementWithTagAttribute(String tagName, String tagAttribute, String tagAttributeValue, String index) {
        testStepInfo("[Start]: " + getMethodName());
        String partialLocator = "";
        try {
            if (tagAttribute.equalsIgnoreCase("text()"))
                partialLocator = tagName + "[normalize-space()='" + tagAttributeValue + "']";
            else
                partialLocator = tagName + "[@" + tagAttribute + "='" + tagAttributeValue + "']";
            String elementXpath = "Field locator #xpath=(//" + partialLocator + ")[" + index + "]";
            clickElement(elementXpath);
            testStepPassed("Successfully clicked on the '" + partialLocator + "' tag");
        } catch (Exception e) {
            testStepFailed("Failed to click on the '" + partialLocator + "' tag due to \n" + e.getMessage());
        }
        testStepInfo("[End]: " + getMethodName());
    }

    /**
     * set Value In Tag With Attributte
     *
     * @param tagName           tagName
     * @param tagAttribute      tagAttribute
     * @param tagAttributeValue tagAttributeValue
     * @param FieldValue        FieldValue
     */
    public void setValueInTagWithAttribute(String tagName, String tagAttribute, String tagAttributeValue, String FieldValue) {
        testStepInfo("[Start]: " + getMethodName());
        String fieldXpath, partialLocator = "";
        try {
            partialLocator = tagName + "[@" + tagAttribute + "='" + tagAttributeValue + "']";
            fieldXpath = "Field locator #xpath=//" + partialLocator;
            setValueIntoSpecificElement(fieldXpath, FieldValue);
            testStepPassed("Successfully set'" + FieldValue + "' value in '" + partialLocator + "' tag");
        } catch (Exception e) {
            testStepFailed("Failed to set value in '" + partialLocator + "' tag");
        } finally {
            testStepInfo("[End]: " + getMethodName());
        }
    }

    /**
     * Select dropdown value by tag attribute
     *
     * @param attributeName     Attribute Name
     * @param tagAttributeValue Tag attribute value
     * @param dropDownOption    Drop-down option
     * @param firstItem         Drop-down first item
     */
    public void selectDropdownValueByTagAttribute(String attributeName, String tagAttributeValue, String dropDownOption, boolean firstItem) {
        testStepInfo("[Start]: " + getMethodName());
        String dropdownXpath;
        try {
            dropdownXpath = "Dropdown Box value#xpath=//select[@" + attributeName + "='" + tagAttributeValue + "']";
            WebElement webElement = getElementBy(elementState.present, dropdownXpath, 0, false);
            Select select = new Select(webElement);
            if (firstItem)
                select.selectByIndex(1);
            else
                select.selectByVisibleText(dropDownOption);
            waitForLoadingSpinner(2);
            testStepPassed(" '" + dropDownOption + "' drop-down item was selected successfully");
        } catch (Exception e) {
            e.printStackTrace();
            testStepFailed(" '" + dropDownOption + "' drop-down item was failed to get selected");
        }
        testStepInfo("[End]: " + getMethodName());
    }

    /**
     * Is Element Exist With Tag Attributte
     *
     * @param tagName           tagName
     * @param tagAttribute      tagAttribute
     * @param tagAttributeValue tagAttributeValue
     */
    public boolean isElementExistWithTagAttribute(String tagName, String tagAttribute, String tagAttributeValue) {
        testStepInfo("[Start]: " + getMethodName());
        String fieldXpath, partialLocator = "";
        boolean status;
        if (tagAttribute.equalsIgnoreCase("text()")) {
            partialLocator = tagName + "[contains(" + tagAttribute + " ," + replaceQuoteInXPath(tagAttributeValue) + ")]";
        } else {
            partialLocator = tagName + "[@" + tagAttribute + "=" + replaceQuoteInXPath(tagAttributeValue) + "]";
        }

        fieldXpath = "Field locator #xpath=//" + partialLocator;
        status = isElementExist(fieldXpath);
        testStepInfo("Attribute present status is '" + status + "' for '" + partialLocator + "' tag");
        testStepInfo("[End]: " + getMethodName());
        return status;
    }

    /**
     * Replace Quote For XPath
     *
     * @param formattingText Formatting Text
     */
    public String replaceQuoteInXPath(String formattingText) {
        if (formattingText.contains("'") && !formattingText.contains("\"")) {
            formattingText = "\"" + formattingText + "\"";
        }
        if (!formattingText.contains("'") && formattingText.contains("\"")) {
            formattingText = "'" + formattingText + "'";
        }
        if (!formattingText.contains("'") && !formattingText.contains("\"")) {
            formattingText = "'" + formattingText + "'";
        }
        return formattingText;
    }

    /**
    Switch to the new window
     */
    public void switchToNewWindow() {
        browser.switchNewWindow();
    }

    /**
    Switch back to previous window
     */
    public void switchBackToPreviousWindow() {
        browser.switchBackPreviousWindow();
    }
}
