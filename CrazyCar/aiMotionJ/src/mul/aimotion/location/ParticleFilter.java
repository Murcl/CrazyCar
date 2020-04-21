/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mul.aimotion.location;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import mul.aimotion.car.Car;
import mul.aimotion.car.CarData;

/**
 *
 * @author Martin Antenreiter
 */
class ParticleFilter implements LocationEstimator {

    @Override
    public void setLocationHint(double x, double y, double direction) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    static class Particle {
        double x, y, direction;
        double w;

        public Particle(double x, double y, double direction, double w) {
            this.x = x;
            this.y = y;
            this.direction = direction;
            this.w = w;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getDirection() {
            return direction;
        }

        public double getW() {
            return w;
        }

        @Override
        public String toString() {
            return "Particle{" + "x=" + x + ", y=" + y + ", direction=" + direction + ", w=" + w + '}';
        }
               
        Location toLocation() {
            return new Location(x, y, direction);
        }
    
    }
    
    private final CarData cardata;
    private final TrackData trackdata;
    private final int nrParticles;
    private Car car;
    private final ArrayList<Particle> particles;

    private final RpmSteeringMotionModel motionModel;

    public ParticleFilter(String carfile, String trackfile, int nrParticles) throws IOException {
        cardata = new CarData(carfile);
        trackdata = new TrackData(trackfile);
        this.nrParticles = nrParticles;
        particles = new ArrayList<>(nrParticles);
        motionModel = new RpmSteeringMotionModel();
    }

    @Override
    public List<Location> getEstimatedLocations() {
        List<Location> result = new ArrayList<>(nrParticles);
        for(int i=0; i<particles.size(); ++i) {
            result.add(particles.get(i).toLocation());
        }
        return result;
    }

    @Override
    public void init() {
        particles.clear();
        Random rnd = new Random(0);
        for(int i=0; i<nrParticles; ++i) {
            double x = rnd.nextDouble() * (trackdata.getMaxX()-trackdata.getMinX()) + trackdata.getMinX();
            double y = rnd.nextDouble() * (trackdata.getMaxY()-trackdata.getMinY()) + trackdata.getMinY();
            double direction = rnd.nextDouble() * Math.PI * 2.0 - Math.PI;
            particles.add(new Particle(x, y, direction, 1.0/nrParticles));
        }
    }

    /**
     * https://books.google.at/books?id=SP5sCQAAQBAJ&pg=PA28&lpg=PA28&dq=Bel+probability+robotics&source=bl&ots=ZXRnwHkKnt&sig=ACfU3U1MRqFvk1HF-Xz6dm_dTG4OjEGgkA&hl=de&sa=X&ved=2ahUKEwiT582V0OrhAhWSYlAKHSL3B3EQ6AEwBnoECAgQAQ#v=onepage&q=Bel%20probability%20robotics&f=false
     * Integration of sensor input: data fromt the robot's sensors are used to 
     * update the belief Bel(l). An observation model P(o|l') models the 
     * likelihood of making an observation o given the robot is at position l'.
     * Bel(l) = alpha * P(o|l') Bel(l') (alpha=normalization factor).
     * 
     * Belief projection for robot motion: A motion model P(l|l', m) is used 
     * to predict the likelihood of the robot being in position l assuming 
     * that it executed a motion command m and was previously in position l'.
     * Bel(l) = Int(P(l|l', m) Bel(l')) dl'
     */
    @Override
    public void update() {
        double deltaTime = car.getDeltaTimeInMillis();
        int left = car.getDistLeft();
        int front = car.getDistFront();
        int right = car.getDistRight();        
        
        int rpm = car.getRpm();
        int steering = car.getSteering();
        
        sampleProjectionWithMotionModel(deltaTime, rpm, steering);
        beliefUpdateWithSensorData(left, front, right);
        
        
                
    }

    private void sampleProjectionWithMotionModel(double deltaTime, int rpm, int steering) {
        double normalization = 0.0;
        for(int i=0, I=particles.size(); i<I; ++i) {
            Particle p = particles.get(i);
            normalization += motionModel.predict(p, rpm, steering, deltaTime);
        }
        for(int i=0, I=particles.size(); i<I; ++i) {
            Particle p = particles.get(i);
            p.w /= normalization;
        }
    }

    private void beliefUpdateWithSensorData(int left, int front, int right) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void reset() {
        init();
    }

    @Override
    public void setCar(Car mycar) {
        this.car = mycar;
    }

}
