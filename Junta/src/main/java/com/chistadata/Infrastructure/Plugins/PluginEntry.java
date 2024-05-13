package com.chistadata.Infrastructure.Plugins;

public class PluginEntry {
    public String key;
    public String packagename;
    public String assemblyname;
    public PluginEntry( String pkey, String packages, String assembly)
    {
        key = pkey;
        packagename = packages;
        assemblyname = assembly;
    }
}
