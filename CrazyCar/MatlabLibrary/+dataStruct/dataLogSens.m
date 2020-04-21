function data = dataLogSens(recV, data)

    data.sens.leftSideSensorRaw(1) = bitshift(uint16(recV(3)),8) + uint16(recV(2));
    data.sens.rightSensorRaw(1) = bitshift(uint16(recV(5)),8) + uint16(recV(4));
    data.sens.frontSensorRaw(1) = bitshift(uint16(recV(7)),8) + uint16(recV(6));
    
    %%%%%
    data.sens.leftSensorRaw(1) = bitshift(uint16(recV(11)),8) + uint16(recV(10));
    data.sens.rightSideSensorRaw(1) = (bitshift(uint16(recV(13)),8) + uint16(recV(12)))/1.55;
    %%%%%
    data.sens.leftSideSensor(1) = dataPrepare.distanceCalcSideSide(data.sens.leftSideSensorRaw(1),Inf);
    data.sens.rightSensor(1) = dataPrepare.distanceCalcSide(data.sens.rightSensorRaw(1),Inf);
    data.sens.frontSensor(1) = dataPrepare.distanceCalcFront(data.sens.frontSensorRaw(1),Inf);
    data.sens.leftSensor(1) = dataPrepare.distanceCalcSide(data.sens.leftSensorRaw(1),Inf);
    data.sens.rightSideSensor(1) = dataPrepare.distanceCalcSideSide(data.sens.rightSideSensorRaw(1),Inf);
       
    
    
    data.sens.vBatSensor(1) = double(bitshift(uint16(recV(9)),8) + uint16(recV(8))) * (2.5 / (2^12-1)) / (33/(33+120));
    
    speed = (double(bitshift(uint32(recV(43)),24) + bitshift(uint32(recV(42)),16) + bitshift(uint32(recV(41)),8) + uint32(recV(40))));    
    if speed == 0
        speed = Inf;
    end
    data.sens.speed(1) = (5.2122 / (speed/625));

    data.sens.accelX(1) = double(typecast(bitshift(uint16(recV(14)),8) + uint16(recV(15)),'int16')) / 32767.5 * 2;
    data.sens.accelY(1) = double(typecast(bitshift(uint16(recV(16)),8) + uint16(recV(17)),'int16')) / 32767.5 * 2;
    data.sens.accelZ(1) = double(typecast(bitshift(uint16(recV(18)),8) + uint16(recV(19)),'int16')) / 32767.5 * 2;      

    data.sens.temp(1) = double(bitshift(uint16(recV(20)),8) + uint16(recV(21))) / 333.87 + 21.0;            

    data.sens.gyroX(1) = double(typecast(bitshift(uint16(recV(22)),8) + uint16(recV(23)),'int16')) / 32767.5 * 500;           
    data.sens.gyroY(1) = double(typecast(bitshift(uint16(recV(24)),8) + uint16(recV(25)),'int16')) / 32767.5 * 500;          
    data.sens.gyroZ(1) = double(typecast(bitshift(uint16(recV(26)),8) + uint16(recV(27)),'int16')) / 32767.5 * 500;    

    data.sens.distance(1) = (5.2122/1000)*(double(bitshift(uint32(recV(31)),24) + bitshift(uint32(recV(30)),16) + bitshift(uint32(recV(29)),8) + uint32(recV(28))));    
    data.sens.time(1) = double(bitshift(uint32(recV(35)),24) + bitshift(uint32(recV(34)),16) + bitshift(uint32(recV(33)),8) + uint32(recV(32)))/10000;
    data.sens.timeStep(1) = double(bitshift(uint32(recV(39)),24) + bitshift(uint32(recV(38)),16) + bitshift(uint32(recV(37)),8) + uint32(recV(36)))/10000;
    
    

end


