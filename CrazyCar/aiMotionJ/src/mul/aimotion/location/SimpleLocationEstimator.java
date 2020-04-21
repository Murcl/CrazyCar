/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mul.aimotion.location;

import java.util.ArrayList;
import java.util.List;
import mul.aimotion.car.Car;
import mul.aimotion.car.HPIRacingModel;

/**
 *
 * @author Martin Antenreiter
 */
public class SimpleLocationEstimator implements LocationEstimator {

    private Car mycar;
    private double initx;
    private double inity;
    private double initdirection;
    private final HPIRacingModel model;

    public SimpleLocationEstimator(double x, double y, double direction, Car mycar) {
        this.initx = x;
        this.inity = y;
        this.initdirection = direction;
        this.mycar = mycar;
        this.model = new HPIRacingModel(x, y, direction);
    }   
    
    @Override
    public List<Location> getEstimatedLocations() {
        Location l = new Location(model.getX(), model.getY(), model.getDirection());
        List<Location> result = new ArrayList<>(1);
        result.add(l);
        return result;
    }

    @Override
    public void init() {
        model.setX(initx);
        model.setY(inity);
        model.setDirection(initdirection);
    }

    @Override
    public void update() {
        int vInMillis = mycar.getRpm();
        int steering = mycar.getSteering();
        double timeInMillis = mycar.getDeltaTimeInMillis();
        model.predict(vInMillis, steering, timeInMillis);
    }

    @Override
    public void reset() {
        init();
    }

    @Override
    public void setCar(Car mycar) {
        this.mycar = mycar;
    }

    @Override
    public void setLocationHint(double x, double y, double direction) {
        this.initx = x;
        this.inity = y;
        this.initdirection = direction;
    }

}
