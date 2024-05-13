package com.chistadata.Handlers.ContactMgmt.ContactGroup;

import com.chistadata.Handlers.BusinessLogicHandler;
import com.chistadata.Infrastructure.Plugins.COMPUTATION_CONTEXT;
import com.chistadata.Services.ContactMgmt.ContactGroupManagerService;

public class DeleteContactGroupHandler extends BusinessLogicHandler {
    public boolean PreExecute(COMPUTATION_CONTEXT ctx) {
        return true;
    }
    public boolean Execute(COMPUTATION_CONTEXT ctx) {

        java.util.Map sr = (java.util.Map) ctx.get("MAP_VARIABLES");
        if ( sr == null) { return false; }
        Object obj = sr.get("groupid");
        if ( obj == null) {return false; }
        if (!( obj instanceof Integer))
        {
            return false;
        }
        int id = ((Integer)obj).intValue();
        return ContactGroupManagerService.Delete(id);
    }
    public boolean PostExecute(COMPUTATION_CONTEXT ctx) {
        return true;
    }
}
