function out = Stanley(el,etheta,vs,kP,kTheta)

out = atan(el*kP/vs) + etheta * kTheta;

end

