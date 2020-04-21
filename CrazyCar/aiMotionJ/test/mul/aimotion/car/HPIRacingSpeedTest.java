/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mul.aimotion.car;

import java.io.IOException;
import java.net.UnknownHostException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Martin Antenreiter <martin.antenreiter@unileoben.ac.at>
 */
public class HPIRacingSpeedTest {

    private HPIRacingModel model;
    private CarFirmware20190717 car;
    
    public HPIRacingSpeedTest() {
    }
    
    @Before
    public void setUp() throws UnknownHostException, IOException {
        model = new HPIRacingModel(0, 0, 0);
        car = new CarFirmware20190717();
        car.connect();
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    //@Test
    public void test6Second() throws InterruptedException, IOException {
        double dist = 0.0;
        int speed = 190;
        car.setSpeed(speed);
        car.waitForNextData();
        long start=System.currentTimeMillis();
        long end = start;
        do {       
            car.waitForNextData();
            double time = car.getDeltaTimeInMillis();            
            double vInMillis = car.getRpm();
            System.out.println(vInMillis + " t=" + (end-start));
            dist += vInMillis * (time / 1000.0);
            end = System.currentTimeMillis();            
        } while((end-start) < 10000);
        System.out.println("Time: " + (end-start) + ", distance = " + dist);
        car.setSpeed(90);
        car.waitForNextData();
        Thread.sleep(500l);
        car.disconnect();        
    }
    
    //@Test
    public void test3Second() throws InterruptedException, IOException {
        double dist = 0.0;
        int speed = 210;
        car.setSpeed(speed);
        car.waitForNextData();
        long start=System.currentTimeMillis();
        long end = start;
        do {       
            car.waitForNextData();
            double time = car.getDeltaTimeInMillis();            
            double vInMillis = car.getRpm();
            System.out.println(vInMillis + " t=" + (end-start));
            dist += vInMillis * (time / 1000.0);
            end = System.currentTimeMillis();            
        } while((end-start) < 2000);
        System.out.println("Time: " + (end-start) + ", distance = " + dist);
        car.setSpeed(70);
        car.waitForNextData();
        Thread.sleep(500l);
        car.disconnect();        
    }

    //@Test
    public void test1Second() throws InterruptedException, IOException {
        double dist = 0.0;
        int speed = 250;
        car.setSpeed(speed);
        car.waitForNextData();
        long start=System.currentTimeMillis();
        long end = start;
        do {       
            car.waitForNextData();
            double time = car.getDeltaTimeInMillis();            
            double vInMillis = car.getRpm();
            System.out.println(vInMillis + " t=" + (end-start));
            dist += vInMillis * (time / 1000.0);
            end = System.currentTimeMillis();            
        } while((end-start) < 1000);
        System.out.println("Time: " + (end-start) + ", distance = " + dist);
        car.setSpeed(70);
        car.waitForNextData();
        Thread.sleep(500l);
        car.disconnect();        
    }

    //@Test
    public void testSpeed210Outside() throws InterruptedException, IOException {
        double dist = 0.0;
        int speed = 210;
        car.setSpeed(speed);
        car.waitForNextData();
        long start=System.currentTimeMillis();
        long end = start;
        do {       
            car.waitForNextData();
            double time = car.getDeltaTimeInMillis();            
            double vInMillis = car.getRpm();
            System.out.println(vInMillis + " t=" + (end-start));
            dist += vInMillis * (time / 1000.0);
            end = System.currentTimeMillis();            
        } while((end-start) < 5000);
        System.out.println("Time: " + (end-start) + ", distance = " + dist);
        car.setSpeed(70);
        car.waitForNextData();
        Thread.sleep(500l);
        car.disconnect();        
    }

//    @Test
    public void testTurnLeft210() throws InterruptedException, IOException {
        double dist = 0.0;
        int speed = 210;
        car.setSpeed(speed);
        car.waitForNextData();
        long start=System.currentTimeMillis();
        long end = start;
        boolean doTurn = true;
        do {       
            car.waitForNextData();
/*            double time = car.getDeltaTimeInMillis();            
            double vInMillis = car.getRpm();
            System.out.println(vInMillis + " t=" + (end-start));
            dist += vInMillis * (time / 1000.0);*/
            int distFront = car.getDistFront();
            System.out.println(distFront);
            if(doTurn && distFront < 1000) {
                System.out.println("turnLeft");
                car.setSteering(60);
                Thread.sleep(650);
                doTurn = false;
            } else {
                car.setSteering(car.getSteeringTrim());                
            }
            end = System.currentTimeMillis();            
        } while((end-start) < 3500);
        System.out.println("Time: " + (end-start) + ", distance = " + dist);
        car.setSpeed(70);
        car.waitForNextData();
        Thread.sleep(500l);
        car.disconnect();        
    }

    //@Test
    public void testTurnLeft250() throws InterruptedException, IOException {
        double dist = 0.0;
        int speed = 250;
        car.setSpeed(speed);
        car.waitForNextData();
        long start=System.currentTimeMillis();
        long end = start;
        boolean doTurn = true;
        do {       
            car.waitForNextData();
/*            double time = car.getDeltaTimeInMillis();            
            double vInMillis = car.getRpm();
            System.out.println(vInMillis + " t=" + (end-start));
            dist += vInMillis * (time / 1000.0);*/
            int distFront = car.getDistFront();
            System.out.println(distFront);
            if(doTurn && distFront < 1400) {
                System.out.println("turnLeft");
                car.setSteering(60);
                Thread.sleep(500);
                doTurn = false;
            } else {
                car.setSteering(car.getSteeringTrim());                
            }
            end = System.currentTimeMillis();            
        } while((end-start) < 1500);
        System.out.println("Time: " + (end-start) + ", distance = " + dist);
        car.setSpeed(70);
        car.waitForNextData();
        Thread.sleep(500l);
        car.disconnect();        
    }

    //@Test
    public void testDistance2000mm() throws InterruptedException, IOException {
        double dist = 0.0;
        int speed = 200;
        car.setSpeed(speed);
        car.waitForNextData();
        long start=System.currentTimeMillis();
        long end = start;
        do {       
            car.waitForNextData();
            double time = car.getDeltaTimeInMillis();            
            double vInMillis = car.getRpm();
            System.out.println(vInMillis + " t=" + (end-start));
            dist += vInMillis * (time / 1000.0) * (1097.0/913.0);
            end = System.currentTimeMillis();            
        } while(dist < 2500);
        System.out.println("Time: " + (end-start) + ", distance = " + dist);
        car.setSpeed(10);
        car.waitForNextData();
        Thread.sleep(200l);
        car.disconnect();        
    }

    @Test
    public void testDistance10mm() throws InterruptedException, IOException {
        double dist = 0.0;
        double distCar = 0.0;
        int speed = 200;
        car.setSpeed(speed);
        car.waitForNextData();
        long start=System.currentTimeMillis();
        long end = start;
        do {       
            car.waitForNextData();
            double time = car.getDeltaTimeInMillis();            
            double vInMillis = car.getRpm();
            System.out.println(vInMillis + " t=" + (end-start));
            double d = vInMillis * (time / 1000.0);
            distCar += d;
            dist +=  d * 1.1908; //1.0981; //(1097.0/913.0);
            end = System.currentTimeMillis();            
        } while(dist < 10000);
        System.out.println("Time: " + (end-start) + ", distanceNorm = " + dist + ", distanceCar = " + distCar);
        car.setSpeed(10);
        car.waitForNextData();
        Thread.sleep(200l);
        car.disconnect();        
    }
    
}
