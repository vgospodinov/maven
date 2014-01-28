package com.initech.ini.maven.service;

import com.initech.ini.maven.util.IniBuildException;

/**
 * 
 * Building the ini application. Mojo classes are expected to use this
 * IniBuildManager.
 * 
 * @author andyman
 */
public interface IniBuildManager {

	
	/**
	 * Is build currently running in iniMaster project?
	 */
	boolean isIniMaster();
	
	/**
	 * Is the current project the one used to generate the runtime 
	 * configuration?
	 */
	boolean isRuntimeConfigProject();
	
	
	/** or null */
	String getActivatedStage();
	
	
	/** or null */
	String getActivatedClient();

	/**
	 * Merge the available runtime profiles with the configuration template.
	 */
	void mergeConfigurations() throws IniBuildException;

	/**
	 * 
	 */
	boolean isConfigurableProject();
	
}
