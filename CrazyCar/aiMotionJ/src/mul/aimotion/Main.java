/*
 */
package mul.aimotion;

import mul.aimotion.car.CarFirmware20190717;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mul.aimotion.car.Car;
import mul.aimotion.view.ControllerView;

/**
 *
 * @author Martin Antenreiter <martin.antenreiter@unileoben.ac.at>
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {

        
        final long start = System.currentTimeMillis();
        //
        //final CarFirmware20190717 car = new DummyCar();
        final CarFirmware20190717 car = new CarFirmware20190717();
        
        //final Controller c = new Controller(car);
        final AbstractController c = new JoystickController(car);
        //final AbstractController c = new SpeedController(car);
        
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                long end = System.currentTimeMillis();
                try {
                    car.disconnect();
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                //System.out.println("Bye! runtime = " + (end-start) + "ms");
            }
        });
        Logger.getGlobal().log(Level.SEVERE, "Connecting to car.");
        car.connect();
        //System.out.println("Connected!");

        ControllerView.buildView().setCar(car);

        int speed=100;
        long dist = 0;
        long counter = 0;
        while(car.isConnected()) {
            //speed++;            
            c.controll();
            
            String data = car.getNextCarData();            
            if(data != null) {
//                System.out.println(data);
                System.out.println(car.getEstimatedSpeed() + "->" + car.getEstimatedDistance());
                
                /*dist += car.getDistRight();
                ++counter;
                if(counter % 1000 == 0) {
                    System.out.println("Avg: " + ((double) dist)/counter);
                    dist = 0;
                    counter = 0;
                } */               
            }
            //Thread.sleep(25l);
        }
    }
    
}
