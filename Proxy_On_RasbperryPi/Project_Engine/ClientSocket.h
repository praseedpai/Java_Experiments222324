#ifndef CLIENT_SOCKET_H
#define CLIENT_SOCKET_H

#ifdef WINDOWS_OS
	#include <windows.h>
#else
	#include <sys/socket.h>
    #include <netinet/in.h>
    #include <arpa/inet.h>
	#include <netdb.h>
	#include <pthread.h>
#endif
	#include <stdio.h>
	#include <string.h>
	#include <stdlib.h>
	#include <ctype.h>
	#include <iostream>
	#include <fstream>
	#include <chrono>
	#include <thread>

using namespace std;


#ifdef WINDOWS_OS
	void Cleanup();  
	bool StartSocket();
	int SocketGetLastError();
#else
	#define SOCKET int
	void Cleanup();
	bool StartSocket();
	int SocketGetLastError();
	#define SOCKET_ERROR (-1) 
	int CloseSocket( int s );
	void Sleep(unsigned int microseconds);
#endif
/////////////////////////////////////////////////////
// Base Class for ClientSocket - contains most of the Logic
// for Socket API
//
//
class CClientSocket{

private:
	char  m_ServerName[255];
	int m_PortNumber;
	struct sockaddr_in m_Server; 
	struct hostent     *m_HostPointer; 
	unsigned int m_addr; 
	SOCKET m_ConnectSock; 
public:
	CClientSocket(const char *ServerName ,  int PortNumber); 
	SOCKET GetSocket(); 
	bool Resolve();   
	bool Connect();  
	bool Write( void *buffer , int len ) ;
	bool Receive( void *buffer , int* len );
	int Close();
}; 
#endif
