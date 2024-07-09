
#include "first.h"



const T_ACTION * Merge2(T_ACTION *first ,int n1, T_ACTION *second ,int  n2 );
HANDLE_COMMAND Lookup(  const char *ptr );
/*
//==== From DLL one
T_ACTION actions[] = {
    {"show",handle_command_show },
    {"list",handle_command_list },
    {"pwd",handle_command_pwd },
    {"ls",handle_command_ls },

};

// From DLL 2 (.so)

T_ACTION actions2[] = {
    {"exec",handle_command_exec },
    {"spawn",handle_command_list }
    

};

*/

// Global Variables for storing state

T_ACTION global_commands[100];
int global_command_count = 0;



bool Dump_Actions(  ) {
      
       for(int i=0; i <global_command_count ; ++ i ) {
           
                
                 printf("Action: %s %p\n",global_commands[i].command_name,
			global_commands[i].handler);
                

            
       }
       cout << "Actions .... " << endl;
       return true;

}

extern "C" bool LoadCommands() {
      
      int first_dll_command_count = 0; 
      T_ACTION *first_dll_commands = 
		GetCommandHandlers_spawn(&first_dll_command_count);
      if ( first_dll_command_count < 1 ) {
         printf("%s %d\n","No of First Commands... ",first_dll_command_count);
         return false;
      }
      
      int second_dll_command_count = 0;
      T_ACTION *second_dll_commands = 
		GetCommandHandlers_internal(&second_dll_command_count);  
      
      if ( second_dll_command_count < 1 ) {
         printf("%s %d\n","No of Commands... ",second_dll_command_count);
         return false;
      }
      
      if ( first_dll_commands == 0 ) {
          fprintf(stdout,"Failed to Load the first Command....");
          return false;
      }
      if ( second_dll_commands == 0 ) {
          fprintf(stdout,"Failed to Load the second Command....");
          return false;
      }

    
	Merge2(first_dll_commands,
			first_dll_command_count,
                        second_dll_commands,
                        second_dll_command_count);
      

     
      global_command_count = first_dll_command_count +
			     second_dll_command_count;
      
      free(first_dll_commands);
      free(second_dll_commands);
      return global_command_count > 0;

}






const T_ACTION * Merge2(T_ACTION *first ,int n1, T_ACTION *second ,int  n2 ) {
       
        T_ACTION *temp_action_ptr = first;
        int command_count =0;
        for(int i=0; i<n1; ++ i ) {
             memcpy(&global_commands[command_count++],temp_action_ptr,sizeof(T_ACTION));
            
             temp_action_ptr += sizeof(T_ACTION);
        }
        
        temp_action_ptr = second;
        for( int i=0; i<n2; ++ i ) {
             memcpy(&global_commands[command_count++],temp_action_ptr,sizeof(T_ACTION));
             
             temp_action_ptr += sizeof(T_ACTION);
        }
       
        return (&global_commands[0]);

}





//--------------------- Psuedo Code for Search 


HANDLE_COMMAND Lookup(  const char *ptr ) {

       for(int i=0; i <global_command_count ; ++i ) {
           if ( strncmp(global_commands[i].command_name,ptr,strlen(ptr) ) == 0 ) {
                   return global_commands[i].handler;
           }
       }
       return 0;

}






extern "C" bool command_dispatch(char *buff ) {
    string ns(buff);
    vector<string> vs = split(ns,string(" "));
    T_PARAM_LIST res = SetupParams(vs);
    
	
   
    HANDLE_COMMAND hm = Lookup( vs[0].c_str());
    if ( hm != 0 ) {
            printf("Invoking : %s \n",vs[0].c_str());
            hm(&res);
            return true;

    }
   		
    printf("[%s]\n", buff);
    return true;

}





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

////////////////////////////////////////
//
//
T_PARAM_LIST  SetupParams(vector<string>& strvector ) {
      int ns = strvector.size();
      T_PARAM_LIST ls;
      ls.p_count = ns;
      int i=0;
      for( string str : strvector ) {
             ls.param_one[i].type = 1; //string
             strcpy(ls.param_one[i].DATA.buffer,str.c_str());
             i++;
      }
      return ls;


}


void dump_params(T_PARAM_LIST *lst ) {
   int ns = lst->p_count;
   for(int i=1; i<ns; ++i ) {
          cout << ".." << lst->param_one[i].DATA.buffer << "..";

   }
   cout << endl;
   return;

}
