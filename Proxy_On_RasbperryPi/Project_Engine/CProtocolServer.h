#ifndef PROTOCOL_DOT_H
#define PROTOCOL_DOT_H
#include "ServerSocket.h"

////////////////////////////
// Protocol Handler Interface, has got only one Overriden method. Handler, which
// handles the Protocol Request
//
class CProtocolHandler{
	public:
		CProtocolHandler() {}
		virtual bool Handler( void *Buffer, int len , CLIENT_DATA& CData  )= 0;
		virtual ~CProtocolHandler() {}

}; //EndOfClass => CProtocolHandler
//////////////////////////////////////
// Proxy Handler Interface, Has got two overriden methdos
//
class CProxyHandler {
	public:
		CProxyHandler () {}
		virtual bool HandleUpstreamData( void *Buffer, int len , CLIENT_DATA& CData  )= 0;
		virtual bool HandleDownStreamData( void *Buffer, int len , CLIENT_DATA& CData  )= 0;
		virtual ~CProxyHandler() {}
};

/////////////////////////////////////////////
// CProtocolSocket - A Base class for implementing a Protocol
//
class CProtocolSocket : public CServerSocket{
	private:
		//---------- Handler for a Particular Protocol implementation
		CProtocolHandler *m_handler = 0;
		//------------- Default Pipeline to Handle a Protocol
		static void * ThreadHandler( CProtocolSocket * ptr, void *lptr );
		//----------- Lambda Function which provides the Core Logic of the Protocol
		std::function<void* (void *)> thread_routine_override=0;

	public:
		//---------- CTOR 
		CProtocolSocket(int port );
		// Set Handler 
		bool SetHandler( CProtocolHandler * ph );
		//-------- Retrieve the Handler
		CProtocolHandler *GetHandler(); 
		//-------- Kickstart the Protocol Loop
		bool Start();
		bool SetPipeline(std::function<void *(CProtocolSocket *, void *)> test_func);

}; // EndOfClass => CProtocolSocket

/////////////////////////////////////////////
// ProxySocket - A Class to Handle Proxying of any Protocol
//
//
class CProxySocket : public CServerSocket{
	private:
		//-------- Handler Interface implementation
		CProxyHandler *m_handler = 0;
		//-------- Default Pipeline
		static void * ThreadHandler( CProxySocket * ptr, void *lptr );
		//--------------- Lambda Routine - Client Logic
		std::function<void* (void *)> thread_routine_override=0;
	public:
		CProxySocket (int port );
		bool SetHandler( CProxyHandler * ph ); 
		CProxyHandler *GetHandler();
        bool Start(); 
		bool SetPipeline(std::function<void *(CProxySocket *, void *)> test_func);
};

//////////////////////////////////////////
// A Naive Ping Handler, Demonstrates the usage of Protocol Handler Interface
//
//
class PingHandler : public CProtocolHandler {
		public:
			PingHandler() {}
			virtual bool Handler( void *Buffer, int len , CLIENT_DATA& CData  );
			virtual ~PingHandler() {}
};

/////////////////////////////////////////////////////
//
// A Passthrough Handler, Demonstrates the usage of Proxy Handler Interface
//
//
class PTHandler : public CProxyHandler {
		public:
			PTHandler ();
			virtual bool HandleUpstreamData( void *Buffer, int len , CLIENT_DATA& CData  );
			virtual bool HandleDownStreamData( void *Buffer, int len , CLIENT_DATA& CData  ); 
			
};

/////////////////////////////////////////////////////
//
// A Passthrough HTTP Handler, Demonstrates the usage of Proxy Handler Interface
//
//
class PTHTTPHandler : public CProxyHandler {
		public:
			PTHTTPHandler ();
			virtual bool HandleUpstreamData( void *Buffer, int len , CLIENT_DATA& CData  );
			virtual bool HandleDownStreamData( void *Buffer, int len , CLIENT_DATA& CData  ); 
			
};


#endif
