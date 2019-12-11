package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class Controller {
    private String operator = null;
    char sign = '+';
    private String retainedOperator = null;
    private double calcResult = 0;
    public TextArea result;

    public void numberPressed(ActionEvent event) {
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
                if (operator != null) {
                    currentResult = Double.parseDouble(result.getText()) * 100 / calcResult;
                }
                break;
            case "-/+":
                currentResult = - Double.parseDouble(result.getText());
                break;

        }
        result.setText(beautifyDouble(currentResult));

    }

    private String beautifyDouble(Double number) {
        String myStringNumber = String.valueOf(number);
        if (myStringNumber.contains(".")) {
            if (myStringNumber.indexOf('.') > 11) {
                return "Error. Press C";
            } else {
                if (number > number.intValue()) {
                    return myStringNumber.substring(0, 11);
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

    public void operate(ActionEvent event) {
        Button button = (Button) event.getSource();
        operator = button.getText();
        if (retainedOperator == null) {
            calcResult = Double.parseDouble(result.getText());
        } else {
            switch (retainedOperator) {
                case "+":
                    calcResult = calcResult + Double.parseDouble(result.getText());
                    break;
                case "-":
                    calcResult = calcResult - Double.parseDouble(result.getText());
                    break;
                case "*":
                    calcResult = calcResult * Double.parseDouble(result.getText());
                    break;
                case "/":
                    calcResult = calcResult / Double.parseDouble(result.getText());
                    break;
                case "=":
                    break;
            }
            result.setText(beautifyDouble(calcResult));
            retainedOperator = null;
        }
    }
}
