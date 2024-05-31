////////////////////////////
// A Simple Protocol to test Protocol Sequencing
//https://github.com/eminfedar/async-sockets-cpp

#include "Utils.h"
#include "ClientSocket.h"
#include "CProtocolServer.h"



/////////////////////////////////////////////////////
// A Factory method for Creating Socket for ClickHouse,
// PosgreSQL and MySQL ..not used, retained for historical purpose
//
//
CClientSocket *GetSocket(string host , string dbname ) {
		if (dbname == "CH" ) { return new CClientSocket(host.c_str(),9000);}
        else if (dbname == "PG" ) { return new CClientSocket(host.c_str(),5432);}
        else if (dbname == "MY" ) { return new CClientSocket(host.c_str(), 3306);}
        return 0;
} 
//////////////////////////////////////////
// Get a Default Endpoint
//
#if 0
END_POINT * GetDefaultEndpoint() {
    return  new END_POINT { "127.0.0.1" ,8123 , 1,"",0,"  "};  
}
#else
END_POINT * GetDefaultEndpoint() {
    return  new END_POINT { "127.0.0.1" ,9000 , 1,"",0,"  "};  
}
#endif
//////////////////////////////////////////////
//
// The Naive Ping Handler...Does not do much
//
bool  PingHandler ::Handler( void *Buffer, int len , CLIENT_DATA& CData) {
                   //---------- Do not do much with Received Data  
                   cout << "Pinged from Remote Server ....................." << endl;
                   return true;

}
/////////////////////////////////////////////////
// Implementation of the Pass Through Handler..
//
//
PTHandler::PTHandler() {}

bool PTHandler::HandleUpstreamData( void *Buffer, int len , CLIENT_DATA& CData  ) {
			// Log the Content and Forward the Data to the EndPoint
			cout << "==================================" << endl;
			cout << "Received a Client packet..................... " << endl;
			cout << "Length of Packet is " << len << endl;
			cout << "Packet Type = " <<  (int)  *((unsigned char *) Buffer) << endl;
			cout << "======================================" << endl;
			//----------- Send the Data to the Forward port
			send(CData.forward_port,Buffer,len,0);
			return true;
}

bool PTHandler::HandleDownStreamData( void *Buffer, int len , CLIENT_DATA& CData  ) {
			// Log the Content and Forward the Data to the EndPoint
			cout << "==================================" << endl;
			cout << "Received a Server packet..................... " << endl;
			cout << "Length of Packet is " << len << endl;
			cout << "Packet Type = " <<  (int)  *((unsigned char *) Buffer) << endl;
			cout << "======================================" << endl;
			//------- Send the Data Back
			send(CData.Sh,Buffer,len,0);
			return true;
}

/////////////////////////////////////////////////
// Implementation of the HTTP Pass Through Handler..
//
//
PTHTTPHandler::PTHTTPHandler() {}

bool PTHTTPHandler::HandleUpstreamData( void *Buffer, int len , CLIENT_DATA& CData  ) {
			char buffer[32000];
			memset(buffer,0,32000);
			memcpy(buffer,Buffer,len);
			// Log the Content and Forward the Data to the EndPoint
			cout << "==================================" << endl;
			cout << "Received a Client packet..................... " << endl;
			cout << "Length of Packet is " << len << endl;   
			printf("%s\n",buffer);
			cout << "======================================" << endl;
			//----------- Send the Data to the Forward port
			send(CData.forward_port,Buffer,len,0);
			return true;
}

bool PTHTTPHandler::HandleDownStreamData( void *Buffer, int len , CLIENT_DATA& CData  ) {
			// Log the Content and Forward the Data to the EndPoint
			
			//------- Send the Data Back
			send(CData.Sh,Buffer,len,0);
			return true;
}

//////////////////////////////////////////////////////////
// The Default Thread Handler of CProtocolSocket, the Protocol Handler
//
void * CProtocolSocket::ThreadHandler( CProtocolSocket *ptr , void *lptr ) {
		CLIENT_DATA CData;
		memcpy(&CData,lptr,sizeof(CLIENT_DATA));
		//----------- Retrieve the Handler from the Socket..
        //----------- If null, do not do further processing
		CProtocolHandler * proto_handler = ptr->GetHandler(); 
		if ( proto_handler == 0 ) { return 0; }
        //------------- This should be a configurable item          
        char bfr[32000];
		while (1) {
			memset(bfr,0,32000);
			int num_read = 0;
            //------------ Read Maximum amount of data from the Socket Buffer
            if ( !ProtocolHelper::ReadSocketBuffer( CData.Sh, bfr, sizeof(bfr),&num_read))
			{ return  nullptr;}
            //------------- Dispatch it to the handler
            if ( !(proto_handler->Handler(bfr,num_read,CData))) { break;}
		}
        //--- if handler, fails only, one will reach here!
        return 0;

}

//---------- CTOR 
CProtocolSocket::CProtocolSocket(int port ): CServerSocket::CServerSocket(port,"DEFAULT") {
            //--- Set up the Default Pipeline
			std::function<void * (void *) > myfunc =  [this] (void *ptr ) -> void *{  
				return CProtocolSocket::ThreadHandler(this,ptr);
			};  
			//---- Set the Client Logic
			thread_routine_override =  myfunc;
}
// Set Handler 
bool CProtocolSocket::SetHandler( CProtocolHandler * ph ) { 
	m_handler = ph;
	return m_handler != 0;
}
//-------- Retrieve the Handler
CProtocolHandler *CProtocolSocket::GetHandler() { return m_handler; }
//-------- Kickstart the Protocol Loop
bool CProtocolSocket::Start() { return Open("<str>",thread_routine_override);}

bool CProtocolSocket::SetPipeline(std::function<void *(CProtocolSocket *, void *)> test_func) {
    std::function<void *(void *)> myfunc = [this, test_func](void *ptr) -> void * {
        return test_func(this, ptr);
    };
    thread_routine_override = myfunc;

    return thread_routine_override != nullptr;
}



////////////////////////////////////////////////////////////////
//
// Default Implementation of CProxySocket, Thread Handler
//
void * CProxySocket::ThreadHandler(CProxySocket *ptr, void *lptr ) {
		CLIENT_DATA CData;
		memcpy(&CData,lptr,sizeof(CLIENT_DATA));
		char bfr[32000];
		int RetVal;

        //-------- Retrieve the Default End Point
        END_POINT * ep = GetDefaultEndpoint();  
		if ( ep == 0 ) { return 0; }
		cout << "Resolved " << ep->ipaddress << "   " << ep->port << endl;

        //------ Retreive the Handler from the Proxy Socket
		CProxyHandler * proxy_handler = ptr->GetHandler(); 
		if ( proxy_handler == 0 ) { return 0; }

		//----------- Create a Client Socket with the retrieved EndPoints or 
		//----------- Create a Socket for ClickHouse
		#if 1
			CClientSocket  *client_sock = new CClientSocket((char *)(ep->ipaddress.c_str()), ep->port);
		#else
			CClientSocket  *client_sock = GetSocket("127.0.0.1", "CH");
		#endif
		if ( client_sock == 0 ) { cout << "Failed to Create Client" << endl; return 0; }
          
		//----------- Resole the IP Address, Connect and Retrieve the Remote Socket Client
		if ( !client_sock->Resolve() ) { 
			cout << "Failed to Resolve Client" << endl; 
			delete client_sock; 
			return 0; 
		}
		if ( !client_sock->Connect() ) { 
			cout <<"Failed To Connect " << endl; 
			delete client_sock; 
			return 0; 
		}
		SOCKET s = client_sock->GetSocket();
		if ( s == -1) { 
			cout << "Invalid Socket" << endl ; 
			return 0; 
		}

		//---- Set Read Timeout for forward and Return socket
		CData.forward_port = s;
		ProtocolHelper::SetReadTimeOut(  s, 1 );
		ProtocolHelper::SetReadTimeOut(CData.Sh,1);
		
		//------------ Get into the Proxy Double Infinite Loop
		int num_cycles = 0;
		cout << "Entered Nested Loop " << endl;
	 	while (1){
			num_cycles ++;
			//-------- Enter the Inner Loop
			while (1){
				memset(bfr,0,32000);
				//---------Poll the Incoming Socket for any data
				RetVal= recv(CData.Sh ,bfr,sizeof(bfr),0);
				if ( RetVal == -1)  { break; }
				else if ( RetVal == 0)  { num_cycles++; break; }
				
				//------ Send the Data to the forward port or Handler
				#ifdef INLINE_LOGIC
					send(CData.forward_port,bfr,RetVal,0);
				#else
					if (!proxy_handler->HandleUpstreamData(bfr, RetVal, CData) )
                    {return 0;}
				#endif
				if ( RetVal < 32000 ) { break; }
				RetVal = 0; //Reset the RetVale to Zero
			} //---------- End 1st Inner Loop
			//-------- Enter the Second Inner Loop
			while (1)  {
				memset(bfr,0,32000);
				//-----Poll the forward port for any data
				RetVal = recv(CData.forward_port ,bfr,sizeof(bfr),0);
				if ( RetVal == -1)  {   break; }
				else if ( RetVal == 0)  { num_cycles++; break; }
				//---------- Send the Data Back 
				#ifdef INLINE_LOGIC
					send(CData.Sh,bfr,RetVal,0);
				#else
					if (! proxy_handler->HandleDownStreamData( bfr, RetVal, CData) ) 
					{ return 0;}                  
				#endif
				if ( RetVal < 32000 ) {  break; }
                RetVal = 0;
			} //--- End 2nd Inner While Loop
			//----------------- If num_cycles greater than threshhold, exit the protocol loop
			if ( num_cycles > 15) {break;}
		}
	return 0;
} //-- EndOFFunction => CProxySocket::ThreadHandler

//------------ CProxySocket CTOR
CProxySocket::CProxySocket (int port ): CServerSocket::CServerSocket(port,"DEFAULT") {
	std::function<void * (void *) > myfunc =  [this] (void *ptr ) -> void *{  
		return CProxySocket ::ThreadHandler(this,ptr);
	};  
	thread_routine_override =  myfunc;
}
//---- SetHandler
bool CProxySocket::SetHandler( CProxyHandler * ph ) {
	m_handler = ph;
	return m_handler != 0;
}
//---- Retrieve Handler 
CProxyHandler *CProxySocket::GetHandler() { return m_handler; }
//----- KickStart the Proxy Loop
bool CProxySocket::Start() { return Open("<str>",thread_routine_override);}

bool CProxySocket::SetPipeline(std::function<void *(CProxySocket *, void *)> test_func) {
    std::function<void *(void *)> myfunc = [this, test_func](void *ptr) -> void * {
        return test_func(this, ptr);
    };
    thread_routine_override = myfunc;

    return thread_routine_override != nullptr;
}

#if 0
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
                  int port = atoi(argv[1]);
    	CServerSocket sock{ port};     
                  std::function<void * (void *) > myfunc =  [] (void *ptr ) -> void *{  
                                return ThreadHandler(ptr);
                     }  ;  
                  if ( ! sock.Open("localhost:master 1:5000:READ",myfunc) )
                  {
                                      cout << "Could not Open Socket Properly" << endl;
                                      return 0;
                   }
	CServerSocket sock2{ port+1};          
                  if (!sock2.Open("localhost:master 2:5001:WRITE", myfunc ))
                  {
                                      cout << "Could not Open Socket Properly" << endl;
                                      return 0;
                   }
                 while (1) ;
   	Cleanup();
	return 0;
}


#endif
