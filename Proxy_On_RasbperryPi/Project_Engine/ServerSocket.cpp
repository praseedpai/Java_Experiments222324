//ServerSocket.cpp - The Implementation of ServerSocket class
//
//https://github.com/eminfedar/async-sockets-cpp
//
//

#include "ServerSocket.h"


#ifdef WINDOWS_OS

//--------------- Call WSACleanUP for resource de-allocation
void Cleanup() {
	WSACleanup();
} 
//------------ Initialize WinSock Library
//--- only necessary for Windows
bool StartSocket(){
	WORD Ver;
	WSADATA wsd;
	Ver = MAKEWORD( 2, 2 );
	if (WSAStartup(Ver,&wsd) == SOCKET_ERROR) {
		WSACleanup();
		return false;
	}
  	return true;
}
//-----------------Get Last Socket Error
int SocketGetLastError(){ return  WSAGetLastError();}
//----------------- Close Socket
int CloseSocket( SOCKET s ) { closesocket(s); return 0;}
/////////////////////////////////////////////////////////
//---This is the critical section object (statically allocated)
//--- in POSIX , we use Mutex ( CS is slightly faster )
//
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

#else // POSIX

//--- CleanUp and Start Socket are stubbed here.. it is only useful
//--- in MS Windows
void Cleanup() {} 
bool StartSocket(){return true;}

//--- An Emulator for WSAGetLastError ... a stub for GNU Linux
int SocketGetLastError(){ return  0xFFFF;}
int CloseSocket( int s ) {shutdown (s, 2); return 0;}

//////////////////////////////////////////////////////
// This is the critical section object (statically allocated)
// We are using a Mutex in GNU Linux
static pthread_mutex_t cs_mutex =  PTHREAD_RECURSIVE_MUTEX_INITIALIZER_NP;
void InitializeLock() {}
void AcquireLock( ) {
	//------ Enter the critical section -- other threads are locked out */
    pthread_mutex_lock( &cs_mutex );
}
void ReleaseLock() {
    //Leave the critical section -- other threads can now pthread_mutex_lock() 
    pthread_mutex_unlock( &cs_mutex );
}

#endif
/////////////////////////////////////////
// CTOR for CServerSocket
//--------------------- Initialize Local Socket                 
CServerSocket::CServerSocket(int p_port,string protocol):m_ProtocolPort(p_port){
	//--- AF_INET - Address Family Internet
	//--- Can be AF_UNIX for Unix Domain Socket
	m_LocalAddress.sin_family = AF_INET;
	//--- The Local IP details will be initialized, if given INADDR_ANY
	m_LocalAddress.sin_addr.s_addr = INADDR_ANY;
	//--- Port # should be in Network Byte Order (Big Endian)
	//--- We are using x86 (Little Endian ) to write this
	m_LocalAddress.sin_port = htons(m_ProtocolPort);
	//----- Copy the Protocol Name
	strcpy(Protocol, protocol.c_str());
}
//////////////////////////////////////////////////////
// After Constructing the Object, a Client Programmer is supposed to
// Call Open Method with a Lambda Function. The Lambda Function should contain
// the Logic which is supposed to execute in the Client Thread
//
//---------------------------- Open Socket 
bool CServerSocket::Open(string node_info , std::function<void* (void *)> pthread_routine) {
	///////////////////////////////////////////////////////////////////
	// Fetch Socket Descriptor, Bind , Listen and Start Listening Thread
	if ((m_ListnerSocket = socket(AF_INET, SOCK_STREAM, 0)) ==INVALID_SOCKET) {
		cout << "Failed to create Socket Descriptor " << endl;
		return false;
	}
	//--- Bind the Socket Descriptor and Local Address 
	if (bind(m_ListnerSocket, (struct sockaddr*)&m_LocalAddress,sizeof(m_LocalAddress))
			== SOCKET_ERROR) 
	{  
		cout << "Failed to Bind" << endl;
		return false;
	}
	//--- Listen function, we need to specify # of concurrent connection accepted (2nd parameter)
	if (listen(m_ListnerSocket, 5) == SOCKET_ERROR) { 
		cout << "Listening Socket Failed.. ...." << endl;
		return false; 
	}
	/////////////////////////////////////////////////////
	//--- Start the Listening thread ...the second parameter is the Client Thread Routine 
	//--- Passed as Lambda Function
	return StartListeningThread(node_info, pthread_routine);
}
////////////////////////////////////////////////////////////////
// in Socket API, Listening or Master Thread accepts connections
// Listening happens in a master thread..
//
//------------------- Start a Listening Thread
bool CServerSocket::StartListeningThread(string node_info, 
	std::function<void* (void *)> pthread_routine){
		//------- Copy the String given as first parameter to the Open
		//---- At present, not used...can be of use to pass hints
		strcpy(info.node_info, node_info.c_str());
		cout << "\nFirst Thread  =>" << info.node_info << endl;
		//--- Not of much consequence in most applications
		//--- Defaults to 1
		info.mode = 1;
		//-------- Assign Lambda Address to the thread_routine of the 
		//-------- Of the current object
		this->thread_routine = pthread_routine;
		//-------- The Thread routine is a static function. We are using a
		//---- hack here to pass the this to the function as a parameter to
		//---- the thread routine
		info.ptr_to_instance = (void *) this;
		if ( info.ptr_to_instance == 0 ) { return false; }
		////////////////////////////////////////////////////////////////////
		// We are starting Listener ( or Master Thread ) Thread to Listen to 
		// the incoming connection. We are using #ifdef for Windows and Linux
		// variability
		//
		#ifdef WINDOWS_OS
			DWORD  Thid;
        	//--- in Windows, we call Win64 CreateThread API		
			CreateThread(NULL,0,CServerSocket::ListenThreadProc,(void *)&info,0,&Thid);
		#else
			pthread_t thread1;
			int  iret1;
			//----- We are using pthread_create ( POSIX thread Create ) for 
			//----- Master thread
            iret1 = pthread_create( &thread1, NULL, CServerSocket::ListenThreadProc, (void*) &info);
		#endif 
		cout << "Started First Listening Thread " << endl;
		return true;
}
/////////////////////////////////////////////////////////////
// ListenThreadProc or MasterThreadRoutine. In Socket API, the logic within
// The master thread is more or less same across the world..
//
//
//
#ifdef WINDOWS_OS 
	//--- EntryPoint for Windows OS
	DWORD WINAPI CServerSocket::ListenThreadProc(LPVOID lpParameter)
#else
	//--- FOR POSIX API
	void * CServerSocket:: ListenThreadProc(void * lpParameter)
#endif
{
	
	printf("Entered the Listener Thread....\n");
	//--- Copy the Parameter to NODE_INFO

	NODE_INFO info;
	memcpy(&info, lpParameter, sizeof(NODE_INFO));

	//---- Recover the Current instance of the Object
	//--- In a Static method, we won't get instance data
	//--- For Gaining instance data, we are resorting to Hack

	CServerSocket *curr_instance = (CServerSocket *)(info.ptr_to_instance);
	cout << "node info => " << "string(info.node_info)" << endl;

	if ( curr_instance == 0 ) { cout <<"Failed to Retrieve Pointers" << endl; return 0; }
	//--- Get into the infinite loop
	while(1){
		cout << "................" <<endl;
		unsigned int Len = sizeof( curr_instance->m_RemoteAddress );
		//-- There is a slight diferrence in parameter types between
		//-- Windows and POSIX, thus the conditional compilation
		#ifdef WINDOWS_OS
			SOCKET InComingSocket = accept(curr_instance->m_ListnerSocket,
				(struct sockaddr*)&(curr_instance->m_RemoteAddress), (int *)&Len);
        #else
			SOCKET InComingSocket = accept(curr_instance->m_ListnerSocket,
				(struct sockaddr*)&(curr_instance->m_RemoteAddress),(socklen_t *) &Len);
		#endif
		printf("\n....................After the Accept........\n");
		if (InComingSocket == INVALID_SOCKET) {
			fprintf(stderr,"accept error %d\n",SocketGetLastError());
			Cleanup();
			return 0;
		}
		printf("\n....................Accepted a new Connection........\n");
		/////////////////////////////////////
		// Initialize the CLIENT_DATA data structure
		//
		CLIENT_DATA ClientData;
		
		ClientData.Sh = InComingSocket;
		memcpy(ClientData.node_info, info.node_info,255);
		cout << "B4 callint Client Threaed => " << "ClientData.node_info" << endl;
		ClientData.mode = info.mode;
		//--- Retrieve the IP address as a ASCII string
		string remote_ip = ProtocolHelper::GetIPAddressAsString( &(curr_instance->m_RemoteAddress));
		strcpy(ClientData.remote_addr,remote_ip.c_str());
		cout << "Remote IP address == " << remote_ip << endl;
		//--- Pass Pointer to Instance to ClientThread as a Parameter (a Hack!)
		ClientData.ptr_to_instance = curr_instance;
		#ifdef WINDOWS_OS
			DWORD ThreadId;
			::CreateThread(NULL,0,CServerSocket::ClientThreadProc,(LPVOID)&ClientData,
					0,&ThreadId);
		#else 
			pthread_t thread2;
			pthread_create( &thread2, NULL, CServerSocket::ClientThreadProc, (void*) &ClientData);
		#endif
	} // End => While(1)
	return 0;
} //EndOfFunction => ListenThreadPRoc

//////////////////////////////////////////////////////
// ClientThreadProc - Does not do much..It just schedules
//
//
#ifdef WINDOWS_OS
	DWORD WINAPI CServerSocket:: ClientThreadProc(LPVOID lpParam)
#else
	void * CServerSocket::ClientThreadProc(void * lpParam)
#endif
{
	CLIENT_DATA CData;
 	memcpy(&CData,lpParam,sizeof(CLIENT_DATA));
	CServerSocket *exec = (CServerSocket *)CData.ptr_to_instance;
	if ( exec == nullptr ) { return 0; }
 	exec->thread_routine(lpParam);
  	return 0;
}
///////////////////////////////////////
//  Close The Server Socket
//
bool CServerSocket::Close() {
	CloseSocket(m_ListnerSocket); 
	return false;
}
/////////////////////////////////
// Read to a Buffer
//
bool CServerSocket::Read( char *bfr ,  int size ){
	int RetVal= recv(m_ListnerSocket ,bfr,size,0);
	if ( RetVal == 0 || RetVal == -1)  {return false; }
	return true;
}
/////////////////////////////////////////////////
// Write from a Buffer 
//
bool CServerSocket::Write( char *bfr , int size ) {
	int bytes_send = send(m_ListnerSocket , (char *)bfr, size,0);
	return bytes_send > 0 ;
}

//////////////////////////////////////////////////////////////////////
// Retrieve IP Address as ASCII string
//
//
string ProtocolHelper::GetIPAddressAsString(struct sockaddr_in *client_addr) {
	struct sockaddr_in* pV4Addr = client_addr;
	struct in_addr ipAddr = pV4Addr->sin_addr;
	char str[INET_ADDRSTRLEN];
	inet_ntop( AF_INET, &ipAddr, str, INET_ADDRSTRLEN );
	return string(str);
}
/////////////////////////////////////////////
//
// Read Timeout for Socket Descriptor
//
bool ProtocolHelper::SetReadTimeOut( SOCKET s, long second ) {
	struct timeval		tv;
	tv.tv_sec = second;
	tv.tv_usec = 0;
  	int timeoutVal = 0;
  	int timeoutValSizeInInt = sizeof(int);
   	int timeoutValSizeInTimeVal = sizeof(timeval);
   	if (setsockopt(s, SOL_SOCKET, SO_RCVTIMEO,
		(const char *)&tv, timeoutValSizeInTimeVal) != SOCKET_ERROR){
			return true;
	}
	return false;
}
//////////////////////////////////////////////////////
//
//
//
bool ProtocolHelper::ReadSocketBuffer( SOCKET s, char *bfr ,  int size , int *num_read){
	int RetVal = recv(s ,bfr,size,0);
	if ( RetVal == 0 || RetVal == -1)  {  *num_read = RetVal; return false; }
	*num_read = RetVal;
    return true;
}
//////////////////////////////////////////
//
//
//
bool ProtocolHelper::ReadSocketBuffer( SOCKET s, char *bfr ,  int size ){
	int RetVal = recv(s ,bfr,size,0);
	if ( RetVal == 0 || RetVal == -1)  {   return false; }
	return true;
}
//////////////////////////////////////
//
//
//
bool ProtocolHelper::WriteSocketBuffer( SOCKET s, char *bfr ,  int size ){
	int RetVal = send(s ,bfr,size,0);
	if ( RetVal == 0 || RetVal == -1) { return false; }
    return true;
}

	


