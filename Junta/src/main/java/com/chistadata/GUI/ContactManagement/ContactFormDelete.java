package com.chistadata.GUI.ContactManagement;


import com.chistadata.Domain.ContactMgmt.DTO.ContactDTO;
import com.chistadata.GUI.Utilties.GUIHelpers;
import com.chistadata.Presentation.ContactMgmt.ContactManagerPresenter;


import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

public class ContactFormDelete extends JFrame implements ActionListener {
    private JLabel ComboboxidLabel = new JLabel("Select Name");
    private JComboBox NameToSelect = new JComboBox(new String[ ] { ""});
    private JLabel contactIdLabel;
    private JLabel contactGrpIdLabel;
    private JTextField contactGrpIdText;
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
    private String _formmode = "ADD";
    public ContactFormDelete(String pformmode) {


            super("Contact Form -" + pformmode);
            _formmode= pformmode;
            NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(false);
        NumberFormatter numberFormatter = new NumberFormatter(numberFormat);
        numberFormatter.setAllowsInvalid(false);

        contactIdLabel = new JLabel("Contact Id:");
        contactIdText = new JTextField(20);
        contactGrpIdLabel = new JLabel("Contact Groupid");
        contactGrpIdText=new JTextField(20);
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
        add(ComboboxidLabel);
        add(NameToSelect);
        add(contactIdLabel);
        add(contactIdText);
        contactIdText.setEditable(false);
        add(contactGrpIdLabel);
        add(contactGrpIdText);
        contactGrpIdText.setEditable(false);
        add(contactNameLabel);
        add(contactNameText);
        contactNameText.setEditable(false);
        add(contactPhoneLabel);
        add(contactPhoneText);
        contactPhoneText.setEditable(false);
        add(contactAddr1Label);
        add(contactAddr1Text);
        contactAddr1Text.setEditable(false);
        add(contactAddr2Label);
        add(contactAddr2Text);
        contactAddr2Text.setEditable(false);
        add(contactAddr3Label);
        add(contactAddr3Text);
        contactAddr3Text.setEditable(false);
        add(contactPinLabel);
        add(contactPinText);
        contactPinText.setEditable(false);
        add(outputLabel);
        add(button);
        button.addActionListener(this);
        GUIHelpers.PopulateComboBox(NameToSelect,ContactManagerPresenter.getContactNameAsArray());
        NameToSelect.addActionListener(this);
        setSize(400, 400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void viewHandler(ContactFormDelete contactForm) throws Exception{
        ContactDTO entity = getEntityFromUI(contactForm);
        if(entity.equals(null)) {
            System.out.println("Error in input");
            return;
        }

        if (!ContactManagerPresenter.DoesExists(entity.getContactId())) {
            GUIHelpers.MessageBox("Contact does not exists");
            return;
        }

      //  boolean result = ContactManagerPresenter.Delete(entity.getContactId());
        boolean result = ContactManagerPresenter.DeleteViaPlugin(entity.getContactId());

        if(result) {
            System.out.println("success!");
            contactForm.outputLabel.setText("success!");
            GUIHelpers.PopulateComboBox(contactForm.NameToSelect,ContactManagerPresenter.getContactNameAsArray());
        } else {
            System.out.println("failed");
            contactForm.outputLabel.setText("failed");
        }
    }

    public static ContactDTO getEntityFromUI(ContactFormDelete contactForm) {
        ContactDTO  contactEntity = new ContactDTO();
        contactEntity.setContactId(GUIHelpers.ConvertToInteger(contactForm.contactIdText));
        contactEntity.setContactName(contactForm.contactNameText.getText());
        contactEntity.setContactPhone(contactForm.contactPhoneText.getText());
        contactEntity.setContactAddr1(contactForm.contactAddr1Text.getText());
        contactEntity.setContactAddr2(contactForm.contactAddr2Text.getText());
        contactEntity.setContactAddr3(contactForm.contactAddr3Text.getText());
        contactEntity.setContactPin(contactForm.contactPinText.getText());

        return contactEntity;
    }

    public static ContactDTO setUIfromEntity(ContactFormDelete contactForm,ContactDTO contactEntity) {

       // contactEntity.setContactId(GUIHelpers.ConvertToInteger(contactForm.contactIdText));
      //  contactEntity.setContactName(contactForm.contactNameText.getText());
       // contactEntity.setContactPhone(contactForm.contactPhoneText.getText());
      //  contactEntity.setContactAddr1(contactForm.contactAddr1Text.getText());
      //  contactEntity.setContactAddr2(contactForm.contactAddr2Text.getText());
      //  contactEntity.setContactAddr3(contactForm.contactAddr3Text.getText());
      //  contactEntity.setContactPin(contactForm.contactPinText.getText());
        contactForm.contactIdText.setText(Integer.toString(contactEntity.getContactId()));
        contactForm.contactGrpIdText.setText(Integer.toString(contactEntity.getContactgrpId()));
        contactForm.contactNameText.setText(contactEntity.getContactName());
        contactForm.contactPhoneText.setText(contactEntity.getContactPhone());
        contactForm.contactAddr1Text.setText(contactEntity.getContactAddr1());
        contactForm.contactAddr2Text.setText(contactEntity.getContactAddr2());
        contactForm.contactAddr3Text.setText(contactEntity.getContactAddr3());
        contactForm.contactPinText.setText(contactEntity.getContactPhone());



        return contactEntity;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        outputLabel.setText("Creating a contact");
        Object obj = e.getSource();

        if ( obj instanceof  JComboBox)
        {
            JComboBox box = (JComboBox) obj;
            String f = (String) box.getSelectedItem();
            if ( f == null) {
                JOptionPane.showMessageDialog(null,"Failed to Retrieve Selection");
                return;
            }
            ContactDTO grp = ContactManagerPresenter.getData(f);
            if (grp == null ) {
                JOptionPane.showMessageDialog(null,"Failed to Retrieve Selection "+f);
                return;
            }
            ContactFormDelete.setUIfromEntity((ContactFormDelete)this,grp);
            return ;

        }
        try {
            viewHandler(this);
        }catch(Exception e2)
        {
            e2.printStackTrace();
        }
    }
}
