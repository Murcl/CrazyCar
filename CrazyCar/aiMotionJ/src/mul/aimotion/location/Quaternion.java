/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mul.aimotion.location;

/**
 *
 * @author Martin Antenreiter
 */
public class Quaternion {

    private final double q1;
    private final double q2;
    private final double q3;
    private final double q4;

    public Quaternion(double q1, double q2, double q3, double q4) {
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.q4 = q4;
    }

    public double getQ1() {
        return q1;
    }

    public double getQ2() {
        return q2;
    }

    public double getQ3() {
        return q3;
    }

    public double getQ4() {
        return q4;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.q1) ^ (Double.doubleToLongBits(this.q1) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.q2) ^ (Double.doubleToLongBits(this.q2) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.q3) ^ (Double.doubleToLongBits(this.q3) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.q4) ^ (Double.doubleToLongBits(this.q4) >>> 32));
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
        final Quaternion other = (Quaternion) obj;
        if (Double.doubleToLongBits(this.q1) != Double.doubleToLongBits(other.q1)) {
            return false;
        }
        if (Double.doubleToLongBits(this.q2) != Double.doubleToLongBits(other.q2)) {
            return false;
        }
        if (Double.doubleToLongBits(this.q3) != Double.doubleToLongBits(other.q3)) {
            return false;
        }
        if (Double.doubleToLongBits(this.q4) != Double.doubleToLongBits(other.q4)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Quaternion{" + "q1=" + q1 + ", q2=" + q2 + ", q3=" + q3 + ", q4=" + q4 + '}';
    }

    public double[] toArray() {
        return new double[] {q1, q2, q3, q4};
    }
        
}
