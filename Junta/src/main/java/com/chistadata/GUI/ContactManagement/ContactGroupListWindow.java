package com.chistadata.GUI.ContactManagement;

import com.chistadata.Presentation.ContactMgmt.ContactGroupManagerPresenter;

import javax.swing.*;
import java.awt.*;

public class ContactGroupListWindow extends JFrame  {
    ContactGroupListWindow() throws Exception
    {
        String[] columnNames = {"ID", "Group Name", "Group Description",};
        Object data[][] = ContactGroupManagerPresenter.getGroupAsArray();
        final JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        this.setSize(600,700);
        add(table);

    }

}
