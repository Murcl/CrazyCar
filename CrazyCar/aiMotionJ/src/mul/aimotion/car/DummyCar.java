/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mul.aimotion.car;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Martin Antenreiter
 */
public class DummyCar extends CarFirmware20190717 {

    private static final String DUMMY_IP = "127.0.0.1";
    
    public DummyCar() throws UnknownHostException {
        super("DUMMY-CC", DUMMY_IP);
    }

    public DummyCar(String name, String ip) throws UnknownHostException {
        super(name, ip);
    }

    @Override
    public void connect() throws IOException {
        connected = true;
        vbat = 2500;
        temp = 1500;
    }

    @Override
    public boolean isConnected() {
        return connected && vbat != 0 && temp != 0;
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(25l);
            } catch (InterruptedException ex) {
                Logger.getLogger(DummyCar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void disconnect() throws IOException {
        synchronized(this) {
            connected = false;
            reset();
            try {
               Thread.sleep(200l); // Wait for the run-Method, 
            } catch (InterruptedException ex) {
                Logger.getLogger(DummyCar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }       
    }

    @Override
    public String toString() {
        return super.toString();
    }    
    
}
