#include <stdio.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include <unistd.h>
#include <spawn.h>
#include <sys/wait.h>
#include <string>
#include <iostream>
#include <thread>
#include <list>
#include "CProtocolServer.h"
using namespace std;

extern char **environ;
class EnvArgUtils {
public:
static int GetEnvironmentLength( char **environment ) {
     int nLen = 0;
     char **p = environment;
     while (  *( p +1 ) != 0 ) {
                 p++;
                 nLen++;
      }

      return nLen;
}

static int PrintEnvironment( char **environment ) {
     
     char **p = environment;
     while (  *( p +1 ) != 0 ) {
                 printf("%s\n",*p);
                 p++;
               
      }

      return 0;
}

static char ** CloneEnvironment( char **environment )  {
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

static char ** CloneEnvironment2( char **environment )  {
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

static int FreeEnvironment( char **environment ) {
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
};

class SpawnProcess
{
       char _process_name[255];
      const  char  **  _argv;
      int _argc;
      const  char  ** _envp;
      pid_t  _pid;
      int  _status;
    public:
         SpawnProcess(const char *processname,const char **argv , int argc , const char **envp) {
                         strcpy(_process_name, processname);
                         _argv = argv;
                         _argc = argc;
                         _envp = envp;
         }

         bool Start() {
                  _status = posix_spawn(&_pid, _process_name , NULL, NULL,(char* const *) _argv, ( char * const *)_envp);
                  if ( _status == 0 ) { return true; }
                  return false;
         }
         bool IsAlive() {
                  if (waitpid(_pid, &_status, WNOHANG)  == -1)   { return false; }

                  if (WIFEXITED(_status)) {
                             if ( WEXITSTATUS(_status)== 0 ) { 
                                                 return true; 
                              } else { 
                                             return false; 
                              }
                  }
           
             else if (WIFSIGNALED(_status)) {
                return false;
            } else if (WIFSTOPPED(_status)) {
                return false;
            } else if (WIFCONTINUED(_status)) {
                return true;
            }

            
                  printf("%s\n", "Not Alive,,,,,,,,,,,,,,,,,,,,,,,,,,");
                  return false;
        }

};

class TaskList 
{
                list<SpawnProcess> process_list;
        public:
                TaskList() { process_list.clear();}
                 bool Add( SpawnProcess& p ) { process_list.push_back(p); return true;}
                 bool Start() {    
                  for (SpawnProcess& n : process_list )  { if ( !n.Start() ) { return false; }}
                   return true;
                 }

                 bool IsAliveAll( ) {
                          for (SpawnProcess& n : process_list )  { if ( !n.IsAlive() ) { return false; }}
                          return true;
                 }
        


};
#if 0
// The function we want to execute on the new thread.
void task1(string msg)
{
     pid_t pid;
   const  char *argv2[] = {"./first.exe",  NULL};
    int status;
   SpawnProcess  process( (const char *)"./first.exe", (const char **)argv2, 2, (const char **) environ);
   SpawnProcess  process1( (const char *)"./first.exe", (const char **)argv2, 2, (const char **) environ);
   SpawnProcess  process2( (const char *)"./first.exe", (const char **)argv2, 2, (const char **) environ);
   TaskList lst;
   lst.Add(process);
   lst.Add(process1);
   lst.Add(process2);
   if ( !lst.Start() ) {
               printf("%s\n", "Failed to start Process list ");
               return;
   }
   printf("%s\n", "Started The Process....................");
   while ( lst.IsAliveAll() ) {
             sleep(10);
             printf("%s\n","All process is still alive");
   }
}

#else
// The function we want to execute on the new thread.
void task2(string msg) {
   CProtocolSocket server{7086};
   if (!server.SetHandler(new PingHandler()) ) {
               cout << "Failed to set Handler.................." << endl;
               return;
   }
   if (!server.Start() ) {
               cout << "Failed To Start Server........................" << endl;
               return;

   }
   while(1);
}
void task1(string msg)
{
     pid_t pid;
   const  char *argv2[] = {"./first.exe", "786", NULL};
    int status;
   SpawnProcess  process( (const char *)"./first.exe", (const char **)argv2, 2, (const char **) environ);
   SpawnProcess  process1( (const char *)"./first.exe", (const char **)argv2, 2, (const char **) environ);
   SpawnProcess  process2( (const char *)"./first.exe", (const char **)argv2, 2, (const char **) environ);
   TaskList lst;
   lst.Add(process);
   lst.Add(process1);
   lst.Add(process2);
   if ( !lst.Start() ) {
               printf("%s\n", "Failed to start Process list ");
               return;
   }
  
  
   while (1);

}

#endif

   

#if  1
int main( int argc, char **argv ) {
    
   

  
    // Constructs the new thread and runs it. Does not block execution.
    thread t2(task2, "Hello");
    sleep(10);
    thread t1(task1, "Hello");
     

    // Do other things...
#if 0
    // Makes the main thread wait for the new thread to finish execution, therefore blocks its own execution.
     t1.join();
     int r = EnvArgUtils::GetEnvironmentLength(environ);
     cout << r << endl;
     cout << "==============================" << endl;
     char **p = EnvArgUtils::CloneEnvironment2(environ);
     int s = EnvArgUtils::GetEnvironmentLength(p);
     cout << s  << endl;
     EnvArgUtils::PrintEnvironment(p);
     cout <<"=============================" << endl;
     EnvArgUtils::PrintEnvironment(environ);
#endif

     t1.join();
     t2.join();

}


#else
int main( int argc, char **argv ) {

    pid_t pid;
   const  char *argv2[] = {"./first.exe",  NULL};
    int status;
   
    status = posix_spawn(&pid, "./first.exe", NULL, NULL,(char * const *) argv2, environ);
    if (status == 0) {
        printf("Child pid: %in", pid);
        do {
          sleep(5);
          if (waitpid(pid, &status, WNOHANG) != -1) {
            printf("Child status %d\n", WEXITSTATUS(status));
          } else {
            perror("waitpid");
            exit(1);
          }
        } while (1);
    } else {
        printf("posix_spawn: %sn", strerror(status));
    }


}
#endif
