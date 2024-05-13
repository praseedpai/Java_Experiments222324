package com.chistadata.Presentation.ContactMgmt;

import com.chistadata.Domain.ContactMgmt.DTO.ContactGroupDTO;
import com.chistadata.Domain.ContactMgmt.Entities.ContactGroup;
import com.chistadata.Domain.ContactMgmt.Transfer.TransferValues;
import com.chistadata.Infrastructure.Plugins.COMPUTATION_CONTEXT;
import com.chistadata.Infrastructure.Plugins.PluginHelpers;
import com.chistadata.Services.ContactMgmt.ContactGroupManagerService;

import java.util.*;

public class ContactGroupManagerPresenter {

    public ContactGroupManagerPresenter() {}
    public static boolean DoesExists(int id) throws Exception
    {
        return ContactGroupManagerService.DoesExists(id);
    }
    public static List<ContactGroupDTO> Get() {
        try {
            return ContactGroupManagerService.RetrieveContactGroups();
        }
        catch(Exception e) { System.out.println(e); return null; }
    }

    public static int getContactGroupId( String str)
    {
        return ContactGroupManagerService.GetContactManagerId(str);
    }

    public static ContactGroupDTO getContactGroup( String str)
    {
        return TransferValues.FromContactGroupDTO2(ContactGroupManagerService.getGroup(str));
    }

    public static String []  getGroupNames()
    {
        return ContactGroupManagerService.getGroupNames();
    }
    public static Object [][] getGroupAsArray()
    {
        return ContactGroupManagerService.getGroupAsArray();
    }
    public static List<ContactGroupDTO> GetAllGroups()
    {
        return ContactGroupManagerService.RetrieveContactGroups();
    }
    public static boolean AddViaScript(ContactGroupDTO grp)
    {
        COMPUTATION_CONTEXT ctx = new COMPUTATION_CONTEXT();
        ctx.put("SCRIPT_FILE", "scripts/AddContactGroup.py");
        java.util.Map mv = new java.util.Hashtable();
        ContactGroup s = new ContactGroup();
        s.setGroupId(grp.getGroupId());
        s.setGroupName(grp.getGroupName());
        s.setGroupDescription(grp.getGroupDescription());
        mv.put("contactgrp",s);
        ctx.put("MAP_VARIABLES",mv);
        PluginHelpers.SetPluginFileFolder("plugin.xml","D:\\DLT_PROTO\\target\\");
        boolean result = PluginHelpers.LoadScriptPluginExecute("plugin.xml",
                "AddContactGroup", "singleton", ctx);
        return result;
    }
    public static boolean Delete( int id)
    {
        return ContactGroupManagerService.Delete(id);
    }
    public static boolean DeleteViaPlugin( int id )
    {
        COMPUTATION_CONTEXT ctx = new COMPUTATION_CONTEXT();
        java.util.Map mv = new java.util.Hashtable();
        mv.put("groupid", id);
        ctx.put("MAP_VARIABLES",mv);
        PluginHelpers.SetPluginFileFolder("plugin.xml","D:\\DLT_PROTO\\target\\");
        boolean result = PluginHelpers.LoadBusinessPluginExecute("plugin.xml",
                "DeleteContactGroupPlugin", "singleton", ctx);
        return result;
    }

    public static boolean DeleteViaScript( int id)
    {
        COMPUTATION_CONTEXT ctx = new COMPUTATION_CONTEXT();
        ctx.put("SCRIPT_FILE", "scripts/DeleteContactGroup.py");
        java.util.Map mv = new java.util.Hashtable();
        mv.put("groupid",id);
        ctx.put("MAP_VARIABLES",mv);
        PluginHelpers.SetPluginFileFolder("plugin.xml","D:\\DLT_PROTO\\target\\");
        boolean result = PluginHelpers.LoadScriptPluginExecute("plugin.xml",
                "DeleteContactGroup", "singleton", ctx);
        return result;
    }

}
