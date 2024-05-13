package com.chistadata.Infrastructure.Plugins;
import java.util.HashMap;

public class COMPUTATION_CONTEXT {
    private HashMap<String, Object> symbols = new HashMap<String, Object>();

    public void put(String k, Object v) {
        symbols.put(k, v);
    }

    public Object get(String k) {
        return symbols.get(k);
    }
}

