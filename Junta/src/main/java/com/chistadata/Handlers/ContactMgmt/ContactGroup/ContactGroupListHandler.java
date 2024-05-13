package com.chistadata.Handlers.ContactMgmt.ContactGroup;

import com.chistadata.Domain.ContactMgmt.DTO.ContactGroupDTO;
import com.chistadata.Handlers.BusinessLogicHandler;
import com.chistadata.Infrastructure.Plugins.COMPUTATION_CONTEXT;
;
import com.chistadata.Presentation.ContactMgmt.ContactGroupManagerPresenter;
import java.util.*;
public class ContactGroupListHandler extends BusinessLogicHandler {
    public boolean PreExecute(COMPUTATION_CONTEXT ctx) {
        return true;
    }
    public boolean Execute(COMPUTATION_CONTEXT ctx) {
        List<ContactGroupDTO>  ls = ContactGroupManagerPresenter.Get();
        for (ContactGroupDTO s : ls)
        {
            System.out.println(s.getGroupDescription());
        }
        return true;
    }
    public boolean PostExecute(COMPUTATION_CONTEXT ctx) {
        return true;
    }
}