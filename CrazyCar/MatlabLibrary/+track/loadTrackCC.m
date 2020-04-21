function carTrack = loadTrackCC(maxSpeed,kP)
    
amp = 1;

radius = 35*amp;
Kappa = [];

Kappa = [Kappa, track.straight(524.12*amp)];
Kappa = [Kappa, track.curve('left',90,radius)];
Kappa = [Kappa, track.straight(151.96*amp)];
%Kappa = [Kappa, carTrack.straight(143.96*amp)];
Kappa = [Kappa, track.curve('left',90,radius)];
Kappa = [Kappa, track.straight(290.72*amp)];
Kappa = [Kappa, track.curve('left',90,radius)];
%Kappa = [Kappa, carTrack.straight(8.08*amp)];
Kappa = [Kappa, track.curve('left',90,radius)];
Kappa = [Kappa, track.straight(190.26*amp)];
Kappa = [Kappa, track.curve('right',90,radius)];
Kappa = [Kappa, track.straight(6*amp)];
Kappa = [Kappa, track.curve('right',90,radius)];
Kappa = [Kappa, track.straight(270.1*amp)];
Kappa = [Kappa, track.curve('right',90,radius)];
Kappa = [Kappa, track.straight(77.44*amp)];
Kappa = [Kappa, track.curve('left',90,radius)];
Kappa = [Kappa, track.straight(83.56*amp)];
Kappa = [Kappa, track.curve('left',90,radius)];
Kappa = [Kappa, track.straight(151.88*amp)];
Kappa = [Kappa, track.curve('left',90,radius)];

Kappa_int = zeros(1,length(Kappa));
for i = 1 : length(Kappa)
    theta(i) = wrapToPi(trapz(Kappa(1:i)));
    x(i) = trapz(cos(theta(1:i)));
    y(i) = trapz(sin(theta(1:i)));
    speed(i) = maxSpeed-abs(Kappa(i))*kP;
end

carTrack.x = x/100;
carTrack.y = y/100;
carTrack.theta = theta;
carTrack.speed = speed;

end

