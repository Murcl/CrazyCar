function [g,f,h] = drawInit(data,xlimit,ylimit)
    
    g = figure(313); 
    subplot(4,2,1)
    plot(data.sens.leftSensor);
    title('left Sensor');
    xlabel('Sample');
    ylabel('ADC Value');
    xlim([xlimit ylimit]);
    grid minor;
    
    subplot(4,2,2)
    plot(data.sens.rightSensor);
    title('right Sensor');
    xlabel('Sample');
    ylabel('ADC Value');
    xlim([xlimit ylimit]);
    grid minor;
    
    subplot(4,2,3)
    plot(data.sens.frontSensor);
    title('front Sensor');
    xlabel('Sample');
    ylabel('ADC Value');
    xlim([xlimit ylimit]);
    grid minor;
    

    subplot(4,2,4)
    plot(data.sens.leftSideSensor);
    title('leftSide Sensor');
    xlabel('Sample');
    ylabel('ADC Value');
    xlim([xlimit ylimit]);
    grid minor;
    
    subplot(4,2,5)
    plot(data.sens.rightSideSensor);
    title('rightSide Sensor');
    xlabel('Sample');
    ylabel('ADC Value');
    xlim([xlimit ylimit]);
    grid minor;
    
        subplot(4,2,6)
    plot(data.sens.vBatSensor);
    title('vBat Sensor');
    xlabel('Sample');
    ylabel('ADC Value');
    xlim([xlimit ylimit]);
    grid minor;
    
    subplot(4,2,7)
    plot(data.sens.speed);
    title('Speed Sensor');
    xlabel('Sample');
    ylabel('mm per second');
    xlim([xlimit ylimit]);
    grid minor;
    
    
    
    subplot(4,2,8)
    plot(data.sens.time);
    title('Time Stamp');
    xlabel('Sample');
    ylabel('uS/10');
    xlim([xlimit length(data.sens.time)]);
    grid minor;
    
    
    
    f = figure(314); 
    subplot(3,2,1)
    plot(data.sens.accelX);
    title('accelX');
    xlabel('Sample');
    ylabel('Value');
    xlim([xlimit ylimit]);
    grid minor;
    
    subplot(3,2,2)
    plot(data.sens.accelY);
    title('accelY');
    xlabel('Sample');
    ylabel('Value');
    xlim([xlimit ylimit]);
    grid minor;
    
    subplot(3,2,3)
    plot(data.sens.accelZ);
    title('accelZ');
    xlabel('Sample');
    ylabel('Value');
    xlim([xlimit ylimit]);
    grid minor;
    
    subplot(3,2,4)
    plot(data.sens.gyroX);
    title('gyroX');
    xlabel('Sample');
    ylabel('Value');
    xlim([xlimit ylimit]);
    grid minor;
    
    subplot(3,2,5)
    plot(data.sens.gyroY);
    title('gyroY');
    xlabel('Sample');
    ylabel('Value');
    xlim([xlimit ylimit]);
    grid minor;
    
    subplot(3,2,6)
    plot(data.sens.gyroZ);
    title('gyroZ');
    xlabel('Sample');
    ylabel('Value');
    xlim([xlimit ylimit]);
    grid minor;
    
    h = figure(315) ;
    plot(data.sens.accelX(1:10),data.sens.accelY(1:10));
    title('XYZ Plot');
    xlabel('Sample');
    ylabel('Value');
    xlim([-1 1]);
    ylim([-1 1]);
    grid minor;     
    
    drawnow;
end

