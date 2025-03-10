package Page_Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class BasePage {
    protected static WebDriver driver;
    protected static WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));;
    public static final String configPath ="C:/Users/avish/IdeaProjects/MobileTest/ReadingFromFile/config.xml";

    public void click(By elementLocation) {
        waitVisibility(elementLocation);
        driver.findElement(elementLocation).click();
    }

    public void waitVisibility(By elementLocation) {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException error) {
            error.printStackTrace();
        }
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(elementLocation)));
    }

    public String getText(By elementLocation) {
        waitVisibility(elementLocation);
        return driver.findElement(elementLocation).getText();
    }

    private static String readFromFile(String keyData, String pathName) throws ParserConfigurationException, IOException, SAXException {
        String value = null;
        try {
            // Load the XML file
            File xmlFile = new File(pathName.toString());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            // Normalize the document
            doc.getDocumentElement().normalize();

            // Extract values from XML
            value = doc.getElementsByTagName(keyData).item(0).getTextContent();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }

    public static String readFromThisFile(String keyData) throws ParserConfigurationException, IOException, SAXException {
        return readFromFile(keyData, configPath);
    }

}
