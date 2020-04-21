function [x,y,theta,speed] = loadTrackDemo


    amp = 1;

    

radius = 37*amp;
Kappa = [];

Kappa = [Kappa, track.straight(134*amp)];
Kappa = [Kappa, track.curve('left',90,radius)];
Kappa = [Kappa, track.straight(100*amp)];
Kappa = [Kappa, track.curve('left',90,radius*2)];
Kappa = [Kappa, track.straight(190*amp)];
Kappa = [Kappa, track.curve('left',90,radius)];
Kappa = [Kappa, track.straight(190*amp)];
Kappa = [Kappa, track.curve('left',90,radius*1.25)];
Kappa = [Kappa, track.straight(300*amp)];
Kappa = [Kappa, track.curve('left',90,radius)];
Kappa = [Kappa, track.straight(120*amp)];
Kappa = [Kappa, track.curve('left',90,radius)];
Kappa = [Kappa, track.straight(220*amp)];
Kappa = [Kappa, track.curve('left',90,radius)];
Kappa = [Kappa, track.straight(60*amp)];
Kappa = [Kappa, track.curve('left',90,radius)];




Kappa_int = zeros(1,length(Kappa));
for i = 1 : length(Kappa)
    theta(i) = wrapToPi(trapz(Kappa(1:i)));
    x(i) = trapz(cos(theta(1:i)));
    y(i) = trapz(sin(theta(1:i)));
    speed(i) = 100-Kappa(i)*1000;
end


end

