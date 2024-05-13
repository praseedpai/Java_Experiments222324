REM 'Compile the Class file and generate native stub
REM =============================================
REM ' This was compiled at Visual C++ Command Prompt
REM ' We can use G++/Visual C++ or CLANG++ for compilation
REM '
javac -h . NativeClass.java

REM ==========================================
REM 'compile C/C++ code according to the stub geneated
REM 'generate Obj files....
REM ' in POSIX, we can use clang++ or g++ to generate .so
REM ' in one Go..
REM ' g++ -o NativeClass.so -shared -fPIC NativeClass.cpp
REM ' ( Coded from Memory )

cl /c NativeClass.cpp

REM ==========================================
REM generate DLL
REM ' We are generating DLL in Windows
REM In Posix, we generate link

link /out:NativeClass.dll /DLL NativeClass.obj

REM ====================================================
REM ' execute 

java NativeClass