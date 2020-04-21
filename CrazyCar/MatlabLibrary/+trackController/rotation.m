function [xOut,yOut] = rotation(x,y,theta)

xOut = x*cos(theta) + y*sin(theta);
yOut = y*cos(theta) - x*sin(theta);

end

