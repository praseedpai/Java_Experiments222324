package com.chistadata.GUI.Utilties;

import javax.swing.*;

public class GUIHelpers {
    public static void MessageBox(String t)
    {
        JOptionPane.showMessageDialog(null,t);
    }
    public static int ConvertToInteger( JTextField jf ) {
        try {
            return Integer.parseInt(jf.getText());
        }
        catch(Exception e )
        {
            return Integer.MIN_VALUE;
        }

    }
    public static double ConvertToDouble( JTextField jf ) {
        try {
            return Double.parseDouble(jf.getText());
        }
        catch(Exception e )
        {
            return Double.NaN;
        }

    }

    //------------------------- Populate ComboBox
    public static boolean PopulateComboBox( JComboBox box,String [] arr)
    {
        box.removeAllItems();

        for(int i=0; i< arr.length; ++i)
        {
            box.addItem(arr[i]);
        }
        return true;
    }

}
