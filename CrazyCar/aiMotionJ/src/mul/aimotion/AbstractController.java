/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mul.aimotion;

import mul.aimotion.car.Car;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Martin Antenreiter
 */
public abstract class AbstractController extends Thread {

    protected final Car car;
    protected volatile boolean finished;

    public AbstractController(Car car) {
        this.car = car;
    }

    public void exit() {
        finished = true;
    }

    @Override
    public void run() {
        try {
            car.connect();
            while (!finished) {
                controll();
            }
            car.disconnect();
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public abstract void controll() throws IOException;
    
}
