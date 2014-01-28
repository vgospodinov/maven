package com.initech.ini.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.initech.ini.service.IniRuntime;



/**
 *  Implementation of the central IniRuntime Interface. Almost all
 *  injected values are from the generated configuration.
 *  <p>
 *  See module iniRuntimeConfig
 */
@Component("iniRuntime")
public class IniRuntimeImpl implements IniRuntime {

    /** @see IniRuntime#getStage() */
    @Value("#{iniRuntimeProperties['ini.stage']}")
    private String stage;
    
    /** 
     * global ini release version 
     */
    private String version = "2.0.0-SNAPSHOT";
    
    /** @see IniRuntime#getClientCode() */
    @Value("#{iniRuntimeProperties['ini.client']}")
    private String clientCode;
    
    /** @see IniRuntime#getClientName() */
    @Value("#{iniRuntimeProperties['ini.client.name']}")
    private String clientName;

    @Value("#{iniRuntimeProperties['ini.data.basedir']}")
    private String baseDir;

    @Value("#{iniRuntimeProperties['ini.core.http.url']}")
    private String coreURL;

    @Value("#{iniRuntimeProperties['ini.db.pool.connections.initial']}")
    private int dbPoolSizeInitial;

    @Value("#{iniRuntimeProperties['ini.db.password']}")
    private String dbPassword;

    @Value("#{iniRuntimeProperties['ini.db.name']}")
    private String dbName;

    @Value("#{iniRuntimeProperties['ini.db.host']}")
    private String dbHost;

    @Value("#{iniRuntimeProperties['ini.db.user']}")
    private String dbUser;

    @Value("#{iniRuntimeProperties['ini.db.sql.dialect']}")
    private String hibernateSQLDialect;

    @Value("#{iniRuntimeProperties['ini.jdbc.url']}")
    private String jdbcURL;

    @Value("#{iniRuntimeProperties['ini.sgw.http.url']}")
    private String serviceGatewayURL;

    @Value("#{iniRuntimeProperties['ini.portal.http.url']}")
    private String portalURL;

    @Value("#{iniRuntimeProperties['ini.db.port']}")
    private String dbPort;

    @Value("#{iniRuntimeProperties['ini.db.pool.connections.max']}")
    private int dbPoolSizeMax;

    @Value("#{iniRuntimeProperties['ini.sgi.http.url']}")
    private String sgiURL;
    
    @PostConstruct
    public void init() {
        if(clientCode == null) {
            throw new BeanInitializationException("ini runtime configuration properties not available");
        }
    }
    
    
    @Override
    public String getIniBaseDir() {
        return baseDir;
    }
    
    
    @Override
    public String getCoreURL() {
        return coreURL;
    }
    
    @Override
    public String getDbHost() {
        return dbHost;
    }
    
    @Override
    public String getDbName() {
        return dbName;
    }
    
    @Override
    public String getDbPassword() {
        return dbPassword;
    }
    
    @Override
    public int getDbPoolSizeInitial() {
        return dbPoolSizeInitial;
    }
    
    @Override
    public int getDbPoolSizeMax() {
        return dbPoolSizeMax;
    }
    
    @Override
    public String getDbPort() {
        return dbPort;
    }
    
    @Override
    public String getDbUser() {
        return dbUser;
    }
    
    @Override
    public String getHibernateSQLDialect() {
        return hibernateSQLDialect;
    }
    
    @Override
    public String getJdbcUrl() {
        return jdbcURL;
    }
    
    @Override
    public String getPortal() {
        return portalURL;
    }
    
    @Override
    public String getSgiURL() {
        return sgiURL;
    }
    
    @Override
    public String getServiceGatewayURL() {
        return serviceGatewayURL;
    }
    
    @Override
    public String getStage() {
        return stage;
    }
    
    @Override
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    @Override
    public String getClientName() {
        return clientName;
    }
    
    public void setStage(String stage) {
        this.stage = stage;
    }
    
    public void setClientName(String clientName) {
        this.clientCode = clientName;
    }
    
}
