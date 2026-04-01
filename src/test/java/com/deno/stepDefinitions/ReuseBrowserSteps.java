package com.deno.stepDefinitions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.deno.factory.DriverFactory;
import com.deno.pages.InternetAppPage;
import com.deno.utils.ExcelUtils;
import com.deno.utils.ExtentReportManager;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ReuseBrowserSteps {

    private static final Logger logger =
            LoggerFactory.getLogger(ReuseBrowserSteps.class);

    private InternetAppPage getPage() {
        return new InternetAppPage(DriverFactory.getDriver());
    }

    // ===================== COMMON STEPS =====================

    @Given("user opens the login page")
    public void user_opens_the_login_page() {
        getPage().openLoginPage();
        logger.info("Opened login page");

        ExtentReportManager.getTest()
                .log(Status.INFO, "User opened login page");
    }

    @Given("user is already using the same browser session")
    public void user_is_already_using_the_same_browser_session() {
        logger.info("Browser reused");

        ExtentReportManager.getTest()
                .log(Status.INFO, "Browser session reused");
    }

    // ===================== EXCEL LOGIN SCENARIO =====================

    @When("user logs in using excel data")
    public void user_logs_in_using_excel_data() {

        String path = "src/test/resources/testdata/LoginDatanew.xlsx";
        int lastRow = ExcelUtils.getLastRowCount(path);

        for (int i = 1; i <= lastRow; i++) {

            String username = ExcelUtils.getCellData(path, i, 0);
            String password = ExcelUtils.getCellData(path, i, 1);
            String expectedResult = ExcelUtils.getCellData(path, i, 2); // valid / invalid
            String expectedMessage = ExcelUtils.getCellData(path, i, 3);

            ExtentReportManager.getTest().log(
                    Status.INFO,
                    "Login attempt -> User: " + username +
                    " | Expected: " + expectedResult
            );

            getPage().openLoginPage();
            getPage().login(username, password);

            boolean secureAreaDisplayed =
                    getPage().isSecureAreaMessageDisplayed();

            boolean errorMessageDisplayed =
                    DriverFactory.getDriver()
                            .getPageSource()
                            .contains(expectedMessage);

         // ✅ EXPECT VALID LOGIN
            if (expectedResult.equals("valid")) {

                Assert.assertTrue(
                        secureAreaDisplayed,
                        "Expected VALID login but secure area not shown for user: " + username
                );

                ExtentReportManager.getTest().log(
                        Status.PASS,
                        "Login SUCCESS as expected for user: " + username
                );
            }

            // ✅ EXPECT INVALID LOGIN
            else if (expectedResult.equals("invalid")) {

                Assert.assertTrue(
                        errorMessageDisplayed,
                        "Expected INVALID login but error message not shown for user: " + username
                );

                ExtentReportManager.getTest().log(
                        Status.PASS,
                        "Login FAILED as expected for user: " + username
                );
            }

            // ❌ UNKNOWN VALUE (real configuration issue)
            else {
                Assert.fail(
                        "Invalid expectedResult value in Excel for user: " + username +
                        " -> [" + expectedResult + "]"
                );
            }
            
        }
    }

    // ===================== LOGOUT SCENARIO =====================

    @When("user logs in with username {string} and password {string}")
    public void user_logs_in_with_username_and_password(String username, String password) {
        getPage().login(username, password);

        ExtentReportManager.getTest()
                .log(Status.INFO, "User logged in with username: " + username);
    }

    @And("user clicks logout button")
    public void user_clicks_logout_button() {
        getPage().clickLogout();

        ExtentReportManager.getTest()
                .log(Status.INFO, "User clicked logout");
    }

    @Then("user should see logout message")
    public void user_should_see_logout_message() {

        Assert.assertTrue(
                getPage().isLogoutMessageDisplayed(),
                "Logout success message was not displayed"
        );

        ExtentReportManager.getTest()
                .log(Status.PASS, "Logout message displayed successfully");
    }
}