function [steering,steeringSat,speed,ControlPosition] = TrackControl(data,Track ,LAD,kP,kTheta,direction)

ds = 1; %in mm
vs = 0; %cm/s
dt = ds/vs;

persistent initial;
persistent Index;
if isempty(initial)
        initial = 0;
end
if isempty(Index)
        Index = 0;
end

    data.direction = direction;

    
    wrapN = @(x, N) (1 + mod(x-1, N));

    if initial == 0
        range = 1:length(Track.x);
        initial = 1;
    else
        if direction == 1
            range =wrapN(Index-100:Index,length(Track.x));
        else
            range =wrapN(Index:Index+100,length(Track.x));
        end
    end
    if direction == 1
        xTrackActive = fliplr(Track.x(range));
        yTrackActive = fliplr(Track.y(range));
    else
        xTrackActive = Track.x(range);
        yTrackActive = Track.y(range); 
    end
    [c IndexRel] = min(sum(abs([xTrackActive; yTrackActive] - [data.optitrack.x(1); data.optitrack.y(1)])));
    if direction == 1
        Index = wrapN(Index - IndexRel + 1,length(Track.x));
        trackIndex = mod(round(Index*ds -LAD),length(Track.x));
    else
        Index = wrapN(Index + IndexRel - 1,length(Track.x));
        trackIndex = mod(round(Index*ds +LAD),length(Track.x));
    end

    
    
    if trackIndex == 0
        trackIndex = 1;
    end
    
%     trackIndexSpeed = mod(round(Index*ds +40),length(Track.x));
%     
%     if trackIndexSpeed == 0
%         trackIndexSpeed = 1;
%     end
    
    ex = (Track.x(trackIndex) - data.optitrack.x(1))*100;% + x_offset;
    ey = (Track.y(trackIndex) - data.optitrack.y(1))*100;% - y_offset;
%     plot(Track.x,Track.y);
%     hold on
%     plot(Track.x(trackIndex),Track.y(trackIndex),'o',data.optitrack.x(1),data.optitrack.y(1),'x');
    
    ControlPosition.x = Track.x(trackIndex)/100;
    ControlPosition.y = Track.y(trackIndex)/100;
    ControlPosition.ex = ex/100;
    ControlPosition.ey = ey/100;
    
    if direction == 1
        etheta = wrapToPi(Track.theta(trackIndex) - data.optitrack.theta(1) + pi);
    else
        etheta = wrapToPi(Track.theta(trackIndex) - data.optitrack.theta(1));
    end
    
    ControlPosition.theta = Track.theta(trackIndex);
    ControlPosition.etheta = etheta;

    [exl,eyl] = trackController.rotation(ex,ey,data.optitrack.theta(1));

    delta = trackController.Stanley(eyl,etheta,data.sens.speed(1),kP,kTheta);
        
    steering = floor(delta*50 + 100);
    steeringSat = min(max(steering, 20), 180);
    
    trackIndexSpeed = mod(round(Index*ds +LAD+40),length(Track.x));
    if trackIndexSpeed == 0
        trackIndexSpeed = 1;
    end
    if data.optitrack.isTracked(1) == 0
        speed = 0.5;
        warning('Object Lost')
    else
    speed = Track.speed(trackIndexSpeed);
    end
    
%     plot(Track.x,Track.y,data.optitrack.x(1),data.optitrack.y(1),'x','MarkerSize',12);
%     hold on
%     plot(Track.x(trackIndex),Track.y(trackIndex),'o');
%     hold off
%     pause(0.001);
    
end

