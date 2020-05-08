package com.sweng.tools;

import com.sweng.controller.ParameterViewController;
import com.sweng.drive.DriveAssistant;
import com.sweng.drive.CarImpl;
import com.sweng.drive.Obstacle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

@Component@Getter
public class SimulatorWindow {

    @Autowired CarImpl car;
    @Autowired DriveAssistant driveAssistant;
    @Autowired ParameterViewController parameterViewController;

    Group carVisual = new Group();
    Group world;

    Label x, y ,v,a,angle, distFront,distLeft,distRight;

    private DecimalFormat df1 = new DecimalFormat("#.##");

    public void createStage() {
        createDisplays();
        setCarImpl();
        Scene secondScene = new Scene(world, 1000, 1000);
        Stage newWindow = new Stage();
        newWindow.setTitle("Simulator");
        newWindow.setScene(secondScene);
        newWindow.show();
        secondScene.setOnMouseReleased(event -> {
            double x = event.getSceneX();
            double y = event.getSceneY();
            Obstacle obstacle = new Obstacle( x-25,y-25,50, 50);
            world.getChildren().addAll(obstacle);
            obstacle.setOnMouseClicked(event1 -> {
                world.getChildren().remove(obstacle);
                return;
            });
        });

    }

    public void setCarImpl() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), (ActionEvent event) -> {
            car.calcSimulatorValues();
            car.getDriveAssistant().assist();
            x.setText("X: "+df1.format(car.getX()));
            y.setText("Y: "+df1.format(car.getY()));
            v.setText("V: "+df1.format(car.getSpeed()));
            a.setText("A: "+df1.format(car.getAccelX()));
            angle.setText("Angle: "+df1.format(car.getSteering()));
            distFront.setText("Sensor Front: "+df1.format(car.getDistFront()));
            distLeft.setText("Sensor Left: "+df1.format(car.getDistLeft()));
            distRight.setText("Sensor Right: "+df1.format(car.getDistRight()));
            parameterViewController.setValues();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }

    public void createDisplays() {
        HBox box = new HBox();
        box.setSpacing(20);
        box.setLayoutY(20);
        box.setLayoutX(20);
        world = new Group(carVisual,box);
        x = new Label("X: "+ car.getX());
        y = new Label("Y: "+ car.getY() );
        v = new Label("V: "+ car.getSpeed());
        a = new Label("A: "+ car.getAccelX());
        angle = new Label("Angle: "+ car.getAngle());
        distFront = new Label("Sensor Front: "+ car.getDistFront());
        distLeft = new Label("Sensor Left: "+ car.getDistLeft());
        distRight = new Label("Sensor Right: "+ car.getDistRight());
        Button resetButton = new Button("Reset");
        resetButton.setOnAction(event -> car.reset());
        box.getChildren().addAll(x,y, v,a,angle, distFront,distLeft,distRight,resetButton);
    }

    public void clearObstacles() {
        for (int i = world.getChildren().size()-1;i>=0;i--) {
            if (world.getChildren().get(i) instanceof Obstacle)
                world.getChildren().remove(i);
        }
    }
}
