package com.chistadata.Infrastructure.Plugins;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;

public class BillFactory {
    private HashMap<String, String> plugins = new HashMap<String, String>();
    private String dir_name = null;

    public BillFactory(String xmlFile,String pdir_name) {
        dir_name = pdir_name;
        System.out.println(dir_name);

        plugins = loadData( xmlFile);
        // for (Map.Entry<String, PluginEntry> set : plugins.entrySet()) {
        //    System.out.println(set.getKey() + " = " + set.getValue());
        // }
    }

    private HashMap<String, String> loadData(String xmlFile) {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        HashMap<String, String> xmlData = new HashMap<String, String>();
        try {
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(
                    Thread.currentThread().getContextClassLoader().getResourceAsStream(xmlFile)
            );
            NodeList planlist = document.getElementsByTagName("plan");
             if (planlist == null ) { return null; }


            for(int i = 0; i < planlist.getLength(); i++) {
                Node p = planlist.item(i);


                if(p.getNodeType() == Node.ELEMENT_NODE) {
                    Element el = (Element) p;
                    System.out.println(el.getChildNodes().toString());
                    String er = el.getTagName();
                    System.out.println(er);
                    xmlData.put(er, er);
                }
            }
            return xmlData;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return null;
        } catch (SAXException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
