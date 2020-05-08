package com.sweng;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class SwengApplication extends Application {

	private ConfigurableApplicationContext springContext;
	private Parent rootNode;
	private FXMLLoader fxmlLoader;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void init() throws Exception {
		springContext = SpringApplication.run(SwengApplication.class);
		fxmlLoader = new FXMLLoader();
		fxmlLoader.setControllerFactory(springContext::getBean);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		fxmlLoader.setLocation(getClass().getResource("/fxml/main.fxml"));
		rootNode = fxmlLoader.load();
		Scene scene = new Scene(rootNode,1200,800);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
