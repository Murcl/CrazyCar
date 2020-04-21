function data = dataLogWall(data,SensorAngle)

%SensorAngle = [-20,80,-80,80,-80];

WallAdd = zeros(1,2);

    for k = 1:5
        switch k 
            case 1 
            Distance = data.sens.frontSensor(1);
            
            case 2
            Distance = data.sens.leftSensor(1);
           
            case 3 
            Distance = data.sens.rightSensor(1);
           
            case 4 
            Distance = data.sens.leftSideSensor(1);
            
            case 5 
            Distance = data.sens.rightSideSensor(1);
           
        end    
            
        [WallAdd(1),WallAdd(2)] =  trackController.rotation(Distance/100,0,-wrapToPi(SensorAngle(k).*pi/180+data.optitrack.theta(1)));
        data.wall(1,1,k) = data.optitrack.x(1) + WallAdd(1);
        data.wall(2,1,k) = data.optitrack.y(1) + WallAdd(2);  
    end

end

