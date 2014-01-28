package com.initech.ini.portal;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.initech.ini.service.IniRuntime;


@SuppressWarnings("serial")
public class HelloServlet extends HttpServlet{

    public void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        
        PrintWriter out = response.getWriter();
        
        WebApplicationContext ctx = 
            WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        
        IniRuntime iniRuntime = ctx.getBean(IniRuntime.class);
        
        out.println("Application: iniPortal");
        out.println("Version = " + iniRuntime.getVersion());
        out.println("IniRuntime Configuration: available");
        out.println("");
        out.println("ini.client.name = " + iniRuntime.getClientName());
        out.println("ini.client = " + iniRuntime.getClientCode());
        out.println("ini.db.host = " + iniRuntime.getDbHost());
        out.println("ini.db.name = " + iniRuntime.getDbName());
        out.println("ini.db.sql.dialect = " + iniRuntime.getHibernateSQLDialect());
        out.println("ini.sgw.url = " + iniRuntime.getServiceGatewayURL());
        
        
        
    }

    
}
