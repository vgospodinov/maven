package com.initech.ini.maven.service;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

/**
 * 
 * @author andyman
 *
 */
public class IniSystemFactory  {

	
	public static IniBuildManager getBuildManager(MavenProject mavenProject, Log log) throws MojoExecutionException {
		
		if(mavenProject == null) {
			throw new MojoExecutionException("mavenProject is required to instantiate iniBuildManager");
		}

		IniBuildManagerImpl buildManager = new IniBuildManagerImpl();
		buildManager.setMavenProject(mavenProject);
		buildManager.setLog(log);
		return buildManager;
		
	}
	
	

}
