package com.chistadata.CUI.ContactMgmt;

import com.chistadata.Infrastructure.Plugins.*;
/*import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.Context;
import org.apache.catalina.Wrapper;*/
import com.chistadata.Services.Auxilary.SimpleHello;


//
//   ExtensionLoader<MyPlugin> loader = new ExtensionLoader<MyPlugin>();
//   somePlugin = loader.LoadClass("path/to/jar/file", "com.example.pluginXYZ", MyPlugin.class);
//
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;

public class MainClass2 {
    public static void main(String[] args) throws Exception {
       /* Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("temp");
        // Solution 1
        //tomcat.setPort(8080);
        //tomcat.getConnector();

        // Solution 2, make server listen on 2 ports
        Connector connector1 = tomcat.getConnector();
        connector1.setPort(8080);
        Connector connector2 = new Connector();
        connector2.setPort(8090);
        String contextPath = "";
        String docBase = new File(".").getAbsolutePath();

        Context context = tomcat.addContext(contextPath, docBase);

        class SampleServlet extends HttpServlet {

            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                    throws ServletException, IOException {
                PrintWriter writer = resp.getWriter();

                writer.println("<html><title>Welcome</title><body>");
                writer.println("<h1>Have a Great Day Joseph!</h1>");
                writer.println("</body></html>");
            }
        }

        String servletName = "SampleServlet";
        String urlPattern = "/aa";*/
//        JAXRSServerFactoryBean factoryBean = new JAXRSServerFactoryBean();
//        factoryBean.setResourceClasses(SimpleHello.class);
//        factoryBean.setResourceProvider(
//                new SingletonResourceProvider(new SimpleHello()));
//        factoryBean.setAddress("http://localhost:8080/");
//        org.apache.cxf.endpoint.Server  server = factoryBean.create();
//        server.start();
//        server.wait();

        JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
        sf.setResourceClasses(SimpleHello.class);
        sf.setResourceProvider(SimpleHello.class,
                new SingletonResourceProvider(new SimpleHello()));
        sf.setAddress("http://localhost:9000/");

        sf.create();
        while (true);

       /* tomcat.addServlet(contextPath, servletName, new SampleServlet());
        context.addServletMappingDecoded(urlPattern, servletName);

        tomcat.start();
        tomcat.getService().addConnector(connector1);
        tomcat.getService().addConnector(connector2);
        tomcat.getServer().await();*/
    }
    public static void main4( String[] args ) throws Exception
    {
//        Tomcat tomcat = new Tomcat();
//        Context context = tomcat.addContext( "/", "" );
//
//        Wrapper servlet = context.createWrapper();
//        servlet.setName( "jaxrs" );
//        servlet.setServletClass(
//                "org.apache.cxf.jaxrs.servlet.CXFNonSpringJaxrsServlet" );
//
//
//        servlet.addInitParameter(
//                "jaxrs.serviceClasses",
//                SimpleHello.class.getName()
//        );
//
//        servlet.setLoadOnStartup( 1 );
//        context.addChild( servlet );
//        context.addServletMappingDecoded( "/rest/*", "jaxrs" );
//
//        tomcat.start();
//        tomcat.getServer().await();
    }
//
//    public static void main3(String[] args) throws LifecycleException {
//        Tomcat tomcat = new Tomcat();
//        tomcat.setBaseDir("temp");
//        // Solution 1
//        //tomcat.setPort(8080);
//        //tomcat.getConnector();
//
//        // Solution 2, make server listen on 2 ports
//        Connector connector1 = tomcat.getConnector();
//        connector1.setPort(8080);
//        Connector connector2 = new Connector();
//        connector2.setPort(8090);
//        String contextPath = "";
//        String docBase = new File(".").getAbsolutePath();
//
//        Context context = tomcat.addContext(contextPath, docBase);
//
//        class SampleServlet extends HttpServlet {
//
//            @Override
//            protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//                    throws ServletException, IOException {
//                PrintWriter writer = resp.getWriter();
//
//                writer.println("<html><title>Welcome</title><body>");
//                writer.println("<h1>Have a Great Day Joseph!</h1>");
//                writer.println("</body></html>");
//            }
//        }
//
//        String servletName = "SampleServlet";
//        String urlPattern = "/aa";
//
//        tomcat.addServlet(contextPath, servletName, new SampleServlet());
//        context.addServletMappingDecoded(urlPattern, servletName);
//
//        tomcat.start();
//        tomcat.getService().addConnector(connector1);
//        tomcat.getService().addConnector(connector2);
//        tomcat.getServer().await();
//    }
    public static void main2(String[] args){
        //-------------- Set up Plugin Files and Folders
      //  if ( args.length == 0 ) { System.out.println("No command line arguments");  return; }

        com.chistadata.Infrastructure.Plugins.PluginHelpers.SetPluginFileFolder("plugin.xml","D:\\ChistaDATA\\Junta\\target\\");
        try {
            // ------ We have made sure that ...somehting will be here
            String sr = "Bill.xml";
            BillFactory fact = new BillFactory(sr,".");
            if (fact == null) { System.out.println("Failed to Load XML"); }
            System.out.println("Subham");

        }
        catch(Exception e ) {
            e.printStackTrace();;
            System.out.println("Failed to load the Class");
        }

    }
}
