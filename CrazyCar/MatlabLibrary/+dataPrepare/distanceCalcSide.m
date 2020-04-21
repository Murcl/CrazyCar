function [out,voltage] = distanceCalcSide(in,limit)

    voltage = (in*560./(560+82))*2.5 ./ 2^10;

    out = 16569 ./ ((in*3/2.5)*560./(560+82)./4+25) - 11;
   % if out > limit
       out(out > limit) = NaN;
   % end

end
