/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mul.aimotion.location;

import mul.aimotion.location.ParticleFilter.Particle;

/**
 *
 * @author Martin Antenreiter
 */
class RpmSteeringMotionModel {


    public RpmSteeringMotionModel() {
    }

    public double predict(Particle p, int rpm, int steering, double deltaTime) {
/*       double dist = rpm * deltaTime;
       // FIXME: use steering/85.0 * MAGIC_CONST
       double angle = (steering - STEERING_OFFSET_MUL_CAR) / MAX_STEERING_VALUE   * (10.0*Math.PI/180.0); 
       p.direction += p.direction + angle;
       if(p.direction < -Math.PI) {
           p.direction += 2.0 * Math.PI;
       } else if(p.direction > Math.PI) {
           p.direction -= 2.0 * Math.PI;
       }
       p.x += dist*Math.cos(p.direction);
       p.y += dist*Math.sin(p.direction);*/
       return 1.0;
    }
}
