package com.chistadata.Infrastructure.DBEngine;

import java.sql.*;


//////////////////////////////////////
//
// A Simple DAL class
//
//
public class SQLAccess
{

    private  Connection _con = null;
    private Statement  _cmd = null;
    private  ResultSet  _res = null;
    private  String _constr;

    ///////////////////////////////////////
    //
    // Ctor - Takes the driver name as the parameter
    //
    public SQLAccess(String drivername) throws ClassNotFoundException
    {
        Class.forName(drivername);

    }

    ////////////////////////////////////////////////
    //
    // Open the Connection. Once the use is over ,
    // close should be called.
    //
    public boolean Open(String constr) throws SQLException
    {
        _constr = constr;
        if ( _con != null) {
            _con.close();
            _con = null;
        }
        _con = DriverManager.getConnection(_constr);
        return true;
    }

    /////////////////////////////////////////////////
    //
    // Execute a SELECT statement. The caller has to
    // stringify the query and delivered to the routine
    //

    public ResultSet Execute(String SQL) throws SQLException
    {
        if ( _con == null )
            return null;

        if ( _cmd != null )
            _cmd.close();

        _cmd = _con.createStatement();

        if ( _res != null )
            _res.close();

        _res = _cmd.executeQuery(SQL);
        return _res;

    }


    ////////////////////////////////////////////
    //
    //
    // Execute a DELETE OR UPDATE statement
    // ( Non Query statements )
    //
    public boolean ExecuteNonQuery(String SQL)
    {

        if ( _con == null )
            return false;

        try
        {
            if ( _cmd != null )
                _cmd.close();

            _cmd = _con.createStatement();
            _cmd.executeUpdate(SQL);
            return true;
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
            return false;
        }
    }

    //////////////////////////////////
    //
    // Close all the open handles
    //
    public boolean Close() throws SQLException {
        if ( _cmd != null ) {
            _cmd.close();
            _cmd = null;
        }

        if ( _con != null ) {
            _con.close();
            _con = null;
        }

        return true;
    }




}