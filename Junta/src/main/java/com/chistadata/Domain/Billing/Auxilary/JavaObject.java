package com.chistadata.Domain.Billing.Auxilary;

import com.chistadata.Domain.Billing.Entities.Bill;
import com.chistadata.Domain.Billing.Entities.Employee;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.StringReader;


public class JavaObject {

    JAXBContext jaxbContext;

    public Employee createEmployeeFromFile(File xmlFile) throws JAXBException {
        jaxbContext = JAXBContext.newInstance(Employee.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        Employee employee = (Employee) jaxbUnmarshaller.unmarshal(xmlFile);

        return employee;
    }

    public Employee createEmployeeFromString(String xmlFile) throws JAXBException {
        jaxbContext = JAXBContext.newInstance(Employee.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        Employee employee = (Employee) jaxbUnmarshaller.unmarshal(new StringReader(xmlFile));

        return employee;
    }
    public Bill createObjectFromFile(File xmlFile) throws JAXBException {
        jaxbContext = JAXBContext.newInstance(Bill.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        Bill bill = (Bill) jaxbUnmarshaller.unmarshal(xmlFile);

        return bill;
    }
}
