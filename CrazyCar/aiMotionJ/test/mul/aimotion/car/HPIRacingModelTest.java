/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mul.aimotion.car;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Martin Antenreiter <martin.antenreiter@unileoben.ac.at>
 */
public class HPIRacingModelTest {

    private HPIRacingModel model;
    
    public HPIRacingModelTest() {
    }

    private static final double START_Y_IN_MILLIS = 0.0;
    private static final double START_X_IN_MILLIS = 1.0;
    private static final double START_DIRECTION_IN_RAD = 0.0;
    
    @Before
    public void setUp() {
        model = new HPIRacingModel(START_X_IN_MILLIS,
                START_Y_IN_MILLIS,
                START_DIRECTION_IN_RAD);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetX() {
        assertEquals(START_X_IN_MILLIS, model.getX(), 1e-6);                
    }

    @Test
    public void testGetY() {
        assertEquals(START_Y_IN_MILLIS, model.getY(), 1e-6);                
    }

    @Test
    public void testGetDirection() {
        assertEquals(START_DIRECTION_IN_RAD, model.getDirection(), 1e-6);                
    }

    @Test
    public void testRPMPredictDir0() {
        model.predict(1000, HPIRacingModel.STEERING_OFFSET_MUL_CAR, 1000.0);
        assertEquals(START_X_IN_MILLIS + 1000, model.getX(), 1e-6);
        assertEquals(START_Y_IN_MILLIS, model.getY(), 1e-6);
        assertEquals(START_DIRECTION_IN_RAD, model.getDirection(), 1e-6);                                
    }
    
    @Test
    public void testRPMPredictDirMinus90() {
        final double NEW_DIRECTION = -Math.PI/2.0;
        model.setDirection(NEW_DIRECTION);
        model.predict(1000, HPIRacingModel.STEERING_OFFSET_MUL_CAR, 10.0);
        assertEquals(START_X_IN_MILLIS, model.getX(), 1e-6);
        assertEquals(START_Y_IN_MILLIS - 10, model.getY(), 1e-6);
        assertEquals(NEW_DIRECTION, model.getDirection(), 1e-6);                                
    }

    @Test
    public void testRPMPredictDir90() {
        final double NEW_DIRECTION = Math.PI/2.0;
        model.setDirection(NEW_DIRECTION);
        model.predict(1000, HPIRacingModel.STEERING_OFFSET_MUL_CAR, 10.0);
        assertEquals(START_X_IN_MILLIS, model.getX(), 1e-6);
        assertEquals(START_Y_IN_MILLIS + 10, model.getY(), 1e-6);
        assertEquals(NEW_DIRECTION, model.getDirection(), 1e-6);                                
    }

    @Test
    public void testRPMPredictDir45() {
        final double NEW_DIRECTION = Math.PI * 45.0/180.0;
        final double F = Math.sin(Math.PI/4.0);
        model.setDirection(NEW_DIRECTION);
        model.predict(1000, HPIRacingModel.STEERING_OFFSET_MUL_CAR, 10.0);
        
        assertEquals(START_X_IN_MILLIS + F*10.0, model.getX(), 1e-6);
        assertEquals(START_Y_IN_MILLIS + F*10.0 , model.getY(), 1e-6);
        assertEquals(NEW_DIRECTION, model.getDirection(), 1e-6);                                
    }

    @Test
    public void testRPMPredictDirMinus45() {
        final double NEW_DIRECTION = -Math.PI * 45.0/180.0;
        final double F = Math.sin(Math.PI/4.0);
        model.setDirection(NEW_DIRECTION);
        model.predict(1000, HPIRacingModel.STEERING_OFFSET_MUL_CAR, 10.0);
        
        assertEquals(START_X_IN_MILLIS + F*10.0, model.getX(), 1e-6);
        assertEquals(START_Y_IN_MILLIS - F*10.0 , model.getY(), 1e-6);
        assertEquals(NEW_DIRECTION, model.getDirection(), 1e-6);                                
    }

    @Test
    public void testRPMPredictDir135() {
        final double NEW_DIRECTION = Math.PI * 135.0/180.0;
        final double F = Math.sin(Math.PI/4.0);
        model.setDirection(NEW_DIRECTION);
        model.predict(1000, HPIRacingModel.STEERING_OFFSET_MUL_CAR, 10.0);
        
        assertEquals(START_X_IN_MILLIS - F*10.0, model.getX(), 1e-6);
        assertEquals(START_Y_IN_MILLIS + F*10.0 , model.getY(), 1e-6);
        assertEquals(NEW_DIRECTION, model.getDirection(), 1e-6);                                
    }

    @Test
    public void testSteeringLeftSlowSpeed75() {
        final double RADIUS_STEERING_75 = 825;
        final double ARC_LEN = RADIUS_STEERING_75 * Math.PI/2.0;
        for(int i=0, I = (int)(ARC_LEN + 0.5); i<I; ++i) {
            model.predict(1, 75, 1000);
//            System.out.println(i + ": " + model);
        }
        assertEquals(Math.PI/2.0, model.getDirection(), 2e-2);                                
        assertEquals(START_Y_IN_MILLIS+RADIUS_STEERING_75, model.getY(), 10);                                        
        assertEquals(START_X_IN_MILLIS+RADIUS_STEERING_75, model.getX(), 10);                                
    }
    

    @Test
    public void testSteeringLeftSlowSpeed96() {
        final double RADIUS_STEERING_96 = 2720;
        final double ARC_LEN = RADIUS_STEERING_96 * Math.PI/2.0;
        for(int i=0, I = (int)(ARC_LEN + 0.5); i<I; ++i) {
            model.predict(10, 96, 100);
//            System.out.println(i + ": " + model);
        }
        assertEquals(Math.PI/2.0, model.getDirection(), 2e-2);                                
        assertEquals(START_Y_IN_MILLIS+RADIUS_STEERING_96, model.getY(), 35);                                        
        assertEquals(START_X_IN_MILLIS+RADIUS_STEERING_96, model.getX(), 35);                                
    }

    @Test
    public void testSteeringLeftSlowSpeed60() {
        final double RADIUS_STEERING_60 = 555 - 20; // 20 mm == DISTANCE WALL-CARWHEEL
        final double ARC_LEN = RADIUS_STEERING_60 * Math.PI/2.0;
        for(int i=0, I = (int)(ARC_LEN + 0.5); i<I; ++i) {
            model.predict(100, 60, 10);
//            System.out.println(i + ": " + model);
        }
        assertEquals(Math.PI/2.0, model.getDirection(), 2e-2);                                
        assertEquals(START_Y_IN_MILLIS+RADIUS_STEERING_60, model.getY(), 10);                                        
        assertEquals(START_X_IN_MILLIS+RADIUS_STEERING_60, model.getX(), 10);                                
    }

    @Test
    public void testSteeringLeftSlowSpeed135() {
        final double RADIUS_STEERING_75 = 825;
        final double ARC_LEN = RADIUS_STEERING_75 * Math.PI/2.0;
        for(int i=0, I = (int)(ARC_LEN + 0.5); i<I; ++i) {
            model.predict(1, 135, 1000);
//            System.out.println(i + ": " + model);
        }
        assertEquals(-Math.PI/2.0, model.getDirection(), 2e-2);                                
        assertEquals(START_Y_IN_MILLIS-RADIUS_STEERING_75, model.getY(), 10);                                        
        assertEquals(START_X_IN_MILLIS+RADIUS_STEERING_75, model.getX(), 10);                                
    }
    

}
