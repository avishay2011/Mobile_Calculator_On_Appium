package test;

import Page_Objects.Calculator_Page;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    //attributes
    protected static WebDriver driver;
    public static Actions actions;
    public static WebDriverWait wait;

    //Calling to page objects
    Calculator_Page calculatorPage;

    //report;
    public static ExtentReports extent=new ExtentReports();
    static ExtentTest test;

    @BeforeClass
    public static void setUp() throws ParserConfigurationException, IOException, SAXException {
        //create time stamp for the report
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        // Create a unique report name based on timestamp
        String reportPath = "test-output/ExtentReport_" + timeStamp + ".html";
        // Initialize Extent Reports
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(reportPath);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        //Set up appium cpabilities for automation on mobile
        UiAutomator2Options capabilities = new UiAutomator2Options();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "androidDevice");
        capabilities.setCapability("appPackage", "com.sec.android.app.popupcalculator");
        capabilities.setCapability("appActivity", "com.sec.android.app.popupcalculator.Calculator");
        capabilities.setCapability("newCommandtimeout", 120);
        capabilities.setCapability("unicodeKeyBoard", true);
        capabilities.setCapability("resetKeyboard", true);
        capabilities.setUdid("RF8R42F61LA");
        //setUp WebDriver
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), capabilities);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Before
    public void initiatePageObject(){
        calculatorPage=new Calculator_Page(driver);
    }

    public static String takeScreenShot() {
        try {
            // Create a unique file name
            String testName =test.getModel().getName();  // Getting the test name from ExtentTest object
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            String fileName = testName + "_"+timeStamp+" " + System.currentTimeMillis() + ".png";

            // Take screenshot
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            File screenShotFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

            // Save screenshot in "test-output/images/"
            Path destinationPath = new File("test-output/images/" + fileName).toPath();
            Files.createDirectories(destinationPath.getParent()); // Ensure the directory exists
            Files.copy(screenShotFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
            // Return not absulute path for the report
            return "images/" + fileName;
        } catch (IOException e) {
            System.out.println("Failed to save screenshot: " + e.getMessage());
            return null;
        }
    }

    protected static void addScreenShotToReport(){
        test.log(Status.INFO, "Screenshot Attached", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot()).build());
    }


    @AfterClass
    public static void quit(){
        driver.quit();
        // Flush Extent Reports
        extent.flush();
    }






}
