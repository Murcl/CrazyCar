package com.sweng.controller;

import com.sweng.interfaces.Car;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ControllsController {
    @FXML
    Button left,right,straight,back,bremse;

    @Autowired
    Car carImpl;

    public void initialize() {
        setStraight();
        setLeft();
        setRight();
        setBack();
        setBremse();
    }

    public void setBack() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), (ActionEvent event) -> {
            int speed = carImpl.getSpeed();
            speed=speed-2;
            if (back.isPressed())
                carImpl.setSpeed(speed);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void setBremse() {
        /*bremse.setOnMouseReleased(event -> {
            carImpl.setAcceleration(0);
        });
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), (ActionEvent event) -> {
            if (bremse.isPressed())
                carImpl.bremsen();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();*/
    }

    public void setStraight() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), (ActionEvent event) -> {
            int speed = carImpl.getSpeed();
            speed=speed+2;
            if (straight.isPressed())
                carImpl.setSpeed(speed);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void setRight() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), (ActionEvent event) -> {
            int steering = carImpl.getSteering();
            steering++;
            if (right.isPressed())
                carImpl.setSteering(steering);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void setLeft() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), (ActionEvent event) -> {
            int steering = carImpl.getSteering();
            steering--;
            if (left.isPressed())
                carImpl.setSteering(steering);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
