function Kappa = curve(direction,angle,radius)

U = round(radius*(angle*2*pi/360));

if strcmp(direction,'left')
    Kappa(1:U) = 1/radius;
end

if strcmp(direction,'right')
    Kappa(1:U) = -1/radius;
end

end

