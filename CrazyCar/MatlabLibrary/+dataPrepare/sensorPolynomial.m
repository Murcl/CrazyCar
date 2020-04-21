function out = sensorPolynomial(in)
% Measurement Data 

%sensorOutput = [2.75 2.55 2 1.55 1.25 1.05 0.9 0.8 0.725 0.65 0.6 0.55 0.5 0.495 0.49];
%ADCchange = 4096 / max(2.75);

% out = 27.86 *(in.*2.5./2^12).^(-1.15)
% out =  5461 ./ ((in./2^12).*2^10.*2 - 17) - 2;
out = 6787 ./ (in*560./(560+82)./2.*3.3./2.5-3) - 4

outF = 16569 ./ (in*560./(560+82)./2.*3.3./2.5+25) - 11

 out = 16569 * ((in./2)+25)-11

adcOutput = [4095 3858 3357 3073 2871 2533 2237 1816 1517 1316 1179 1063 935 868 811 755,...
    719 672 645 615 587 552 532 517];

relatedDistance_mm = [0 2 4 5 6 8 10 15 20 25 30 35 40 45 50 55 60 65 70 75 80 85 90 95] * 10;

[p,s] = polyfit(adcOutput, relatedDistance_mm, 8);
out = floor(polyval(p,in));

end

% 
% typedef const struct
% {
% 	const signed short a;
% 	const signed short b;
% 	const signed short k;
% }
% ir_distance_sensor;
%  
% // The object of the parameters of GP2Y0A21YK sensor
% const ir_distance_sensor GP2Y0A21YK = { 5461, -17, 2 };
% 
% signed short ir_distance_calculate_cm(ir_distance_sensor sensor,
% 	unsigned short adc_value)
% {
% 	if (adc_value + sensor.b <= 0)
% 	{
% 		return -1;
% 	}
%  
% 	return sensor.a / (adc_value + sensor.b) - sensor.k;
% }