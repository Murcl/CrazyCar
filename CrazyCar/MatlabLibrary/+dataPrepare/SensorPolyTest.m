%%
clc
neuralInput = dataPrepare.dataImport('../../Optitrack_Data','data.sens',{'frontSensor','leftSensor','rightSensor','leftSideSensor','rightSideSensor'});

Front = dataPrepare.distanceCalcFront(neuralInput(:,1))

Side = dataPrepare.distanceCalcSide(neuralInput(:,2:5))

Output = dataPrepare.dataImport('../../Optitrack_Data','data.act',{'steer'});
