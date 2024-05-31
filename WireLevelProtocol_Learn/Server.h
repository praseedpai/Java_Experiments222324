#ifndef _SERVER_DOT_H
#define _SERVER_DOT_H

 
class CServerSocket {
    int m_ProtocolPort = 3500; 
    struct sockaddr_in m_LocalAddress;
     public:
    static struct sockaddr_in m_RemoteAddress;
    char   m_Buffer[1024];
    static SOCKET m_ListnerSocket;
    static DWORD  Thid;
   static DWORD WINAPI ListenThreadProc( LPVOID lpParameter);
 static DWORD WINAPI ClientThreadProc(LPVOID lpParam);

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
	CreateThread(NULL,0,ListenThreadProc,NULL,
		0,&Thid);
                  return true;
   }
///////////////////////////////////////
//
//
   bool Close() {
            closesocket(m_ListnerSocket); return false;
   }
};




#endif
