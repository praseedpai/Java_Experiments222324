package com.chistadata.Domain.ContactMgmt.DAO;

import com.chistadata.Domain.ContactMgmt.Entities.Contact;
import com.chistadata.Domain.ContactMgmt.VO.AddressVO;
import com.chistadata.Infrastructure.DBEngine.SQLAccess;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
//---------------------- Contact related logic
public class ContactDAO extends BaseDAO {
    //--------------------- Create Query to Insert Contacts
    public String CreateQueryInsert(Contact contactEntity)
    {
        String QUERY_TO_EXECUTE = "insert into Contacts values (" + contactEntity.getContactId() +","
                + contactEntity.getContactgrpId() +",'" + contactEntity.getContactName() + "','"
                +contactEntity.getContactPhone()+"','"  + contactEntity.getAddress().getaddress1() + "','"
                + contactEntity.getAddress().getaddress2() + "','"
                + contactEntity.getAddress().getaddress3() + "','" + contactEntity.getContactPin() + "');";
        return QUERY_TO_EXECUTE;
    }
    //------------------ Create Query to Update Contacts
    public String CreateQueryUpdate(Contact contactEntity)
    {
        String QUERY_TO_EXECUTE = "Update Contacts set  contactid = " + contactEntity.getContactId() +","
              + "contactgrpid = " + contactEntity.getContactgrpId() +",contactname = '" + contactEntity.getContactName()
                + "',contactphone = ' " +contactEntity.getContactPhone()+"',contactAddr1 = '"  + contactEntity.getAddress().getaddress1()
                + "',contactAddr2 = '" + contactEntity.getAddress().getaddress2() + "',contactAddr3 = '"
                + contactEntity.getAddress().getaddress3() + "',contactPin = '" + contactEntity.getContactPin() + "' where " +
                "contactid = " + contactEntity.getContactId() + ";";
        return QUERY_TO_EXECUTE;
    }
    //--------------------- Create Query to Delete Contacts
    public String CreateQueryDelete( int id)
    {
        String QUERY_TO_EXECUTE = "delete from Contacts where contactid =" + id;
        return QUERY_TO_EXECUTE;
    }
    //--------------------- Create Query to Delete ContactGroup

    public String CreateQuerySelect(int id) {
        String QUERY_TO_EXECUTE = "select * from Contacts where contactid =" + id;
        return QUERY_TO_EXECUTE;
    }

    public String CreateQuerySelectFromName(String name) {
        String QUERY_TO_EXECUTE = "select * from Contacts where contactname = '" + name+"'";
        return QUERY_TO_EXECUTE;
    }
    public boolean createOrUpdateContact(Contact contactEntity) throws Exception {
        SQLAccess s = new SQLAccess(DRIVER_NAME);

        if ( !s.Open(DATABASE_PATHNAME))
            return false;

        String QUERY_TO_EXECUTE = CreateQueryInsert(contactEntity);
        System.out.println(QUERY_TO_EXECUTE);
        boolean bresult =  s.ExecuteNonQuery(QUERY_TO_EXECUTE);
        s.Close();
        return bresult;

    }

    public boolean DeleteContact( int id) throws Exception {
        SQLAccess s = new SQLAccess(DRIVER_NAME);
        if ( !s.Open(DATABASE_PATHNAME))
            return false;
        String QUERY_TO_EXECUTE = CreateQueryDelete(id);
        System.out.println(QUERY_TO_EXECUTE);
        boolean ret =  s.ExecuteNonQuery(QUERY_TO_EXECUTE);
        s.Close();
        return ret;
    }

    public boolean UpdateContact( int id , Contact cs) throws Exception {
        SQLAccess s = new SQLAccess(DRIVER_NAME);
        if ( !s.Open(DATABASE_PATHNAME))
            return false;
        String QUERY_TO_EXECUTE = CreateQueryUpdate(cs);
        boolean bresult =  s.ExecuteNonQuery(QUERY_TO_EXECUTE);
        s.Close();
        return bresult;

    }

    public List<Contact> ListContact( ) throws Exception {
        SQLAccess s = new SQLAccess(DRIVER_NAME);
        if ( !s.Open(DATABASE_PATHNAME))
            return null;
        ResultSet rs = s.Execute("Select * from Contacts");
        if ( rs == null) { return null; }
        List<Contact> retval = new ArrayList<Contact>();
        while (rs.next()) {
            int cid = rs.getInt(1);
            int cgid = rs.getInt(2);
            String cgname = rs.getString(3);
            String phone = rs.getString(4);
            String addr1 = rs.getString(5);
            String addr2 = rs.getString(6);
            String addr3 = rs.getString(7);
            String pin   = rs.getString(8);
            Contact temp = new Contact();
            temp.setContactId(cid);
            temp.setContactgrpId(cgid);
            temp.setContactName(cgname);
            temp.setContactPhone(phone);
            AddressVO vo = new AddressVO(addr1,addr2,addr3);
            temp.setAddress(vo);
            temp.setContactPin(pin);
            retval.add(temp);

        }
        rs.close();
        s.Close();
        return retval;


    }
    //-------------------- Get Contact as Array
    public Object[][] getContactAsArray() throws Exception {
        java.util.List<Contact> rs = this.ListContact();
        Object data[][] = new Object[rs.size()][3];

        int i = 0;
        for (Contact cg : rs) {
            Object temp[] = new Object[]{cg.getContactId(), cg.getContactgrpId(), cg.getContactName()};
            data[i] = temp;
            i++;
        }
        return data;
    }
    //------------------- Get Name as Array
    //------------------
    public String[] getContactNameAsArray()  {
        try {
            java.util.List<Contact> rs = ListContact();
            String data[] = new String[rs.size()];
            int i = 0;
            for (Contact cg : rs) {
                data[i] = cg.getContactName();
                i++;
            }
            return data;
        }
        catch(Exception e ) { e.toString(); return new String[] {"NULL"}; }
    }

    public  String getContactNamefromId(int id) throws Exception
    {
        SQLAccess s = new SQLAccess(DRIVER_NAME);

        if ( !s.Open(DATABASE_PATHNAME))
            return null;

        ResultSet rs = s.Execute(CreateQuerySelect(id));
        if ( rs == null) return null;
        String ns = null;
        if (rs.next())
            ns =  rs.getString(3);
        rs.close();
        s.Close();
        return ns;

    }
    //----------------- Check whether an Id exists
    public boolean AlreadyExists( int id) throws Exception
    {
        return getContactNamefromId(id) != null;
    }

    public Contact getData(String contactname) {
        try {
            SQLAccess s = new SQLAccess(DRIVER_NAME);

            if (!s.Open(DATABASE_PATHNAME))
                return null;

            ResultSet rs = s.Execute(CreateQuerySelectFromName(contactname));
            if (rs == null)  return null;
            Contact temp = null;
            if (rs.next()) {
                int cid = rs.getInt(1);
                int cgid = rs.getInt(2);
                String cgname = rs.getString(3);
                String phone = rs.getString(4);
                String addr1 = rs.getString(5);
                String addr2 = rs.getString(6);
                String addr3 = rs.getString(7);
                String pin   = rs.getString(8);
                temp = new Contact();
                temp.setContactId(cid);
                temp.setContactgrpId(cgid);
                temp.setContactName(cgname);
                temp.setContactPhone(phone);
                AddressVO vo = new AddressVO(addr1,addr2,addr3);
                temp.setAddress(vo);

                temp.setContactPin(pin);
            }
            rs.close();
            s.Close();
            return temp;

        }
        catch( Exception er )
        {
            return null;
        }

    }
}
