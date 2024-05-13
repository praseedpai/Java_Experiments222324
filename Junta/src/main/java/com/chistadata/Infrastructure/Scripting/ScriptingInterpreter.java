package com.chistadata.Infrastructure.Scripting;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

import java.util.Iterator;
import java.util.Map;


public class ScriptingInterpreter {
    private String _filename;
    public ScriptingInterpreter(String filename) {
        _filename = filename;
    }
    private PythonInterpreter createInterpreter(Map vars) {
        PythonInterpreter pi = new PythonInterpreter();
        Iterator it = vars.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry ent = (Map.Entry) it.next();
            pi.set((String) ent.getKey(), ent.getValue());
        }
        return pi;
    }

    public boolean Execute(Map vars) {
        PythonInterpreter ps = createInterpreter(vars);
        ps.execfile(Thread.currentThread().getContextClassLoader().getResourceAsStream(_filename));
        PyString ret = (PyString) ps.get("RET_VAL");
        String m = ret.asString();
        return (m == "FALSE") ? false : true;

    }

    public boolean Execute() {
        PythonInterpreter ps = new PythonInterpreter();
        ps.execfile(Thread.currentThread().getContextClassLoader().getResourceAsStream(_filename));
        return true;
    }
}
