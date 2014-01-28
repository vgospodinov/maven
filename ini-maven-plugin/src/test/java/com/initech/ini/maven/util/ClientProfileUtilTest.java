package com.initech.ini.maven.util;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.initech.ini.maven.util.ClientProfileUtil;

import static org.junit.Assert.*;


public class ClientProfileUtilTest {

    
    private ClientProfileUtil util;
    
    
    @Before
    public void init() throws Exception{
    	util = new ClientProfileUtil();
    }
    
    
    @Test
    public void instantion() throws Exception{
    	assertNotNull(util);
    }
    
    
    @Test
    public void getKeys() throws Exception{
	
		File f = new File(getClass().getResource("profile.unittest.properties").toURI());
		
		Set<String> result = util.getKeys(f);
		
		assertNotNull(result);
		assertEquals(5, result.size());
		assertTrue(result.contains("prop1"));
	
    }
    
    
    @Test( expected = IOException.class )
	public void getKeys_file_notExistant() throws Exception{
		File f = new File("sfdagdfahgdfahgd"); // let's hope that file does not exits
		util.getKeys(f);
    }
    
    
    @Test( expected = IllegalArgumentException.class )
	public void getKeys_file_null() throws Exception{
		util.getKeys(null);
    }
    
    
    
}
