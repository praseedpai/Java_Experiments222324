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
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class ObjectFactory {
    private HashMap<String, PluginEntry> plugins = new HashMap<String, PluginEntry>();
    private HashMap<String, IComputationCommand> commands = new HashMap<String, IComputationCommand>();
    private String dir_name = null;

    public ObjectFactory(String xmlFile,String pdir_name) {
        dir_name = pdir_name;
        System.out.println(dir_name);

        plugins = loadData( xmlFile);
       // for (Map.Entry<String, PluginEntry> set : plugins.entrySet()) {
        //    System.out.println(set.getKey() + " = " + set.getValue());
       // }
    }

    private HashMap<String, PluginEntry> loadData(String xmlFile) {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        HashMap<String, PluginEntry> xmlData = new HashMap<String, PluginEntry>();
        try {
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(
                    Thread.currentThread().getContextClassLoader().getResourceAsStream(xmlFile)
            );
            NodeList pluginList = document.getElementsByTagName("plugin");

            for(int i = 0; i < pluginList.getLength(); i++) {
                Node p = pluginList.item(i);
                if(p.getNodeType() == Node.ELEMENT_NODE) {
                    Element plugin = (Element) p;
                    String archetype = ((Element) p).getAttribute("archetype");
                    String command = ((Element) p).getAttribute("command");
                    String jar = ((Element)p).getAttribute("jar");
                    PluginEntry pe = new PluginEntry(archetype,command,jar);
                    xmlData.put(archetype, pe);
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

    public IComputationCommand get(String archetype, String mode) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        try {
            if (mode != "singleton" && mode != "prototype") return null;

            IComputationCommand cmd = null;
            cmd = commands.get(archetype);
            if(cmd != null) {
                return (mode.equals( "singleton"))? cmd: CloneHelpers.deepClone(cmd);
            }

            PluginEntry className = plugins.get(archetype);
            if(className == null)
                return null;

            ExtensionLoader<IComputationCommand> loader = new ExtensionLoader<IComputationCommand>();
            IComputationCommand c =  loader.LoadClass(dir_name,className.assemblyname,
                    className.packagename, IComputationCommand.class);

            if(c == null)
                return null;

            commands.put(archetype, c);
        }  catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }  catch (Exception e ) {
            return null;
        }

        return commands.get(archetype);
    }
}