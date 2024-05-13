package com.chistadata.Domain.ContactMgmt.Facade;

import com.chistadata.Domain.ContactMgmt.DAO.ContactDAO;
import com.chistadata.Domain.ContactMgmt.DAO.ContactGroupDAO;
import com.chistadata.Domain.ContactMgmt.Entities.Contact;
import com.chistadata.Domain.ContactMgmt.Entities.ContactGroup;

public class ContactManagerFacade {

    public static boolean createOrUpdateGroup(ContactGroup groupEntity) throws Exception {
       ContactGroupDAO groupDAO = new ContactGroupDAO();
       boolean result = groupDAO.createOrUpdateGroup(groupEntity);
       return result;
    }

    public static boolean createOrUpdateContact(Contact contactEntity) throws Exception {
       ContactDAO contactDAO = new ContactDAO();
       boolean result = contactDAO.createOrUpdateContact(contactEntity);
        return result;
    }
}