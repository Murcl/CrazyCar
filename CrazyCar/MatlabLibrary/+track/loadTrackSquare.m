function [x,y,theta] = loadTrackSquare


    amp = 1;

    

radius = 37*amp;
Kappa = [];

Kappa = [Kappa, track.straight(134*amp)];
Kappa = [Kappa, track.curve('left',90,radius)];
Kappa = [Kappa, track.straight(65*amp)];
Kappa = [Kappa, track.curve('left',90,radius)];
Kappa = [Kappa, track.straight(134*amp)];
Kappa = [Kappa, track.curve('left',90,radius)];
Kappa = [Kappa, track.straight(65*amp)];
Kappa = [Kappa, track.curve('left',90,radius)];




Kappa_int = zeros(1,length(Kappa));
for i = 1 : length(Kappa)
    theta(i) = wrapToPi(trapz(Kappa(1:i)));
    x(i) = trapz(cos(theta(1:i)));
    y(i) = trapz(sin(theta(1:i)));
end


end

