package com.chistadata.Domain.ContactMgmt.DTO;

public class ContactDTO {
    private int contactId;
    private int contactgrpid;
    private String contactName;
    private String contactPhone;
    private String contactAddr1;
    private String contactAddr2;
    private String contactAddr3;
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

    public String getContactAddr1() {
        return contactAddr1;
    }

    public void setContactAddr1(String contactAddr1) {
        this.contactAddr1 = contactAddr1;
    }

    public String getContactAddr2() {
        return contactAddr2;
    }

    public void setContactAddr2(String contactAddr2) {
        this.contactAddr2 = contactAddr2;
    }

    public String getContactAddr3() {
        return contactAddr3;
    }

    public void setContactAddr3(String contactAddr3) {
        this.contactAddr3 = contactAddr3;
    }

    public String getContactPin() {
        return contactPin;
    }

    public void setContactPin(String contactPin) {
        this.contactPin = contactPin;
    }
}
