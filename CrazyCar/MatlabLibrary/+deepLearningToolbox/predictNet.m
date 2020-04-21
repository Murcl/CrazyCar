function steering = predictNet(data,Net)
    input = [data.sens.frontSensor(1),data.sens.leftSensor(1),data.sens.rightSensor(1),data.sens.leftSideSensor(1),data.sens.rightSideSensor(1)];
    steering = predict(Net,num2cell(input',1));
end

