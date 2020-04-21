function data = dataLogAct(data , steer, steerSat, throotle)

    data.act.steer(1) = steer;
    data.act.steerSat(1) = steerSat;
    data.act.throttle(1) = throotle;
        
end

