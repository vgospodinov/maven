package com.initech.ini.maven.service;


import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.File;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.junit.Before;
import org.junit.Test;

import com.initech.ini.maven.bo.IniBuildFilesystemImpl;
import com.initech.ini.maven.bo.IniSystemModel;
import com.initech.ini.maven.service.IniSystemFactory;
import com.initech.ini.maven.service.IniBuildManager;

/**
 * 
 * @author andyman
 *
 */
public class IniFactoryImplTest {

	
	private MavenProject mavenProjectMock;
	private Log logMock;
	
	@Before
	public void setup() throws Exception {
		mavenProjectMock = mock(MavenProject.class);
		logMock = mock(Log.class);
	}

	@Test
	public void testRetrieveBuildManager() throws Exception{
		IniBuildManager buildManager = IniSystemFactory.getBuildManager(mavenProjectMock, logMock);
		assertNotNull(buildManager);
	} 
	
	

	
}
