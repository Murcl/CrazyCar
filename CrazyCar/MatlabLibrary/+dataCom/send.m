function send(steer,throttle,ip,port)

    steer = min(max(steer, 0), 200);

    carcontrol = [num2str(steer,'%03d') ',' num2str(throttle,'%03d')];
    judp.judp('send',port,ip,int8(carcontrol));
        
end

