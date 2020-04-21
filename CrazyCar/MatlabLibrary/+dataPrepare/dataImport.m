function output = dataImport(path,structureName,inputNames)



indexOffset = 1; % Initial Offset

%inputNames = {'leftSensor','rightSensor','frontSensor'};
D = dir(path);
    for k = 3 : length(D)
        tmppath = [path '/' D(k).name];
        files = dir([tmppath '/*.mat']); % 'train_data/*.mat'
        for file = files'
            load([file.folder '\' file.name]);
            names.sens = fieldnames(eval(structureName));
            internal_index = 1;
            for tmplength = 1 : size(inputNames,2)%size(fieldnames(data.sens),1)
              [~,loc] = ismember(inputNames,fieldnames(eval(structureName))); 
                if loc(tmplength) ~= 0 %ismember(inputNames,fieldnames(data.sens))
                    eval(cell2mat(['tmp' num2str(tmplength) '=' 'fliplr(' structureName '.' names.sens(loc(tmplength)) '(1:data.lengthActual)' ')' ''';']));
                    output(indexOffset:indexOffset+data.lengthActual-1,internal_index) = eval(['tmp' num2str(tmplength) '(1:data.lengthActual)']);
                    eval(['clear ' 'tmp' num2str(tmplength) ';']);
                    internal_index = internal_index + 1;
                else 
                    string = cell2mat(['Variable: ' inputNames(tmplength) ' is not member of structure']);
                    disp(string)
                    clear string;
                end
            end
            indexOffset = indexOffset + data.lengthActual;
        end
    end
end 