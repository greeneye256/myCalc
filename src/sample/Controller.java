package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class Controller {
    private String operator = null;
    private String retainedOperator = null;
    private boolean lastKeyPressedIsEquals = false;
    private double memoryResult = 0;
    public TextArea displayedValue;

    public void handleNumberKeys(ActionEvent event) {

        if (lastKeyPressedIsEquals) {
            memoryResult = 0;
            lastKeyPressedIsEquals = false;
            displayedValue.setText("0");
        }

        Button button = (Button) event.getSource();
        String keyPressed = button.getText();
        String currentDisplayedValue = displayedValue.getText();

        if (keyPressed.equals(".")) {
            if (currentDisplayedValue.contains(".")) {
                return;
            }
            displayedValue.setText(currentDisplayedValue + ".");
            return;
        }
        if (operator == null) {

            displayedValue.setText(appendDigitToNumber(keyPressed, currentDisplayedValue));

        } else {
            retainedOperator = operator;
            operator = null;
            memoryResult = getDoubleFromString(currentDisplayedValue);
            displayedValue.setText(keyPressed);
        }
    }

    public void handleFunctionKeys(ActionEvent event) {
        Button button = (Button) event.getSource();
        String keyPressed = button.getText();
        Double currentValue = getDoubleFromString(displayedValue.getText());
        double result = 0;
        switch (keyPressed) {
            case "1/x":
                result = 1 / currentValue;
                break;
            case "x^2":
                result = currentValue * currentValue;
                break;
            case "\u221Ax":
                result = Math.sqrt(currentValue);
                break;
            case "%":
                if (operator == null) {
                    result = (memoryResult * currentValue) / 100;
                }
                break;
            case "-/+":
                result = -currentValue;
                break;

        }
        displayedValue.setText(beautifyDouble(result));

    }

    public void handleUtilKey(ActionEvent event) {
        Button button = (Button) event.getSource();
        String keyPressed = button.getText();
        String currentDisplayedValue = displayedValue.getText();

        switch (keyPressed) {
            case "C":
                operator = null;
                memoryResult = 0;
                retainedOperator = null;
                displayedValue.setText("0");
                break;
            case "CE":
                if (operator != null) {
                    retainedOperator = operator;
                    operator = null;
                }
                displayedValue.setText("0");
                break;
            case "\u232b":
                if (operator == null && !lastKeyPressedIsEquals) {
                    if (currentDisplayedValue.length() == 1) {
                        displayedValue.setText("0");
                    } else {
                        displayedValue.setText(currentDisplayedValue.substring(0, currentDisplayedValue.length() - 1));
                    }
                }
        }
    }

    private String beautifyDouble(Double number) {
        String myStringNumber = String.valueOf(number);
        if (myStringNumber.contains(".")) {
            if (myStringNumber.indexOf('.') > 12) {
                return "Error. Press C";
            } else {
                if (number != number.intValue()) {
                    return (myStringNumber.length() > 12) ? myStringNumber.substring(0, 12) : myStringNumber;
                } else {
                    return String.valueOf(number.intValue());
                }
            }
        } else {
            if (myStringNumber.length() > 11) {
                return "Error. Press C";
            }
        }
        return myStringNumber;
    }

    public void handleBasicOperations(ActionEvent event) {
        lastKeyPressedIsEquals = false;
        Button button = (Button) event.getSource();
        operator = button.getText();


        if (retainedOperator == null) {
            memoryResult = getDoubleFromString(displayedValue.getText());
        } else {

            if (operator.equals("=")) {
                lastKeyPressedIsEquals = true;
                displayedValue.setText(beautifyDouble(calculate(retainedOperator, memoryResult, getDoubleFromString(displayedValue.getText()))));
                memoryResult = getDoubleFromString(displayedValue.getText());
                operator = null;
                retainedOperator = null;
            } else {
                displayedValue.setText(beautifyDouble(calculate(retainedOperator, memoryResult, getDoubleFromString(displayedValue.getText()))));
                retainedOperator = null;
            }
        }
    }

    private Double calculate(String operator, Double d1, Double d2) {
        switch (operator) {
            case "+":
                return d1 + d2;
            case "-":
                return d1 - d2;
            case "/":
                return d1 / d2;
            case "*":
                return d1 * d2;
        }
        return 0d;
    }

    private String appendDigitToNumber(String digit, String number) {
        if (number.length() < 13) {
            if (number.equals("0.")) {
                return number + digit;
            }
            if (getDoubleFromString(number) == 0) {
                return digit;
            }
            return number + digit;
        }
        return number;
    }

    private double getDoubleFromString(String currentDisplayedValue) {
        return Double.parseDouble(currentDisplayedValue);
    }

}
