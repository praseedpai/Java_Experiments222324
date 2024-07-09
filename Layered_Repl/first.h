#ifndef FIRST_DOT_H
#define FIRST_DOT_H
//////////////////////////////////
// first.cpp
// A minimal REPL prototype
// Dependency - GNU ReadLine Library (-lreadline )
// g++ first.cpp -lreadline
// ./a.out ( Type exit to come out of the REPL loop )

#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <string>
#include <vector>

using namespace std;
#include <readline/readline.h>
#include <readline/history.h>

#pragma pack(push)
#pragma pack(1)


typedef struct {
   int type; // variant
   union  {
       char buffer[255];
       int  value;
       float fvalue;
       double dvalue;
       void *ptr;
       char *ptr_str;
   }DATA;
}T_PARAM;

typedef struct {
   int p_count; // parameter count
   T_PARAM param_one[16]; //---
}T_PARAM_LIST;



typedef   T_PARAM_LIST*  (*HANDLE_COMMAND)(T_PARAM_LIST * );

typedef struct 
{
   char command_name[96];
   HANDLE_COMMAND handler;

}T_ACTION;

#pragma pack(pop)
vector<string> split (string s, string delimiter);
T_PARAM_LIST  SetupParams(vector<string>& strvector );
void dump_params(T_PARAM_LIST *lst );


//
// T_ACTION *handle_command_xxx(T_PARAM_LIST * param_list );
//
T_PARAM_LIST * handle_command_show( T_PARAM_LIST *param_list );
T_PARAM_LIST *handle_command_list( T_PARAM_LIST *param_list );

T_PARAM_LIST *handle_command_pwd( T_PARAM_LIST *param_list );

T_PARAM_LIST *handle_command_ls( T_PARAM_LIST *param_list );


T_PARAM_LIST *handle_command_exec( T_PARAM_LIST *param_list );

T_PARAM_LIST *handle_command_spawn( T_PARAM_LIST *param_list );

extern "C" bool LoadCommands() ;
extern "C" bool command_dispatch(char *buff );

//----------- We will move the code to other DLLs

extern "C" T_ACTION * GetCommandHandlers_spawn( int * );
extern "C" T_ACTION * GetCommandHandlers_internal( int * );

#endif