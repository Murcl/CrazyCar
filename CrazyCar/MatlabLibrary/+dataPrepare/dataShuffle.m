function output = dataShuffle(input1,output2)

files = dir(path); % 'train_data/*.mat'

indexOffset = 1; % Initial Offset

%inputNames = {'leftSensor','rightSensor','frontSensor'};

for file = files'
    load([file.folder '\' file.name]);
    names.sens = fieldnames(eval(structureName));
    internal_index = 1;
    for length = 1 : size(inputNames,2)%size(fieldnames(data.sens),1)
      [~,loc] = ismember(inputNames,fieldnames(eval(structureName))); 
        if loc(length) ~= 0 %ismember(inputNames,fieldnames(data.sens))
            eval(cell2mat(['tmp' num2str(length) '=' structureName '.' names.sens(loc(length)) '(1:data.length_actual)' ''';']));
            output(indexOffset:indexOffset+data.length_actual-1,internal_index) = eval(['tmp' num2str(length) '(1:data.length_actual)']);
            eval(['clear ' 'tmp' num2str(length) ';']);
            internal_index = internal_index + 1;
        else 
            string = cell2mat(['Variable: ' inputNames(length) ' is not member of structure']);
            disp(string)
            clear string;
        end
    end
    indexOffset = indexOffset + data.length_actual;
end
end 