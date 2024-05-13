package com.chistadata.Infrastructure.Plugins;

import com.chistadata.Handlers.BusinessLogicHandler;
import com.chistadata.Handlers.ScriptingHandler;
import com.chistadata.Infrastructure.Plugins.*;
import com.chistadata.Infrastructure.Plugins.ObjectFactory;

import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

public class PluginHelpers {
    private static ConcurrentHashMap<String,ObjectFactory>  handlers = new ConcurrentHashMap<String,ObjectFactory>();
    private  ObjectFactory fs = null;
    public static boolean SetPluginFileFolder( String filename, String dirname) {
        if ( handlers.get(filename) != null)
            return false;
        handlers.put(filename, new ObjectFactory(filename, dirname));
        return true;
    }

    public  static IComputationCommand LoadPlugin(String filename, String componentname,String mode) {
        try {
            ObjectFactory fs = handlers.get(filename);
            if ( fs == null ) {
                System.out.println("Failed to Load Pluginsv" + componentname);
                return null;
            }
            IComputationCommand c = fs.get(componentname, mode);
            return c;
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean LoadBusinessPluginExecute(String filename, String componentname , String mode,COMPUTATION_CONTEXT ctx)
    {
        IComputationCommand c = LoadPlugin(filename,componentname,mode);
        if ( c == null) return false;

        if (!( c instanceof BusinessLogicHandler))
            return false;

       BusinessLogicHandler d = (BusinessLogicHandler) c;

       boolean result = false;

       if ( d.PreExecute(ctx))
       {
           result = d.Execute(ctx);
           d.PostExecute(ctx);
       }
       return result;
    }

    public static boolean LoadScriptPluginExecute(String filename, String componentname , String mode,COMPUTATION_CONTEXT ctx)
    {
        IComputationCommand c = LoadPlugin(filename,componentname,mode);
        if ( c == null) {
            System.out.println("Fauled to Load Plugin..." + componentname + " ->" + filename);
            return false;
        }

        if (!( c instanceof ScriptingHandler)) {
            System.out.println("IS not an instance of ScriptingHandler");
            return false;
        }

        ScriptingHandler d = (ScriptingHandler) c;

        boolean result = false;

        if ( d.PreExecute(ctx))
        {
            result = d.Execute(ctx);
            d.PostExecute(ctx);
        }
        return result;
    }


}
