/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mul.aimotion.car;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mul.aimotion.location.LocationEstimator;

/**
 *
 * @author Martin Antenreiter
 */
public class CarFirmware20190507 implements Runnable, Car {

    private static final String DEFAULT_IP = "192.168.220.60";

    private static final int IN_PORT = 8080;

    private static final int OUT_PORT = 1112;
    private static int RECV_BUF_SIZE = 30;
    private static int SEND_BUF_SIZE = 7;

    private final String carname;

    private final InetAddress ip;

    private DatagramChannel inChannel;
    private DatagramChannel outChannel;

    protected volatile boolean connected;
    protected volatile int distRight;
    protected volatile int distLeft;
    protected volatile int distFront;
    protected volatile int vbat;
    protected volatile int rpm;
    protected volatile int accelX;
    protected volatile int accelY;
    protected volatile int accelZ;
    protected volatile int temp;
    protected volatile int gyroX;
    protected volatile int gyroY;
    protected volatile int gyroZ;
    protected volatile int steering;
    protected volatile int speed;
    protected volatile long time;

    private long oldtime;
    protected LocationEstimator estimator;
    private volatile double deltaTimeInMillis;
    
    public CarFirmware20190507() throws UnknownHostException {
        this("MUL-CC", CarFirmware20190507.DEFAULT_IP);
    }

    public CarFirmware20190507(String name, String ip) throws UnknownHostException {
        this.carname = name;
        this.oldtime = -1;
        String[] numbers = ip.split("\\.");
        byte[] ip_bytes = new byte[4];
        for(int i=0; i<numbers.length; ++i) {
            ip_bytes[i] = (byte) Integer.parseInt(numbers[i]);
        }
        this.ip = InetAddress.getByAddress(ip_bytes);
        this.steering = getSteeringTrim();
        this.speed = 100;
        this.connected = false;
    }

    public void connect() throws IOException {
        setSpeed(100);
        inChannel = DatagramChannel.open();
        inChannel.socket().bind(new InetSocketAddress(CarFirmware20190507.IN_PORT));
        outChannel = DatagramChannel.open();
        outChannel.socket().bind(new InetSocketAddress(CarFirmware20190507.OUT_PORT));
        connected = true;
        new Thread(this).start();
        try {
            while(!isConnected()) {
                Thread.sleep(25l);
            }
            setSteering(getSteeringTrim()-getMaxSteering());
            Thread.sleep(250l);
            setSteering(getSteeringTrim()+getMaxSteering());
            Thread.sleep(250l);
            setSteering(getSteeringTrim());            
            Thread.sleep(500l);
        } catch(InterruptedException ex) {            
        }
    }

    public boolean isConnected() {
        return connected && vbat != 0 && temp != 0;
    }

    /*public byte read() throws IOException {
        return in.readByte();
    }*/

    private static int convertWord(byte[] buf, int index1, int index2) {
        return ((buf[index1] & 0xFF) << 8) + (buf[index2] & 0xFF);       
    }
    
    private static int convertSignedWord(byte[] buf, int index1, int index2) {
        return (int) ((buf[index1] << 8) + (buf[index2] & 0xFF));       
    }

    private static long convertInt(byte[] buf, int index1, int index2, 
                                              int index3, int index4) {
        return ((buf[index1] & 0xFF) << 24) + ((buf[index2] & 0xFF) << 16) 
                + ((buf[index3] & 0xFF) << 8) + (buf[index4] & 0xFF);       
    }
    
    @Override
    public void run() {
        ByteBuffer inBuf = ByteBuffer.allocate(CarFirmware20190507.RECV_BUF_SIZE);
        ByteBuffer outBuf = ByteBuffer.allocate(CarFirmware20190507.SEND_BUF_SIZE);
        InetSocketAddress address = new InetSocketAddress(ip, CarFirmware20190507.OUT_PORT);
        while (connected) {
            receiveData(inBuf);
            try {
                Thread.sleep(1l);
            } catch (InterruptedException ex) {
                Logger.getLogger(CarFirmware20190507.class.getName()).log(Level.SEVERE, null, ex);
            }
            sendData(address, outBuf);    
            if(estimator != null) estimator.update();
        }
        try {
            if (inChannel != null) {
                inChannel.close();
                inChannel = null;
            }
            if (outChannel != null) {
                outChannel.close();
                outChannel = null;
            }
        } catch(IOException io) { }
    }

    private void sendData(SocketAddress address, ByteBuffer outBuf) {
        try {
            outBuf.clear();
            int local_steering = steering;
            int local_speed = speed;
            byte val1 = (byte) (local_steering % 10 + '0');
            byte val2 = (byte) ((local_steering / 10) % 10 + '0');
            byte val3 = (byte) ((local_steering / 100) % 10 + '0');
            outBuf.put(val3);
            outBuf.put(val2);
            outBuf.put(val1);
            outBuf.put((byte)',');
            val1 = (byte) (local_speed % 10 + '0');
            val2 = (byte) ((local_speed / 10) % 10 + '0');
            val3 = (byte) ((local_speed / 100) % 10 + '0');
            outBuf.put(val3);
            outBuf.put(val2);
            outBuf.put(val1);            
            outBuf.flip();
            outChannel.send(outBuf, address);
        } catch(IOException ex) {
            Logger.getLogger(CarFirmware20190507.class.getName()).log(Level.SEVERE, "Send IO exception\n" + ex);
        }
    }

    private void receiveData(ByteBuffer inBuf) {
        try {
            inBuf.clear();
            SocketAddress result = inChannel.receive(inBuf);
            if(result == null) return;
            byte[] inbuf = inBuf.array();
            int len = inbuf.length;
            int pos = 0;
            if (inbuf[0] == 'B') pos = 0;
            if (inbuf[pos + 0] == 'B' && inbuf[len - 1] == 'E') {
                synchronized(this) {
                    distRight = convertWord(inbuf, pos + 2, pos + 1);
                    distLeft = convertWord(inbuf, pos + 4, pos + 3);
                    distFront = convertWord(inbuf, pos + 6, pos + 5);
                    vbat = convertWord(inbuf, pos + 8, pos + 7);
                    rpm = convertWord(inbuf, pos + 10, pos + 9);
                    accelX = convertSignedWord(inbuf, pos + 11, pos + 12);
                    accelY = convertSignedWord(inbuf, pos + 13, pos + 14);
                    accelZ = convertSignedWord(inbuf, pos + 15, pos + 16);
                    temp = convertWord(inbuf, pos + 17, pos + 18);
                    gyroX = convertSignedWord(inbuf, pos + 19, pos + 20);
                    gyroY = convertSignedWord(inbuf, pos + 21, pos + 22);
                    gyroZ = convertSignedWord(inbuf, pos + 23, pos + 24);
                    long prevtime = time;
                    time = convertInt(inbuf, pos + 28, pos + 27, pos + 26, pos + 25);
                    //FIXME: Überlauf beachten! Liefert das System dann negative Werte?
                    deltaTimeInMillis = Math.abs((prevtime - time) / 10.0);
                    if(estimator != null) estimator.update();
                }
                //System.out.println(distLeft + "\t," + distRight + "\t," + distFront + "\t," + vbat + "\t," + rpm);
            } else {
                Logger.getLogger(CarFirmware20190507.class.getName()).log(Level.SEVERE, "Rejecting input " + (char) inbuf[pos + 0] + "..." + (char) inbuf[len - 1]);
                Logger.getLogger(CarFirmware20190507.class.getName()).log(Level.SEVERE, "RAW input is " + Arrays.toString(inbuf));
            }
        } catch (IOException ex) {
            Logger.getLogger(CarFirmware20190507.class.getName()).log(Level.SEVERE, "Receive IO exception\n" + ex);
        }
    }
    
    public String getCarName() {
        return carname;
    }

    public String getIp() {
        return ip.getHostAddress();
    }

    public int getDistRight() {
        return distRight;
    }

    public int getDistLeft() {
        return distLeft;
    }

    public int getDistFront() {
        return distFront;
    }

    public double getVbat() {
        return vbat * 3.3 * 2 / 4095;
    }

    public double getVbatRaw() {
        return vbat;
    }

    public int getRpm() {
        return rpm;    
    }

    public double getAccelX() {
        return ((double) accelX) / 65535 * 8;
    }
    public double getAccelY() {
        return ((double) accelY) / 65535 * 8;
    }
    public double getAccelZ() {
        return ((double) accelZ) / 65535 * 8;
    }
    
    public int getAccelXRaw() {
        return accelX;
    }

    public int getAccelYRaw() {
        return accelY;
    }

    public int getAccelZRaw() {
        return accelZ;
    }

    public double getTemp() {
        return temp / 333.87 + 21.0;
    }
    
    public int getTempRaw() {
        return temp;
    }

    public double getGyroX() {
        return ((double)gyroX) / 65535 * 1000 ;
    }

    public double getGyroY() {
        return ((double)gyroY) / 65535 * 1000 ;
    }

    public double getGyroZ() {
        return ((double)gyroZ) / 65535 * 1000 ;
    }

    public int getGyroXRaw() {
        return gyroX;
    }

    public int getGyroYRaw() {
        return gyroY;
    }

    public int getGyroZRaw() {
        return gyroZ;
    }

    public int getSteering() {
        return steering;
    }

    public int getSpeed() {
        return speed;
    }

    /**
     * Sets the steering value, 100 is straight ahead (default).
     *
     * @param steering range 0-200, 100 straight,
     */
    public void setSteering(int steering) {
        if(connected) this.steering = steering;
    }

    /**
     * Sets the speed for the motor, engine idle = 100 (default).
     *
     * @param speed range 0-200, 100=engine idle, speed < 100 = backward, speed
     * > 100 forward.
     */
    public void setSpeed(int speed) {
        if(connected) this.speed = speed;
    }

    public void disconnect() throws IOException {
        synchronized(this) {
            reset();
            try {
               Thread.sleep(100l); // Wait for the run-Method, 
            } catch (InterruptedException ex) {
                Logger.getLogger(CarFirmware20190507.class.getName()).log(Level.SEVERE, null, ex);
            }
            connected = false;
        }        
        vbat = 0;
        temp = 0;
    }

    public void reset() {
        if(estimator != null) estimator.reset();
        speed = 100;
        steering = getSteeringTrim();        
    }

    /**
     * Busy-waiting for next data.
     */
    public void waitForNextData() {
        long _oldtime = time;
        while(_oldtime == time);        
    }
    
    public String getNextCarData() {
        synchronized(this) {
            if(time == oldtime) return null;
            oldtime = time;
            return System.currentTimeMillis() + ","
                    +carname +","+ip+","+time+","+speed+","+steering+","
                    +rpm+","
                    +distLeft+","+distFront+","+distRight+","
                    +accelX+","+accelY+","+accelZ+","
                    +gyroX+","+gyroY+","+gyroZ+","
                    +vbat;
        }
    }
    
    @Override
    public String toString() {
        return "Car{" + "carname=" + carname + ", ip=" + ip + " ,time="+ time +", distRight=" + distRight + ", distLeft=" + distLeft + ", distFront=" + distFront + ", vbat=" + vbat + ", rpm=" + rpm + ", accelX=" + accelX + ", accelY=" + accelY + ", accelZ=" + accelZ + ", temp=" + temp + ", gyroX=" + gyroX + ", gyroY=" + gyroY + ", gyroZ=" + gyroZ + ", steering=" + steering + ", speed=" + speed + '}';
    }    

    public int getSteeringTrim() {
        return 105;
    }

    public int getMaxSteering() {
        return 85;
    }
 
    public void setLocationEstimator(LocationEstimator estimator) {
        if(this.estimator != null) {
            this.estimator.setCar(null);
        }
        this.estimator = estimator;
        if(estimator != null) {
            this.estimator.setCar(this); 
            this.estimator.init();
        }
    }

    public LocationEstimator getLocationEstimator() {
        return estimator;
    }

    public double getDeltaTimeInMillis() {
        return deltaTimeInMillis;
    }
    
    public List<Double> getObservationFRL() {
        List<Double> result = new ArrayList<Double>(3);
        
        synchronized(this) {
            result.add(convertFront(this.distFront));
            result.add(convertSide(this.distRight, 0));
            result.add(convertSide(this.distLeft,  0));
        }
        return result;
    }

    private double linInter(int currentM, double a1, double a2, int m1, int m2) {
        double k = (a1-a2)/(m1-m2);
        double d = a1 - k * m1;
        return k*currentM + d;
    }
    
    private double convertFront(int dist) {        
        double l = 0.0;
        if(dist < 644) { l = 1.5; }
        else if(dist < 686)  { l = linInter(dist, 1.5, 1.4,  644,  686); }
        else if(dist < 742)  { l = linInter(dist, 1.4, 1.3,  686,  742); }
        else if(dist < 798)  { l = linInter(dist, 1.3, 1.2,  742,  798); }
        else if(dist < 862)  { l = linInter(dist, 1.2, 1.1,  798,  862); }
        else if(dist < 950)  { l = linInter(dist, 1.1, 1.0,  862,  950); }
        else if(dist < 1048) { l = linInter(dist, 1.0, 0.9,  950, 1048); }
        else if(dist < 1223) { l = linInter(dist, 0.9, 0.8, 1048, 1223); }
        else if(dist < 1400) { l = linInter(dist, 0.8, 0.7, 1223, 1400); }
        else if(dist < 1552) { l = linInter(dist, 0.7, 0.6, 1400, 1552); }
        else if(dist < 1886) { l = linInter(dist, 0.6, 0.5, 1552, 1886); }
        else if(dist < 2386) { l = linInter(dist, 0.5, 0.4, 1886, 2386); }
        else if(dist < 3163) { l = linInter(dist, 0.4, 0.3, 2386, 3163); }
        else if(dist < 4071) { l = linInter(dist, 0.3, 0.2, 3163, 4071); }
        else if(dist <= 4095) { l = linInter(dist, 0.2, 0.155, 4071, 4095); }
        else l = 0.15;
        
        return 1.0 - l / 1.5;
    }

    private double convertSide(int dist, int offset) {        
        double l = 0.0;
        dist += offset;
        if(dist < 667) { l = 0.8; }
        else if(dist < 748)  { l = linInter(dist, 0.8, 0.7,  667,  748); }
        else if(dist < 831)  { l = linInter(dist, 0.7, 0.6,  748,  831); }
        else if(dist < 968)  { l = linInter(dist, 0.6, 0.5,  831,  968); }
        else if(dist < 1150) { l = linInter(dist, 0.5, 0.4,  968, 1150); }
        else if(dist < 1444) { l = linInter(dist, 0.4, 0.3, 1150, 1444); }
        else if(dist < 1999) { l = linInter(dist, 0.3, 0.2, 1444, 1999); }
        else if(dist < 3593) { l = linInter(dist, 0.2, 0.1, 1999, 3593); }
        else if(dist <= 4095) { l = linInter(dist, 0.1, 0.055, 3593, 4095); }
        else l = 0.05;
        
        return 1.0 - l / 0.8;
    }
    
}
