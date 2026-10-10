package com.banking.online_banking;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import com.banking.online_banking.servlet.LoginServlet;
import com.banking.online_banking.servlet.RegisterServlet;
import com.banking.online_banking.servlet.TransferServlet;

import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();

        Context ctx = tomcat.addWebapp("", new File("src/main/webapp").getAbsolutePath());

        // Instance registration
        tomcat.addServlet("", "RegisterServlet", new RegisterServlet());
        ctx.addServletMappingDecoded("/register", "RegisterServlet");

        tomcat.addServlet("", "LoginServlet", new LoginServlet());
        ctx.addServletMappingDecoded("/login", "LoginServlet");

        tomcat.addServlet("", "TransferServlet", new TransferServlet());
        ctx.addServletMappingDecoded("/transfer", "TransferServlet");

        tomcat.start();
        System.out.println(">>> SERVER RUNNING AT: http://localhost:8080/register.jsp <<<");
        tomcat.getServer().await();
    }
}