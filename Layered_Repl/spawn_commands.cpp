#include "first.h"



extern "C" T_ACTION *  GetCommandHandlers_spawn(int *howmany) {

 // From DLL 2 (.so)

static T_ACTION actions2[] = {
    {"exec",handle_command_exec },
    {"spawn",handle_command_list }
    

};
       const T_ACTION *ptr_action = actions2;
       T_ACTION *temp = (T_ACTION *) malloc(sizeof(T_ACTION)*2);
       T_ACTION *temp_ptr = temp;
       for(int i=0; i <2 ; ++ i ) {
           memcpy(temp_ptr,&actions2[i],sizeof(T_ACTION));
           temp_ptr += sizeof(T_ACTION);
           

            
       }

       *howmany = 2;
       return temp; 
}


//------- DLL 2
T_PARAM_LIST *handle_command_exec( T_PARAM_LIST *param_list ) {
     fprintf(stdout,"%s\n","EXEC ..");
     dump_params(param_list);
     return 0;
}

T_PARAM_LIST *handle_command_spawn( T_PARAM_LIST *param_list ) {
     fprintf(stdout,"%s\n","SPAWN ..");
     dump_params(param_list);
     return 0;
}

