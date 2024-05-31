//  compile
//  -------
//  javac -cp jython-standalone-2.7.3.jar ChistaIntegration.java
// run
// ----
// java -cp jython-standalone-2.7.3.jar;. ChistaIntegration <src> <dest>
// example
// -------
// java -cp jython-standalone-2.7.3.jar;. ChistaIntegration rest rest
// java -cp jython-standalone-2.7.3.jar;. ChistaIntegration rest db
// java -cp jython-standalone-2.7.3.jar;. ChistaIntegration db rest
// 
// A Prototype for Integration  Pipeline
// The Hierarchy is 
//====================
//    IntegrationPipeline - An inteface for Pipeline (Design By Contract)              
//       DataTransformationPipeline
//    InputOrchestrator
//       .... Different Instances for REST,SOAP,JDB,other SDKs
//       .... A Jython Interface
//    InputContentHandler
//	 .... Input Data Handlers
//    BaseTransformer
//       ScriptTransformer (Jython Transformer)
//       NullTransformer
//    OutputContentHandler
//       .... Output Data Handlers
//    OutputOrchestrator
//       ...Different instances for REST,SOAP,JDBC,other SDKs
//    InputAdapter
//       ... Input Adapter ( for wrapping Camel, Spring Integration)
//       ... These Adapters are called from Jython
//    OutputAdapter
//       ... Output Adapter ( for wrapping Camel, Spring Integration)
//       ... These Adapters are called from Jython
//    InputBridge
//       ... Input Bridge ( Calling Native APIs)
//       ... These Adapters are called from Jython
//    OutputBridge
//       ... Input Adapter ( Calling NativeAPIs )
//       ... These Adapters are called from Jython
//
import java.lang.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;
import java.util.Iterator;
import java.util.Map;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/////////////////////////////////////////////
// This class integrates Python code to Java
// We are leveraging Jython
// We can incorporate Kotlin,Groovy through Java Scripting Host
//
//
class ScriptingInterpreter {
    private String _filename;
    public ScriptingInterpreter(String filename) {
        _filename = filename;
    }
    private PythonInterpreter createInterpreter(Map<String, Object>  vars) {
        PythonInterpreter pi = new PythonInterpreter();
        Iterator<Map.Entry<String,Object>> it  			=vars.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String,Object> ent = it.next();
            pi.set(ent.getKey(), ent.getValue());
        }
        return pi;
    }

    public boolean Execute(Map<String, Object>  vars) throws Exception{
        PythonInterpreter ps = createInterpreter(vars);
        FileInputStream is = new FileInputStream(_filename);
        if ( is == null) {
            System.out.println("Could not load File");
            return false;
        }
        try {
            ps.execfile(is);
        }
        catch(Exception e) {
            System.out.println("Warning....................Could not interpret");
            e.printStackTrace();
            return false;
        }
        PyString ret = (PyString) ps.get("RET_VAL");
        String m = ret.asString();
        vars.put("RET_CODE",ps.get("RET_CODE").asString());
        return (m == "FALSE") ? false : true;

    }

   
}

//////////////////////////////////////////////
//
// A Simple Python Script invoker
//
class PyInvoker {

public static boolean Invoke( String filename , String prompt )  {

	final Map<String, Object> map = 
			new HashMap<String,Object>();
	map.put("RET_VAL","TRUE");
        map.put("RET_CODE","");
        map.put("INPUT_DATA",prompt);
        ScriptingInterpreter sp = new ScriptingInterpreter(filename);
        try {
        boolean bRet = sp.Execute(map);
        if ( bRet == false) {  
		System.out.println("Erorr File"); 
		return false; 
	}
        String code= (String) map.get("RET_CODE");
        if ( code == null) {
            return false;
        }
        System.out.println(code);
        return true;
      }
      catch(Exception e ) {
          System.out.println("Exception caught.....");
          return false;

      }
      
}

}
//---------- Execution Context is a Bag
// to transfer state
class EXECUTION_CONTEXT {
    private HashMap<String, Object> symbols 
		= new HashMap<String, Object>();

    public void put(String k, Object v) {
        symbols.put(k, v);
    }

    public Object get(String k) {
        return symbols.get(k);
    }

}


///////////////////////////
// Cannonical Interface for the Plugin Arch.
//
interface IComputationContract {
    public boolean PreExecute(EXECUTION_CONTEXT cont );
    public boolean Execute( EXECUTION_CONTEXT cont );
    public boolean PostExecute( EXECUTION_CONTEXT cont );
}

//////////////////////////////////////
//
// T in ETL
// 
class BaseTransformer implements IComputationContract {
    
    public boolean PreExecute(EXECUTION_CONTEXT cont ) {return true;}
    public boolean Execute( EXECUTION_CONTEXT cont ) { return false; }
    public boolean PostExecute( EXECUTION_CONTEXT cont ) { return true; }
}

////////////////////////////
// E in ETL
//
class InputOrchestrator implements IComputationContract {

    public boolean PreExecute(EXECUTION_CONTEXT cont ) {return true;}
    public boolean Execute( EXECUTION_CONTEXT cont ) { return false; }
    public boolean PostExecute( EXECUTION_CONTEXT cont ) { return true; }
}
//////////////////////////////////////
// L in ETL
//
class OutputOrchestrator implements IComputationContract {

    public boolean PreExecute(EXECUTION_CONTEXT cont ) {return true;}
    public boolean Execute( EXECUTION_CONTEXT cont ) { return false; }
    public boolean PostExecute( EXECUTION_CONTEXT cont ) { return true; }
}
/////////////////////////////////
// Pipeline to integrate all the Types to create an integration
// pipeline
//
class IntegrationPipeLine implements IComputationContract {
    public boolean PreExecute(EXECUTION_CONTEXT cont ) {return true;}
    public boolean Execute( EXECUTION_CONTEXT cont ) { return false; }
    public boolean PostExecute( EXECUTION_CONTEXT cont ) { return true; }
}

////////////////////////////////////////////////////
// Adapter Class which takes care of Src or Destination 
// Endpoints
// 
//
class IntegrationAdapter implements IComputationContract {
    public boolean PreExecute(EXECUTION_CONTEXT cont ) {return true;}
    public boolean Execute( EXECUTION_CONTEXT cont ) { return false; }
    public boolean PostExecute( EXECUTION_CONTEXT cont ) { return true; }
}

class InputAdapter extends IntegrationAdapter {

  public boolean Execute( EXECUTION_CONTEXT cont ) {
                 
		return false; 
  }
}

class OutputAdapter extends IntegrationAdapter {

  public boolean Execute( EXECUTION_CONTEXT cont ) { return false; }
}
//////////////////////////////////////////////////
// REST input Adapter
//
class RESTInputAdapter extends InputAdapter {
     public boolean Execute( EXECUTION_CONTEXT cont ) { 
          System.out.println("Call...Apache Camel....for..input REST");
          return true; 
    }

}

//////////////////////////////////////////////////
// REST output Adapter
//
class RESTOutputAdapter extends OutputAdapter {
     public boolean Execute( EXECUTION_CONTEXT cont ) { 
          System.out.println("Call...Apache Camel....for..output REST");
          return true; 
    }

}

//////////////////////////////////////////////////
// SOAP input Adapter
//
class SOAPInputAdapter extends InputAdapter {
     public boolean Execute( EXECUTION_CONTEXT cont ) { 
          System.out.println("Call...Apache Camel....for..input SOAP");
          return true; 
    }

}

//////////////////////////////////////////////////
// SOAP output Adapter
//
class SOAPOutputAdapter extends OutputAdapter {
     public boolean Execute( EXECUTION_CONTEXT cont ) { 
          System.out.println("Call...Apache Camel....for..output SOAP");
          return true; 
    }

}

//////////////////////////////////////////////////
//  CSV input Adapter
//
class CSVInputAdapter extends InputAdapter {
     public boolean Execute( EXECUTION_CONTEXT cont ) { 
          System.out.println("Call...Apache Camel....for..input CSV");
          return true; 
    }

}

//////////////////////////////////////////////////
// CSV output Adapter
//
class CSVOutputAdapter extends OutputAdapter {
     public boolean Execute( EXECUTION_CONTEXT cont ) { 
          System.out.println("Call...Apache Camel....for..output CSV");
          return true; 
    }

}

//////////////////////////////////////////////////
// DB input Adapter
//
class DBInputAdapter extends InputAdapter {
     public boolean Execute( EXECUTION_CONTEXT cont ) { 
          System.out.println("Call...Apache Camel....for..input DB");
          return true; 
    }

}

//////////////////////////////////////////////////
// DB output Adapter
//
class DBOutputAdapter extends OutputAdapter {
     public boolean Execute( EXECUTION_CONTEXT cont ) { 
          System.out.println("Call...Apache Camel....for..output DB");
          return true; 
    }

}

////////////////////////////////////////////////////
//
// Bridge Class which takes care of the Src or Destination
// Endpoints
class IntegrationBridge implements IComputationContract {
    public boolean PreExecute(EXECUTION_CONTEXT cont ) {return true;}
    public boolean Execute( EXECUTION_CONTEXT cont ) { return false; }
    public boolean PostExecute( EXECUTION_CONTEXT cont ) { return true; }
}


class InputBridge extends IntegrationBridge {

  public boolean Execute( EXECUTION_CONTEXT cont ) { return false; }
}

class OutputBridge extends IntegrationBridge {

  public boolean Execute( EXECUTION_CONTEXT cont ) { return false; }
}

class ODBCOutputBridge extends OutputAdapter {
     public boolean Execute( EXECUTION_CONTEXT cont ) { 
          System.out.println("Call...ODBC Data Src....for..output DB");
          return true; 
    }

}

class ODBCInputBridge extends InputAdapter {
     public boolean Execute( EXECUTION_CONTEXT cont ) { 
          System.out.println("Call...ODBC Data Src....for..input DB");
          return true; 
    }

}


class CURLOutputBridge extends OutputAdapter {
     public boolean Execute( EXECUTION_CONTEXT cont ) { 
          System.out.println("Call...libCurl....for..output REST");
          return true; 
    }

}

class CURLInputBridge extends InputAdapter {
     public boolean Execute( EXECUTION_CONTEXT cont ) { 
          System.out.println("Call...libcurl....for..input REST");
          return true; 
    }

}

////////////////////////////////////////////////////
//
//
class IntegrationContentHandler implements IComputationContract {
    public boolean PreExecute(EXECUTION_CONTEXT cont ) {return true;}
    public boolean Execute( EXECUTION_CONTEXT cont ) { return false; }
    public boolean PostExecute( EXECUTION_CONTEXT cont ) { return true; }
}

class InputContentHandler extends IntegrationContentHandler {

  public boolean Execute( EXECUTION_CONTEXT cont ) { return false; }
}

class OutputContentHandler extends IntegrationContentHandler {

  public boolean Execute( EXECUTION_CONTEXT cont ) { return false; }
}

class PassthroughInputContentHandler extends InputContentHandler {
     public boolean Execute( EXECUTION_CONTEXT cont ) { 
          System.out.println("Call...NULL=input=ContentHandler....");
          return true; 
    }

}

class PassthroughOutputContentHandler extends OutputContentHandler {
     public boolean Execute( EXECUTION_CONTEXT cont ) { 
          System.out.println("Call...NULL=input=ContentHandler....");
          return true; 
    }

}

/////////////////////////
// Stock implementation of InputOchestrator
// These are packaged in external jar (aka Plugin)
//

class ScriptInputPlugin extends InputOrchestrator {
       public boolean Execute( EXECUTION_CONTEXT cont ) {
            System.out.println("......Take input from....CSV File");
            return PyInvoker.Invoke("Test.py","Python Plugin....");

       }

}


class CSVOrchestratorI extends InputOrchestrator {
   public boolean Execute( EXECUTION_CONTEXT cont ) {
            System.out.println("......Take input from....CSV File");
	return true;
            
}
}


class DBOrchestratorI extends InputOrchestrator {
   public boolean Execute( EXECUTION_CONTEXT cont ) {
            System.out.println("......Take input from....DB");
	    return true;  
}
}

class RESTOrchestratorI extends InputOrchestrator {
   public boolean Execute( EXECUTION_CONTEXT cont ) {
            System.out.println("......Take input from....Rest");
            return true;  
   }
}

class SOAPOrchestratorI extends InputOrchestrator {
   public boolean Execute( EXECUTION_CONTEXT cont ) {
            System.out.println("......Take input from....SOAP");
            return true;  
   }
}
///////////////////////////////////////////
//
// Stock implementation of Output Orchestrator
//

class ScriptOutputPlugin extends OutputOrchestrator {
       public boolean Execute( EXECUTION_CONTEXT cont ) {
            
            return PyInvoker.Invoke("Test.py","Python Output Plugin....");

       }

}

class CSVOrchestratorO extends OutputOrchestrator {
   public boolean Execute( EXECUTION_CONTEXT cont ) {
            System.out.println("......PopulateOutput....CSV File");
	return true; 
  }
}

class DBOrchestratorO extends OutputOrchestrator {
   public boolean Execute( EXECUTION_CONTEXT cont ) {
            System.out.println("......Populate....DB");
	return true;   
}
}

class RESTOrchestratorO extends OutputOrchestrator {
   public boolean Execute( EXECUTION_CONTEXT cont ) {
            System.out.println("......Populate....Rest");
return true;
   }
}

class SOAPOrchestratorO extends OutputOrchestrator {
   public boolean Execute( EXECUTION_CONTEXT cont ) {
            System.out.println("......Populate....SOAP");
return true;
   }
}
/////////////////////////////////////
//
// A Null Transformation
//

class ScriptTransformer extends BaseTransformer {
       public boolean Execute( EXECUTION_CONTEXT cont ) {
            
            return PyInvoker.Invoke("Test.py","Transformer Script....");

       }

}
class NullTransformer extends BaseTransformer {
   public boolean Execute( EXECUTION_CONTEXT cont ) {
           System.out.println(".... Transformation ....");
	   return true;
   }
  
}

//////////////////////////////////////
//
// A Basic Orchestrator for the whole Integration
//
//
// class Pipeline
//     - InputOrchestrator
//            - ContentHandler => InputConentHandler ( Java/Jython )
//     - Transformer
//     - OutputOrchestrator
//            - ContentHandler => OutputContentHandler (Java/Jython)
//       
class DataTransformationPipeline extends IntegrationPipeLine {
      private InputOrchestrator _inp = null;
      private OutputOrchestrator _outp = null;
      private BaseTransformer _bt = null;
      public void SetInput(InputOrchestrator pinput ) {
	 _inp = pinput;
      }
      public void SetOutput(OutputOrchestrator poutput ) {
	 _outp = poutput;
      }
      public void SetTransformer(BaseTransformer pbt ) {
	 _bt = pbt;
      }
      public DataTransformationPipeline() {
	
      }
      public boolean Execute( EXECUTION_CONTEXT cont ) {
           if ( _inp == null ) {
		  System.out.println("Execution requires Input");
                  return false;
	   }
           if (!_inp.PreExecute( cont )) {
                      System.out.println("Pre Condition..for Input failed");
                      return false;
                } 

                if (!_inp.Execute( cont ) ){
                      System.out.println("Execution for..for Input failed");
                      return false;
                }
                if (!_inp.PostExecute( cont )) {
                      System.out.println("PostExecution for..for Input failed");
                      return false;
                }  
		if ( _bt != null ) {
                if ( !_bt.Execute(cont) ) {
                      System.out.println("Transformation Failed..");
                      return false;

                  }
                }
                if ( _outp != null ) {
                if (!_outp.Execute( cont )) {
                      System.out.println("Execution for..for Input failed");
                      return false;
                }
                }
                return true;
      }    


}
//////////////////////////////////////
//
//
class IntegrationOrchestrator {

 public boolean Run( InputOrchestrator inp,
                  BaseTransformer bt,
                  OutputOrchestrator outp,
                  EXECUTION_CONTEXT cont ) {

                if (!inp.PreExecute( cont )) {
                      System.out.println("Pre Condition..for Input failed");
                      return false;
                } 

                if (!inp.Execute( cont ) ){
                      System.out.println("Execution for..for Input failed");
                      return false;
                }
                if (!inp.PostExecute( cont )) {
                      System.out.println("PostExecution for..for Input failed");
                      return false;
                }  

                if ( !bt.Execute(cont) ) {
                      System.out.println("Transformation Failed..");
                      return false;

                }

                if (!outp.Execute( cont )) {
                      System.out.println("Execution for..for Input failed");
                      return false;
                }
                return true;
 }
 

}
public class ChistaIntegration {
public  static boolean IsValidParam( String s ) {
   String [] target = { "csv" , "db" , "rest","soap" };
   for( String n : target ) {
        if (n.equals(s) ) { return true; }
   }
   return false;
}

public static  InputOrchestrator ResolveIn(String s ) {
            if ( s.equals("csv") ) {
                   return new CSVOrchestratorI();
            } 
            else if ( s.equals("db" ) ) {
                   return new DBOrchestratorI();
            }
            else if ( s.equals("rest" ) ) {
		   return new RESTOrchestratorI();
            }
            else if (s.equals("soap") ) {
                 return new SOAPOrchestratorI();
            }
            return null;
            
} 

public static  OutputOrchestrator ResolveOut(String s ) {
            if ( s.equals("csv") ) {
                   return new CSVOrchestratorO();
            } 
            else if ( s.equals("db" ) ) {
                   return new DBOrchestratorO();
            }
            else if ( s.equals("rest" ) ) {
		   return new RESTOrchestratorO();
            }
            else if (s.equals("soap") ) {
                 return new SOAPOrchestratorO();
            }
            return null;
            
} 
public static void main(String [] args ) {
	System.out.println("Integration Software.....");
        if ( args.length != 2 ) {
          System.out.println("Invalid number of arguments");
          return;
        }
        if ( !IsValidParam(args[0]) ) {
           System.out.println("....Invalid First Parameter....");
           return; 
        }
        
        if ( !IsValidParam(args[1]) ) {
           System.out.println("....Invalid Second.. Parameter....");
           return; 
        }
        
        InputOrchestrator source = ResolveIn(args[0] );
        OutputOrchestrator dest =  ResolveOut(args[1]);
        BaseTransformer transform = new ScriptTransformer();
        
       /* if ( source == null || dest == null || transform == null ) {
                   System.out.println("Resolution Failed....");
                   return;
        } */

        //IntegrationOrchestrator srt = new IntegrationOrchestrator();
        EXECUTION_CONTEXT ct = new EXECUTION_CONTEXT();
       // if (!srt.Run(source,transform,dest,ct) )
         //     System.out.println("Integration Failed...");
        // else
           //   System.out.println("Integration Succuessfull...");

        DataTransformationPipeline pipe = new DataTransformationPipeline();
        pipe.SetInput(source);
        pipe.SetOutput(dest);
        pipe.SetTransformer(transform);

        if ( !pipe.Execute(ct) ) {
          System.out.println("Integration Failed...");
          return;
        }
        else {
		System.out.println("Integration Successful...");
		return;
        }  

}

}