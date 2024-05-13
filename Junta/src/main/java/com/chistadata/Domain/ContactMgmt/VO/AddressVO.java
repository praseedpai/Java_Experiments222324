package com.chistadata.Domain.ContactMgmt.VO;
//==================== A ValueObject class
//==================== follows the whole value concept
public class AddressVO {
    private String address1;
    private String address2;
    private String address3;

    public AddressVO( String first, String second , String third )
    {
        address1 = first;
        address2 = second;
        address3 = third;
    }

    public String getaddress1() { return address1; }
    public String getaddress2() { return address2; }
    public String getaddress3() { return address3; }
}
