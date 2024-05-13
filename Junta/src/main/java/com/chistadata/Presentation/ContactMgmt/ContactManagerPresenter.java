package com.chistadata.Presentation.ContactMgmt;

import com.chistadata.Domain.ContactMgmt.DTO.ContactDTO;
import com.chistadata.Domain.ContactMgmt.Entities.Contact;
import com.chistadata.Domain.ContactMgmt.Transfer.TransferValues;
import com.chistadata.Infrastructure.Plugins.COMPUTATION_CONTEXT;
import com.chistadata.Infrastructure.Plugins.PluginHelpers;
import com.chistadata.Services.ContactMgmt.ContactManagerService;

import java.util.List;

public class ContactManagerPresenter{
  //  public static final String DRIVER_NAME = "org.sqlite.JDBC";
  //  public static final String DATABASE_PATHNAME = "jdbc:sqlite:D:\\DLT_PROTO\\DATA\\Contacts.DB";
    public ContactManagerPresenter() {}

    public static boolean DoesExists( int id) throws Exception
    {
         return ContactManagerService.IsContactExists(id);
    }
    public static List<ContactDTO> Get() {
        try {
            return ContactManagerService.RetrieveContacts();
        }
        catch(Exception e) { System.out.println(e); return null; }
    }


    public static ContactDTO getData(String str)
    {

        return ContactManagerService.getData(str);
    }
    public static String[] getContactNameAsArray()
    {
        return ContactManagerService.getContactNameAsArray();
    }
    public static boolean AddContact( ContactDTO entity)
    {
        Contact entity2 = TransferValues.FromContactDTO(entity);
        return ContactManagerService.AddContact(entity2);
    }

    public static boolean AddContactViaScrpt( ContactDTO entity)
    {
        Contact entity2 = TransferValues.FromContactDTO(entity);
        COMPUTATION_CONTEXT ctx = new COMPUTATION_CONTEXT();
        ctx.put("SCRIPT_FILE", "scripts/AddContact.py");
        java.util.Map mv = new java.util.Hashtable();
        mv.put("contact",entity2);
        ctx.put("MAP_VARIABLES",mv);
        PluginHelpers.SetPluginFileFolder("plugin.xml","D:\\DLT_PROTO\\target\\");
        boolean result = PluginHelpers.LoadScriptPluginExecute("plugin.xml",
                "AddContact", "singleton", ctx);
        return result;

    }

    public static boolean Delete(int id) {
        return ContactManagerService.Delete(id);
    }
    public static boolean DeleteViaPlugin(int id) {
        COMPUTATION_CONTEXT ctx = new COMPUTATION_CONTEXT();
        java.util.Map mv = new java.util.Hashtable();
        mv.put("contactid", id);
        ctx.put("MAP_VARIABLES",mv);
        PluginHelpers.SetPluginFileFolder("plugin.xml","D:\\DLT_PROTO\\target\\");
        boolean result = PluginHelpers.LoadBusinessPluginExecute("plugin.xml",
                "DeleteContactPlugin", "singleton", ctx);
        return result;
    }
}

