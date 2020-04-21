/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mul.aimotion;

import mul.aimotion.car.CarFirmware20190717;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Martin Antenreiter
 */
public class SpeedController extends AbstractController {


    public SpeedController(CarFirmware20190717 car) {
        super(car);
    }
    

    @Override
    public void controll() throws IOException {
        if(car.getDistFront() > 2300) {
            car.setSpeed(65);
            try {
                Thread.sleep(500l);
            } catch (InterruptedException ex) {
                Logger.getLogger(SpeedController.class.getName()).log(Level.SEVERE, null, ex);
            }
            car.disconnect();
        } else {
            car.setSpeed(210);  // 230 240
        } 
    }
    
    
    
}
