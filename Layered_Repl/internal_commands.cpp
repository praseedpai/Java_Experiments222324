#include "first.h"




extern "C" T_ACTION * GetCommandHandlers_internal(int *howmany) {

       //==== From DLL one
      static T_ACTION actions[] = {
    {"show",handle_command_show },
    {"list",handle_command_list },
    {"pwd",handle_command_pwd },
    {"ls",handle_command_ls },

};
      
       T_ACTION *temp = (T_ACTION *) malloc(sizeof(T_ACTION)*4);
       T_ACTION *temp_ptr = temp;
       for(int i=0; i <4 ; ++ i ) {
           memcpy(temp_ptr,&actions[i],sizeof(T_ACTION));
           temp_ptr += sizeof(T_ACTION);
           

            
       }

       *howmany = 4;
       return temp; 

     
}

//
// T_ACTION *handle_command_xxx(T_PARAM_LIST * param_list );
//
T_PARAM_LIST * handle_command_show( T_PARAM_LIST *param_list ) {
     fprintf(stdout,"%s\n","SHOW .....");
     dump_params(param_list);
     return 0;
}

T_PARAM_LIST *handle_command_list( T_PARAM_LIST *param_list ) {
     fprintf(stdout,"%s\n","LIST ...");
     dump_params(param_list);
     return 0;
}

T_PARAM_LIST *handle_command_pwd( T_PARAM_LIST *param_list ) {
     fprintf(stdout,"%s\n","PWD ...");
     dump_params(param_list);
     return 0;
}

T_PARAM_LIST *handle_command_ls( T_PARAM_LIST *param_list ) {
     fprintf(stdout,"%s\n","LS ..");
     dump_params(param_list);
     return 0;
}