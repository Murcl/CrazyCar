package com.sweng.drive;

import com.sweng.interfaces.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DriveAssistant {

    @Autowired
    Car car;
    boolean disableSide = false;
    boolean left = true;
    boolean sideSet = false;

    public void assist() {
        emergencyStop();
        if (!disableSide) {
            sideSet = false;
            sideAssistant();
        }
    }

    public void emergencyStop() {
        disableSide=false;
        if(car.getSpeed()<=100)
            return;
        int distFront = car.getDistFront();
        if (distFront>75)
            return;
        disableSide=true;
        if (distFront>20) {
            fuzzyControl(1);
            return;
        }
        double remainingDist = ((distFront)/20.0);
        if (remainingDist == 1)
            return;
        double sollspeed = 100+(car.getSpeed()-100)*remainingDist;
        car.setSpeed((int)sollspeed);
    }

    public void sideAssistant() {
        fuzzyControl(0);
    }

    private void fuzzyControl(double defaultValue) {
        int right = car.getDistRight();
        int left = car.getDistLeft();

        double valueRight = 0;
        double valueLeft = 0;

        if (disableSide) {
            if (left < right && !sideSet) {
                this.left = true;
                sideSet = true;
            } else if (!sideSet) {
                sideSet = true;
                this.left = false;
            }
        }

        if (!this.left)
            valueRight = defaultValue;
        else
            valueLeft = defaultValue;
        if (!disableSide) {
            if (right <= 40)
                valueRight = right / 40.0;

            if (left <= 40)
                valueLeft = left / 40.0;
            if (valueRight == 0 && valueLeft == 0)
                return;
        }

        double targetSteering = 100 + (valueLeft-valueRight)*car.getMaxSteering();
        int currentSteering = car.getSteering();
        if (targetSteering < currentSteering) {
            currentSteering--;
            car.setSteering(currentSteering);
        }
        else if (targetSteering>currentSteering) {
            currentSteering++;
            car.setSteering(currentSteering);
        }
    }
}
