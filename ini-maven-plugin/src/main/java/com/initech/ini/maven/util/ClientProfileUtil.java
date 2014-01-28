package com.initech.ini.maven.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;


/**
 * 
 * 
 *
 */
public class ClientProfileUtil {

    
    public Set<String> getKeys(File file) throws IOException{
	
	assertFileIsReadable(file);
	
	Set<String> result = new HashSet<String>();
	
	Properties props = new Properties();
	props.load(new FileInputStream(file));
	
	
	Set<Object> keySet = props.keySet();
	
	for (Object key : keySet) {
	    result.add((String) key);
	}
	return result;
    }

    
    
    
    
    
    
    /**
     * Helper method. Throws IlleagalArgumentException in case file is null.
     * Throws IOException if file does not exist or cannot be read.
     */
    private void assertFileIsReadable(File file) throws IOException {
		if(file == null) {
		    throw new IllegalArgumentException("file is null");
		}
		
		if(!file.exists()) {
		    throw new IOException("file '" + file.getAbsolutePath() + "' does not exist" );
		}
		
		if(!file.canRead()) {
		    throw new IOException("file '"+file.getAbsolutePath()+"' cannot be read");
		}
    }
    
    

}
