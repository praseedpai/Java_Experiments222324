#ifndef _PAYLOAD_DOT_H
#define _PAYLOAD_DOT_H

//----------------------- Packet Types for the Wire Level Protocol for the FILE transfer
enum  PacketType 
{ 
       CHISTA_NULL =-1, 
       //------------ Id of the HandShake Packet
       CHISTA_HAND_SHAKE=1,   // sends "hello"
 
       //---------------- Acknowledgement from the Server
       CHISTA_ACK=2,  // "olleh"
       //--------------- Data about File - Name, Size and other attributes
       CHISTA_META_DATA=3,  // sends file_name and size
       //------ The File Chunk ( a Block of Data )
       CHISTA_FILE_CONTENT=4, // sends chunks of 4096
       //------------- End of File Indicator
       CHISTA_META_EOF =5 ,   // sends the End of File Token
       //-------------- Indicates Fatal Error
       CHISTA_META_FATAL_ERROR = 6  // sends the Fatal Error message
};      

//-------------- HandShake Packet  ( PDU - Protocol Data Unit )
typedef struct{ int packet_type;  char hello_str[6];  char padding[2]; }T_HAND_SHAKE;

//------------ Acknowledgement Packet
typedef struct{  int packet_type;  char hello_str[6];  char padding[2];}T_ACK;

//--------------------- File Meta Data
typedef struct { int packet_type; char file_name[255]; long size; char padding[1];}T_FILE_META;

//----------------File Chunk ...does use a trick to store largeer buffer
typedef struct{int packet_type;  int packet_seq_num; int buffer_size; char buffer[1];}T_FILE_CHUNK;

//----------------- End of File String
typedef struct{int packet_type;  char eof_str[4] ; }T_FILE_EOF;

//----------------- Fatal Error 
typedef  struct { int packet_type; char err_msg[1020]; } T_FILE_FATAL_ERROR;

// Function for generating Packets
T_FILE_META MakeFileMeta( char *file_name ,  long size );
T_HAND_SHAKE  MakeHandShake( );
T_ACK  MakeAck( ) ;
T_FILE_EOF  MakeEof( );
T_FILE_CHUNK *  MakeBufferPack( void *buffer ,  int len , int );
T_FILE_FATAL_ERROR MakeErrorPack( char * buffer , int len );
//------------ Compute The file Size
long ComputeFileSize(char *file_name);



#endif
