package com.initech.ini.maven.bo;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.initech.ini.maven.util.SetBuilder;




public class IniSystemModelImpl implements IniSystemModel {

	private String[] clientIDs;
	private String[] stageIDs;
	
	private String[] warModules;
	private String[] jarModules;
	
	private String mavenPluginModule;
	private String distributionModule;
	private String configGenModule;
	private String masterModule;
	
	


	
	@Override
	public Set<String> getIniModules() {
		return new SetBuilder<String>()
		    .addAll(getIniModulesBuilding())
		    .addAll(getJarModules())
		    .addAll(getWarModules())
		    .build();
	};
	
	
	/** 
	 * all the modules that are not core ini software, but helpers for building, for 
	 * artifact generation etc. 
	 */
	private Set<String> getIniModulesBuilding() {
		return createSet(new String[] { 
			getMasterModule(), 
			getConfigGenModule(),
			getDistributionModule(),
			getMavenPluginModule(),
		});
	}
	
	
	@Override
	public Set<String> getWarModules() {
		return createSet(warModules);
	}
	
	
	
	public Set<String> getJarModules() {
		return createSet(jarModules);
	}
	
	
	private HashSet<String> createSet(String[] x) {
		HashSet<String> result = new HashSet<String>();
		if(x == null || x.length == 0) {
			return result; 
		} 
		for (String client : x) {
			result.add(client);
		}
		return result;
	}
	
	@Override
	public Set<String> getClients(){
		return createSet(clientIDs);
	}
	
	@Override
	public Set<String> getStages(){
		return createSet(stageIDs);
	}
	
	@Override
	public Set<IniBuildProfile> getAllProfiles(){
		Set<IniBuildProfile> result = new HashSet<IniBuildProfile>();
		Set<String> clients = getClients();
		Set<String> stages = getStages();
		for (String client : clients) {
			for (String stage : stages) {
				result.add(new IniBuildProfile(client, stage));
			}
			
		}
		return result;
	}
	
	
	@Override
	public Set<IniBuildProfile> getActiveProfiles(File moduleBaseDir) {
		Set<IniBuildProfile> result = new HashSet<IniBuildProfile>();
		Set<IniBuildProfile> allProfiles = getAllProfiles();
		
		// get profile home
		File profileHome = new File(moduleBaseDir, "profiles");
		
		
		
		for (IniBuildProfile iniBuildProfile : allProfiles) {
		    if(isAvailableProfile(iniBuildProfile, profileHome)) {
		    	result.add(iniBuildProfile);
		    }	
		}
		
		return Collections.unmodifiableSet(result);
	}
	
	
	/**
	 * check wheter a profile exists in the profile home 
	 * (which basicly means: is there a directory called 
	 *    stage.profile
	 *  and does it contain a file called
	 *    client.properties
	 * @param iniBuildProfile
	 * @param profileHome
	 * @return
	 */
	private boolean isAvailableProfile(IniBuildProfile iniBuildProfile, File profileHome) {
		
		if(!profileHome.exists()) return false;
		
		File profileDir = new File(profileHome, iniBuildProfile.getStage());
		
		if(!profileDir.exists()) return false;
		
		File clientFile = new File(profileDir, iniBuildProfile.getClient() + ".properties");
		
		if(clientFile.exists()) return true;
		
		return false;
	}


	public void setClientIDs(String[] clientIDs) {
		this.clientIDs = clientIDs;
	}
	
	
	public void setStageIDs(String[] stageIDs) {
		this.stageIDs = stageIDs;
	}


	@Override
	public String getMasterModule() {
		return masterModule;
	}


	@Override
	public String getConfigGenModule() {
		return configGenModule;
	}


	@Override
	public String getDistributionModule() {
		return distributionModule;
	}


	@Override
	public String getMavenPluginModule() {
		return mavenPluginModule;
	}


	public void setMavenPluginModule(String mavenPluginModule) {
		this.mavenPluginModule = mavenPluginModule;
	}


	public void setDistributionModule(String distributionModule) {
		this.distributionModule = distributionModule;
	}

	public void setConfigGenModule(String configGenModule) {
		this.configGenModule = configGenModule;
	}


	public void setMasterModule(String masterModule) {
		this.masterModule = masterModule;
	}

	public void setWarModules(String[] warModules) {
		this.warModules = warModules;
	}
	
	public void setJarModules(String[] jarModules) {
		this.jarModules = jarModules;
	}
}
