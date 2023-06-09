package pages;

import utilities.Common;

public class LoginPage extends basePage{

    //[Start]: Attribute values
    public static final String submitCreateId = "SubmitCreate";
    public static final String emailId = "email";
    public static final String passwdId = "passwd";
    public static final String submitLoginId = "SubmitLogin";

    public static final String authenticationFailedText = "Authentication failed.";
    //[End]: Attribute values

    //[Start]: Button Names
    public static final String signInButton = "Sign in";
    //[End]: Button Names

    /**
     * Login into the application with valid credential
     *
     */
    public void login() {
        String userName = Common.getTestConfigurationProperty("UserName");
        String password = Common.getTestConfigurationProperty("Password");
        clickOnElementWithTagAttribute(aTag, classAttribute, "login");
        setValueInTagWithAttribute(inputTag, idAttribute, emailId, userName);
        setValueInTagWithAttribute(inputTag, idAttribute, passwdId, password);
        clickOnElementWithTagAttribute(buttonTag, idAttribute, submitLoginId);
        moveToElement(getElementBy(basePage.elementState.present, logOffButtonLocator, false));
        testStepInfo("Successfully logged into the application");
    }

    /**
     * Log off from the application
     *
     */
    public void logOff() {
        clickOnElementWithTagAttribute(aTag, classAttribute, "logout");
        moveToElement(getElementBy(basePage.elementState.present, createAnAccountButtonLocator, false));
        testStepInfo("Successfully logged off from the application");
    }

    /**
     * Click On Specific Button On SignUp Page
     *
     * @param buttonNameOrId Button Name or ID
     * */
    public void clickOnSpecificButtonOnSignUpPage(String buttonNameOrId){
        testStepInfo("[Start]: "+getMethodName());
        String buttonLocator = "Button locator#xpath=//a[normalize-space()= '" + buttonNameOrId + "'] | //button[@id='" + buttonNameOrId + "']";
        try {
            moveToElement(getElementBy(elementState.present, buttonLocator, true));
            clickElement(buttonLocator);
            testStepPassed("Successfully clicked on the '" + buttonNameOrId + "' button");
        } catch (Exception e) {
            testStepFailed("Failed to click on '" + buttonNameOrId + "' button due to \n" + getStackTrackAsString(e));
        }
        testStepInfo("[End]: "+getMethodName());
    }

    /**
     * Set value into the Specific field
     *
     * @param inputFieldName Input Field Name
     * @param inputText Input Text
     */
    public void setValueIntoSpecificField(String inputFieldName, String inputText){
        testStepInfo("[Start]: "+getMethodName());
        String inputField = "Input field locator#xpath=//label[normalize-space()= '"+ inputFieldName +"']/..//input";
        try {
            moveToElement(getElementBy(elementState.present, inputField, true));
            setValueIntoSpecificElement(inputField, inputText);
            testStepPassed("Successfully entered value in the '" + inputField + "' field");
        } catch (Exception e) {
            testStepFailed("Failed to enter value in the '" + inputField + "' field due to \n" + getStackTrackAsString(e));
        }
        testStepInfo("[End]: "+getMethodName());
    }
}
