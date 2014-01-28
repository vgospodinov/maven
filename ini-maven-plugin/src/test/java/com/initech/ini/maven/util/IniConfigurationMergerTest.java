package com.initech.ini.maven.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import java.util.Properties;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.junit.Before;
import org.junit.Test;


public class IniConfigurationMergerTest {

    private IniProfileMerger merger;
    
    private Log logMock; 
    
    private MavenProject mavenProjectMock;
    
    
    @Before
    public void setup() throws Exception{
        merger = new IniProfileMerger();
        
        logMock = mock(Log.class);
        merger.setLog(logMock);
        merger.setMavenProject(mavenProjectMock);
        
        mavenProjectMock = mock(MavenProject.class);
    }
    
    
    
    @Test
    public void testGetEffectiveProperties_simple() throws Exception{
        
        String key = "key";
        Properties template = new Properties();
        template.put(key, "@key@");
        
        Properties clientProfile = new Properties();
        clientProfile.put(key, "replacement");
        
        Properties result = merger.getEffectiveProperties(template, clientProfile);
        
        assertNotNull(result);
        assertEquals(1, result.size());
        
        assertProperty("replacement", "key", result);
        
    }
    
    
    
    /**
     * not sure if this is the required behaviour. maybe it would be better
     * to fail the bild under this circumstances...
     * 
     */
    @Test
    public void testGetEffectiveProperties_no_profile_value() throws Exception{
        
        String key = "key";
        Properties template = new Properties();
        template.put(key, "@key@");
        
        Properties clientProfile = new Properties();
        
        Properties result = merger.getEffectiveProperties(template, clientProfile);
        
        assertNotNull(result);
        assertEquals(1, result.size());
        
        assertProperty("", "key", result);
        
    }
    
    
    /** too much @? don't know, better test */
    public void testGetEffectiveProperties_at_value() throws Exception{
        
        String key = "key";
        Properties template = new Properties();
        template.put(key, "@key@");
        
        Properties clientProfile = new Properties();
        clientProfile.put("key", "my@email.com");
        
        Properties result = merger.getEffectiveProperties(template, clientProfile);
        
        assertNotNull(result);
        assertEquals(1, result.size());
        
        assertProperty("my@email.com", "key", result);
        
    }
    
    
    
    
    


    @Test
    public void testGetEffectiveProperties_override_default() throws Exception{
        
        String key = "key";
        Properties template = new Properties();
        template.put(key, "defaultValue");
        
        Properties clientProfile = new Properties();
        clientProfile.put(key, "replacementValue");
        
        Properties result = merger.getEffectiveProperties(template, clientProfile);
        
        assertNotNull(result);
        assertEquals(1, result.size());
        
        assertProperty("replacementValue", "key", result);
        
    }
    
    
    @Test
    public void testGetEffectiveProperties_use_default_no_override() throws Exception{
        
        String key = "key";
        Properties template = new Properties();
        template.put(key, "defaultValue");
        
        Properties clientProfile = new Properties();
        
        Properties result = merger.getEffectiveProperties(template, clientProfile);
        
        assertNotNull(result);
        assertEquals(1, result.size());
        
        assertProperty("defaultValue", "key", result);
        
    }
    
    
    /**
     *  test the case, when in the template properties file only
     *  a short notation like
     *    key=
     *  is used, which essentially means
     *    key=@key@ 
     * 
     */
    @Test
    public void testGetEffectiveProperties_shorty_template_style() throws Exception{
        
        String key = "key";
        Properties template = new Properties();
        template.put(key, "");
        
        Properties clientProfile = new Properties();
        clientProfile.put("key", "value from profile");
        Properties result = merger.getEffectiveProperties(template, clientProfile);
        
        assertNotNull(result);
        assertEquals(1, result.size());
        
        assertProperty("value from profile", "key", result);
        
    }
    

    
    

    /** 
     * helper to make tests more readable 
     */
    private void assertProperty(String expectedValue, String key, Properties result) {
        String resultVal = result.getProperty(key);
        assertNotNull(resultVal);
        assertEquals(expectedValue, resultVal);
    }
	
	
}
