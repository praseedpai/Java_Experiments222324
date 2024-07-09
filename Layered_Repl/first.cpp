//////////////////////////////////
// first.cpp
// A  REPL prototype
// -------------------------
// The REPL has got five Layers
//	REPL proper ( only dependency is readline )
//	Command_processing layer ( command_processing.cpp )
//      Commands layer ( spawn_commands.cpp , internal_commands.cpp )
//	SDK/API layer ( Client SDK for Services like Proxy,Portal API )
//	Channel API ( TCP-TLS,HTTPS etc )
// -----------------------------------------------------
// Dependency - GNU ReadLine Library (-lreadline )
// ------------------------------------------------------
//  g++ first.cpp command_processing.cpp 
//      internal_commands.cpp spawn_commands.cpp -lreadline
// ----------------------------------------------------------
// ./a.out ( Type exit to come out of the REPL loop )
//
//
//
#include "first.h"





int main(int argc, char** argv) {
  printf("Welcome! You can exit by pressing Ctrl+C at any time...\n");

  if (argc > 1 && std::string(argv[1]) == "-d") {
    // By default readline does filename completion. With -d, we disable this
    // by asking readline to just insert the TAB character itself.
    rl_bind_key('\t', rl_insert);
  }
  printf("%s\n", "Before Loading Commands.............");
  fflush(stdout);
  if ( LoadCommands() == false ) {
       fprintf(stdout,"%s\n","Error Loading Global Commands...");
       return -2;
  }
  printf("%s\n", "After Loading Commands.............");
  fflush(stdout);
  char* buf;
  while ((buf = readline(">> ")) != nullptr) {
    if (strlen(buf) > 0) {
      add_history(buf);
    }
    command_dispatch(buf);
    

    // readline malloc's a new buffer every time.
    free(buf);
  }

  return 0;
}



