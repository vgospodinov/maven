package com.initech.ini.maven.service;

import java.util.Properties;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

import com.initech.ini.maven.bo.IniSystemModel;
import com.initech.ini.maven.bo.IniSystemModelImpl;
import com.initech.ini.maven.dao.IniSystemModelDAO;
import com.initech.ini.maven.dao.IniSystemModelDaoImpl;
import com.initech.ini.maven.util.IniBuildException;
import com.initech.ini.maven.util.IniProfileMerger;



public class IniBuildManagerImpl implements IniBuildManager {

	private MavenProject mavenProject;
	private Log log;
	
	
	/** IniBuildManager serves as factory for modelDAO */
	private IniSystemModelDAO modelDAO = new IniSystemModelDaoImpl();

	
	@Override
	public boolean isConfigurableProject() {
		
		if(isRuntimeConfigProject() || isIniMaster()){
			return false;
		} 
		
		return true;
	}
	
	@Override
	public String getActivatedStage() {
		Properties properties = mavenProject.getProperties();
		return properties.getProperty("stage");
	}

	
	@Override
	public void mergeConfigurations() throws IniBuildException {
		
		assertCorrectModule();
		
		IniProfileMerger merger = createMerger();
		
		merger.mergeConfigurations();
		
	}


	private IniProfileMerger createMerger() {
		IniProfileMerger merger = new IniProfileMerger();
		merger.setSystemModel(modelDAO.getIniSystemModel());
		merger.setMavenProject(mavenProject);
		merger.setLog(log);
		return merger;
	}


	private void assertCorrectModule() throws IniBuildException {
		if(!isRuntimeConfigProject()) {
			throw new IniBuildException("wrong project - refuse to mere ini runtime configurations");
		}
	}
	
	@Override
	public boolean isRuntimeConfigProject() {
		
		String artifactId = mavenProject.getArtifactId();
		IniSystemModel appModel = modelDAO.getIniSystemModel();
		String configModule = appModel.getConfigGenModule();
		return artifactId.equals(configModule);
		
	}


	@Override
	public String getActivatedClient() {
		Properties properties = mavenProject.getProperties();
		return properties.getProperty("client");
		
	}

	

	@Override
	public boolean isIniMaster() {
		String artifactId = mavenProject.getArtifactId();
		IniSystemModel appModel = modelDAO.getIniSystemModel();
		String masterModule = appModel.getMasterModule();
		return artifactId.equals(masterModule);
	}


	
	public void setMavenProject(MavenProject mavenProject) {
		this.mavenProject = mavenProject;
	}

	
	public void setLog(Log log) {
		this.log = log;
	}

	/** for unit tests */
	void setModelDAO(IniSystemModelDAO modelDAO) {
		this.modelDAO = modelDAO;
	}

}
