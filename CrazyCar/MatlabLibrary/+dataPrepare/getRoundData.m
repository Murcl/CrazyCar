function [in out] = getRoundData(in1,in2,start,step)

in = in1(start:start+step-1,:);
out = in2(start:start+step-1);

%figure()
%plot(clockTest)
%title('im Uhrzeigersinn')
%legend('Lenk Input')

end