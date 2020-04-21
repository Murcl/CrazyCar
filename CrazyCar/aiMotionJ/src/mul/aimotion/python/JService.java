package mul.aimotion.python;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import mul.aimotion.car.CarFirmware20190717;
import mul.aimotion.location.LocationEstimator;
import mul.aimotion.location.SimpleLocationEstimator;
import py4j.GatewayServer;

/**
 *
 * @author Martin Antenreiter
 */
public class JService {
    
    private final Lock lock = new ReentrantLock();
    
    private final CarFirmware20190717 car;
    private boolean running;
    private double posX;
    private double posY;
    private double posZ;
    private double[] quaternion;
    private double yaw;
    private BufferedWriter writer;

    public JService() throws UnknownHostException {
        this.car = new CarFirmware20190717();
        this.running = true;
        this.quaternion = new double[4];
        this.posX = 0.0;
        this.posY = 0.0;
        this.posZ = 0.0;
        this.yaw = 0.0;
    }       
    
    public CarFirmware20190717 getCar() {
        return car;
    }        
    
    public CarFirmware20190717 getCarWithSimpleLocationEstimator(double x, double y, double direction) {
        LocationEstimator estimator = new SimpleLocationEstimator(x, y, direction, car);
        car.setLocationEstimator(estimator);
        return car;
    }
    
    public void createLog(String filename) {
        try {
            if(writer != null) closeLog();
            writer = new BufferedWriter(new FileWriter(filename));
        } catch (IOException ex) {
            Logger.getLogger(JService.class.getName()).log(Level.SEVERE, null, ex);
            stopService();
        }
    }
    
    public void writeToLog() throws IOException { 
        if(writer == null) return;
        long optiTrackTime = System.currentTimeMillis();
        String carData = car.getNextCarData();
        while(carData == null) {
            carData = car.getNextCarData();
        }
        writer.append(carData);
        writer.append(',');
        writer.append(getOptitrackData(optiTrackTime));
        writer.newLine();
    }
    
    private String getOptitrackData(long timestamp) {
        return "TD," + timestamp
                + posX + "," + posY + "," + posZ 
                + "," + yaw  
                + "," + quaternion[0] + "," + quaternion[1] 
                + "," + quaternion[2] + "," + quaternion[3];
                
    }
    
    public void closeLog() throws IOException {
        if(writer == null) return;
        writer.flush();
        writer.close();
        writer = null;
    }
    
    public boolean isRunning() {
        return running;
    }
    
    public void stopService() {
        running = false;
    }
    
  
    public void lock() {
        lock.lock();
    }
    
    public void unlock() {
        lock.unlock();
    }
    
    public void setPosition(double[] position) {
        posX = position[2];
        posY = position[0];
        posZ = position[1];
    }
    
    public void setQuaternion(double[] quat) {
        System.arraycopy(quat, 0, this.quaternion, 0, this.quaternion.length);
    }
    
    public void setYaw(double yaw) {
        this.yaw = yaw;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public double getPosZ() {
        return posZ;
    }

    public double[] getQuaternion() {
        return quaternion;
    }

    public double getYaw() {
        return yaw;
    }
    
    public static void main(String[] args) throws UnknownHostException {
        startServer();
    }
    
    private static JService app;
    
    public static JService getService() {
        if(app == null) throw new IllegalStateException("Please call startServer() first!");
        return app;
    }
    
    public static GatewayServer startServer() throws UnknownHostException {
        app = new JService();
        // app is now the gateway.entry_point
        GatewayServer server = new GatewayServer(app);
        server.start();
        return server;
    }
    
    
}
