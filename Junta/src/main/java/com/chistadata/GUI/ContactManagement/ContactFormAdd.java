package com.chistadata.GUI.ContactManagement;


import com.chistadata.Domain.ContactMgmt.DTO.ContactDTO;
import com.chistadata.GUI.Utilties.GUIHelpers;
import com.chistadata.Presentation.ContactMgmt.ContactGroupManagerPresenter;
import com.chistadata.Presentation.ContactMgmt.ContactManagerPresenter;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

public class ContactFormAdd extends JFrame implements ActionListener {
    private JLabel contactIdLabel;
    private JLabel contactGrpIdLabel;
    private JComboBox contactGrpIdText;
    private JLabel contactNameLabel;
    private JLabel contactPhoneLabel;
    private JLabel contactAddr1Label;
    private JLabel contactAddr2Label;
    private JLabel contactAddr3Label;
    private JLabel contactPinLabel;

    private JTextField contactIdText;
    private JTextField contactNameText;
    private JFormattedTextField contactPhoneText;
    private JTextField contactAddr1Text;
    private JTextField contactAddr2Text;
    private JTextField contactAddr3Text;
    private JFormattedTextField contactPinText;

    private JLabel outputLabel = new JLabel("");
    private JButton button = new JButton("Submit");
    private JLabel listindent = new JLabel("      ");
    private JButton ListButton = new JButton("LIST");
    private String _formmode = "ADD";
    public ContactFormAdd(String pformmode) {


        super("Contact Form -" + pformmode);
        _formmode= pformmode;

        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(false);
        NumberFormatter numberFormatter = new NumberFormatter(numberFormat);
        numberFormatter.setAllowsInvalid(false);

        contactIdLabel = new JLabel("Contact Id:");
        contactIdText = new JTextField(20);
        contactGrpIdLabel = new JLabel("Contact GroupName");
        contactGrpIdText=new JComboBox(new String[]{"FRIENDS","RELATIVES","OTHERS"});
        contactGrpIdText.setBounds(50, 50,90,20);

        contactNameLabel = new JLabel("Contact Name");
        contactNameText = new JTextField(20);
        contactPhoneLabel = new JLabel("Phone");
        contactPhoneText = new JFormattedTextField(numberFormatter);
        contactPhoneText.setColumns(20);
        contactAddr1Label = new JLabel("Addr1");
        contactAddr1Text = new JTextField(20);
        contactAddr2Label = new JLabel("Addr2");
        contactAddr2Text = new JTextField(20);
        contactAddr3Label = new JLabel("Addr3");
        contactAddr3Text = new JTextField(20);
        contactPinLabel = new JLabel("Pin");
        contactPinText = new JFormattedTextField(numberFormatter);
        contactPinText.setColumns(20);


        setLayout(new GridLayout(10,2));
        add(contactIdLabel);
        add(contactIdText);
        add(contactGrpIdLabel);
        add(contactGrpIdText);
        add(contactNameLabel);
        add(contactNameText);
        add(contactPhoneLabel);
        add(contactPhoneText);
        add(contactAddr1Label);
        add(contactAddr1Text);
        add(contactAddr2Label);
        add(contactAddr2Text);
        add(contactAddr3Label);
        add(contactAddr3Text);
        add(contactPinLabel);
        add(contactPinText);
        add(outputLabel);
        add(button);
        add(listindent);
        add(ListButton);
        button.addActionListener(this);
        ListButton.addActionListener(this);
        GUIHelpers.PopulateComboBox(contactGrpIdText,ContactGroupManagerPresenter.getGroupNames());
        setSize(400, 400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void viewHandler(ContactFormAdd contactForm) throws Exception{
        ContactDTO entity = getEntityFromUI(contactForm);
        if ( entity == null)
        {
            GUIHelpers.MessageBox("Invalid retrieval of Entity");
            return;
        }
        if(entity.equals(null)) {
            System.out.println("Error in input");
            return;
        }

        if (ContactManagerPresenter.DoesExists(entity.getContactId()))
        {
            GUIHelpers.MessageBox("The ID already exists");
            return ;
        }

        boolean result = ContactManagerPresenter.AddContactViaScrpt(entity);
        if(result) {
            System.out.println("success!");
            contactForm.outputLabel.setText("success!");
        } else {
            System.out.println("failed");
            contactForm.outputLabel.setText("failed");
        }
    }

    public static ContactDTO getEntityFromUI(ContactFormAdd contactForm) {
        ContactDTO contactEntity = new ContactDTO();
        contactEntity.setContactId(GUIHelpers.ConvertToInteger(contactForm.contactIdText));
        String str  = (String)contactForm.contactGrpIdText.getSelectedItem();
        if ( str == null) { return null; }
        int id = ContactGroupManagerPresenter.getContactGroupId(str);
        if ( id == -1) { return null; }
        contactEntity.setContactgrpId(id);
        contactEntity.setContactName(contactForm.contactNameText.getText());
        contactEntity.setContactPhone(contactForm.contactPhoneText.getText());
        contactEntity.setContactAddr1(contactForm.contactAddr1Text.getText());
        contactEntity.setContactAddr2(contactForm.contactAddr2Text.getText());
        contactEntity.setContactAddr3(contactForm.contactAddr3Text.getText());
        contactEntity.setContactPin(contactForm.contactPinText.getText());

        return contactEntity;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        outputLabel.setText("Creating a contact");

        String cmd = e.getActionCommand();
        GUIHelpers.MessageBox("From Action Performed " + cmd);
        if ( cmd == "LIST") {
            try {
                ContactListWindow s = new ContactListWindow();
                s.setVisible(true);
                return;
            }
            catch( Exception er) {return; }
        }
        try {
            viewHandler(this);
        }catch(Exception e2)
        {
            e2.printStackTrace();
        }
    }
}
