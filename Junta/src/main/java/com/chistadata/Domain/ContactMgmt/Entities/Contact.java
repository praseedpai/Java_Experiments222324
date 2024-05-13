package com.chistadata.Domain.ContactMgmt.Entities;

import com.chistadata.Domain.ContactMgmt.VO.AddressVO;


public class Contact {
    private int contactId;
    private int contactgrpid;
    private String contactName;
    private String contactPhone;
    private AddressVO address; //Value Object

    private String  contactPin;

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public int getContactgrpId() {
        return contactgrpid;
    }

    public void setContactgrpId(int contactId) {
        this.contactgrpid = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public AddressVO getAddress() { return this.address; }
    public void setAddress(AddressVO vo) {
        this.address = vo;
    }
    public String getContactPin() {
        return contactPin;
    }

    public void setContactPin(String contactPin) {
        this.contactPin = contactPin;
    }
}
