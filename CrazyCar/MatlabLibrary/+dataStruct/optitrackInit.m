function obj = optitrackInit()
    obj = OptiTrack;
    obj.Initialize;
    
    if isempty(obj.FrameRate)
        obj = 0;
    end
end

