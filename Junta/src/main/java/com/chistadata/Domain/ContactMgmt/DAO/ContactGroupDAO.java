package com.chistadata.Domain.ContactMgmt.DAO;

import com.chistadata.Domain.ContactMgmt.Entities.ContactGroup;
import com.chistadata.GUI.Utilties.GUIHelpers;
import com.chistadata.Infrastructure.DBEngine.SQLAccess;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactGroupDAO extends BaseDAO {

    //--------------------- Create Query to Insert ContactGroup
    public String CreateQueryInsert(ContactGroup groupEntity) {
        String QUERY_TO_EXECUTE = "insert into ContactGroup values (" + groupEntity.getGroupId() + ",'"
                + groupEntity.getGroupName() + "','" + groupEntity.getGroupDescription() + "');";
        return QUERY_TO_EXECUTE;
    }

    //------------------ Create Query to Update ContactGroup
    public String CreateQueryUpdate(ContactGroup cs) {
        String QUERY_TO_EXECUTE = "Update ContactGroup set  groupname ='" + cs.getGroupName() + "',"
                + " groupDescription = '" + cs.getGroupDescription() + "'" + " WHERE groupid = " + cs.getGroupId() + ";";
        return QUERY_TO_EXECUTE;
    }

    //--------------------- Create Query to Delete ContactGroup
    public String CreateQueryDelete(int id) {
        String QUERY_TO_EXECUTE = "delete from ContactGroup where groupid =" + id;
        return QUERY_TO_EXECUTE;
    }

    public String CreateQuerySelect(int id) {
        String QUERY_TO_EXECUTE = "select * from ContactGroup where groupid =" + id;
        return QUERY_TO_EXECUTE;
    }

    public String CreateQuerySelectFromName(String name) {
        String QUERY_TO_EXECUTE = "select * from ContactGroup where groupname = '" + name+"'";
        return QUERY_TO_EXECUTE;
    }

    //
    // ------------------------ Retrieve data for JGrid
    public Object[][] getGroupAsArray() throws Exception {
        java.util.List<ContactGroup> rs = getGroups();
        Object data[][] = new Object[rs.size()][3];

        int i = 0;
        for (ContactGroup cg : rs) {
            Object temp[] = new Object[]{cg.getGroupId(), cg.getGroupName(), cg.getGroupDescription()};
            data[i] = temp;
            i++;
        }
        return data;
    }

    //------------------
    public String[] getGroupNameAsArray()  {
        try {
            java.util.List<ContactGroup> rs = getGroups();
            String data[] = new String[rs.size()];

            int i = 0;
            for (ContactGroup cg : rs) {

                data[i] = cg.getGroupName();
                i++;
            }
            return data;
        }
        catch ( Exception e ) { e.toString(); return new  String [] {"EMPTY"} ; }
    }

    public ContactGroup getData(String groupname) {
        try {
            SQLAccess s = new SQLAccess(DRIVER_NAME);

            if (!s.Open(DATABASE_PATHNAME))
                return null;

            ResultSet rs = s.Execute(CreateQuerySelectFromName(groupname));
            if (rs == null)  return null;
            ContactGroup ns = null;
            if (rs.next()) {
                ns = new ContactGroup();
                ns.setGroupId(rs.getInt(1));
                ns.setGroupName(rs.getString(2));
                ns.setGroupDescription(rs.getString(3));
            }
            rs.close();
            s.Close();
            return ns;

        }
    catch( Exception er )
    {
        return null;
    }

}
    //-------------------- Get Name for the Contact id
    public  String getNamefromId(int id) throws Exception
    {
        SQLAccess s = new SQLAccess(DRIVER_NAME);

        if ( !s.Open(DATABASE_PATHNAME))
            return null;

        ResultSet rs = s.Execute(CreateQuerySelect(id));
        if ( rs == null) return null;
        String ns = null;
        if (rs.next())
           ns =  rs.getString(2);
        rs.close();
        s.Close();
        return ns;

    }
    //----------------- Check whether an Id exists
    public boolean AlreadyExists( int id) throws Exception
    {
        return getNamefromId(id) != null;
    }
    //==================== Retrieve Groupss
    public List<ContactGroup> getGroups() throws Exception{
        SQLAccess s = new SQLAccess(DRIVER_NAME);

        if ( !s.Open(DATABASE_PATHNAME))
            return null;


        ResultSet rs = s.Execute("Select * from ContactGroup");
        if ( rs == null) { return null; }
        List<ContactGroup> retval = new ArrayList<ContactGroup>();
        while (rs.next()) {
            int cgid = rs.getInt(1);
            String cgname = rs.getString(2);
            String cgdescription = rs.getString(3);
            ContactGroup temp = new ContactGroup();
            temp.setGroupId(cgid);
            temp.setGroupName(cgname);
            temp.setGroupDescription(cgdescription);
            retval.add(temp);

        }
        rs.close();
        s.Close();
        return retval;

    }

    public boolean createOrUpdateGroup(ContactGroup groupEntity) throws Exception {
        SQLAccess s = new SQLAccess(DRIVER_NAME);

        if ( !s.Open(DATABASE_PATHNAME)) {
            throw new Exception(DATABASE_PATHNAME + "cannot open file");
        }
        String QUERY_TO_EXECUTE =CreateQueryInsert(groupEntity);
        GUIHelpers.MessageBox(QUERY_TO_EXECUTE);
        boolean result =  s.ExecuteNonQuery(QUERY_TO_EXECUTE);
        s.Close();
        return result;


    }

    public boolean DeleteContactGroup( int id) throws Exception {
        SQLAccess s = new SQLAccess(DRIVER_NAME);
        if ( !s.Open(DATABASE_PATHNAME))
            return false;
        String QUERY_TO_EXECUTE = CreateQueryDelete(id);
        boolean result =  s.ExecuteNonQuery(QUERY_TO_EXECUTE);
        s.Close();
        return result;
    }

    public boolean UpdateContactGroup( int id , ContactGroup cs) throws  Exception {
        SQLAccess s = new SQLAccess(DRIVER_NAME);
        if ( !s.Open(DATABASE_PATHNAME))
            return false;
        String QUERY_TO_EXECUTE = CreateQueryUpdate(cs);
        boolean result =  s.ExecuteNonQuery(QUERY_TO_EXECUTE);
        s.Close();
        return result;

    }
}
