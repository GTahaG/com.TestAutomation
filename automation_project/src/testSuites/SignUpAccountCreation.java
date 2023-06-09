package testSuites;

import pages.HomePage;
import pages.LoginPage;
import pages.SignUpPage;
import pages.basePage;

public class git remote add origin https:SignUpAccountCreation extends basePage {


    /**
     * TC-01 : Create a new account with Ethnic Store
     * TC-02 : Leave out the “Country” field unselected. Handle the popped-up alert. Do the same for “post code” which is less than 4 characters
     */
    public void tC_01() {
        LoginPage loginPage = new LoginPage();
        String emailAddress = retrieveTestData("Email Address");

        testStepInfo("[Start]: TC-01 & TC-02");
        loginPage.clickOnSpecificButtonOnSignUpPage(LoginPage.signInButton);
        loginPage.setValueIntoSpecificField(SignUpPage.emailAddressField, getRandomString(4).toLowerCase() + "_" + emailAddress);
        loginPage.clickOnSpecificButtonOnSignUpPage(LoginPage.submitCreateId);

        setValueInTagWithAttribute(inputTag, idAttribute, SignUpPage.customerFirstnameId, getRandomString(5).toLowerCase());
        setValueInTagWithAttribute(inputTag, idAttribute, SignUpPage.customerLastnameId, getRandomString(5).toLowerCase());
        setValueInTagWithAttribute(inputTag, idAttribute, SignUpPage.passwdId, getRandomString(5));
        setValueInTagWithAttribute(inputTag, idAttribute, SignUpPage.address1Id, getRandomString(10));
        setValueInTagWithAttribute(inputTag, idAttribute, SignUpPage.cityId, getRandomString(10));
        selectDropdownValueByTagAttribute(idAttribute, SignUpPage.idStateId, "Alaska", false);
        setValueInTagWithAttribute(inputTag, idAttribute, SignUpPage.postCodeId, "12345");
        selectDropdownValueByTagAttribute(idAttribute, SignUpPage.idCountryId, "-", false);
        setValueInTagWithAttribute(inputTag, idAttribute, SignUpPage.phoneId, getRandomNumericStringValue(5));
        setValueInTagWithAttribute(inputTag, idAttribute, SignUpPage.phoneMobileId, getRandomNumericStringValue(5));
        clickOnElementWithTagAttribute(buttonTag, idAttribute, SignUpPage.submitAccountId);

        assertEquals(isElementExistWithTagAttribute(liTag, textAttribute, SignUpPage.countryIsInvalidText), true,
                "'"+SignUpPage.countryIsInvalidText+"' validation text should be available in the screen");
        testStepInfo("[End]: TC-01 & TC-02");
    }

    /**
     * TC-03 : Login to the account with valid credentials
     * TC-04 : Try Login with invalid credential
     * TC-05 : Click on Log Off button
     */
    public void tC_02() {
        LoginPage loginPage = new LoginPage();

        testStepInfo("[Start]: TC-03");
        loginPage.login();
        assertEquals(isElementExistWithTagAttribute(aTag, classAttribute, HomePage.logoutClass), true,
                "'Sign Out' button should be available");
        testStepInfo("[End]: TC-03");

        testStepInfo("[Start]: TC-05");
        loginPage.logOff();
        assertEquals(isElementExistWithTagAttribute(buttonTag, idAttribute, LoginPage.submitCreateId), true,
                "Logged out from the application should be successfully completed");
        testStepInfo("[End]: TC-05");

        testStepInfo("[Start]: TC-04");
        setValueInTagWithAttribute(inputTag, idAttribute, LoginPage.emailId, "invalid@gmail.com");
        setValueInTagWithAttribute(inputTag, idAttribute, LoginPage.passwdId, "invalidPassword");
        clickOnElementWithTagAttribute(buttonTag, idAttribute, LoginPage.submitLoginId);
        waitForLoadingSpinner(1);
        assertEquals(isElementExistWithTagAttribute(liTag, textAttribute, LoginPage.authenticationFailedText), true,
                "Authentication Failed message should be available in the screen");
        testStepInfo("[End]: TC-04");
    }
}
