function [steering,steeringSat,speed] = XboxGetValue(CCpad)

    try
        [axes, buttons] = read(CCpad);
        steering = floor((-1)*(axes(4)*80*1) + 100);
        speed = floor((-1)*(axes(2)*100 - 125));
        steeringSat = min(max(steering, 20), 180);
    catch
        %warning('Controller not connected.');
        steeringSat = 100;
        steering = 100;
        speed = 125;
    end
    
    
    
end

