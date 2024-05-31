#ifndef UTILS_DOT_H
#define UTILS_DOT_H
#include <stdio.h>
#include <iostream>
#include <map>
#include <vector>
#include <set>
#include <cstring>

using namespace std;
////////////////////////////////////////////
// Split a std::string using a  delimiter
//
vector<string> split (string s, string delimiter);

////////////////////////////////////////
// Some Helper functions to manipulate environment
// variables
//
class EnvArgUtils {
	public:
		static int GetEnvironmentLength( char **environment );
		static int PrintEnvironment( char **environment );
		static char ** CloneEnvironment( char **environment );  
		static char ** CloneEnvironment2( char **environment ); 
		static int FreeEnvironment( char **environment ); 
};

#endif
