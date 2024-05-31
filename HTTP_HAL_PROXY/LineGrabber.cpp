///////////////////////////////
// LineGrabber.cpp
//
// A Simple Program to dump the content of HTTP verbs ( GET, POST, PUT, DELETE )
// 
//  uses C++ STL (Standard Template Library), part of the Standard C++ Library
//
//  g++ -o test.exe LineGrabber.cpp
//  ./test.exe GET.txt
//  ./test.exe POST.txt
//

#include <stdio.h>
#include <string.h>
#include <iostream>
#include <map>

//---- include the Standard Name space
using namespace std;

/////////////////////////////
//
// Load the Content of a Text File to a Memory Buffer
//  
// char *filename - File name
// char *readbuffer - Buffer to which contents ought to be read ( passed by pointer )
// int size ( max size )
// int *read_size ( passed by pointer to retreive the actual bytes read )
//
bool LoadFromFile( char *filename , char *readbuffer,int size, int *read_size )
{
             memset(readbuffer,0,size);
             FILE * fp = fopen(filename,"rb");
             if ( fp == 0 ) { return false; }
             *read_size = fread(readbuffer, 1, size,fp );
             fclose(fp);
              return true;
}

/////////////////////////////////
// Chop a line to Key, Value ( aka  Pair )
//  The string should be in "Key:Value" format
//   
//
pair<string,string>   ChopLine( string str ) {
              char buffer[4096];
              strcpy(buffer,str.c_str());
              char * ptr = buffer;
              string key;
              string value;
              while ( *ptr != 0 && *ptr != ':' ) { ptr++; }
              if ( *ptr == 0 ) { return pair<string,string>("",""); }
               *ptr++=0;
             key = string(buffer);
             value = string(ptr);
             return pair<string,string>(key,value);
}

////////////////////////////////////////////
// A Class to Grab Lines from a text file
//
class LineGrabber
{
          char *m_buffer;
          int m_index;
          int m_size;
     public:
          LineGrabber( char *pbuffer, int size ):m_buffer(pbuffer),m_size(size),m_index(0) {}
          bool IsEof() { return m_index >= m_size; }
          string  get_next_line() {
                     char buffer[1024];
                     memset(buffer,0,1024);
                     char *ptr = buffer;
                     if ( m_index >= m_size ) { return string(""); }
                     while ( m_index < m_size ) {
                                  if ( m_buffer[m_index] != 13   && m_buffer[m_index] != 10 )
                                             *ptr++ = m_buffer[m_index++];
                                  else {
                                              if ( m_buffer[m_index] == 13 ) { m_index +=2; break; }
                                              else if ( m_buffer[m_index] == 10 ) { m_index++; break; }
                                  }
                     }
                     return string(buffer);
          }
};

///////////////////////////////////
// User EntryPoint
//
int main( int argc , char **argv )
{
         if ( argc != 2 ) {
                  cout << "Usage: HTTP Parse <filename> " << endl;
                  return -1;
        }
        char *filename = argv[1];
       char buffer[32000];
       int num_read;
       if ( !LoadFromFile(filename, buffer, 32000 , &num_read) )
       {
                  cout << " Failed to Load HTTP PayLoad " << endl;
                  return -2;
        }
        LineGrabber  ln(buffer,num_read);
        string first_line = ln.get_next_line();
        char first_line2[1024];
        strcpy( first_line2 , first_line.c_str() );
        char method[sizeof (buffer)];
        char url[sizeof (buffer)];
        char protocol[sizeof (buffer)];
        sscanf (buffer, "%s %s %s", method, url, protocol);
        cout << "HTTP Method =  " << method << endl;
        cout << "URL =   "  << url <<endl;
        cout << "Protocol = " <<  protocol << endl;
        cout << "============================= Dump Dictionary" << endl;
        //------------------ Dump the dictionary ----------------------------
        map<string, string> header_map;
        while ( !ln.IsEof()) {
                 string test = ln.get_next_line() ;
                 if ( strlen(test.c_str()) == 0 ) { break; }
                 pair<string,string> temp = ChopLine(test);
                 header_map.insert(temp);
                 cout << "Key : " << temp.first << "  Value :  " << temp.second << endl;
         }
         cout << "================================ Print Body " << endl ;
         while ( !ln.IsEof()) {
                 cout << ln.get_next_line()  << endl;
         }
         return 0;
}

// EOF