function draw(data,g,f,h,periodic)

    if mod(data.lengthActual,periodic) == 0
        set(g.Children(8).Children,'YData',data.sens.leftSensor);
        set(g.Children(7).Children,'YData',data.sens.rightSensor);
        set(g.Children(6).Children,'YData',data.sens.frontSensor);

        set(g.Children(5).Children,'YData',data.sens.leftSideSensor);
        set(g.Children(4).Children,'YData',data.sens.rightSideSensor);

        set(g.Children(3).Children,'YData',data.sens.vBatSensor);
        set(g.Children(2).Children,'YData',data.sens.speed);
        set(g.Children(1).Children,'YData',data.sens.time)

        set(f.Children(6).Children,'YData',data.sens.accelX)
        set(f.Children(5).Children,'YData',data.sens.accelY)
        set(f.Children(4).Children,'YData',data.sens.accelZ)

        set(f.Children(3).Children,'YData',data.sens.gyroX)
        set(f.Children(2).Children,'YData',data.sens.gyroY)
        set(f.Children(1).Children,'YData',data.sens.gyroZ)

        set(h.Children(1).Children,'XData',data.sens.accelY(1:10))
        set(h.Children(1).Children,'YData',data.sens.accelX(1:10))
        drawnow;
    end

end

