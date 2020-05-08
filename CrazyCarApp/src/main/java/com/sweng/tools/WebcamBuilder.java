package com.sweng.tools;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

@Component
public class WebcamBuilder {

    @Autowired
    ConfigurableApplicationContext context;

    public String requestInputUrlSetting() {
        Optional<String> ret = Optional.of("");
        boolean connected;
        final boolean[] exit = {false};
        TextInputDialog td = new TextInputDialog("");
        td.setContentText("Enter Camera URL");
        td.setGraphic(new ImageView( getClass().getResource( "/cctv.png").toExternalForm()));
        td.setTitle("Setup Parameter");
        td.setHeaderText("Camera Connection");
        ((Button) td.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Close Application");
        ((Button) td.getDialogPane().lookupButton(ButtonType.CANCEL)).setOnAction(actionEvent -> exit[0] =true );
        while (true && !exit[0]) {
            ret = td.showAndWait();
            if (exit[0]) {
                Platform.exit();
                return "";
            }
            if (ret.isPresent() && ret.get().equals("")) {
                showAlert("URL field must be different from empty");
                continue;
            }
            td.setContentText("Connecting...");
            connected = openConnection(ret.get());
            if (connected == false) {
                showAlert("Camera URL not reachable");
                continue;
            }
            return ret.get();
        }
        return null;
    }

    private void showAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Test Connection");

        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }

    protected boolean openConnection(String url){
        try {
            URL u = new URL(url);
            HttpURLConnection huc = (HttpURLConnection) u.openConnection();

            int ret = huc.getResponseCode();
            if (ret != 200)
                return false;
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

}
