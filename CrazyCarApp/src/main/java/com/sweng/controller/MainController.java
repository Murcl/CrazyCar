package com.sweng.controller;

import com.sweng.drive.CarImpl;
import com.sweng.tools.FXLoader;
import com.sweng.tools.SimulatorWindow;
import com.sweng.tools.WebcamBuilder;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

@Component
public class MainController {

    @Autowired
    FXLoader loader;
    @Autowired
    SimulatorWindow simulatorWindow;

    @FXML
    private WebView webview;
    private String url = "";

    @FXML
    private SplitPane splitPaneH, splitPaneV;

    @Autowired
    WebcamBuilder webcamBuilder;
    @Autowired
    CarImpl carImpl;

    @FXML
    public void initialize() {
        url = webcamBuilder.requestInputUrlSetting();
        if (url.equals(""))
            Platform.exit();
        else
            lauchWebEngine();
        splitPaneH.getItems().add(loader.loadFile("controls"));
        splitPaneH.setDividerPosition(0,0.66);
        splitPaneV.getItems().add(loader.loadFile("parameterView"));
        splitPaneV.setDividerPosition(0,0.66);
    }

    @FXML
    public void openSimulator() {
        simulatorWindow.createStage();
    }

    private void lauchWebEngine() {
        WebEngine webEngine = webview.getEngine();
        webEngine.setJavaScriptEnabled(true);
        webEngine.getLoadWorker().stateProperty().addListener(
                (observableValue, state, t1) ->  {
                    System.out.println("newState = " + t1);
                    if (t1 == Worker.State.SUCCEEDED) {
                        System.out.println(webEngine.getLocation());

                    }
                });
        webEngine.load(url);
    }

    private void requestInputUrlSetting() {
        Optional<String> ret = Optional.of("");
        boolean connected = false;
        final boolean[] exit = {false};
        TextInputDialog td = new TextInputDialog();
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
                break;
            }
            if (ret.isPresent())
                url = ret.get();
            if (url.equals("")) {
                showAlert("URL field must be different from empty");
                continue;
            }
            td.setContentText("Connecting...");
            connected = openConnection();
            if (connected == false) {
                showAlert("Camera URL not reachable");
                continue;
            }
            break;
        }
    }

    public void showAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Test Connection");

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    protected boolean openConnection(){
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
