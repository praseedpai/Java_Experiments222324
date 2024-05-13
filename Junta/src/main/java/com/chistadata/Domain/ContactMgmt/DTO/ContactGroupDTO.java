package com.chistadata.Domain.ContactMgmt.DTO;

public class ContactGroupDTO {
    private int groupdId;
    private String groupName;
    private String groupDescription;

    public int getGroupId() {
        return groupdId;
    }

    public void setGroupId(int groupdId) {
        this.groupdId = groupdId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }
}
