package test;

import Page_Objects.Calculator_Page;
import com.aventstack.extentreports.Status;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import static Page_Objects.BasePage.readFromThisFile;


@FixMethodOrder(MethodSorters.NAME_ASCENDING) //Set the tests order by ascending order
public class Tests extends BaseTest{
    public static WebDriverWait wait;



    @Test
    public void test01_Operators() throws ParserConfigurationException, IOException, SAXException {
        // Create a test in Extent Reports
        test = extent.createTest("Add ,Substruct,Multiply,Division ");
        double number1 = Double.parseDouble(readFromThisFile("number1"));
        double number2 = Double.parseDouble(readFromThisFile("number2"));
        String[] operation = {"add","substruct","multiply","division"}; //Array contains the operation names ,just for the print
        Map<Integer, Double>  expected_Result; //Hash map that contains the expected results for the assertions
        expected_Result=new HashMap<>();
        expected_Result.put(0,number1+number2);
        expected_Result.put(1,number1-number2);
        expected_Result.put(2,number1*number2);
        expected_Result.put(3,number1/number2);

        for (int i=0;i<calculatorPage.methods.length;i++){//The loop is executing the methods of runnable array . the actions and the array written on calculator_Page class
            try {                                //After each method have assertion,update the report , add screenshot and print for the user
                calculatorPage.methods[i].run();
                if (expected_Result.get(i)>=0) {
                    Assert.assertEquals(expected_Result.get(i), calculatorPage.getActualResultNumber(i), 0.0001);
                    test.log(Status.PASS, "Test Passed");
                    addScreenShotToReport();
                    System.out.println(operation[i] +" Numbers :"+ number1 + "," +number2  +"\nresult is "+ calculatorPage.getActualResultNumber(i) + " -- Test Passed");
                } else {
                    double expected_Absolute_Value = (expected_Result.get(i)) * -1;
                    Assert.assertTrue(calculatorPage.getActualResultText().contains("מינוס"));
                    Assert.assertEquals(expected_Absolute_Value, calculatorPage.getActualResultNumber(i), 0.0001);
                    test.log(Status.PASS, "Test Passed");
                    addScreenShotToReport();
                    System.out.println(operation[i] +" Numbers :"+ number1 + "," +number2  +"\nresult is - "+ calculatorPage.getActualResultNumber(i) + " -- Test Passed");
                }
            }
           catch (AssertionError error){
               test.log(Status.FAIL, "Test Failed " + error.getMessage());
               addScreenShotToReport();
               System.out.println(operation[i] +"Numbers :"+ number1 + "," +number2  +"\n result is - "+ calculatorPage.getActualResultNumber(i) + " -- Test Failed");
           }

        }

    }

    @Test
    public void test02_Divide_By_Zero()  throws ParserConfigurationException, IOException, SAXException{  ///Check the error message that appears in case of divide by zero
        // Create a test in Extent Reports
        test = extent.createTest("Divide by zero and get an error message ");
        try {
                calculatorPage.divideNumbers(readFromThisFile("number1"),readFromThisFile("number3"));
                Assert.assertTrue( calculatorPage.getError_DivideByZero().contains("לא ניתן לחלק באפס"));
                test.log(Status.PASS, "Test Passed");
                addScreenShotToReport();
        }
        catch (AssertionError error){
            test.log(Status.FAIL, "Test Failed " + error.getMessage());
            addScreenShotToReport();
        }

    }
}









