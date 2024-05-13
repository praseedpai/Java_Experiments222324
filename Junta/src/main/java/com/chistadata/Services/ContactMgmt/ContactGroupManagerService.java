package com.chistadata.Services.ContactMgmt;
import com.chistadata.Domain.ContactMgmt.DAO.ContactDAO;
import com.chistadata.Domain.ContactMgmt.DTO.ContactGroupDTO;
import com.chistadata.Domain.ContactMgmt.Entities.Contact;
import com.chistadata.Domain.ContactMgmt.Entities.ContactGroup;
import com.chistadata.Domain.ContactMgmt.DAO.ContactGroupDAO;
import com.chistadata.Domain.ContactMgmt.Transfer.TransferValues;

import java.util.ArrayList;
import java.util.List;

public class ContactGroupManagerService {
    public static String[ ] getGroupNames()
    {
        return (new ContactGroupDAO()).getGroupNameAsArray();
    }

    public static Object [][] getGroupAsArray()
    {
        try {
            return (new ContactGroupDAO()).getGroupAsArray();
        }
        catch(Exception er) { return null; }
    }
    public static boolean CreateGroup(ContactGroup a) throws Exception
    {
        return ( new ContactGroupDAO()).createOrUpdateGroup(a);
    }
    public static List<ContactGroupDTO> RetrieveContactGroups()
    {
        try {
            List<ContactGroup> lst =  (new ContactGroupDAO()).getGroups();
            List<ContactGroupDTO> ret = new ArrayList<>() ;
            for ( ContactGroup rs : lst)
            {
                ret.add(TransferValues.FromContactGroupDTO2(rs));

            }
            return ret;

        }catch(Exception er) { er.toString(); return null; }


    }

    public static  ContactGroup getGroup(String str)
    {
        return (new ContactGroupDAO()).getData(str);
    }
    public static  boolean DoesExists(int id) throws Exception
    {
        return (new ContactGroupDAO()).AlreadyExists(id);
    }

    public static boolean CanWeDelete(int id) throws Exception
    {
        List<Contact> lcs = (new ContactDAO()).ListContact();
        for( Contact g : lcs)
        {
            if ( g.getContactgrpId() == id)
                return false;
        }
        return true;
    }

    public static int GetContactManagerId( String str)
    {
        ContactGroup grp = null;
        try {
          grp =   (new ContactGroupDAO()).getData(str);
          if ( grp == null) { return -1; }
          return grp.getGroupId();
        }
        catch(Exception er) { er.toString(); return -1; }

    }
    public static boolean Delete( int id)
    {

        try {
            if ( !(CanWeDelete(id)))
                return false;
            return (new ContactGroupDAO()).DeleteContactGroup(id);
        }
        catch(Exception er)
        {
            er.printStackTrace();
            return false;
        }
    }
}
