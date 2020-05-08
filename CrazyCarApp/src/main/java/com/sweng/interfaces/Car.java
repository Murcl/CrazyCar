/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sweng.interfaces;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author Martin Antenreiter
 */
public interface Car {

    void connect() throws IOException;

    void disconnect() throws IOException;

    double getAccelX();

    int getAccelXRaw();

    double getAccelY();

    int getAccelYRaw();

    double getAccelZ();

    int getAccelZRaw();

    String getCarName();

    double getDeltaTimeInMillis();

    int getDistFront();

    int getDistLeft();

    int getDistRight();

    double getGyroX();

    int getGyroXRaw();

    double getGyroY();

    int getGyroYRaw();

    double getGyroZ();

    int getGyroZRaw();

    String getIp();

    int getMaxSteering();

    String getNextCarData();

    List<Double> getObservationFRL();

    int getRpm();

    int getSpeed();

    int getSteering();

    int getSteeringTrim();

    double getTemp();

    int getTempRaw();

    double getVbat();

    double getVbatRaw();

    boolean isConnected();

    void reset();

    /**
     * Sets the speed for the motor, engine idle = 100 (default).
     *
     * @param speed range 0-200, 100=engine idle, speed < 100 = backward, speed
     * > 100 forward.
     */
    void setSpeed(int speed);

    /**
     * Sets the steering value, 100 is straight ahead (default).
     *
     * @param steering range 0-200, 100 straight,
     */
    void setSteering(int steering);

    /**
     * Busy-waiting for next data.
     */
    void waitForNextData();
    
}
