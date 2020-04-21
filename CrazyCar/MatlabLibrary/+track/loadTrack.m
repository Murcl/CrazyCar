function [x,y,theta] = loadTrack(direction,Unit)

if strcmp(Unit,'mm')
    amp = 10;
else
    amp = 1;
end
    

radius = 37*amp;
Kappa = [];

Kappa = [Kappa, track.straight(504*amp)];
Kappa = [Kappa, track.curve('left',90,radius)];

Kappa = [Kappa, track.straight(144*amp)];
Kappa = [Kappa, track.curve('left',90,radius)];

Kappa = [Kappa, track.straight(205*amp)];
Kappa = [Kappa, track.curve('left',180,radius)];

Kappa = [Kappa, track.straight(130*amp)];
Kappa = [Kappa, track.curve('right',180,radius)];

Kappa = [Kappa, track.straight(265*amp)];
Kappa = [Kappa, track.curve('right',90,radius)];

Kappa = [Kappa, track.straight(70*amp)];
Kappa = [Kappa, track.curve('left',90,radius)];

Kappa = [Kappa, track.straight(83*amp)];
Kappa = [Kappa, track.curve('left',90,radius)];

Kappa = [Kappa, track.straight(144*amp)];
Kappa = [Kappa, track.curve('left',90,radius)];

Kappa_int = zeros(1,length(Kappa));
for i = 1 : length(Kappa)
    theta(i) = wrapToPi(trapz(Kappa(1:i)));
    x(i) = trapz(cos(theta(1:i)));
    y(i) = trapz(sin(theta(1:i)));
end

if strcmp(direction,'counterclockwise')

elseif strcmp(direction,'clockwise')

end

end

