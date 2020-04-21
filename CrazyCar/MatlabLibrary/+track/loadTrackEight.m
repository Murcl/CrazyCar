function [x,y,theta] = loadTrackSquare


    amp = 1;

    

radius = 80*amp;
Kappa = [];


Kappa = [Kappa, track.curve('left',360,radius)];

Kappa = [Kappa, track.curve('right',360,radius)];

%Kappa = [Kappa, track.curve('left',90,radius)];

%Kappa = [Kappa, track.curve('left',90,radius)];




for i = 1 : length(Kappa)
    theta(i) = wrapToPi(trapz(Kappa(1:i)));
    x(i) = trapz(cos(theta(1:i)));
    y(i) = trapz(sin(theta(1:i)));
end


end

