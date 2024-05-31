#ifndef SERVER_SOCKET_H
#define SERVER_SOCKET_H
////////////////////
// The Code is supposed to run on Windows and GNU Linux
// For Windows, one is supposed to install windows.h
// POSIX requires a lot of headers pertaining to INET and Threads
//
#ifdef WINDOWS_OS 
	#include <windows.h>
#else  // else POSIX
	#include <sys/socket.h>
	#include <netinet/in.h>
	#include <arpa/inet.h>
	#include <netdb.h>
	#include <pthread.h>  // Posix Threads
	#define DWORD unsigned long // For MS Compatibility
#endif

//-------------- C Headers
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>
//--------------- ANSI C++ Headers

#include <iostream>
#include <functional>
using namespace std;

////////////////////////////////



#ifdef WINDOWS_OS
///////////////////////////////
// CLIENT_DATA struct meant for Windows
// Socket Descriptor is of type "SOCKET" (int in POSIX )
// "ptr_to_instance" => A Hack to pass instance (this) to a static function
//
typedef struct{
 	SOCKET Sh; // Socket Handle which represents a Client
	SOCKET forward_port;  // In a non proxying mode, it will be -1
	char remote_addr[32];    //  Address of incoming endpoint
	void *ptr_to_instance;    //  Pointer to the instance to which this ClientDATA belongs
	char node_info[255];     //   Node Information
	int mode;                //   R/W mode - Read Or Write
	char ProtocolName[255];
}CLIENT_DATA; 

//---------------- Socket Descriptor for Windows 
//---------------- Listener Socket => Accepts Connection
//---------------- Incoming Socket is Socket Per Client

//----------------- Thread Entry Points for Listener and Thread Per Client
//DWORD WINAPI ListenThreadProc( LPVOID lpParameter);
//DWORD WINAPI ClientThreadProc( LPVOID lpParam   );
//--------------- Call WSACleanUP for resource de-allocation
void Cleanup();
//------------ Initialize WinSock Library
bool StartSocket();
//-----------------Get Last Socket Error
int SocketGetLastError();
//----------------- Close Socket
int CloseSocket( SOCKET s ); 



void InitializeLock(); 

void AcquireLock( ); 
 void ReleaseLock(); 

#else // POSIX
////////////////////////////////////
// CLIENT_DATA struct meant for POSIX
// Socket descriptor is an int ( #defined to SOCKET for uniformity)
// "ptr_to_instance" => Is a Hack to Pass instance variable to a thread
//
#define SOCKET int
typedef struct{
 	SOCKET Sh; // Socket Handle which represents a Client
	SOCKET forward_port;  // In a non proxying mode, it will be -1
	char remote_addr[32];    //  Address of incoming endpoint
	void *ptr_to_instance;    //  Pointer to the instance to which this ClientDATA belongs
	char node_info[255];     //   Node Information
	int mode;                //   R/W mode - Read Or Write
	char ProtocolName[255];  // Not Used now
}CLIENT_DATA; 


//int InComingSocket;
void Cleanup();
bool StartSocket();

int SocketGetLastError();
int CloseSocket( int s ); 
#define INVALID_SOCKET 	(-1)

//----- Not Used Anymore, Part of the CServerSocket class
//void* ListenThreadProc( void* lpParameter);
//void* ClientThreadProc( void* lpParam   );
//

#define SOCKET_ERROR 	(-1) 

//----------- To Handle Critical Section
// SemaPhore is an IPC mechanism
// IPC - Inter Proess Communication Mechanism
// SemaPhore, Mutex , Critical Section, Events, FIFO Queue etc
// Study about it...
void InitializeLock(); 
void AcquireLock( );
void ReleaseLock(); 

#endif
///////////////////////////////////////////////////
// Persistence - SQLite DB calls
// Integrations
// Model - C++ STL and composite entities
// DTO - Data Transfer Objects via C/C++ structs
// API
// Invocation
// Interface
//====================================
// The following DTO represents an EndPoint
//             - Needs tidying up
typedef struct{
	string ipaddress;  // ip address of the Remote Endpoint
	int port;               //  port at which Remote Listens
	int r_w;               //  Read Endpoint or Write EndPoint
	string alias;         //  unused
	float reserved;     //  unused
	char Buffer[255];   // unused 
}END_POINT;
//===================================
// The following struct represents an Endpoint and Proxies to 
// which it can map to
typedef struct {
	string key;   // ip address of the incoming request
	int port;       // port at which incoming request is being listened
	vector<END_POINT> proxies;  // potentially reachable clickhouse masters
}PROXY_ENTRY;
//----------------- This is a Structure which will be passed from Socket class to Listen Thread Routine
typedef struct{
     char node_info[255];  // Encoded Current Node Information as String
     int mode;        // R/W
     void *ptr_to_instance;  // Pointer to CServerSocket class 
}NODE_INFO;
////////////////////////////////////////////////////
// CServerSocket class 
//
class CServerSocket {
	private:
		//---- Default Protocol Port is 3500...can be changed
		int m_ProtocolPort = 3500;
		//---- Local Protocol Address to which we Bind
		struct sockaddr_in m_LocalAddress;
		//---- A Buffer to store the Protocol name .... "DEFAULT" is the default
		char Protocol[255];
	public:
		//---- Address of the Remote Connection which we accept
		struct sockaddr_in m_RemoteAddress{};
		//--- The Master Socket...
		SOCKET m_ListnerSocket=-1;
		//---- See the documentation of NODE_INFO above
		NODE_INFO info;
		//-------------------------------- Parametrize Thread Routine 
		//---- A Lambda Function - as part of Modern C++ ( C++ 11 and above )
		std::function<void* (void *)> thread_routine;
	
	#ifdef WINDOWS_OS
		//--- Master Thread Routine 
		static DWORD WINAPI ListenThreadProc(LPVOID lpParameter);
		//---- Thread Routine which is getting invoked per Client
		static DWORD WINAPI ClientThreadProc(LPVOID lpParam);
	#else
		//----- Master Thread Routine
		static void* ListenThreadProc(void* lpParameter);
		//----- Thread Routine which is getting invoked per Cleint
		static void* ClientThreadProc(void* lpParam);
	#endif
	//--------------------- Initialize Local Socket                 
	CServerSocket(int p_port,string protocol="DEFAULT");
	//---------------------------- Open Socket 
	bool Open(string node_info , std::function<void* (void *)> pthread_routine);
	//------------------- Start a Listening Thread
	bool StartListeningThread(string node_info, std::function<void* (void *)> pthread_routine); 
	///////////////////////////////////////
	//  Close The Server Socket
	//
	bool Close(); 
	//--- R/W Routine to/from Socket Descriptor
	bool Read( char *bfr ,  int size );
	bool Write( char *bfr , int size );

};

////////////////////////////////////////////////////
// A Helper class to Read/Write from/to a Socket Descriptor
// Written in a XPlatform manner

class ProtocolHelper {
	public:
		//------- Retrieve IP Address as ASCII string from endpoint data
		static string GetIPAddressAsString(struct sockaddr_in *client_addr);
		//-------- How much time one waits for read timeout 
		static bool SetReadTimeOut( SOCKET s, long second );
		//-------- Read from Socket, returns # of bytes read as last parameter
		static bool ReadSocketBuffer( SOCKET s, char *bfr ,  int size , int *num_read);
        //---- R/W From and To Socket Descriptor
		static bool ReadSocketBuffer( SOCKET s, char *bfr ,  int size );
		static bool WriteSocketBuffer( SOCKET s, char *bfr ,  int size );

};

#endif
