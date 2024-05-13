package com.chistadata.Domain.ContactMgmt.Transfer;

import com.chistadata.Domain.ContactMgmt.DTO.ContactDTO;
import com.chistadata.Domain.ContactMgmt.DTO.ContactGroupDTO;
import com.chistadata.Domain.ContactMgmt.Entities.Contact;
import com.chistadata.Domain.ContactMgmt.Entities.ContactGroup;
import com.chistadata.Domain.ContactMgmt.VO.AddressVO;
import java.util.ArrayList;
import java.util.List;
//------------------ A Class to Transfer Values between Entities and DTOs
public class TransferValues {
    public static ContactGroup FromContactGroupDTO(ContactGroupDTO s)
    {
        ContactGroup ret = new ContactGroup();
        ret.setGroupId(s.getGroupId());
        ret.setGroupDescription(s.getGroupDescription());
        ret.setGroupName(s.getGroupName());
        return ret;
    }

    public static ContactGroupDTO FromContactGroupDTO2(ContactGroup s)
    {
        ContactGroupDTO ret = new ContactGroupDTO();
        ret.setGroupId(s.getGroupId());
        ret.setGroupDescription(s.getGroupDescription());
        ret.setGroupName(s.getGroupName());
        return ret;
    }

    public static ContactDTO FromContact(Contact s)
    {
        ContactDTO ret = new ContactDTO();
        ret.setContactId(s.getContactId());
        ret.setContactgrpId(s.getContactgrpId());
        ret.setContactName(s.getContactName());
        ret.setContactPhone(s.getContactPhone());
        ret.setContactAddr1(s.getAddress().getaddress1());
        ret.setContactAddr2(s.getAddress().getaddress2());
        ret.setContactAddr3(s.getAddress().getaddress3());
        ret.setContactPhone(s.getContactPhone());

        return ret;
    }

    public static Contact FromContactDTO(ContactDTO s)
    {
        Contact ret = new Contact();
        ret.setContactId(s.getContactId());
        ret.setContactgrpId(s.getContactgrpId());
        ret.setContactName(s.getContactName());
        ret.setContactPhone(s.getContactPhone());
        AddressVO vo = new AddressVO(s.getContactAddr1(),s.getContactAddr2(),s.getContactAddr3());
        ret.setAddress(vo);
        ret.setContactPhone(s.getContactPhone());

        return ret;
    }

    public static List<ContactGroupDTO> FromContactGroupDTOList( List<ContactGroup> lst)
    {
        List<ContactGroupDTO> grp = new ArrayList<>();
        for(ContactGroup cg : lst)
        {
            grp.add(FromContactGroupDTO2(cg));
        }
        return grp;
    }
}
