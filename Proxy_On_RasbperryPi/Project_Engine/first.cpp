/////////////////////////////////
//
// A Simple Program to demonstrate the 
// Server Socket and ClientSocket (CPing)
//
#include <stdio.h>
#include <stdio.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <spawn.h>
#include <sys/wait.h>
#include <string>
///////////////////
// include C++ HeaderFiles..
//
#include <iostream>
#include <thread>
#include <list>
using namespace std;
///////////////////////////////
// Include Custom HeaderFiles
//
#include "CProtocolServer.h"
#include "CPingClient.h"


#if 0
///////////////////////////////////
//----------- A Simple PING Packet
//
typedef struct{
	char node_end_point[40];
	int port;
}NODE_PING_INFO;
///////////////////////////////
// A Stub routine which is supposed to Perform Logic
//
void PerformLogic() {}

//////////////////////////////////////////////////
// A Simple Ping Client to Test Cluster Simulator
//
//
int main(int argc, char **argv ){
	//---- Expects a Command line argument
	if ( argc != 2) {
		fprintf(stdout,"Failed to Start the app\n");
		return -1;
    }
	//----- Convert the Command line argument to Port #
	int rc = atoi(argv[1]);   
	cout << "Received from Command line " << rc << endl;
	//----------Instanitate a Simple Ping Client
	CPing ps("localhost",7086);
	NODE_PING_INFO  pdu{"localhost", rc};
	if ( !ps.Open() ) {
		fprintf(stdout, "Failed to Open The Client\n" );
		return 0;
	}
	//-------------- Get into an Inifinite Loop
	while (1) {
		sleep(10);
		PerformLogic();
		ps.SendSignal(&pdu,sizeof(pdu));
	}
	//----- Do not know, when it will reach here...
	//----- Process will get Closed through CTRL+C
    ps.Close();
    return 0;
}
#endif

#if 0
///////////////////////////////////////////
// A Simple Protocol Server.. for Ping
//
//
int main(int argc, char **argv ){
	//----------- Expects Port as a Commandline argument
	if ( argc != 2) {
		fprintf(stdout,"Failed to Start the app\n");
		return -1;
	}
	//---------- Convert the command line argument to Port #
    int rc = atoi(argv[1]);   
    rc = (rc < 0 ) ? rc : 9100;
    cout << "Received from Command line " << rc << endl;
	//----Instantiate ProxySocket Class
    CProtocolSocket ps(rc);  //default is 9100;
	//------------- Set the Default PingHandler
	if (!ps.SetHandler(new PingHandler ()) ) {
		cout << "Failed to set Handler.................." << endl;
		return -2;
	}
	//----------------------- Start the Protocol Server
	if (!ps.Start() ) {
		cout << "Failed To Start Server........................" << endl;
		return -3;
	}
	//------------ Get into Infinite Loop
    while(1);
	//---- Things will never Reach here
    return 0;
} //EndOfFunction => main

#endif
///////////////////////////////////////////
// A Simple Passthrough Handler...A Protocol agnostic Proxy
//
//
int main(int argc, char **argv ){
	//----------- Expects Port as a Commandline argument
	if ( argc != 2) {
		fprintf(stdout,"Failed to Start the app\n");
		return -1;
	}
	//---------- Convert the command line argument to Port #
    int rc = atoi(argv[1]);   
    rc = (rc > 0 ) ? rc : 9100;
    cout << "Received from Command line " << rc << endl;
	//----Instantiate ProxySocket Class
    CProxySocket ps(rc);  //default is 9100;
	#if 0
	//------------- Set the Default ProxyHandler as Passthrough Handler
	if (!ps.SetHandler(new PTHandler ()) ) {
		cout << "Failed to set Handler.................." << endl;
		return -2;
	}
	#else
	//------------- Set the Default ProxyHandler as Passthrough Handler
	if (!ps.SetHandler(new PTHTTPHandler ()) ) {
		cout << "Failed to set Handler.................." << endl;
		return -2;
	}

	#endif
	//----------------------- Start the Proxy Server
	if (!ps.Start() ) {
		cout << "Failed To Start Server........................" << endl;
		return -3;
	}
	//------------ Get into Infinite Loop
    while(1);
	//---- Things will never Reach here
    return 0;
} //EndOfFunction => main