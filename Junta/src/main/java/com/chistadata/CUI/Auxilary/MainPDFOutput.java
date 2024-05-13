package com.chistadata.CUI.Auxilary;

import com.chistadata.CUI.ContactMgmt.MainClass2;
import com.chistadata.Domain.Billing.Entities.Bill;
import com.chistadata.Domain.Billing.Auxilary.JavaObject;
import com.chistadata.Domain.Billing.Auxilary.PDFOutput;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Logger;

public class MainPDFOutput {
    private static final Logger LOGGER = Logger.getLogger(MainPDFOutput.class.getName());
    public static void main(String[] args) {
        LOGGER.info("Main method");
        // Folder path to store PDF file
        String FILE = MainClass2.class.getClassLoader().getResource("Bill.pdf").getPath();
        //String path = MainClass2.class.getClassLoader().getResource("employee.xml").getPath();
        String path = MainClass2.class.getClassLoader().getResource("bill.xml").getPath();
        File xmlFile = new File(path);
        String xml =
                "<employee id='1'>"+
                        "<name>Lokesh</name>"+
                        "<salary>10000.00</salary>"+
                        "</employee>";
        System.out.println("Hello Junta");
        JavaObject javaObject = new JavaObject();
        try {

            // Employee obj = javaObject.createEmployeeFromFile(xmlFile);
            // Employee obj = javaObject.createEmployeeFromString(xml);
            Bill obj = javaObject.createObjectFromFile(xmlFile);
            System.out.println(obj.getPlan().getCustomerId()+" "+obj.getUsage().getClusterUsageTimeInHour());

            // TODO Auto-generated method stub
            ArrayList records = new ArrayList();
            ArrayList cell = new ArrayList();
            cell.add("Customer ID");
            cell.add(obj.getPlan().getCustomerId());
            records.add(cell);

            cell = new ArrayList();
            cell.add("Service Period");
            cell.add(obj.getPlan().getServicePeriod());
            records.add(cell);

            cell = new ArrayList();
            cell.add("Plan Name");
            cell.add(obj.getPlan().getPlanName());
            records.add(cell);

            cell = new ArrayList();
            cell.add("Tier Name");
            cell.add(obj.getPlan().getTierName());
            records.add(cell);

            cell = new ArrayList();
            cell.add("Plan Price");
            cell.add(obj.getPlan().getPlanPrice());
            records.add(cell);

            cell = new ArrayList();
            cell.add("Cluster Size");
            cell.add(obj.getPlan().getClusterSize());
            records.add(cell);

            cell = new ArrayList();
            cell.add("No of Replica");
            cell.add(obj.getPlan().getNoOfReplica());
            records.add(cell);

            cell = new ArrayList();
            cell.add("No of Shard");
            cell.add(obj.getPlan().getNoOfShard());
            records.add(cell);

            cell = new ArrayList();
            cell.add("Cost Unit Per Node");
            cell.add(obj.getPlan().getCostUnitPerNode());
            records.add(cell);

            cell = new ArrayList();
            cell.add("Resource Rate");
            cell.add(obj.getPlan().getResourceRate());
            records.add(cell);

            cell = new ArrayList();
            cell.add("Network Rate");
            cell.add(obj.getPlan().getNetworkRate());
            records.add(cell);

            cell = new ArrayList();
            cell.add("Storage Rate");
            cell.add(obj.getPlan().getStorageRate());
            records.add(cell);

            cell = new ArrayList();
            cell.add("Cluster Usage Time In Hour");
            cell.add(obj.getUsage().getClusterUsageTimeInHour());
            records.add(cell);

            cell = new ArrayList();
            cell.add("Network Usage Time In Hour");
            cell.add(obj.getUsage().getNetworkUsageTimeInHour());
            records.add(cell);

            cell = new ArrayList();
            cell.add("Storage Usage Time In Hour");
            cell.add(obj.getUsage().getStorageUsageTimeInHour());
            records.add(cell);


            PDFOutput pdfOut = new PDFOutput(records);
            pdfOut.GeneratePDF(FILE);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
