function [out,voltage] = distanceCalcFront(in,limit)

    voltage = (in*(560./(560+82))) ./ 2^12;
    out = 16569 ./ (in*(560+82)./560./4+25)*5/2.5 - 23;
  
    out(out > limit) = NaN;
    
end
