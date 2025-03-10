package Page_Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Calculator_Page extends BasePage {
    public Map<Integer, By> numbersButtons; // initiate a map that contains int end each int have his button element according to the integer value

    //construcctor
    public Calculator_Page(WebDriver driver) {
        super();
        this.driver = driver;
        numbersButtons = new HashMap<>(); //// creating map of buttons .each character get the propriate number according to the numeric character value
        for (int i = 0; i <= 9; i++) {
            numbersButtons.put(i, By.id("calc_keypad_btn_0" + i));
        }
    }

    //elements
    public By plus = By.id("calc_keypad_btn_add");
    public By minus = By.id("calc_keypad_btn_sub");
    public By multiply = By.id("calc_keypad_btn_mul");
    public By divide = By.id("calc_keypad_btn_div");
    public By equal = By.id("calc_keypad_btn_equal");
    public By actualResult = By.id("calc_edt_formula");
    public By replaceToMinus = By.id("calc_keypad_btn_plusminus");
    public By decimalPoint = By.id("calc_keypad_btn_dot");
    public By clear = By.id("calc_keypad_btn_clear");
    public By errorMessage_divideByZero=By.id("snackbar_text");

    //functions

    public Calculator_Page clickPlus() {
        click(plus);
        return this;
    }

    public Calculator_Page addNumbers(String number1, String number2) throws ParserConfigurationException, IOException, SAXException {
        clearResult();
        insertNumber(number1);
        clickPlus();
        insertNumber(number2);
        clickEqualButton();
        return this;
    }

    public Calculator_Page substructNumbers(String number1, String number2) throws ParserConfigurationException, IOException, SAXException {
        clearResult();
        insertNumber(number1);
        clickMinus();
        insertNumber(number2);
        clickEqualButton();
        return this;
    }

    public Calculator_Page multiplyNumbers(String number1, String number2) throws ParserConfigurationException, IOException, SAXException {
        clearResult();
        insertNumber(number1);
        clickMultiply();
        insertNumber(number2);
        clickEqualButton();
        return this;
    }

    public Calculator_Page divideNumbers(String number1, String number2) throws ParserConfigurationException, IOException, SAXException {
        clearResult();
        insertNumber(number1);
        clickDivision();
        insertNumber(number2);
        clickEqualButton();
        return this;
    }

    public Calculator_Page insertNumber(String number) {
        for (char ch : number.toCharArray()) {
            if (ch == '.') {
                clickDecimalPoint();
            } else if (ch == '-') {
                replaceToNegativeNumber();
            } else {
                click(numbersButtons.get(Integer.parseInt(ch + "")));
            }
        }
        return this;
    }

    public Calculator_Page clickMinus() {
        click(minus);
        return this;
    }

    public Calculator_Page clickMultiply() {
        click(multiply);
        return this;
    }

    public Calculator_Page clickDivision() {
        click(divide);
        return this;
    }

    public Calculator_Page clickEqualButton() {
        click(equal);
        return this;
    }

    public Calculator_Page replaceToNegativeNumber() {
        click(replaceToMinus);
        return this;
    }

    public Calculator_Page clickDecimalPoint() {
        click(decimalPoint);
        return this;
    }

    public Calculator_Page clearResult() {
        click(clear);
        return this;
    }

    public String getError_DivideByZero(){
        String error=driver.findElement(errorMessage_divideByZero).getText();
        return error ;
    }

    public String getActualResultText(){
        String actualResultText = driver.findElement(actualResult).getText();
        return actualResultText;
    }

    public double getActualResultNumber(int i) throws ParserConfigurationException, IOException, SAXException {
        double number1 = Double.parseDouble(readFromThisFile("number1"));
        double number2 = Double.parseDouble(readFromThisFile("number2"));
        String actualResultText = getActualResultText().replace(",", "");
        double actualResultDouble = 0;
        if (i == 0) {  //in case of i=0 the method is add
            if (number1 + number2 >= 0) {
                actualResultText = actualResultText.substring(0, actualResultText.indexOf(" "));
                actualResultDouble = Double.parseDouble(actualResultText);
                return actualResultDouble;
            } else {
                String[] textParts = actualResultText.split(" ");
                actualResultDouble = Double.parseDouble(textParts[2]);
                return actualResultDouble;
            }
        }
        else if (i == 1) { ////in case of i=0 the method is substruct
            if (number1 - number2 >= 0) {
                actualResultText = actualResultText.substring(0, actualResultText.indexOf(" "));
                actualResultDouble = Double.parseDouble(actualResultText);
                return actualResultDouble;
            } else {
                String[] textParts = actualResultText.split(" ");
                actualResultDouble = Double.parseDouble(textParts[2]);
                return actualResultDouble;
            }
        }
        else if (i == 2) { ////in case of i=0 the method is multiply
            if (number1 * number2 >= 0) {
                actualResultText = actualResultText.substring(0, actualResultText.indexOf(" "));
                actualResultDouble = Double.parseDouble(actualResultText);
                return actualResultDouble;
            } else {
                String[] textParts = actualResultText.split(" ");
                actualResultDouble = Double.parseDouble(textParts[2]);
                return actualResultDouble;
            }
        }
        else if (i == 3) { ////in case of i=0 the method is divide
            if (number1 / number2 >= 0) {
                actualResultText = actualResultText.substring(0, actualResultText.indexOf(" "));
                actualResultDouble = Double.parseDouble(actualResultText);
                return actualResultDouble;
            } else {
                String[] textParts = actualResultText.split(" ");
                actualResultDouble = Double.parseDouble(textParts[2]);
                return actualResultDouble;
            }
        }
        return 0;
    }

    public Runnable[] methods = {
            () -> {
                try {
                     addNumbers(readFromThisFile("number1"), readFromThisFile("number2"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            },
            () -> {
                try {
                     substructNumbers(readFromThisFile("number1"), readFromThisFile("number2"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            },
            () -> {
                try {
                     multiplyNumbers(readFromThisFile("number1"), readFromThisFile("number2"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            },
            () -> {
                try {
                     divideNumbers(readFromThisFile("number1"), readFromThisFile("number2"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    };
}



