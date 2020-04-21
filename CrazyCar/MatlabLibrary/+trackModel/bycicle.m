function [xVehicle,yVehicle,theta] = bycicle(xVehicle,yVehicle,theta,delta,dt,vs)

max_angle = 20;%grad 
delta_sat = min(max_angle*pi/180, max(-max_angle*pi/180, delta));

L = 15; %% in cm

thetaDot = tan(delta_sat)*(vs/L);
theta = wrapToPi(theta + thetaDot*dt);

xVehicleDot = cos(theta) * vs;
yVehicleDot = sin(theta) * vs;

xVehicle = xVehicle + xVehicleDot*dt;
yVehicle = yVehicle + yVehicleDot*dt;

end

