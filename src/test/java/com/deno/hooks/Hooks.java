package com.deno.hooks;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.deno.factory.DriverFactory;
import com.deno.utils.ExtentReportManager;
import com.deno.utils.ScreenshotUtil;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;

public class Hooks {

    private static final Logger logger =
            LoggerFactory.getLogger(Hooks.class);

    @BeforeAll
    public static void beforeAll() {
        logger.info("=== Opening browser only once ===");
        DriverFactory.initDriver();
        DriverFactory.printSessionId();

        // ✅ Initialize Extent Report
        ExtentReportManager.initReports();
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        ExtentTest test =
                ExtentReportManager
                        .getExtent()
                        .createTest(scenario.getName());

        ExtentReportManager.setTest(test);
    }

    @AfterStep
    public void afterStep(Scenario scenario) {

        String stepName = scenario.getName();

        if (scenario.isFailed()) {
            String base64 =
                    ScreenshotUtil.captureScreenshotAsBase64(
                            DriverFactory.getDriver());

            ExtentReportManager.getTest()
                    .log(Status.FAIL, "Step Failed")
                    .addScreenCaptureFromBase64String(base64);

        } else {
            ExtentReportManager.getTest()
                    .log(Status.PASS, stepName);
        }
    }

    @After
    public void afterScenario(Scenario scenario) {

        if (scenario.isFailed()) {
            logger.error("Scenario FAILED: {}", scenario.getName());

            WebDriver driver = DriverFactory.getDriver();

            byte[] screenshot =
                    ScreenshotUtil.captureScreenshotAsBytes(driver);
            scenario.attach(screenshot, "image/png", scenario.getName());
        }

        ExtentReportManager.getTest().log(
                scenario.isFailed() ? Status.FAIL : Status.PASS,
                "Scenario completed: " + scenario.getName()
        );
    }

    @AfterAll
    public static void afterAll() {
        DriverFactory.quitDriver();
        ExtentReportManager.flushReports();
    }

}