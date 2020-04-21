function [data] = dataLogPosition(data,obj,controlPosition,direction)

if  isempty(controlPosition)
    data.trackController.x(1) = data.trackController.x(2);
    data.trackController.y(1) = data.trackController.y(2);
    data.trackController.theta(1) = data.trackController.theta(2);
    data.trackController.ex(1) = data.trackController.ex(2);
    data.trackController.ey(1) = data.trackController.ey(2);
    data.trackController.etheta(1) = data.trackController.etheta(2);
else
    data.trackController.x(1) = controlPosition.x;
    data.trackController.y(1) = controlPosition.y;
    data.trackController.theta(1) = controlPosition.theta;
    data.trackController.ex(1) = controlPosition.ex;
    data.trackController.ey(1) = controlPosition.ey;
    data.trackController.etheta(1) = controlPosition.etheta;
end
data.direction = direction;

if obj == 0
else
    [data.optitrack.y(1),data.optitrack.z(1),data.optitrack.x(1),data.optitrack.theta(1),data.optitrack.isTracked(1)] = ...
    getRigidPositon(obj,1,data.optitrack.y(2),data.optitrack.z(2),data.optitrack.x(2),data.optitrack.theta(2));

%     data.optitrack.x(1) = data.optitrack.x(1) / 10
%     data.optitrack.y(1) = data.optitrack.y(1) / 10
%     data.optitrack.z(1) = data.optitrack.z(1) / 10
end

end

