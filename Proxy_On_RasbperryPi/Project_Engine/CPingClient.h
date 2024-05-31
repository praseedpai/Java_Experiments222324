#ifndef  PING_CLIENT_DOT_H
#define PING_CLIENT_DOT_H
#include "ClientSocket.h"
//---------- A Simple Ping Client with CClientSocket
class CPing {
	private:
		CClientSocket *m_socket=0;
		string remote_ip="localhost";
		int remote_port = -1;
	public:
		CPing( string r_ip, int r_port ) : remote_ip(r_ip),remote_port(r_port)  {}
		bool Open() ;
		bool SendSignal( void *Buffer , int length );
		bool Close();
		~CPing(); 
};

#endif
