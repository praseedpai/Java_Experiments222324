// Server.cpp : Defines the entry point for the console application.
// under POSIX
// g++ -o Mserver.exe Mserver.cpp -lpthread
// ./Mserver.exe <port>
// under Windows
// cl /DWINDOWS_OS MServer.cpp ws2_32.lib
// Mserver <port>
//

#include <windows.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>
#include <iostream>
using namespace std;
#include "payload.h"
////////////////////////////////

bool StartSocket(); //--- Initialize Socket Engine ..No code inside the Posix


typedef struct{
 	SOCKET Sh; // Socket Handle which represents a Client
}CLIENT_DATA; 
//---------------- Socket Descriptor for Windows 
//---------------- Listener Socket => Accepts Connection
//---------------- Incoming Socket is Socket Per Client

SOCKET InComingSocket;
//----------------- Thread Entry Points for Listener and Thread Per Client
DWORD WINAPI ListenThreadProc( LPVOID lpParameter);
DWORD WINAPI ClientThreadProc( LPVOID lpParam   );
//--------------- Call WSACleanUP for resource de-allocation
void Cleanup() {
      WSACleanup();
} 
//------------ Initialize WinSock Library
bool StartSocket()
{
  WORD Ver;
  WSADATA wsd;
  Ver = MAKEWORD( 2, 2 );
  if (WSAStartup(Ver,&wsd) == SOCKET_ERROR) 
  {
	WSACleanup();
	return false;
  }
  return true;
}
//-----------------Get Last Socket Error
int SocketGetLastError(){ return  WSAGetLastError();}
//----------------- Close Socket
int CloseSocket( SOCKET s ) { closesocket(s); return 0;}

/* This is the critical section object (statically allocated). */
CRITICAL_SECTION m_CriticalSection;

void InitializeLock() {
	InitializeCriticalSection(&m_CriticalSection);
}
	
void AcquireLock( ) {
                 EnterCriticalSection(&m_CriticalSection);
}
 void ReleaseLock() {
    	LeaveCriticalSection( &m_CriticalSection );
}

#include "Server.h"
 DWORD  CServerSocket::Thid =0;
 struct sockaddr_in CServerSocket::m_RemoteAddress{};
 SOCKET CServerSocket::m_ListnerSocket = -1;

/*
/////////////////////////////
//
//
//
class CServerSocket {
    int m_ProtocolPort = 3500; 
    struct sockaddr_in m_LocalAddress;
    struct sockaddr_in m_RemoteAddress;
    char   m_Buffer[1024];
    SOCKET m_ListnerSocket;
    static DWORD  Thid;
    static DWORD WINAPI ListenThreadProc( LPVOID lpParameter);
    static DWORD WINAPI CServerSocket::ClientThreadProc(LPVOID lpParam);
public:
    CServerSocket(int p_port) {
            m_ProtocolPort =  p_port; 
            m_LocalAddress.sin_family = AF_INET;
            m_LocalAddress.sin_addr.s_addr = INADDR_ANY;
            m_LocalAddress.sin_port = htons(m_ProtocolPort);
    }

    bool Open() {

	if ( ( m_ListnerSocket = socket(AF_INET, SOCK_STREAM,0)) ==
		 INVALID_SOCKET ) 
	{
    		return false;
	}
                  printf("%s\n", "About to Bind.. ................ ");
	if (bind(m_ListnerSocket,(struct sockaddr*)&m_LocalAddress,
		sizeof(m_LocalAddress) ) 
		== SOCKET_ERROR) {return false;}
                  printf("%s\n", "Listen. ................ ");
	if (listen(m_ListnerSocket,5) == SOCKET_ERROR) { return false; }
                  StartListeningThread();
                 return true;
    }

    bool StartListeningThread() {
                  printf("%s\n", "Creating a  Windows Thread....... for Listener\n");
	CreateThread(NULL,0,CServerSocket::ListenThreadProc,NULL,
		0,&Thid);
                  return true;
   }
///////////////////////////////////////
//
//
   bool Close() {
            closesocket(m_ListnerSocket); return false;
   }
}; */


DWORD WINAPI CServerSocket::ListenThreadProc(  LPVOID lpParameter){

 printf("Entered the Listener Thread....\n");
 while (1){
	unsigned int Len = sizeof( CServerSocket::m_RemoteAddress );
	InComingSocket = accept(CServerSocket::m_ListnerSocket,
	(struct sockaddr *)&CServerSocket::m_RemoteAddress, (int *)&Len);
                  printf("....................After the Accept........");
	if (InComingSocket == INVALID_SOCKET) 
	{
		fprintf(stderr,"accept error %d\n",SocketGetLastError());
		Cleanup();
		return 0;
	}
                 printf("\n....................Accepted a new Connection........\n");
	CLIENT_DATA ClientData;
	DWORD ThreadId;
	ClientData.Sh = InComingSocket;
	::CreateThread(NULL,0,CServerSocket::ClientThreadProc,(LPVOID)&ClientData,
		0,&ThreadId);
              
     }

 return 0;
}

bool ReadSocketBuffer( SOCKET s, char *bfr ,  int size )
{
                    int RetVal
		 = recv(s ,bfr,size,0);
	 if ( RetVal == 0 || RetVal == -1) 
		 return false;
                   return true;

}

bool SendAcknowledgement(SOCKET s ) {

        T_ACK ack = MakeAck();
         int bytes_send = send( s, (char *)&ack, sizeof(ack),0);
        return bytes_send > 0 ;
}

bool SendEOF(SOCKET s ) {

        T_FILE_EOF eof = MakeEof( );
         int bytes_send = send( s, (char *)&eof, sizeof(eof),0);
        return bytes_send > 0 ;
}

DWORD WINAPI CServerSocket::ClientThreadProc(
  LPVOID lpParam   
){

 
 CLIENT_DATA CData;
 memcpy(&CData,lpParam,sizeof(CLIENT_DATA));
 char bfr[32000];


	 memset(bfr,0,32000);
	 int RetVal
		 = recv(CData.Sh ,bfr,sizeof(bfr),0);
	 if ( RetVal == 0 || RetVal == -1) 
		 return 0; 

	 int packet_type = * ( ( int *) bfr );
                   printf("%d\n", packet_type );
                   if ( packet_type != 1 )  {   cout << "could not receive the right packet" << endl; return 0; }
                   T_ACK ack = MakeAck();
                   int bytes_send = send( CData.Sh, (char *)&ack, sizeof(ack),0);
                   cout << "Finished Sending the Acknowledgement ... bytes send= " <<  bytes_send << endl;
                  
                   if ( !ReadSocketBuffer(CData.Sh, bfr, sizeof(bfr) ) ) {
                              cout << "Failed to Read From Socket........" << "Exiting the App" << endl;
                              return 0;
                   }
                   if ( *((int *) bfr)  != 3 ) { cout << "Expected Meta Data .... " << endl; return 0; }
                    T_FILE_META  file_meta_data;
                    memcpy(&file_meta_data, bfr, sizeof(file_meta_data));
                    cout << " file name = " << file_meta_data.file_name << " size = " << file_meta_data.size ;
                    SendAcknowledgement(CData.Sh);
                    cout << "finished .....sending acknowledgement....." << "transferring files" << endl;
                    cout << "Waiting for the content " << endl;
                    FILE *fp = fopen("DEST_WIRE.out" , "wb" );
                   char buffer_chunk[32000];
                    int transferred_size = 0;
                    while (1) {
                             cout << "Transferred = " << transferred_size << endl;
                             ReadSocketBuffer(CData.Sh, buffer_chunk, sizeof(buffer_chunk));
                             cout << "Packet Type = " << * (int *) buffer_chunk << endl;
                             
                             if ( *(int *) buffer_chunk  == 5 ) {
                                                 cout << "End of File Received" << endl; 
                                                 fclose(fp); 
                                                 SendAcknowledgement(CData.Sh); 
                                                 if ( transferred_size != file_meta_data.size ) {
                                                                 cout << "Expected Size = " << file_meta_data.size << endl;
                                                                 cout << "Received Size = " << transferred_size << endl;
                                                 }
                                                 return 0; 
                             }
                             if  (*(int *) buffer_chunk  != 4 ) {
                                          cout << " I do not know what happens here" << endl;
                                          cout  << "What kind of packet = " << *(int *)buffer_chunk << endl;
                                          fclose(fp);
                                          return 0;
                             }
                           
                             T_FILE_CHUNK *chunk = (T_FILE_CHUNK *) buffer_chunk;
                              char temp_mem[32000];
                              cout << "Received Block ...... " << chunk->buffer_size << endl;
                              memcpy(temp_mem, chunk->buffer,chunk->buffer_size);
                              fwrite(temp_mem, 1, chunk->buffer_size,fp);
                              transferred_size += chunk->buffer_size;   

                    }
                    
             


  return 0;
}
int main(int argc, char* argv[])
{


	if ( argc != 2 ){
      		fprintf(stdout,"Usage: Server <portid> \n");  
     		 return 1;
	}
	if ( !StartSocket() )
	{
     		 fprintf(stdout,"Failed To Initialize Socket Library\n");
	  	return 1;
	}

    	CServerSocket sock{ atoi(argv[1]) };          
                  sock.Open();
	
                 while (1) ;
   	Cleanup();
	return 0;
}


/////////////////////////////////
//
// Initialise Winsock Libraries 
// 
//
//




/////////////////////////////////////
//
//
//
//
//
//

