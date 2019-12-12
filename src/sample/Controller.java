package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class Controller {
    private String operator = null;
    private String retainedOperator = null;
    boolean equalsIsActive = false;
    private double calcResult = 0;
    public TextArea result;

    public void numberPressed(ActionEvent event) {
        if (equalsIsActive) {
            calcResult = 0;
            equalsIsActive = false;
            result.setText("0");
        }
        Button button = (Button) event.getSource();
        if (button.getText().equals(".")) {
            if (result.getText().contains(button.getText())) {
                return;
            }
            result.setText(result.getText() + ".");
            return;
        }
        if (operator == null) {
            if (result.getText().length() < 13) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(result.getText());
                if (stringBuilder.toString().equals("0")) {
                    stringBuilder.setLength(0);
                    stringBuilder.append(button.getText());
                } else {
                    stringBuilder.append(button.getText());
                }
                result.setText(stringBuilder.toString());
            }
        } else {
            retainedOperator = operator;
            operator = null;
            calcResult = Double.parseDouble(result.getText());
            result.setText(button.getText());
        }
    }

    public void equations(ActionEvent event) {
        Button button = (Button) event.getSource();
        double currentResult = 0;
        switch (button.getText()) {
            case "C":
                operator = null;
                calcResult = 0;
                retainedOperator = null;
                result.setText("0");
                break;
            case "CE":
                if (operator != null) {
                    retainedOperator = operator;
                    operator = null;
                }
                result.setText("0");
                break;
            case "\u232b":
                if (operator == null) {
                    if (result.getText().length() == 1) {
                        result.setText("0");
                    } else {
                        result.setText(result.getText().substring(0, result.getText().length() - 1));
                    }
                }
                return;
            case "1/x":
                currentResult = 1 / Double.parseDouble(result.getText());
                break;
            case "x^2":
                currentResult = Double.parseDouble(result.getText()) * Double.parseDouble(result.getText());
                break;
            case "\u221Ax":
                currentResult = Math.sqrt(Double.parseDouble(result.getText()));
                break;
            case "%":
                if (operator == null) {
                    currentResult = (calcResult * Double.parseDouble(result.getText())) / 100;
                }
                break;
            case "-/+":
                currentResult = -Double.parseDouble(result.getText());
                break;

        }
        result.setText(beautifyDouble(currentResult));

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

    //throw arritmetic exception
    public Double calculate(String operator,Double d1,Double d2){
        switch (operator){
            case "+":
                return d1+d2;
            case "-":
                return d1-d2;
            case "/":
                return d1/d2;
            case "*":
                return d1*d2;
        }
        return 0d;
    }

    public void operate(ActionEvent event) {
        Button button = (Button) event.getSource();
        operator = button.getText();

        if (retainedOperator == null) {
            calcResult = Double.parseDouble(result.getText());
        } else {
            switch (retainedOperator) {
                case "+":
                    equalsIsActive = false;
                    calcResult = calcResult + Double.parseDouble(result.getText());
                    break;
                case "-":
                    equalsIsActive = false;
                    calcResult = calcResult - Double.parseDouble(result.getText());
                    break;
                case "*":
                    equalsIsActive = false;
                    calcResult = calcResult * Double.parseDouble(result.getText());
                    break;
                case "/":
                    equalsIsActive = false;
                    calcResult = calcResult / Double.parseDouble(result.getText());
                    break;
            }
            if (operator.equals("=")){
                equalsIsActive = true;
                result.setText(beautifyDouble(calcResult));
                calcResult = 0;
                operator = null;
                retainedOperator = null;
                return;
            }
            result.setText(beautifyDouble(calcResult));
            retainedOperator = null;
        }
    }
}
