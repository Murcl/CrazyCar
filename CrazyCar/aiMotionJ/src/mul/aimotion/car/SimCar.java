/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mul.aimotion.car;

import java.net.UnknownHostException;

/**
 *
 * @author Martin Antenreiter
 */
public class SimCar extends CarFirmware20190717 {
    
    private CarData cardata;
        
    public SimCar(CarData cardata) throws UnknownHostException {
        super();
        this.cardata = cardata;
        cardata.setSimCar(this);
    }
    
    @Override
    public void connect() {
        connected = true;
        updateSensorData();
/*        this.start();
        try {
            while(!isConnected()) {
                Thread.sleep(25l);
            }
        } catch(InterruptedException ex) {            
        }*/
    }
    
    public void run() {
/*        while (true) {                        
            receiveData();
            //sendData(address, outBuf);               
        }*/
    }

    public void updateSensorData() {
        synchronized(this) {
            distRight = cardata.getDistRight();
            distLeft = cardata.getDistLeft();
            distFront = cardata.getDistFront();
            vbat = cardata.getVbatRaw();
            rpm = cardata.getRpm();
            accelX = cardata.getAccelXRaw();
            accelY = cardata.getAccelYRaw();
            accelZ = cardata.getAccelZRaw();
            temp = cardata.getTempRaw();
            gyroX = cardata.getGyroXRaw();
            gyroY = cardata.getGyroYRaw();
            gyroZ = cardata.getGyroZRaw();
            time = cardata.getCarTime();
            
            speed = cardata.getSpeed();
            steering = cardata.getSteering();
        }
        if(estimator != null) {
            estimator.update();
        }
    }

}
