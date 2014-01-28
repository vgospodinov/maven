package com.initech.ini.maven.service;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.apache.maven.project.MavenProject;

import com.initech.ini.maven.bo.IniSystemModel;
import com.initech.ini.maven.dao.IniSystemModelDAO;
import com.initech.ini.maven.service.IniBuildManagerImpl;


public class IniBuildManagerImplTest {
	
	private IniBuildManagerImpl impl;
	private MavenProject projectMock;
	IniSystemModelDAO systemModelDAOMock;
	IniSystemModel buildModelMock;
	
	
	@Before
	public void setup() throws Exception {
		
		impl = new IniBuildManagerImpl();
		
		buildModelMock = mock(IniSystemModel.class);
		
		projectMock = mock(MavenProject.class);
		impl.setMavenProject(projectMock);
		
		systemModelDAOMock = mock(IniSystemModelDAO.class);
		impl.setModelDAO(systemModelDAOMock);
		
	}

	
	
	@Test
	public void testIsIniMaster_yes() throws Exception{
		
		String master = "iniMaster";
		
		when(projectMock.getArtifactId()).thenReturn(master);
		when(systemModelDAOMock.getIniSystemModel()).thenReturn(buildModelMock);
		when(buildModelMock.getMasterModule()).thenReturn(master);
		
		boolean result = impl.isIniMaster();
		
		assertTrue(result);
		
	} 
	
	@Test
	public void testIsIniMaster_no() throws Exception{
		
		String master = "iniMaster";
		
		when(projectMock.getArtifactId()).thenReturn(master);
		when(systemModelDAOMock.getIniSystemModel()).thenReturn(buildModelMock);
		when(buildModelMock.getMasterModule()).thenReturn("iAmDifferent");
		
		boolean result = impl.isIniMaster();
		
		assertFalse(result);
		
	}
	
	@Test
	public void testIsIniConfig() throws Exception{
		
		String config = "iniRuntimeConfig";
		
		when(projectMock.getArtifactId()).thenReturn(config);
		when(systemModelDAOMock.getIniSystemModel()).thenReturn(buildModelMock);
		when(buildModelMock.getConfigGenModule()).thenReturn(config);
		
		boolean result = impl.isRuntimeConfigProject();
		
		assertTrue(result);
		
	}
	
	
	
	
}
