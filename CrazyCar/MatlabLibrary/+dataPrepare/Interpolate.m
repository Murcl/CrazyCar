function output = Interpolate(input,distance)

maxLength = (distance(length(input)) - distance(1) ) *100

t = round(linspace(0,length(input),length(input))); 
x = input(1:length(input),:); 
ti = linspace(0,length(input),maxLength);
output = interp1(t,x,ti,'linear');

end

