package com.chistadata.Services.ContactMgmt;

import com.chistadata.Domain.ContactMgmt.DAO.ContactDAO;
import com.chistadata.Domain.ContactMgmt.DTO.ContactDTO;
import com.chistadata.Domain.ContactMgmt.Entities.Contact;
import com.chistadata.Domain.ContactMgmt.Transfer.TransferValues;

import java.util.ArrayList;
import java.util.List;

public class ContactManagerService {
    public static boolean IsContactExists( int id) throws Exception
    {
        return (new ContactDAO()).AlreadyExists(id);
    }
    public static List<ContactDTO> RetrieveContacts() throws Exception {
        List<Contact> ret = (new ContactDAO()).ListContact();
        List<ContactDTO> ret_value = new ArrayList<>();
        for(Contact c : ret)
        {
            ret_value.add(TransferValues.FromContact(c));
        }
        return ret_value;

    }

    public static boolean Delete( int id )
    {
        try {
            return (new ContactDAO()).DeleteContact(id);
        }catch(Exception er) { return false; }
    }

    public static String[] getContactNameAsArray()
    {
        return ( new ContactDAO()).getContactNameAsArray();
    }
    public static boolean AddContact( Contact entity)
    {
        try {
            return (new ContactDAO()).createOrUpdateContact(entity);
        }
        catch(Exception er ) { return false; }
    }

    public static ContactDTO getData(String str)
    {
        Contact c =  (new ContactDAO()).getData(str);
        return TransferValues.FromContact(c);
    }
}
