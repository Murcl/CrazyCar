/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mul.aimotion.car;

/**
 *
 * @author Martin Antenreiter
 */
public class HPIRacingModel {

    /**
     * Offset for steering, if you use this value than the car drives straight on.
     */
    static final int STEERING_OFFSET_MUL_CAR = 105;
    
    /**
     * Maximum steering value the car can use.
     */
    private static final double MAX_STEERING_VALUE = 85.0;

    private static final double CAR_WHEEL_DISTANCE = 140.0;
    
    private double x;
    private double y;
    private double direction;

    public HPIRacingModel(double x, double y, double direction) {
        this.x = x;
        this.y = y; 
        this.direction = direction;
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

    public void setDirection(double newDirection) {
        this.direction = newDirection;
    }
    
    public void setX(double newX) {
        this.x = newX;            
    }
    
    public void setY(double newY) {
        this.y = newY;            
    }
    
    public void predict(int vInMillisPerSecond, int steering, double timeInMillis) {
       double delta_dist = 1.0/1.1908 * vInMillisPerSecond * timeInMillis/1000.0;
       double phi = -4.0/709.0 * (steering+0.682531381) + 285.0/478.0;               
       double delta_direction = delta_dist * Math.tan(phi) / CAR_WHEEL_DISTANCE;
        direction += delta_direction;
       if(direction < -Math.PI) {
           direction += 2.0 * Math.PI;
       } else if(direction > Math.PI) {
           direction -= 2.0 * Math.PI;
       }
       y += delta_dist*Math.sin(direction);
       x += delta_dist*Math.cos(direction);
    }
    
    @Override
    public String toString() {
        return "HPIRacingModel{" + "x=" + x + ", y=" + y + ", direction=" + direction + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 61 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        hash = 61 * hash + (int) (Double.doubleToLongBits(this.direction) ^ (Double.doubleToLongBits(this.direction) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HPIRacingModel other = (HPIRacingModel) obj;
        if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
            return false;
        }
        if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
            return false;
        }
        if (Double.doubleToLongBits(this.direction) != Double.doubleToLongBits(other.direction)) {
            return false;
        }
        return true;
    }   

    
}
