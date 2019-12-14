package utils;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class MyUtils {

    public static int showInputDialog(String title, String header, String content, String defValue) {
        TextInputDialog input = new TextInputDialog(defValue);
        input.setTitle(title);
        input.setHeaderText(header);
        input.setContentText(content);

        Optional<String> result = input.showAndWait();
        if (result.isPresent())
            return Integer.valueOf(result.get());

        return Integer.valueOf(defValue);
    }

    public static Alert makeDialog(String title, String header, String content, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setHeaderText(header);
        alert.setTitle(title);
        alert.setContentText(content);
        return alert;
    }
}
