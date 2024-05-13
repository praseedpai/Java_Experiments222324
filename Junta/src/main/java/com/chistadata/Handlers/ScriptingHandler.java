package com.chistadata.Handlers;

import com.chistadata.Infrastructure.Plugins.COMPUTATION_CONTEXT;
import com.chistadata.Infrastructure.Plugins.IComputationCommand;
import com.chistadata.Infrastructure.Scripting.ScriptingInterpreter;

public class ScriptingHandler implements IComputationCommand {
    public boolean PreExecute(COMPUTATION_CONTEXT ctx) {
        return true;
    }
    public boolean Execute(COMPUTATION_CONTEXT ctx) {
        String filename = (String) ctx.get("SCRIPT_FILE");
        if ( filename == null ) { System.out.println("Scripting fiel not foind"); return false; }
        java.util.Map sr = (java.util.Map) ctx.get("MAP_VARIABLES");
        sr.put("RET_VAL","FALSE");
        ScriptingInterpreter sp = new ScriptingInterpreter(filename);
        boolean bRet = sp.Execute(sr);
        return bRet;
    }
    public boolean PostExecute(COMPUTATION_CONTEXT ctx) {
        return true;
    }
}
