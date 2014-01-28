package com.initech.ini.service;


/**
 * Readonly set of runtime configuration properties
 * of this particular Ini instance.
 * 
 */
public interface IniRuntime {

    /** stage, one of "prod", "integ", "devel", "local", "hudson" or "local" */
	String getStage();
	
	/** full name */
	String getClientName();
	
	/** short, lower case and possibly abbreviated, e.g. "daffy", "coyote" oder "acme" */
	String getClientCode();
	
	/** the full ini version, like "2.0.0-SNAPSHOT" */
	String getVersion();

	/** retrieve iniCoreHttp-URL of this specific ini instance */
	String getCoreURL();
	
	/** retrieve the ServiceGateway URL of this specific ini instance */
    String getServiceGatewayURL();

    /** retrieve iniSGI-URL of this specific ini instance */
    String getSgiURL();
    
    /** retrieve iniPortal-URL of this specific ini instance */
    String getPortal();
    
    /** username database */
    String getDbUser();
    
    /**   */
    String getDbPassword();
    
    String getDbName();
    
    String getDbHost();
    
    String getDbPort();
    
    String getJdbcUrl();
    
    /** fully qualified implementation class name, used to create HibernateSession bean */
    String getHibernateSQLDialect();
        
        
        
    /**
     * retrieve the base directory if this ini instance
     */
	String getIniBaseDir();
	
	
	int getDbPoolSizeInitial();
	
	
	int getDbPoolSizeMax();
        
        
}
