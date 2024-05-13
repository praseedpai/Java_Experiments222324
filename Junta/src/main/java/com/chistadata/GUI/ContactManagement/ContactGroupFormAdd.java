package com.chistadata.GUI.ContactManagement;

import com.chistadata.Domain.ContactMgmt.DTO.ContactGroupDTO;
import com.chistadata.Presentation.ContactMgmt.ContactGroupManagerPresenter;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContactGroupFormAdd extends JFrame implements ActionListener {
    private JLabel groupIdLabel;
    private JLabel groupNameLabel;
    private JLabel groupDescriptionLabel;
    private JTextField groupIdText;
    private JTextField groupNameText;
    private JTextField groupDescriptionText;
    private JLabel outputLabel = new JLabel("");
    private JButton button = new JButton("Submit");
    private JButton button_list = new JButton("List");
    private String _formmode = "ADD";
    public ContactGroupFormAdd(String pformmode) {
        super("Group Form -" + pformmode);
        _formmode = pformmode;
       groupIdLabel = new JLabel("Group ID");
       groupIdText = new JTextField(5);


        groupNameLabel = new JLabel("Group Name");
        groupNameText = new JTextField(20);
        groupDescriptionLabel = new JLabel("Group Description");
        groupDescriptionText = new JTextField(20);

        setLayout(new GridLayout(6,2));
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
    }
    public void CalibrateUI(String mode)
    {
        if ( mode == "DELETE" || mode == "EDIT")
        {
            this.button.setText(mode);
            groupDescriptionText.setEditable(false);
            groupNameText.setEditable(false);
        }
        else {
            this.button.setText(mode);
        }
    }
    public static void viewHandler(ContactGroupFormAdd groupForm) throws Exception {
        ContactGroupDTO entity = getEntityFromUI(groupForm);
        if(entity.equals(null)) {
            System.out.println("Error in input");
            return;
        }

        if (ContactGroupManagerPresenter.DoesExists(entity.getGroupId()))
        {
            JOptionPane.showMessageDialog(null, "Group Already Exists");
            return;
        }





        boolean result = ContactGroupManagerPresenter.AddViaScript(entity);

        if(result) {
            System.out.println("success!");
            groupForm.outputLabel.setText("success!");
        } else {
            System.out.println("failed");
            groupForm.outputLabel.setText("failed!");
        }

    }

    public static ContactGroupDTO getEntityFromUI(ContactGroupFormAdd groupForm) {
        ContactGroupDTO groupEntity = new ContactGroupDTO();
        groupEntity.setGroupId(Integer.parseInt(groupForm.groupIdText.getText()));
        groupEntity.setGroupName(groupForm.groupNameText.getText());
        groupEntity.setGroupDescription(groupForm.groupDescriptionText.getText());
        JOptionPane.showMessageDialog(null,groupEntity.getGroupDescription());
        return groupEntity;
    }

    @Override
    public void actionPerformed(ActionEvent e)  {
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
}