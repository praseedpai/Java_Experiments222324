#include "CPingClient.h"
bool CPing::Open()  {
                  m_socket = new CClientSocket((char *)remote_ip.c_str(), remote_port );
                  if ( m_socket == 0 ) { 
                           printf("%s\n","Failed to Create Instance of Socket");
                           return false; 
                  }        
        	if (!m_socket->Resolve() ) {
                                    printf("%s\n","Failed in Resolution");
                                    delete m_socket;
                                    m_socket = 0;  
                    	return false;
        	}
                  if ( !m_socket->Connect() )  {
                     printf("%s\n","Failed in Connection");
                    m_socket->Close();
                    m_socket = 0;
                    return false;
                  }
                  return true;
}

 bool CPing::SendSignal( void *Buffer , int length ) {
                     if (!m_socket->Write(Buffer,length) ) { return false; }
                     return true;
 }
 bool CPing::Close() { m_socket->Close(); return true; }
 CPing::~CPing() { Close(); m_socket = 0; }
