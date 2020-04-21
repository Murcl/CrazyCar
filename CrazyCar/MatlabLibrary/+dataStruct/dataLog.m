function data = dataLog(data,logSamples,path)

persistent Init 
persistent optitrackold 
persistent saveActivated
persistent roundNo
persistent InPath
if isempty(Init)
    Init = 0;
    if data.direction == 0
    optitrackold = Inf;
    else
    optitrackold = 0;
    end
    roundNo = 0;
    time = datestr(now,'dd_mm_yy_HH_MM_SS'); 
    InPath = [path '\Data_' time];
    mkdir(InPath)
end 
    if data.direction == 0
        if (optitrackold  < 2) && (data.optitrack.x(1) > 2) && (abs(data.optitrack.y(1)) < 0.3)
                time = datestr(now,'dd_mm_yy_HH_MM_SS'); 
                name = [InPath '\data_' time];
                save(name,'data');
                data.roundTime(roundNo+1) = sum(data.gtime(1:data.lengthActual));
                data.lengthActual = 0;

                roundNo = roundNo + 1;     
        end
    else
        if (optitrackold  > 2) && (data.optitrack.x(1) < 2) && (abs(data.optitrack.y(1)) < 0.3)
                time = datestr(now,'dd_mm_yy_HH_MM_SS'); 
                name = [InPath '\data_' time];
                save(name,'data');
                data.roundTime(roundNo+1) = sum(data.gtime(1:data.lengthActual));
                data.lengthActual = 0;

                roundNo = roundNo + 1;     
        end
    end
    
    
    
     optitrackold = data.optitrack.x(1);
    
    

%     if logSamples ~= 1
%         if data.lengthActual == logSamples
%             time = datestr(now,'dd_mm_yy_HH_MM_SS'); 
%             name = [InPath '\data_' time];
%             save(name,'data');
%             data.lengthActual = 0;
%         end
%     end

    data.roundNo = circshift(data.roundNo,1);
    data.roundNo(1) = roundNo;
    
    data.sens.leftSensor = circshift(data.sens.leftSensor,1);
    data.sens.leftSideSensor = circshift(data.sens.leftSideSensor,1);
    data.sens.rightSideSensor = circshift(data.sens.rightSideSensor,1);
    data.sens.rightSensor = circshift(data.sens.rightSensor,1);
    data.sens.frontSensor = circshift(data.sens.frontSensor,1);
    
    data.sens.leftSensorRaw = circshift(data.sens.leftSensorRaw,1);
    data.sens.leftSideSensorRaw = circshift(data.sens.leftSideSensorRaw,1);
    data.sens.rightSideSensorRaw = circshift(data.sens.rightSideSensorRaw,1);
    data.sens.rightSensorRaw = circshift(data.sens.rightSensorRaw,1);
    data.sens.frontSensorRaw = circshift(data.sens.frontSensorRaw,1);

    data.sens.vBatSensor = circshift(data.sens.vBatSensor,1);
    data.sens.speed = circshift(data.sens.speed,1);
    data.sens.accelX = circshift(data.sens.accelX,1);
    data.sens.accelY = circshift(data.sens.accelY,1);
    data.sens.accelZ = circshift(data.sens.accelZ,1);
    data.sens.temp = circshift(data.sens.temp,1);
    data.sens.gyroX = circshift(data.sens.gyroX,1);
    data.sens.gyroY = circshift(data.sens.gyroY,1);
    data.sens.gyroZ = circshift(data.sens.gyroZ,1);
    data.sens.distance = circshift(data.sens.distance,1);
    data.sens.time = circshift(data.sens.time,1);
    data.sens.timeStep = circshift(data.sens.timeStep,1);
    
    data.optitrack.x = circshift(data.optitrack.x,1);
    data.optitrack.y = circshift(data.optitrack.y,1);
    data.optitrack.z = circshift(data.optitrack.z,1);
    data.optitrack.theta = circshift(data.optitrack.theta,1);
    data.optitrack.isTracked = circshift(data.optitrack.isTracked,1);
    
    data.trackController.x = circshift(data.trackController.x,1);
    data.trackController.y = circshift(data.trackController.y,1);
    data.trackController.theta = circshift(data.trackController.theta,1);
    data.trackController.ex = circshift(data.trackController.ex,1);
    data.trackController.ey = circshift(data.trackController.ey,1);
    data.trackController.etheta = circshift(data.trackController.etheta,1);
    
    data.act.steer = circshift(data.act.steer,1);
    data.act.steerSat = circshift(data.act.steerSat,1);
    data.act.throttle = circshift(data.act.throttle,1);
    
    data.gtime(1) = toc;
    tic;
    
    data.gtime = circshift(data.gtime,1);
    
    data.wall = circshift(data.wall,1,2);

    data.lengthActual = data.lengthActual + 1; 
end

