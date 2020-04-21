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
public class Controller extends AbstractController {


    public Controller(CarFirmware20190717 car) {
        super(car);
    }
    

    @Override
    public void controll() throws IOException {
        /*if(car.getDistFront() > 2500) {
            car.setSpeed(100);
            car.disconnect();
        } else if(car.getDistFront() > 2000) {
            car.setSpeed(210);
        } else if(car.getDistFront() > 1200) {
            car.setSpeed(220);
        } else if(car.getDistFront() > 750) {
            car.setSpeed(230);
        } else if(car.getDistFront() > 600) {
            car.setSpeed(240);
        }*/
        
        if(car.getDistFront() > 2300) {
            car.setSpeed(65);
            try {
                Thread.sleep(500l);
            } catch (InterruptedException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            car.disconnect();
        } else {
            car.setSpeed(210);  // 230 240
        } 
        
        final int DIFF = 300;
        int right = car.getDistRight();
        int left  = car.getDistLeft();
        int abs = Math.abs(right-left);
        if(abs <= DIFF) {
            car.setSteering(100);         // STRAIGHT
        } else {
            if(right-left > DIFF) {
                car.setSteering(20);      // LEFT      
            } else if (left-right > DIFF) {
                car.setSteering(180);     // RIGHT                           
            }
        }
    }
    
    
    
}
