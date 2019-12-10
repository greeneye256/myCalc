package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class Controller {
    private char operator = 'e';
    char sign = '+';
    double calcResult = 0;
    public TextArea currentResult;
    public TextArea result;

    public void numberPressed(ActionEvent event) {
        if (operator == 'e') {
            if (result.getText().length() < 13) {
                Button button = (Button) event.getSource();
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
        }
        else {
            switch (operator){
                case '+':
                    calcResult = calcResult + Double.parseDouble(result.getText());
                    operator = 'e';
                    break;
                case '-':
                    calcResult = calcResult - Double.parseDouble(result.getText());
                    operator = 'e';
                    break;
            }
        }
    }

    public void operate(ActionEvent event) {
        Button button = (Button) event.getSource();
        switch (button.getText()) {
            case "+":
                operator = '+';
                currentResult.setText(result.getText());
                break;
            case "-":
                operator = '-';
                break;
        }
    }
}
