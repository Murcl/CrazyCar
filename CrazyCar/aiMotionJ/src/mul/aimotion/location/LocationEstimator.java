/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mul.aimotion.location;

import java.util.List;
import mul.aimotion.car.Car;

/**
 *
 * @author Martin Antenreiter <martin.antenreiter@unileoben.ac.at>
 */
public interface LocationEstimator {
    public List<Location> getEstimatedLocations();
    public void setLocationHint(double x, double y, double direction);
    public void init();
    public void update();
    public void reset();

    public void setCar(Car mycar);
}
