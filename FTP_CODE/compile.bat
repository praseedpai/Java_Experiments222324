REM ========================================
REM compile the File Transfer Client Program
REM .....

cl /EHsc wire.cpp Payload.cpp user32.lib ws2_32.lib

REM =======================================
REM Compile the Server Code... 

cl /EHsc Server.cpp Payload.cpp user32.lib ws2_32.lib