function [data] = dataInit(length)

    data.lengthActual = 0;
    data.length = length;
    data.roundNo = zeros(1,length);
    
    data.act.steer = zeros(1,length);
    data.act.steerSat = zeros(1,length);
    data.act.throttle = zeros(1,length);
    
    data.sens.leftSensor = zeros(1,length);
    data.sens.rightSensor = zeros(1,length);
    data.sens.frontSensor = zeros(1,length);
    data.sens.rightSideSensor = zeros(1,length);
    data.sens.leftSideSensor = zeros(1,length);
    
    data.sens.leftSensorRaw = zeros(1,length);
    data.sens.rightSensorRaw = zeros(1,length);
    data.sens.frontSensorRaw = zeros(1,length);
    data.sens.rightSideSensorRaw = zeros(1,length);
    data.sens.leftSideSensorRaw = zeros(1,length);

    data.sens.vBatSensor = zeros(1,length);
    data.sens.speed = zeros(1,length);
    data.sens.accelX = zeros(1,length);
    data.sens.accelY = zeros(1,length);
    data.sens.accelZ = zeros(1,length);
    data.sens.temp = zeros(1,length);
    data.sens.gyroX = zeros(1,length);
    data.sens.gyroY = zeros(1,length);
    data.sens.gyroZ = zeros(1,length);
    data.sens.distance = zeros(1,length);
    data.sens.time = zeros(1,length);
    data.sens.timeStep = zeros(1,length);

    data.gtime = zeros(1,length);
    
    data.trackController.x = zeros(1,length);
    data.trackController.y = zeros(1,length);
    data.trackController.theta = zeros(1,length);
    data.trackController.ex = zeros(1,length);
    data.trackController.ey = zeros(1,length);
    data.trackController.etheta = zeros(1,length);
    
    data.optitrack.x = zeros(1,length);
    data.optitrack.y = zeros(1,length);
    data.optitrack.z = zeros(1,length);
    data.optitrack.theta = zeros(1,length);
    data.optitrack.isTracked = zeros(1,length);
    
    data.direction = 0;
    
    data.wall = zeros(2,length,5);

end

