package com.initech.ini.maven.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.Set;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

import com.initech.ini.maven.bo.IniBuildProfile;
import com.initech.ini.maven.bo.IniSystemModel;


/**
 * IniProfileMerger is a service that reads the master runtime configuration template,
 * then reads ini profiles configuration and the merges both. The result is one runtime
 * configuration per client and stage.
 * <p>
 * 
 * @author andyman
 *
 */
public class IniProfileMerger {

    
    private static final String TEMPLATE_REPLACEMENT_CHAR = "@";
    
//    private static final String BUILD_HOME = "target";
//    private static final String DIRNAME_EXPLODED = "conf-exploded";
//    private static final String DIRNAME_JARS = "jars";
    
    private MavenProject project;
    
    private IniSystemModel systemModel;
    
    /** integrates with maven output */
    private Log log;
    
//    /** the base dir of the current project */
//    private File PROJECT_DIR;
    
//    /** name of the current project, i.e. maven artifactID */
//    private String CURRENT_PROJECT_NAME;
//    
//    private File TARGET_FILE;
//    
//    private File BATCH_FILE;
//    
//    private FileWriter BATCH_FILE_WRITER;
//    
//    private File EXPLODED_DIR;
//    
//    private File JAR_DIR;
    
    
    /**
     * Execute this actual merge.
     * The implementation can assume that it is invoked in the correct project,
     * which is "iniRuntimeConfig".
     */
    public void mergeConfigurations() throws IniBuildException{
		
    	log.info("merging ini runtime configurations");
    	
    	// obtain master template
    	Properties templateProperties = retrieveTemplateProperties();
    	
    	// obtain collection of active BuildProfiles
    	Set<IniBuildProfile> activeProfiles = systemModel.getActiveProfiles(project.getBasedir());
    	
    	if(activeProfiles.size() == 0) {
    		throw new IniBuildException("no active ini runtime profiles available");
    	}
    	
    	for (IniBuildProfile iniBuildProfile : activeProfiles) {
			
    		Properties profileProperties = retrieveProfileProperties(iniBuildProfile);
    		Properties effectiveProperties = getEffectiveProperties(templateProperties, profileProperties);
    		generateEffectiveProfile(iniBuildProfile, effectiveProperties);
    		log.info("merged profile " + iniBuildProfile.toString() + "");
			
		}
		
	}


    
    private void generateEffectiveProfile(IniBuildProfile iniBuildProfile,
			Properties effectiveProperties) throws IniBuildException {
    	
    	String outputDirPath = "modules" + File.separator 
					        + iniBuildProfile.getClient() + "-"
					        + iniBuildProfile.getStage() + File.separator 
					        + "src" + File.separator
					        + "main" + File.separator
					        + "resources" + File.separator;
    	
    	// if output dir does not exits, create
    	File outputDir = new File(project.getBasedir(), outputDirPath);
		if(!outputDir.exists()) { 
			boolean result = outputDir.mkdirs();
			if(!result) {
				throw new IniBuildException("cannot write merged properties out:  '"+outputDir.getAbsolutePath()+"' cannot be written");
			}
		}
    	
		String fullPath = outputDirPath + "ini.runtime.properties";
    
    	File mergedPropertiesFile = new File(project.getBasedir(), fullPath);
  
	    try {
	        FileOutputStream outStream = new FileOutputStream(mergedPropertiesFile);
	        effectiveProperties.store(outStream, "generated file, do not edit");
	        outStream.close();
	    } catch (IOException e) {
	        throw new IniBuildException("cannot write merged properties out:  '"+mergedPropertiesFile.getAbsolutePath()+"'", e);
	    }
		
	}



	private Properties retrieveProfileProperties(IniBuildProfile profile) throws IniBuildException {
		
    	String file = "profiles" + File.separator 
		    + profile.getStage() + File.separator
		    + profile.getClient() + ".properties";
		
		File profileTemplateFile = new File(project.getBasedir(), file);
    	if(!profileTemplateFile.exists()) {
    		throw new IniBuildException("build profile missing: " + profile.toString());
    	}
    	Properties profileProps = new Properties();
    	try {
			profileProps.load(new FileInputStream(profileTemplateFile));
		} catch (IOException e) {
			throw new IniBuildException("cannot load build profile properties file: " + profile.toString() + ", file " + profileTemplateFile.getAbsolutePath());
		}
		return profileProps;
	}

    /**
     * new
     * @return
     * @throws IniBuildException
     */
	private Properties retrieveTemplateProperties() throws IniBuildException {
		File masterTemplate = new File(project.getBasedir(), "profiles" + File.separator + "_template_" + File.separator + "ini.template.properties");
    	if(!masterTemplate.exists()) {
    		throw new IniBuildException("no ini runtime configuration template available: missing '"+masterTemplate.getAbsolutePath()+"'");
    	}
    	Properties masterProps = new Properties();
    	try {
			masterProps.load(new FileInputStream(masterTemplate));
		} catch (IOException e) {
			throw new IniBuildException("cannot load ini runttim configuration template properties");
		}
		return masterProps;
	}
	
	



//    /**
//     * When execution is triggered, it is already verified that we are in iniConfigGenerator 
//     * project.
//     * 
//     * @throws MojoExecutionException
//     * @throws MojoFailureException
//     */
//    @Deprecated
//    public void executeConfigMojo() throws MojoExecutionException, MojoFailureException{
//	
//        
//        // configuration-template, master properties
//        Properties masterProperties = getMasterProperties();
//        
//        
//        
//        for (String client : iniBuild.CLIENTS) {
//            for (String stage : iniBuild.STAGES) {
//                
//                log.info(client + "-" + stage);
//                
//                // find merge properties for client and stage
//                Properties mergeProperties = getMergeProperties(client, stage);
//                
//                Properties effectiveProperties = getEffectiveProperties(masterProperties, mergeProperties);
//                
//                String path = "modules" + File.separator 
//                    + client + "-"
//                    + stage + File.separator 
//                    + "src" + File.separator
//                    + "main" + File.separator
//                    + "resources" + File.separator
//                    + "ini.runtime.properties";
//                File mergedPropertiesFile = new File(PROJECT_DIR, path);
//              
//                try {
//                    FileOutputStream outStream = new FileOutputStream(mergedPropertiesFile);
//                    effectiveProperties.store(outStream, "generated file, do not edit");
//                    outStream.close();
//                } catch (IOException e) {
//                    throw new MojoExecutionException("cannot write properties out:  '"+mergedPropertiesFile.getAbsolutePath()+"'", e);
//                }
//                
//                log.info(" -> " + path);
//                
//            }
//        }  // outer loop
//        
//    }


    protected Properties getEffectiveProperties(Properties templateProperties,
            Properties clientProfileProperties) {
        
        Properties effectiveProperties = new Properties();
        
        Set<Object> templateKeys = templateProperties.keySet();
        
        for (Object o : templateKeys) {
            
            String templateKey = (String)o;
            String templateValue = (String) templateProperties.get(templateKey);
            
            boolean mergedValueAvailable = clientProfileProperties.getProperty(templateKey) != null;
            String replacement = clientProfileProperties.getProperty(templateKey);
            
            String search = TEMPLATE_REPLACEMENT_CHAR + templateKey + TEMPLATE_REPLACEMENT_CHAR;
            
            if(replacement == null) {
                // no value in profile
                replacement = "";
            }
            
            String profileValue = templateValue.replace(search, replacement);
            
            if(mustReplaceValue(templateValue.trim())) {
        	
        	effectiveProperties.put(templateKey, profileValue);
        	
            } else {
        	
                // don't replace, value in template file is a default value
        	// there is a default in the template,
                // only override if there is a property
                if(mergedValueAvailable) {
                    effectiveProperties.put(templateKey, replacement);
                } else {
                    effectiveProperties.put(templateKey, templateValue);
                }
                
            }
            
        }
        return effectiveProperties;
    }


    private boolean mustReplaceValue(String trim) {
        return trim.startsWith(TEMPLATE_REPLACEMENT_CHAR) && trim.endsWith(TEMPLATE_REPLACEMENT_CHAR);
    }


    
    public void setLog(Log log) {
        this.log = log;
    }

    public void setMavenProject(MavenProject mavenProject) {
        this.project = mavenProject;
    }

    public void setSystemModel(IniSystemModel systemModel) {
		this.systemModel = systemModel;
	}
    
}
