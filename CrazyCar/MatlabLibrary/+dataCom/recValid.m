function status  = recValid(recV)
    if (length(recV) >= 42)   
        if (recV(1) == 'B' && recV(length(recV)) == 'E')
            status = 1;
        else
            status = 0;
        end
    else
        status = 0;
    end
end

