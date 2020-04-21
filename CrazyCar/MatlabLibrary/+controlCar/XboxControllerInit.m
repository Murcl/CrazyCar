function CCpad = XBoxControllerInit(noInput)
    try
        CCpad = vrjoystick(noInput);
    catch
        warning('Controller not connected.');
        CCpad = 0;
    end
end

