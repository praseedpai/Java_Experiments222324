package com.chistadata.GUI.ContactManagement;

import com.chistadata.Domain.ContactMgmt.DTO.ContactGroupDTO;
import com.chistadata.Domain.ContactMgmt.Entities.ContactGroup;
import com.chistadata.GUI.Utilties.GUIHelpers;
import com.chistadata.Presentation.ContactMgmt.ContactGroupManagerPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContactGroupFormDelete extends JFrame implements ActionListener {
    private JLabel  Selectlbl = new JLabel("Select Value");
    private JComboBox GroupNamebox = null;
    private JLabel groupIdLabel;
    private JLabel groupNameLabel;
    private JLabel groupDescriptionLabel;
    private JTextField groupIdText;
    private JTextField groupNameText;
    private JTextField groupDescriptionText;
    private JLabel outputLabel = new JLabel("");
    private JButton button = new JButton("Submit");
    private JButton button_list = new JButton("List");
    private String _formmode = "DELETE";
    public ContactGroupFormDelete(String pformmode) throws Exception{
        super("Group Form -" + pformmode);
        _formmode = pformmode;

        String names[] = ContactGroupManagerPresenter.getGroupNames();
        GroupNamebox = new JComboBox(names);
       groupIdLabel = new JLabel("Group ID");
       groupIdText = new JTextField(5);


        groupNameLabel = new JLabel("Group Name");
        groupNameText = new JTextField(20);
        groupDescriptionLabel = new JLabel("Group Description");
        groupDescriptionText = new JTextField(20);

        setLayout(new GridLayout(7,2));
        add(Selectlbl);
        add(GroupNamebox);
        add(groupIdLabel);
        add(groupIdText);
        add(groupNameLabel);
        add(groupNameText);
        add(groupDescriptionLabel);
        add(groupDescriptionText);
        add(outputLabel);
        add(button);
        add(button_list);
        button.addActionListener(this);
        button_list.addActionListener(this);
        CalibrateUI(_formmode);
        setSize(400, 400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        GroupNamebox.addActionListener(this);
    }
    public void CalibrateUI(String mode)
    {
        if ( mode == "DELETE" )
        {
            this.button.setText(mode);
            groupDescriptionText.setEditable(false);
            groupNameText.setEditable(false);
            groupIdText.setEditable(false);
        }
        else {
            this.button.setText(mode);
        }
    }
    public static void viewHandler(ContactGroupFormDelete groupForm) throws Exception {
        ContactGroup entity = getEntityFromUI(groupForm);
        if(entity.equals(null)) {
            System.out.println("Error in input");
            return;
        }

        if (!ContactGroupManagerPresenter.DoesExists(entity.getGroupId()))
        {
            JOptionPane.showMessageDialog(null, "Group Does not  Exists");
            return;
        }
        boolean result = false;
        if ( ContactGroupManagerPresenter.DeleteViaPlugin(entity.getGroupId()))
        {
            JOptionPane.showMessageDialog(null, "Group Deleted");
            GUIHelpers.PopulateComboBox(groupForm.GroupNamebox,ContactGroupManagerPresenter.getGroupNames());
            result =true;
        }


       // COMPUTATION_CONTEXT ctx = new COMPUTATION_CONTEXT();
      //  ctx.put("SCRIPT_FILE", "scripts/AddContactGroup.py");
      //  java.util.Map mv = new java.util.Hashtable();
      //  mv.put("contactgrp",entity);
     //   ctx.put("MAP_VARIABLES",mv);
     //   PluginHelpers.SetPluginFileFolder("plugin.xml","D:\\DLT_PROTO\\target\\");
    //    IComputationCommand handler = PluginHelpers.LoadPlugin("plugin.xml", "AddContactGroup","singleton");

      //  boolean result = handler.Execute(ctx);



        //boolean result = ContactManagerFacade.createOrUpdateGroup(entity);

        if(result) {
            System.out.println("success!");
            groupForm.outputLabel.setText("success!");
        } else {
            System.out.println("failed");
            groupForm.outputLabel.setText("failed!");
        }

    }

    public static ContactGroup getEntityFromUI(ContactGroupFormDelete groupForm) {
        ContactGroup groupEntity = new ContactGroup();
        groupEntity.setGroupId(Integer.parseInt(groupForm.groupIdText.getText()));
        groupEntity.setGroupName(groupForm.groupNameText.getText());
        groupEntity.setGroupDescription(groupForm.groupDescriptionText.getText());
        JOptionPane.showMessageDialog(null,groupEntity.getGroupDescription());
        return groupEntity;
    }

    public static ContactGroupDTO setUIFromEntity(ContactGroupFormDelete groupForm, ContactGroupDTO gr) {
       // ContactGroup groupEntity = new ContactGroup();
      //  groupEntity.setGroupId(Integer.parseInt(groupForm.groupIdText.getText()));
      //   groupEntity.setGroupName(groupForm.groupNameText.getText());
      //  groupEntity.setGroupDescription(groupForm.groupDescriptionText.getText());
      //  JOptionPane.showMessageDialog(null,groupEntity.getGroupDescription());
        int grid = gr.getGroupId();

        groupForm.groupIdText.setText(Integer.toString(grid));
        groupForm.groupNameText.setText(gr.getGroupName());
        groupForm.groupDescriptionText.setText(gr.getGroupDescription());
        return gr;
    }

    @Override
    public void actionPerformed(ActionEvent e)  {
        Object obj = e.getSource();

        if ( obj instanceof  JComboBox)
        {
            JComboBox box = (JComboBox) obj;
            String f = (String) box.getSelectedItem();
            if ( f == null) {
                JOptionPane.showMessageDialog(null,"Failed to Retrieve Selection");
                return;
            }
            ContactGroupDTO grp = ContactGroupManagerPresenter.getContactGroup(f);
            if (grp == null ) {
                JOptionPane.showMessageDialog(null,"Failed to Retrieve Selection "+f);
                return;
            }
            ContactGroupFormDelete.setUIFromEntity((ContactGroupFormDelete)this,grp);
            return;

        }
        if ( e.getActionCommand() == "List")
        {
            try {
                ContactGroupListWindow lst = new ContactGroupListWindow();
                lst.setVisible(true);
            }
            catch(Exception e2) { e2.printStackTrace(); }
            return;
        }

        System.out.println(e);
        outputLabel.setText("Creating a group");
        try {
            viewHandler(this);
        }
        catch(Exception e3) {
           e3.printStackTrace();
        }
    }

    private class ItemChanged implements ActionListener {
        public void actionPerformed(ActionEvent e){

        }
    }
}