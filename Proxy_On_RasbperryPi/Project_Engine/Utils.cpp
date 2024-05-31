#include "Utils.h"
////////////////////////////////////////////
// Split a std::string using a  delimiter
//
vector<string> split (string s, string delimiter) {
	size_t pos_start = 0, pos_end, delim_len = delimiter.length();
	string token;
	vector<string> res;
	//--- Leveraging std::string::find, we split the string
	while ((pos_end = s.find (delimiter, pos_start)) != string::npos) {
		token = s.substr (pos_start, pos_end - pos_start);
		pos_start = pos_end + delim_len;
		res.push_back (token);
	}
	//---- Retrieve the remaining string and push to return vector
	res.push_back (s.substr (pos_start));
	return res;
}

//////////////////////////////////////
// Some Helper Functions to Manipulate Environment 
// Variables
//
//
//------------- This Function will compute the # of environment 
//------------- Variables available in a env variable
int EnvArgUtils::GetEnvironmentLength( char **environment ) {
	int nLen = 0;
	char **p = environment;
	while (  *( p +1 ) != 0 ) {
		p++;
		nLen++;
	}
	return nLen;
}
//---------- This functions dump the environment variables to console
int EnvArgUtils::PrintEnvironment( char **environment ) {
     
     char **p = environment;
     while (  *( p +1 ) != 0 ) {
                 printf("%s\n",*p);
                 p++;
               
      }

      return 0;
}

 char ** EnvArgUtils::CloneEnvironment( char **environment )  {
           int nLen = GetEnvironmentLength(environment);
           char **ret_val = ( char **) malloc(sizeof(char *)*(nLen+1));
           memset(ret_val, 0, sizeof(char *)*(nLen + 1 ));
           char **src = environment;
           char **dest = ret_val;
           while  (*(src +1) != 0 ){
                        *dest = (char *) malloc(strlen(*src) +1);
                        strcpy(*dest, *src );
                        src++; dest++;

           }
           return ret_val;          
}

 char ** EnvArgUtils::CloneEnvironment2( char **environment )  {
           int nLen = GetEnvironmentLength(environment);
           char **ret_val = ( char **) malloc(sizeof(char *)*(nLen+1));
           memset(ret_val, 0, sizeof(char *)*(nLen + 1 ));
           char **src = environment;
           char **dest = ret_val;
           int i=0;
           while  (i<=nLen){
                        *dest = (char *) malloc(strlen(*src) +1);
                        strcpy(*dest, *src );
                        src++; dest++;
                        i++;

           }
           return ret_val;          
}

 int EnvArgUtils::FreeEnvironment( char **environment ) {
           int nLen = GetEnvironmentLength(environment);
           char **src = environment;
           int i=0;
           while  (i<nLen){
                        free(*src);
                        src++; 
                        i++;

           }
           return 0;    



}
