package mappings;

import org.testng.Assert;
import org.testng.annotations.*;
import modules.Browser;
import pages.SignUpPage;
import pages.basePage;
import testSuites.HomePageSearchAndPurchaseProduct;
import testSuites.SignUpAccountCreation;
import utilities.*;

import java.lang.reflect.Method;

import com.aventstack.extentreports.testng.listener.ExtentITestListenerClassAdapter;

@Listeners({ExtentITestListenerClassAdapter.class})
public class TestNGMappings extends basePage{

    Browser browser = Browser.getInstance();
    Common common = new Common();

    /**
     * Setup to open browser and maximize
     *
     * @param browserName browserName
     */
    @BeforeClass
    @Parameters({"browserName"})
    public void preCondition(String browserName) {
        LogWriter.writeToLogFile(LogWriter.logLevel.INFO, "[Start]: Opening and maximizing '" + browserName + "' browser");
        browser.openBrowser(browserName);
        browser.maximize();
        LogWriter.writeToLogFile(LogWriter.logLevel.INFO, "[End]: Opened and maximized '" + browserName + "' browser");
    }

    /**
     * Post condition to close browser and rename extent report
     */
    @AfterClass
    @Parameters({"browserName"})
    public void postCondition(String browserName) {
        LogWriter.writeToLogFile(LogWriter.logLevel.INFO, "[Start]: Performing post condition activities");
        browser.quit();
        FilesUtil.renameFile(Common.outputDirectory + "/", "index.html", "Execution_Report_" + Common.reportGenerationTimeStamp + ".html");
        LogWriter.writeToLogFile(LogWriter.logLevel.INFO, "[End]: Performed post condition activities");
    }

    /**
     * Before specific test case execution
     */
    @BeforeMethod
    public void beforeSpecificTestCaseExecution() {
        LogWriter.writeToLogFile(LogWriter.logLevel.INFO, "[Start]: Deleting cookies before test case execution");
        browser.deleteCookies();
        browser.navigateTo("http://automationpractice.com/index.php");
        LogWriter.writeToLogFile(LogWriter.logLevel.INFO, "[End]: Deleted cookies before test case execution");
    }

    /**
     * After specific test case execution
     */
    @AfterMethod
    public void afterSpecificTestCaseExecution() {
        LogWriter.writeToLogFile(LogWriter.logLevel.INFO, "[Start]: Updating report for '" + Common.executingTestCaseName.toString() + "' test case");
        Common.extentReports.flush();
        Common.totalFailureInCurrentTestCase = 0;
        LogWriter.writeToLogFile(LogWriter.logLevel.INFO, "[End]: Updated report for '" + Common.executingTestCaseName.toString() + "' test case");
    }

    /**
     * Log current test case status
     */
    public void logCurrentTestCaseStatus() {
        if (Common.totalFailureInCurrentTestCase > 0) {
            Assert.fail();
        }
    }

    /**
     * Prepare test data for current test case
     *
     * @param currentTestCaseMethodName Current test case method name
     */
    public void prepareTestData(String currentTestCaseMethodName) {
        LogWriter.writeToLogFile(LogWriter.logLevel.INFO, "[Start]: Preparing test data for '" + currentTestCaseMethodName + "' test case");
        Common.testDataList.clear();
        Common.currentTestCaseIterationCounter = 0;
        common.getTestCaseTestData(currentTestCaseMethodName);
        Common.executingTestCaseName.append(currentTestCaseMethodName);
        Common.extentTest = Common.extentReports.createTest(Common.executingTestCaseName.toString());
        LogWriter.writeToLogFile(LogWriter.logLevel.INFO, "[End]: Prepared test data for '" + currentTestCaseMethodName + "' test case");
    }

    /**
     * TC-01 : Create a new account with Ethnic Store
     * TC-02 : Leave out the “Country” field unselected. Handle the popped-up alert. Do the same for “post code” which is less than 4 characters
     *
     * @param currentTestCaseName current test case name
     */
    @Test
    public void TC_01(Method currentTestCaseName) {
        LogWriter.writeToLogFile(LogWriter.logLevel.INFO, "[Start]: Execution started for '" + currentTestCaseName.getName() + "' test case");
        prepareTestData(currentTestCaseName.getName());
        SignUpAccountCreation signUpAccountCreation = new SignUpAccountCreation();
        signUpAccountCreation.moveToElement(getElementBy(basePage.elementState.present, SignUpPage.popularHomeFeatureLocator, false));
        for (int i = 0; i < Common.testDataList.size(); i++) {
            LogWriter.writeToLogFile(LogWriter.logLevel.INFO, "[Start]: Iteration started for  test data set **'" + i + "'** of '" + currentTestCaseName.getName() + "' test case");
            signUpAccountCreation.tC_01();

            common.changeExecutionCounter();
            LogWriter.writeToLogFile(LogWriter.logLevel.INFO, "[End]: Iteration Completed for test data set **'" + i + "'** of '" + currentTestCaseName.getName() + "' test case");
        }
        logCurrentTestCaseStatus();
        LogWriter.writeToLogFile(LogWriter.logLevel.INFO, "[End]: Execution completed for '" + currentTestCaseName.getName() + "' test case");
    }

    /**
     * TC-03 : Login to the account with valid credentials
     * TC-04 : Try Login with invalid credential
     * TC-05 : Click on Log Off button
     *
     * @param currentTestCaseName current test case name
     */
    @Test
    public void TC_02(Method currentTestCaseName) {
        LogWriter.writeToLogFile(LogWriter.logLevel.INFO, "[Start]: Execution started for '" + currentTestCaseName.getName() + "' test case");
        prepareTestData(currentTestCaseName.getName());
        SignUpAccountCreation signUpAccountCreation = new SignUpAccountCreation();
        signUpAccountCreation.moveToElement(getElementBy(basePage.elementState.present, SignUpPage.popularHomeFeatureLocator, true));
        for (int i = 0; i < Common.testDataList.size(); i++) {
            LogWriter.writeToLogFile(LogWriter.logLevel.INFO, "[Start]: Iteration started for  test data set **'" + i + "'** of '" + currentTestCaseName.getName() + "' test case");
            signUpAccountCreation.tC_02();

            common.changeExecutionCounter();
            LogWriter.writeToLogFile(LogWriter.logLevel.INFO, "[End]: Iteration Completed for test data set **'" + i + "'** of '" + currentTestCaseName.getName() + "' test case");
        }
        logCurrentTestCaseStatus();
        LogWriter.writeToLogFile(LogWriter.logLevel.INFO, "[End]: Execution completed for '" + currentTestCaseName.getName() + "' test case");
    }

    /**
     * TC-06 : Type 'silk' or any relevant keyword on search field
     * TC-07 : Select a specific manufacturer
     * TC-08 : Select any product and on the right hand panel, click on "Write a review ..." option. Write a review in less than 50
     * characters and select "rating" and click on "Continue"
     *
     * @param currentTestCaseName current test case name
     */
    @Test
    public void TC_03(Method currentTestCaseName) {
        LogWriter.writeToLogFile(LogWriter.logLevel.INFO, "[Start]: Execution started for '" + currentTestCaseName.getName() + "' test case");
        prepareTestData(currentTestCaseName.getName());
        HomePageSearchAndPurchaseProduct homePageSearchAndPurchaseProduct = new HomePageSearchAndPurchaseProduct();
        homePageSearchAndPurchaseProduct.moveToElement(getElementBy(basePage.elementState.present, SignUpPage.popularHomeFeatureLocator, true));
        for (int i = 0; i < Common.testDataList.size(); i++) {
            LogWriter.writeToLogFile(LogWriter.logLevel.INFO, "[Start]: Iteration started for  test data set **'" + i + "'** of '" + currentTestCaseName.getName() + "' test case");
            homePageSearchAndPurchaseProduct.tC_03();

            common.changeExecutionCounter();
            LogWriter.writeToLogFile(LogWriter.logLevel.INFO, "[End]: Iteration Completed for test data set **'" + i + "'** of '" + currentTestCaseName.getName() + "' test case");
        }
        logCurrentTestCaseStatus();
        LogWriter.writeToLogFile(LogWriter.logLevel.INFO, "[End]: Execution completed for '" + currentTestCaseName.getName() + "' test case");
    }

    /**
     * TC-09 : Click on "Pin It". It will take to Pinterest, type any id and password, Come back to the Ethnic Store page
     * TC-10 : Click on “G+ Share,” Google window opens. Click on any dummy Gmail ID and password, close it, and come back to Apparel Store page.
     * TC-11 : Click on Facebook, opens a new tab. Click on Cancel button at the bottom of the Facebook Page.
     * TC-12 : Purchase 3 products each from different category, checkout, and place the order
     *
     * @param currentTestCaseName current test case name
     */
    @Test
    public void TC_04(Method currentTestCaseName) {
        LogWriter.writeToLogFile(LogWriter.logLevel.INFO, "[Start]: Execution started for '" + currentTestCaseName.getName() + "' test case");
        prepareTestData(currentTestCaseName.getName());
        HomePageSearchAndPurchaseProduct homePageSearchAndPurchaseProduct = new HomePageSearchAndPurchaseProduct();
        homePageSearchAndPurchaseProduct.moveToElement(getElementBy(basePage.elementState.present, SignUpPage.popularHomeFeatureLocator, true));
        for (int i = 0; i < Common.testDataList.size(); i++) {
            LogWriter.writeToLogFile(LogWriter.logLevel.INFO, "[Start]: Iteration started for  test data set **'" + i + "'** of '" + currentTestCaseName.getName() + "' test case");
            homePageSearchAndPurchaseProduct.tC_04();

            common.changeExecutionCounter();
            LogWriter.writeToLogFile(LogWriter.logLevel.INFO, "[End]: Iteration Completed for test data set **'" + i + "'** of '" + currentTestCaseName.getName() + "' test case");
        }
        logCurrentTestCaseStatus();
        LogWriter.writeToLogFile(LogWriter.logLevel.INFO, "[End]: Execution completed for '" + currentTestCaseName.getName() + "' test case");
    }
}

