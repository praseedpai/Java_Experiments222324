package com.chistadata.GUI.ContactManagement;

import com.chistadata.Domain.ContactMgmt.DAO.ContactDAO;

import javax.swing.*;
import java.awt.*;

public class ContactListWindow extends JFrame  {
    ContactListWindow() throws Exception
    {
        String[] columnNames = {"ID", "Group Id", "Group Name",};
        Object data[][] = (new ContactDAO()).getContactAsArray();
        final JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        this.setSize(600,700);
        add(table);

    }

}
