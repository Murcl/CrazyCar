/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mul.aimotion;

import mul.aimotion.car.CarFirmware20190717;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Martin Antenreiter <martin.antenreiter@unileoben.ac.at>
 */
public class CarControlTest {
    
    public CarControlTest() {        
    }
    
    private CarFirmware20190717 car;
    
    @Before
    public void setUp() throws IOException {
        car = new CarFirmware20190717();
        car.connect();
    }
    
    @After
    public void tearDown() throws IOException {
        car.reset();
        car.disconnect();
    }

    @Test
    public void testForward() throws InterruptedException {
        int rpm1 = car.getRpm();
        car.setSpeed(200);
        Thread.sleep(5000l);
        int rpm2 = car.getRpm();
        assertEquals(0, rpm1);
        assertNotEquals(0, rpm2);
    }
}
