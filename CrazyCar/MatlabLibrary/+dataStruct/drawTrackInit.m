function [g] = drawTrackInit(data,xlimit,ylimit)
    
    g = figure(789); 

    for k = 1 : 5
 %   plot(data.wall(1,:,k),data.wall(2,:,k));
    plot(data.wall(1,:,k),data.wall(2,:,k),'XDataSource','data.wall(1,:,k)','YDataSource','data.wall(2,:,k)')
    hold on
    end
    %linkdata on
    
    title('MAP');
    xlabel('x in m');
    ylabel('y in m');
    xlim(xlimit);
    ylim(ylimit);
    grid minor;  
    
    drawnow;
end

