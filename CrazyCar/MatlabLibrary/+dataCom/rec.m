function recV = rec(port)
      
    recV = [];
    recV = judp.judp('receive',port,100000);
    recV = typecast(recV,'uint8');
    
end

