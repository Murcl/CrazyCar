/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mul.aimotion.location;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import mul.aimotion.car.CarData;
import mul.aimotion.car.SimCar;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Martin Antenreiter <martin.antenreiter@unileoben.ac.at>
 */
public class LocationTest {
    
    private static final long TIME = 1553083546953l;
    
    private static CarData cardata;
    
    private static TrackData trackdata;  
    
    private static SimCar car;
    
    public LocationTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws UnknownHostException, IOException {
        trackdata = new TrackData("../aiMotionExp1-2019_03_20/CrazyCAR/Runden_0003_13_05_42.csv");
        cardata = new CarData("../aiMotionExp1-2019_03_20/CrazyCAR/Runden_0003.csv");
        car = new SimCar(cardata);
        car.setLocationEstimator(
                new ParticleFilter("../aiMotionExp1-2019_03_20/CrazyCAR/Runden_0007.csv",
                        "../aiMotionExp1-2019_03_20/CrazyCAR/Runden_0007_13_38_56.csv",
                        1000
                )
        );
    }
    
    @AfterClass
    public static void tearDownClass() {
        car.setLocationEstimator(null);
        car = null;
        cardata = null;
        trackdata = null;
    }
    
    @Before
    public void setUp() {
        car.reset();
        trackdata.seekToTime(TIME);
        cardata.seekToTime(TIME);   
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testFirstTrackData() {        
        assertEquals(TIME, trackdata.getTime());
        assertEquals( 751.2747, trackdata.getX(), 1e-4);
        assertEquals(2442.1291, trackdata.getY(), 1e-4);
        assertEquals(  10.2465, trackdata.getZ(), 1e-4);
        assertEquals(-0.04789822, trackdata.getDirection(), 1e-6);
        assertArrayEquals(new double[]{-0.6850, -0.7273, -0.0062, 0.0414}, trackdata.getQuaternion().toArray(), 1e-4);
    }

    @Test
    public void testNextTrackData() {        
        trackdata.getNext();
        assertEquals(1553083546955l, trackdata.getTime());
        assertEquals( 751.2596, trackdata.getX(), 1e-4);
        assertEquals(2442.1601, trackdata.getY(), 1e-4);
        assertEquals(  10.2385, trackdata.getZ(), 1e-4);
        assertEquals(-0.04790006, trackdata.getDirection(), 1e-6);
        assertArrayEquals(new double[]{-0.6849, -0.7274, -0.0061, 0.0414}, trackdata.getQuaternion().toArray(), 1e-4);
    }
    
    @Test
    public void testFirstCarData() {
        // Entry 173: MUL-CC	/192.168.220.60	1341819	207	103	0	931	991	661	1249	28	8267	1268	65396	40741	2376    1553083546927	        
        assertEquals(1553083546927l, cardata.getTime());
        assertEquals(207, car.getSpeed());
        assertEquals(103, car.getSteering());
        assertEquals(0, car.getRpm());
        assertEquals(931, car.getDistLeft());
        assertEquals(991, car.getDistFront());
        assertEquals(661, car.getDistRight());        
    }    
    
    @Test
    public void testNextCarData() {
        //Entry 174: MUL-CC	/192.168.220.60	1341909	207	103	0	934	990	661	1249	28	8267	1268	65396	40741	2388	1553083546963
        //Entry 175: MUL-CC	/192.168.220.60	1341997	207	103	0	758	989	658	1438	38	8262	1254	65376	40760	2397	1553083546963
        //Entry 176: MUL-CC	/192.168.220.60	1342183	207	103	0	756	1051	664	1546	42	8203	1250	65361	40753	2367	1553083546963
        cardata.getNext();
        assertEquals(1553083546963l, cardata.getTime());
        assertEquals(207, car.getSpeed());
        assertEquals(103, car.getSteering());
        assertEquals(0, car.getRpm());
        assertEquals(756, car.getDistLeft());
        assertEquals(1051, car.getDistFront());
        assertEquals(664, car.getDistRight());        
    }

    @Test
    public void testSeekToCarData() {
        //Entry 173: MUL-CC	/192.168.220.60	1341819	207	103	0	931	991	661	1249	28	8267	1268	65396	40741	2376    1553083546927
        //                                                                                                                                                      1553083546955
        //Entry 174: MUL-CC	/192.168.220.60	1341909	207	103	0	934	990	661	1249	28	8267	1268	65396	40741	2388	1553083546963
        //Entry 175: MUL-CC	/192.168.220.60	1341997	207	103	0	758	989	658	1438	38	8262	1254	65376	40760	2397	1553083546963
        //Entry 176: MUL-CC	/192.168.220.60	1342183	207	103	0	756	1051	664	1546	42	8203	1250	65361	40753	2367	1553083546963
        trackdata.getNext();
        cardata.seekToTime(trackdata.getTime()); // 1553083546955
        assertEquals(1553083546927l, cardata.getTime());
        assertEquals(207, car.getSpeed());
        assertEquals(103, car.getSteering());
        assertEquals(0, car.getRpm());
        assertEquals(931, car.getDistLeft());
        assertEquals(991, car.getDistFront());
        assertEquals(661, car.getDistRight());                
    }
    
    @Test
    public void testExcludeOldValues() {
        trackdata.getNext();
        cardata.seekToTime(trackdata.getTime()); // 1553083546955
        cardata.getNext();
        assertEquals(1553083546963l, cardata.getTime());
        assertEquals(207, car.getSpeed());
        assertEquals(103, car.getSteering());
        assertEquals(0, car.getRpm());
        assertEquals(756, car.getDistLeft());
        assertEquals(1051, car.getDistFront());
        assertEquals(664, car.getDistRight());        
    }
    
    @Test
    public void testLocation() {
        long time = trackdata.getTime();
        while(time < 1553083552080l) {
            cardata.getNext();
            time = cardata.getTime();
        }
        trackdata.seekToTime(time);
        List<Location> locations = car.getLocationEstimator().getEstimatedLocations();
        boolean found = false;
        final int MAX_DIFF_IN_MILLIMETER = 100;
        final double x = trackdata.getX();
        final double y = trackdata.getY();
        final double direction = trackdata.getDirection();
        double best_x = Double.NaN;
        double best_y = Double.NaN;
        double best_diff = Double.POSITIVE_INFINITY;
        for(Location l : locations) {
            double diffx = l.getX() - x;
            double diffy = l.getY() - y;
            double diffd = l.getDirection() - direction;
            diffx = Math.sqrt(diffx*diffx);
            diffy = Math.sqrt(diffy*diffy);
            diffd = Math.sqrt(diffd*diffd);
            if(diffx+diffy < best_diff) {
                best_x = l.getX();
                best_y = l.getY();
                best_diff = diffx+diffy;
            }
            if(diffx < MAX_DIFF_IN_MILLIMETER && diffy < MAX_DIFF_IN_MILLIMETER) {
                found = true;
                break;
            }            
        }
        assertTrue("No location found near " + trackdata.getX() + "/" + trackdata.getY() + 
                   " with direction: " + trackdata.getDirection() + 
                   " best X/Y was: "+best_x+"/"+best_y, found);
    }
    
}