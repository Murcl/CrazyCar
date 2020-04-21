function [throttle_sat,errorRpmSensor] = CruiseControl(data,vs,kP,kI)

    persistent  errorI;
    if isempty(errorI)
        errorI = 0;
    end

    
    errorRpmSensor = vs - double(data.sens.speed(1));
    errorI = errorI + errorRpmSensor;
    throttle = kP*errorRpmSensor + kI*errorI + 125;
    throttle_sat = floor(min(250, max(50, throttle)));
	if((throttle > 300) && (data.sens.speed(1) < 0.2))
		error('CRASH !!! ENGINE DAMAGED')
	end
	if((errorI > 200) && (data.sens.vBatSensor(1) < 4.5))
		error('AKKU LEER !!!')
	end
	if((errorI > 200) && (data.sens.vBatSensor(1) < 4.8))
		warning('AKKU LEER !!!')
	end
	

end