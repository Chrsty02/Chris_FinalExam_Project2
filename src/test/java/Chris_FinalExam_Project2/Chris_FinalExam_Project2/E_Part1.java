package Chris_FinalExam_Project2.Chris_FinalExam_Project2;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class E_Part1 {
    static Logger logger = LogManager.getLogger(E_Part1.class);
    private static ExtentSparkReporter sparkReporter;
    private static ExtentReports extent;
    WebDriver driver;
    private ExtentTest test;

    @BeforeClass
    public void initializer() {
        if (extent == null) {
            sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/Reports/extentSparkReport.html");
            sparkReporter.config().setDocumentTitle("Automation Report");
            sparkReporter.config().setReportName("Test Execution Report");
            sparkReporter.config().setTheme(Theme.STANDARD);
            sparkReporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
        }
    }

    public static String captureScreenshot(WebDriver driver) throws IOException {
        String fileSeparator = System.getProperty("file.separator");
        String reportPath = "." + fileSeparator + "Reports";
        String screenshotDir = reportPath + fileSeparator + "Screenshots";
        File dir = new File(screenshotDir);
        if (!dir.exists()) dir.mkdirs();
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String screenshotName = "screenshot" + System.currentTimeMillis() + ".png";
        File dst = new File(screenshotDir + fileSeparator + screenshotName);
        FileUtils.copyFile(src, dst);
        return dst.getAbsolutePath();
    }

    @BeforeMethod
    public void setUp() {
        logger.info("Setting up ChromeDriver.");
        this.driver = new ChromeDriver();
    }

    @Test
    public void testBondsTables() throws IOException {
        test = extent.createTest("testBondsTables");
        logger.info("Navigating to the target URL.");
        test.info("Navigating to the target URL.");
        driver.get("https://www.finmun.finances.gouv.qc.ca/finmun/f?p=100:3000::RESLT::::");
        String screenshot1 = captureScreenshot(driver);
        test.addScreenCaptureFromPath(screenshot1);

        logger.info("Waiting for the page to load completely.");
        test.info("Waiting for the page to load completely.");
        new WebDriverWait(driver, Duration.ofSeconds(60)).until(
            webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
        String screenshot2 = captureScreenshot(driver);
        test.addScreenCaptureFromPath(screenshot2);

        // Click the English link if present
        try {
            logger.info("Checking for the English link.");
            test.info("Checking for the English link.");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
            WebElement englishLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[normalize-space(text())='English']")));
            logger.info("English link found. Clicking the link.");
            test.info("English link found. Clicking the link.");
            englishLink.click();
            String screenshot3 = captureScreenshot(driver);
            test.addScreenCaptureFromPath(screenshot3);

            logger.info("Waiting for the page to reload after clicking English.");
            test.info("Waiting for the page to reload after clicking English.");
            new WebDriverWait(driver, Duration.ofSeconds(60)).until(
                webDriver -> ((JavascriptExecutor) webDriver)
                    .executeScript("return document.readyState").equals("complete"));
        } catch (org.openqa.selenium.TimeoutException e) {
            logger.warn("English link not found. Continuing in default language.");
            test.warning("English link not found. Continuing in default language.");
        }

        try {
            logger.info("Waiting for the 'Results for the last 90 days - Bonds' tab to be clickable.");
            test.info("Waiting for the 'Results for the last 90 days - Bonds' tab to be clickable.");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            WebElement bondsButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[normalize-space()='Results for the last 90 days - Bonds']")
            ));

            logger.info("Clicking the 'Results for the last 90 days - Bonds' tab using JavaScript.");
            test.info("Clicking the 'Results for the last 90 days - Bonds' tab using JavaScript.");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", bondsButton);
            String screenshot4 = captureScreenshot(driver);
            test.addScreenCaptureFromPath(screenshot4);

            logger.info("Waiting for the results section to be visible.");
            test.info("Waiting for the results section to be visible.");
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Results for the last 90 days - Bonds')]")
            ));

            logger.info("Checked for 'Last 90 Days Opening Results - Bonds'.");
            test.info("Checked for 'Last 90 Days Opening Results - Bonds'.");

            // Click the first 5 links in the table, each in a new tab
            logger.info("Locating the first 5 links in the table.");
            test.info("Locating the first 5 links in the table.");
            java.util.List<WebElement> links = driver.findElements(By.xpath("(//table)[1]//a"));
            int linksToClick = Math.min(5, links.size());
            String originalWindow = driver.getWindowHandle();

            for (int i = 0; i < linksToClick; i++) {
                WebElement link = links.get(i);
                String href = link.getAttribute("href");
                String linkText = link.getText();
                logger.info("Opening link #" + (i + 1) + " in a new tab: " + linkText);
                test.info("Opening link #" + (i + 1) + " in a new tab: " + linkText);

                // Open link in a new tab
                ((JavascriptExecutor) driver).executeScript("window.open(arguments[0], '_blank');", href);

                // Switch to new tab
                java.util.Set<String> windowHandles = driver.getWindowHandles();
                for (String handle : windowHandles) {
                    if (!handle.equals(originalWindow)) {
                        driver.switchTo().window(handle);
                        break;
                    }
                }

                // Wait for the new page to load
                new WebDriverWait(driver, Duration.ofSeconds(60)).until(
                    webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").equals("complete"));

                String screenshotLink = captureScreenshot(driver);
                test.addScreenCaptureFromPath(screenshotLink);

                // Close the new tab and switch back
                driver.close();
                driver.switchTo().window(originalWindow);

                // Re-locate the links after switching back
                links = driver.findElements(By.xpath("(//table)[1]//a"));
            }
        } catch (Exception e) {
            logger.error("Error during testBondsTables execution: " + e.getMessage(), e);
            test.log(Status.FAIL, "Exception: " + e.getMessage());
        }
    }

    @AfterMethod
    public void afterEachTest(ITestResult result) throws IOException {
        if (test != null) {
            if (result.getStatus() == ITestResult.FAILURE) {
                String screenshotPath = captureScreenshot(driver);
                test.log(Status.FAIL, "Test Failed. Screenshot below:");
                test.addScreenCaptureFromPath(screenshotPath);
                logger.error("Test failed. Screenshot saved at: " + screenshotPath);
            } else if (result.getStatus() == ITestResult.SUCCESS) {
                test.log(Status.PASS, "Test Passed");
            } else if (result.getStatus() == ITestResult.SKIP) {
                test.log(Status.SKIP, "Test Skipped");
            }
        } else {
            logger.warn("ExtentTest 'test' is null in afterEachTest.");
        }
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    @AfterClass
    public void tearDown() {
        logger.info("Tearing down and flushing the report.");
        if (extent != null) {
            extent.flush();
        }
    }
}
