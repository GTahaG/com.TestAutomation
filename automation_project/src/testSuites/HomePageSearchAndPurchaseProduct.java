package testSuites;

import pages.HomePage;
import pages.LoginPage;
import pages.basePage;

public class HomePageSearchAndPurchaseProduct extends basePage {
    /**
     * TC-06 : Type 'silk' or any relevant keyword on search field
     * TC-07 : Select a specific manufacturer
     * TC-08 : Select any product and on the right hand panel, click on "Write a review ..." option. Write a review in less than 50
     * characters and select "rating" and click on "Continue"
     */
    public void tC_03() {
        LoginPage loginPage = new LoginPage();
        String keyword = retrieveTestData("Keyword"), manufacturer = retrieveTestData("Manufacturer"),
                reviewTitle = retrieveTestData("Review Title"), comment = retrieveTestData("Comment");

        testStepInfo("[Start]: TC-06");
        setValueInTagWithAttribute(inputTag, idAttribute, HomePage.searchQueryTopId, keyword);
        clickOnElementWithTagAttribute(buttonTag, nameAttribute, HomePage.submitSearchName);
        assertEquals(isElementExistWithTagAttribute(spanTag, textAttribute, HomePage.resultHasBeenFoundText), true,
                "'"+HomePage.resultHasBeenFoundText+"' text should be available");
        testStepInfo("[End]: TC-06");

        testStepInfo("[Start]: TC-07");
        loginPage.login();
        clickOnElementWithTagAttribute(aTag, titleAttribute, manufacturer, "2");
        assertEquals(isElementExistWithTagAttribute(spanTag, textAttribute, manufacturer), true,
                "'"+manufacturer+"' text should be available");
        testStepInfo("[End]: TC-07");

        testStepInfo("[Start]: TC-08");
        clickOnElementWithTagAttribute(aTag, classAttribute, HomePage.productNameClass);
        clickOnElementWithTagAttribute(aTag, classAttribute, HomePage.openCommentFormClass);
        setValueInTagWithAttribute(inputTag, idAttribute, HomePage.commentTitleId, reviewTitle);
        setValueInTagWithAttribute(textareaTag, idAttribute, HomePage.contentId, comment);
        clickOnElementWithTagAttribute(buttonTag, idAttribute, HomePage.submitNewMessageId);
        testStepInfo("[End]: TC-08");
    }

    /**
     * TC-09 : Click on "Pin It". It will take to Pinterest, type any id and password, Come back to the Ethnic Store page
     * TC-10 : Click on “G+ Share,” Google window opens. Click on any dummy Gmail ID and password, close it, and come back to Apparel Store page.
     * TC-11 : Click on Facebook, opens a new tab. Click on Cancel button at the bottom of the Facebook Page.
     * TC-12 : Purchase 3 products each from different category, checkout, and place the order
     */
    public void tC_04() {
        LoginPage loginPage = new LoginPage();

        testStepInfo("[Start]: TC-09");
        loginPage.login();
        clickOnElementWithTagAttribute(aTag, titleAttribute, "Dresses", "2");
        clickOnElementWithTagAttribute(aTag, classAttribute, HomePage.productNameClass, "1");
        clickOnElementWithTagAttribute(buttonTag, classAttribute, HomePage.btnPinterestClass);
        switchToNewWindow();
        setValueInTagWithAttribute(inputTag, idAttribute, HomePage.emailId, "test@gmail.com");
        setValueInTagWithAttribute(inputTag, idAttribute, HomePage.passwordId, "Test Password");
        switchBackToPreviousWindow();
        testStepInfo("[End]: TC-09");

        testStepInfo("[Start]: TC-10");
        clickOnElementWithTagAttribute(buttonTag, classAttribute, HomePage.btnGooglePlusClass);
        switchToNewWindow();
        setValueInTagWithAttribute(inputTag, idAttribute, HomePage.identifierId, "test@gmail.com");
        switchBackToPreviousWindow();
        testStepInfo("[End]: TC-10");

        testStepInfo("[Strat]: TC-11");
        clickOnElementWithTagAttribute(buttonTag, classAttribute, HomePage.btnFacebookClass);
        switchToNewWindow();
        setValueInTagWithAttribute(inputTag, idAttribute, HomePage.emailId, "test@gmail.com");
        switchBackToPreviousWindow();
        testStepInfo("[End]: TC-11");

        testStepInfo("[Start]: TC-12");
        clickOnElementWithTagAttribute(aTag, titleAttribute, "Women", "2");
        clickOnElementWithTagAttribute(aTag, classAttribute, HomePage.productNameClass, "1");
        clickOnElementWithTagAttribute(spanTag, textAttribute, HomePage.addToCartText);
        clickOnElementWithTagAttribute(aTag, titleAttribute, HomePage.proceedToCheckoutTitle);
        clickOnElementWithTagAttribute(aTag, titleAttribute, HomePage.proceedToCheckoutTitle, "2");
        clickOnElementWithTagAttribute(buttonTag, nameAttribute, HomePage.processAddressName);
        clickOnElementWithTagAttribute(inputTag, idAttribute, HomePage.cgvId);
        clickOnElementWithTagAttribute(buttonTag, nameAttribute, HomePage.processCarrierName);
        clickOnElementWithTagAttribute(aTag, titleAttribute, HomePage.payByCheckTitle);
        clickOnElementWithTagAttribute(buttonTag, classAttribute, HomePage.buttonMediumClass);
        assertEquals(isElementExistWithTagAttribute(pTag, textAttribute, HomePage.yourOrderOnMyStoreIsCompleteText), true,
                "'"+HomePage.yourOrderOnMyStoreIsCompleteText+"' text should be available");

        clickOnElementWithTagAttribute(aTag, titleAttribute, "Dresses", "2");
        clickOnElementWithTagAttribute(aTag, classAttribute, HomePage.productNameClass, "1");
        clickOnElementWithTagAttribute(spanTag, textAttribute, HomePage.addToCartText);
        clickOnElementWithTagAttribute(aTag, titleAttribute, HomePage.proceedToCheckoutTitle);
        clickOnElementWithTagAttribute(aTag, titleAttribute, HomePage.proceedToCheckoutTitle, "2");
        clickOnElementWithTagAttribute(buttonTag, nameAttribute, HomePage.processAddressName);
        clickOnElementWithTagAttribute(inputTag, idAttribute, HomePage.cgvId);
        clickOnElementWithTagAttribute(buttonTag, nameAttribute, HomePage.processCarrierName);
        clickOnElementWithTagAttribute(aTag, titleAttribute, HomePage.payByCheckTitle);
        clickOnElementWithTagAttribute(buttonTag, classAttribute, HomePage.buttonMediumClass);
        assertEquals(isElementExistWithTagAttribute(pTag, textAttribute, HomePage.yourOrderOnMyStoreIsCompleteText), true,
                "'"+HomePage.yourOrderOnMyStoreIsCompleteText+"' text should be available");

        clickOnElementWithTagAttribute(aTag, titleAttribute, "T-shirts", "2");
        clickOnElementWithTagAttribute(aTag, classAttribute, HomePage.productNameClass, "1");
        clickOnElementWithTagAttribute(spanTag, textAttribute, HomePage.addToCartText);
        clickOnElementWithTagAttribute(aTag, titleAttribute, HomePage.proceedToCheckoutTitle);
        clickOnElementWithTagAttribute(aTag, titleAttribute, HomePage.proceedToCheckoutTitle, "2");
        clickOnElementWithTagAttribute(buttonTag, nameAttribute, HomePage.processAddressName);
        clickOnElementWithTagAttribute(inputTag, idAttribute, HomePage.cgvId);
        clickOnElementWithTagAttribute(buttonTag, nameAttribute, HomePage.processCarrierName);
        clickOnElementWithTagAttribute(aTag, titleAttribute, HomePage.payByCheckTitle);
        clickOnElementWithTagAttribute(buttonTag, classAttribute, HomePage.buttonMediumClass);
        assertEquals(isElementExistWithTagAttribute(pTag, textAttribute, HomePage.yourOrderOnMyStoreIsCompleteText), true,
                "'"+HomePage.yourOrderOnMyStoreIsCompleteText+"' text should be available");
        testStepInfo("[End]: TC-12");
    }
}
