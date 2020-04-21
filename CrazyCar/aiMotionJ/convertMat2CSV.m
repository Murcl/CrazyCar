prefix = '';
path = '../aiMotionExp1-2019_03_20/CrazyCAR/';


files = dir([path prefix '*.mat']);
for i=1:length(files)
    folder = files(i).folder;
    fname = files(i).name;
    load([folder '/' fname], 'rbData');
    newfile = strrep(fname, '.mat', '.csv');
    fid = fopen([folder '/' newfile], 'w');
    for n=1:length(rbData.TimeStampMat)
        q=cell2mat(rbData.Quaternion(n));
        str = sprintf('%d,%0.4f,%0.4f,%0.4f,%0.8f,%0.8f,%0.8f,%0.8f,%0.8f\n', ...
            uint64(rbData.TimeStampMat(n)*1000),...
            rbData.Position(n, 3), ...
            rbData.Position(n, 1), ...
            rbData.Position(n, 2), ...
            cell2mat(rbData.angle(n)),...
            q(1), q(2), q(3), q(4));                                
        fprintf(fid, str);
    end            
    fclose(fid);
end
