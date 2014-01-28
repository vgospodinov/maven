package com.initech.ini.maven.bo;

import java.io.File;
import java.util.Set;


public interface IniSystemModel {

	
	/** the parent of all ini maven modules */
	String getMasterModule();
	
	/** module where runtime config for all clients / stages is generated */
	String getConfigGenModule();
	
	/** module where releases are packaged for distribution */
	String getDistributionModule();
	
	/** ini maven plugin module */
	String getMavenPluginModule();
	
	/** war applications of the ini system */
	Set<String> getWarModules();
	
	/**
	 * @set of client ids, like "daffy" or "acme" 
	 */
	Set<String> getClients();

	/**
	 * set of stage ids, like "local" or "prod" 
	 */
	Set<String> getStages();

	/**
	 * Return all possible BuildProfiles.
	 * @return set of all possible client-stage combinations, referred to as "profile", 
	 *     as literals, following the scheme "client-stage", e.g.  "acme-local" 
	 *     or "daffy-prod" 
	 */
	Set<IniBuildProfile> getAllProfiles();
	
	/**
	 * Return all activated BuildProfiles. A developer is not strictly required
	 * to have profiles for all stages, only stage local is strictly required.
	 * <p> 
	 *  
	 * @param projectBasedir the basedir of the iniRuntimeConfig maven module 
	 */
	Set<IniBuildProfile> getActiveProfiles(File projectBasedir);
	
	/** 
	 *  Returns the artifactIDs of all ini maven modules, including those
	 *  that are of auxilluary nature (like ini-maven-plugin etc).
	 *   
	 */
	Set<String> getIniModules();


	/** all software modules of the ini system */
	Set<String> getJarModules();
	
	
}