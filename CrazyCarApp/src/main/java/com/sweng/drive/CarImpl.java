package com.sweng.drive;

import com.sweng.interfaces.Car;
import com.sweng.tools.SimulatorWindow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;


@Component
@Getter@Setter
public class CarImpl implements Car {

    /**
     * Simulator related attributes
     */
    @Autowired DriveAssistant driveAssistant;
    @Autowired SimulatorWindow simulatorWindow;
    Sensor[] sensors = new Sensor[3];

    /**
     * Car related attributes
     */
    double x,y,angle,accelX,accelY,accelZ,deltaTimeInMillis,gyroX,gyroY,gyroZ,temp,vbat,vbatRaw,internalSpeed;
    int accelXRaw,accelYRaw,accelZRaw,distFront,distLeft,distRight,gyroXRaw,gyroZRaw,maxSteering,rpm,speed,steering,steeringTrim,gyroYRaw,tempRaw,maxSpeed;
    String carName,ip;
    boolean steeringChanged = false;

    public CarImpl() {

    }

    /**
     * Creates car visual in simulator
     */
    @PostConstruct
    private void setUpCar() {
        Rectangle carVisual = new Rectangle( 50, 20);
        carVisual.setFill(Color.BLACK);
        sensors[0] = new Sensor(80,2,this);
        sensors[1] = new Sensor(150,this);
        sensors[2] = new Sensor(80,0,this);
        simulatorWindow.getCarVisual().getChildren().addAll(carVisual);
        simulatorWindow.getCarVisual().getChildren().addAll(sensors);
        simulatorWindow.getCarVisual().getChildren().addAll(sensors[0].getSensoren());
        simulatorWindow.getCarVisual().getChildren().addAll(sensors[1].getSensoren());
        simulatorWindow.getCarVisual().getChildren().addAll(sensors[2].getSensoren());

        x=250;
        y=250;
        move2D();
        deltaTimeInMillis = 100;
        speed = 100;
        maxSteering = 16;
        steering = 100;
        maxSpeed = 30;
    }

    /* Muss noch implementiert werden */
    @Override
    public void connect() throws IOException {

    }

    /* Muss noch implementiert werden */
    @Override
    public void disconnect() throws IOException {

    }

    @Override
    public void setSpeed(int speed) {
        if (speed < 0 || speed >200)
            return;
        this.speed = speed;
        internalSpeed = maxSpeed * (speed-100)/100.0;
    }

    @Override
    public void setSteering(int steering) {
        if (steering == this.steering)
            return;
        steeringChanged=true;
        if (steering < (100-maxSteering) || steering>(100+maxSteering))
            return;
        this.steering = steering;
    }

    @Override
    public String getNextCarData() {
        return null;
    }

    @Override
    public List<Double> getObservationFRL() {
        return null;
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public void reset() {
        angle = 0;
        setSpeed(100);
        setSteering(100);
        simulatorWindow.clearObstacles();
        x=250;
        y=250;
    }

    @Override
    public void waitForNextData() {

    }

    public int getDistLeft() {
        return sensors[0].intersectsAnyObstacle(simulatorWindow.getWorld().getChildren());
    }
    public int getDistFront() {
        return sensors[1].intersectsAnyObstacle(simulatorWindow.getWorld().getChildren());
    }
    public int getDistRight() {
        return sensors[2].intersectsAnyObstacle(simulatorWindow.getWorld().getChildren());
    }


    /*
        *************** Methoden für den Simulator ***************
     */
    public void calcSimulatorValues() {
        calculateRotate();
        move2D();
    }

    public void calculateRotate() {
        if (!steeringChanged) {
            if (steering<100)
                steering++;
            if (steering>100)
                steering--;
        }
        steeringChanged = false;
        double rotate = (maxSteering * ((steering-100)/100.0));
        angle = angle + rotate;
        simulatorWindow.getCarVisual().getTransforms().add(new Rotate(rotate));
    }

    public void move2D() {
        double maxX =  1000;
        double maxY =  1000;
        x = x + Math.cos(Math.toRadians(angle))*internalSpeed*(deltaTimeInMillis/1000.0);
        y = y + Math.sin(Math.toRadians(angle))*internalSpeed*(deltaTimeInMillis/1000.0);
        if (x < 0)
            x = maxX;
        if (x > maxX)
            x = 0;
        if (y < 0)
            y = maxY;
        if (y > maxY)
            y = 0;
        simulatorWindow.getCarVisual().setLayoutX(x);
        simulatorWindow.getCarVisual().setLayoutY(y);
    }
}