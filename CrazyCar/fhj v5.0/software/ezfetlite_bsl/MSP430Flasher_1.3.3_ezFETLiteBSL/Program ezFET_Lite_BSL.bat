@echo off

:BEGIN

CLS
MSP430Flasher.exe -m SBW2 -n MSP430F5528 -w "EZFET_LITE_Rev1_1_BSL_1_1.txt" -v -b -e ERASE_ALL -g -z[VCC] 
MSP430Flasher.exe -r [ezFETLite_BSL_Log.txt,BSL]
pause

goto BEGIN