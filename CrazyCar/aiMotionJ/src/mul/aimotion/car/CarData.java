/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mul.aimotion.car;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.stream.Stream;

/**
 *
 * @author Martin Antenreiter
 */
public class CarData {

    public static class Data {
        private Long systemtime;
        private int distRight;
        private int distLeft;
        private int distFront;
        private int vBatRaw;
        private int rpm;
        private int accelXRaw;
        private int accelYRaw;
        private int accelZRaw;
        private int tempRaw;
        private int gyroXRaw;
        private int gyroYRaw;
        private int gyroZRaw;
        private long carTime;
        private int speed;
        private int steering;        
    }
    
    private SimCar simcar;

    private NavigableMap<Long, Data> data = new TreeMap<>();
    
    private Data current;
    
    public CarData(String filename) throws FileNotFoundException, IOException {
        BufferedReader rd = new BufferedReader(new FileReader(filename));
        String line = null;
        while( (line = rd.readLine()) != null) {
            String[] columns = line.split(",");
            Data d = new Data();
            d.carTime = Long.parseLong(columns[2]);
            d.speed = Integer.parseInt(columns[3]);
            d.steering = Integer.parseInt(columns[4]);
            d.rpm = Integer.parseInt(columns[5]);
            d.distLeft = Integer.parseInt(columns[6]);
            d.distFront = Integer.parseInt(columns[7]);
            d.distRight = Integer.parseInt(columns[8]);
            d.accelXRaw = Integer.parseInt(columns[9]);
            d.accelYRaw = Integer.parseInt(columns[10]);
            d.accelZRaw = Integer.parseInt(columns[11]);
            d.gyroXRaw = Integer.parseInt(columns[12]);
            d.gyroYRaw = Integer.parseInt(columns[13]);
            d.gyroZRaw = Integer.parseInt(columns[14]);
            d.vBatRaw = Integer.parseInt(columns[15]);
            d.systemtime = Long.parseLong(columns[16]);
            data.put(d.systemtime, d);
        }
        rd.close();
        current = data.firstEntry().getValue();
    }
    
    void setSimCar(SimCar simcar) {
        this.simcar = simcar;
        simcar.updateSensorData();
    }

    public void seekToTime(long time) {
        current = data.floorEntry(time).getValue();
        simcar.updateSensorData();
    }

    public void getNext() {
        current = data.higherEntry(current.systemtime).getValue();
        simcar.updateSensorData();
    }
    
    
    int getDistRight() {
        return current.distRight;
    }

    int getDistLeft() {
        return current.distLeft;
    }

    int getDistFront() {
        return current.distFront;
    }

    int getVbatRaw() {
        return current.vBatRaw;
    }

    int getRpm() {
        return current.rpm;
    }

    int getAccelXRaw() {
        return current.accelXRaw;
    }

    int getAccelYRaw() {
        return current.accelYRaw;
    }

    int getAccelZRaw() {
        return current.accelZRaw;
    }

    int getTempRaw() {
        return current.tempRaw;
    }

    int getGyroXRaw() {
        return current.gyroXRaw;
    }

    int getGyroYRaw() {
        return current.gyroYRaw;
    }

    int getGyroZRaw() {
        return current.gyroZRaw;
    }

    long getCarTime() {
        return current.carTime;
    }

    int getSpeed() {
        return current.speed;
    }

    int getSteering() {
        return current.steering;
    }

    public long getTime() {
        return current.systemtime;
    }

}
