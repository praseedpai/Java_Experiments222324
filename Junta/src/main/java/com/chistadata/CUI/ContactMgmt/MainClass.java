package com.chistadata.CUI.ContactMgmt;
import com.chistadata.Infrastructure.Plugins.*;

//
//   ExtensionLoader<MyPlugin> loader = new ExtensionLoader<MyPlugin>();
//   somePlugin = loader.LoadClass("path/to/jar/file", "com.example.pluginXYZ", MyPlugin.class);
//

public class MainClass {
    public static void main(String[] args){
        //-------------- Set up Plugin Files and Folders
        if ( args.length == 0 ) { System.out.println("No command line arguments");  return; }

        com.chistadata.Infrastructure.Plugins.PluginHelpers.SetPluginFileFolder("plugin.xml","D:\\DLT_PROTO\\target\\");
        try {
            // ------ We have made sure that ...somehting will be here
            String sr = args[0];
            if ( sr == null ) { sr= "ListContact";  return ; }
            IComputationCommand c = PluginHelpers.LoadPlugin("plugin.xml", sr,"singleton");
            if ( c == null )
            {
                System.out.println("Failed to Load Plugin....." + sr );
                return;
            }
            COMPUTATION_CONTEXT ctx = new COMPUTATION_CONTEXT();
            if ( sr.equals("ListContact")) {
                ctx.put("SCRIPT_FILE", "scripts/ListContacts.py");
            }
            else {
                ctx.put("SCRIPT_FILE", "scripts/ListContactGroups.py");
            }
            System.out.println("--------Before Execute......");
            c.Execute(ctx);
            System.out.println(ctx.get("RET_VAL"));

        }
        catch(Exception e ) {
            e.printStackTrace();;
            System.out.println("Failed to load the Class");
        }

    }
}
