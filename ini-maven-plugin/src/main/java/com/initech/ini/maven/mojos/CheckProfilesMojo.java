package com.initech.ini.maven.mojos;

import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.maven.model.Profile;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

/**
 * 
 *
 *  Plexus and Maven settings: 
 *   @goal profile-check
 *   @requiresProject true
 */
public class CheckProfilesMojo extends AbstractMojo{

    
    /**
     * @parameter default-value="${project}"
     * @required
     * @readonly
     */
    protected MavenProject project;
    
    
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        
        String artifactId = project.getArtifactId();
        
        getLog().info("project " + artifactId + ":");
        List<Profile> activeProfiles = project.getActiveProfiles();
        if(activeProfiles.size() == 0) {
            getLog().info(" - no profiles active");
        } else {
            for (Profile profile : activeProfiles) {
                getLog().info(" - profile '" + profile.getId() + "'");
            }
        }
        
        Properties projectProps = project.getProperties();
        Set<Object> keySet = projectProps.keySet();
        for (Object key : keySet) {
            getLog().info(" - prop '" + key + "' = '"+projectProps.getProperty((String) key)+"'");
        }
        
        
    }
    
}
