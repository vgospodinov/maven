package com.initech.ini.maven.mojos;

import java.text.MessageFormat;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

import com.initech.ini.maven.service.IniBuildManager;
import com.initech.ini.maven.service.IniSystemFactory;
import com.initech.ini.maven.util.IniBuildException;


/**
 * Maven Mojo für die Erzeugung der ini-Application-Configuration, abhängig
 * vom gewählten clienten (z. Bsp daffy).
 * <p>
 * 
 * TODO bisher wird nicht berücksichtigt, dass der Benutzer versehentlich
 * mehrere Profile ausgewählt haben könnte...
 * 
 * Plexus and Maven settings:
 * 
 * @goal config
 * @requiresProject true
 * 
 * 
 */
public class RuntimeConfigurationGenerationMojo extends AbstractMojo {

	/**
	 * @parameter default-value="${project}"
	 * @required
	 * @readonly
	 */
	protected MavenProject project;

	private static final String PLUGIN_NAME = "ini-maven-plugin";


	/**
	 * Merge the configuration for all available clients and stages. 
	 */
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		
		IniBuildManager buildManager = IniSystemFactory.getBuildManager(project, getLog());
		
		// we only act when in project "iniRuntimeConfig"
		if(buildManager.isRuntimeConfigProject()) {
			
			try {
				
				buildManager.mergeConfigurations();
				
			} catch (IniBuildException e) {
				
				throw new MojoExecutionException(e.getMessage());
				
			}
			
		} else {
			
			getLog().info(MessageFormat.format(
				"{0} skipping: nothing to merge in child module ''{1}'', merging takes place in parent module",
				PLUGIN_NAME, 
				project.getArtifactId()));
			
		}
		
		
		
	}
	




}
