///////////////
// file_content.cpp
// g++ -o file_content.exe file_content.cpp
// ./file_content.exe <txt_filename>

#include <stdio.h>
#include <string.h>

int main(int argc, char **argv )
{
     //---- Make sure that there are only two parameters 
     if ( argc != 2 ) {
             fprintf(stdout, "Usage: File not given as command line parameters\n");
             return -1;
     }

     char FileName[255];
     // argv[0] => Executable name
     // argv[1] => First Command Line Parameter
     strcpy(FileName, argv[1]);
     //---------- Open the file in the buffered mode
     FILE *fp = fopen(FileName, "rt"); // Open the file in the cooked mode

     if ( fp == 0 ) {
             fprintf(stdout, "File Could not be opened %s \n",FileName);
             return -2;
     }
     // ---- Now dump the content of the file
     char buffer[4096];
     while (!feof(fp)) {
                fgets( buffer, 4096,fp);
                printf("%s", buffer);
     }
     fclose(fp);
     return 0;
}