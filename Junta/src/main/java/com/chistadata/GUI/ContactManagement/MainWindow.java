package com.chistadata.GUI.ContactManagement;

// Java program to construct
// Menu bar to add menu items



import com.chistadata.GUI.Utilties.GUIHelpers;

import javax.swing.*;
import java.awt.event.*;

class MenuActionListener implements ActionListener
{
    @Override
    public void actionPerformed(ActionEvent e) {
       String f = e.getActionCommand();
       if ( f == "Group Add") {
          ContactGroupFormAdd form = new ContactGroupFormAdd("ADD");

          return ;
       }
       else if ( f == "Group Edit") {
          // GroupFormAdd form = new GroupFormAdd("EDIT");
           GUIHelpers.MessageBox("Not implemented");
           return;
       }
       else if ( f == "Group Delete") {
           try {
               ContactGroupFormDelete form = new ContactGroupFormDelete("DELETE");
           }
           catch(Exception r) { r.printStackTrace(); }
       }
       else if (f == "Contact Add") {
           //  GroupForm f = new GroupForm();
           ContactFormAdd form = new ContactFormAdd("ADD");
           return;
       }
       else if ( f == "Contact Delete") {
           ContactFormDelete form = new ContactFormDelete("DELETE");
           return;
       }
       else if ( f == "Contact Edit") {
           GUIHelpers.MessageBox("Not implemented");
           return;
       }
       else if (f == "Exit") {
           System.exit(0);
       }


       // }
    }
}
public class MainWindow extends JFrame {
    // menubar
    static JMenuBar mb;

    // JMenu
    static JMenu x;
    static JMenu x1;

    // Menu items
    static JMenuItem m1, m2, m3,m4,m5,m6,m7;

    // create a frame
    static JFrame f;

    public static void main(String [] args)
    {

        // create a frame
        f = new JFrame("Menu demo");

        // create a menubar
        mb = new JMenuBar();

        // create a menu
        x = new JMenu("Contact Group");

        // create menuitems
        m1 = new JMenuItem("Group Add");
        m2 = new JMenuItem("Group Edit");
        m3 = new JMenuItem("Group Delete");
        m4 = new JMenuItem("Exit");

        x1 = new JMenu("Contact");

        // create menuitems
        m5 = new JMenuItem("Contact Add");
        m6 = new JMenuItem("Contact Edit");
        m7 = new JMenuItem("Contact Delete");

        // add menu items to menu
        x.add(m1);
        x.add(m2);
        x.add(m3);
        x.add(m4);
        x1.add(m5);
        x1.add(m6);
        x1.add(m7);
        m1.addActionListener(new MenuActionListener());
        m2.addActionListener(new MenuActionListener());
        m3.addActionListener(new MenuActionListener());
        m4.addActionListener(new MenuActionListener());
        m5.addActionListener(new MenuActionListener());
        m6.addActionListener(new MenuActionListener());
        m7.addActionListener(new MenuActionListener());

        // add menu to menu bar
        mb.add(x);
        mb.add(x1);

        // add menubar to frame
        f.setJMenuBar(mb);

        // set the size of the frame
        f.setSize(500, 500);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}