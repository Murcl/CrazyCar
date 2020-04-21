function [out,voltage] = distanceCalcSideSide(in,limit)

    voltage = (in*560./(560+82)) ./ 2^12;
    out = 16569 ./ ((in*1.00)./4+25) - 18;
    
    out(out > limit) = NaN;
    
end
