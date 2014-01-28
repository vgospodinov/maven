package com.initech.ini.maven.dao;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Properties;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.initech.ini.maven.bo.IniSystemModel;
import com.initech.ini.maven.dao.IniSystemModelDaoImpl;


public class IniSystemModelDaoImplTest {

	
	private IniSystemModelDaoImpl impl;

	@Before
	public void setup() throws Exception {
		impl = new IniSystemModelDaoImpl();
	}
	
	/** helper to prepare a fictional ini model */
	private Properties prepareIniModel() {
		Properties fixture = new Properties();
		fixture.put("ini.clients.enabled", "acme, snakeoil-inc");
		fixture.put("ini.stages.all",      "local, integ, prod");
		fixture.put("ini.modules.master",  "iniM");
		fixture.put("ini.modules.config.generator",  "iniCG");
		fixture.put("ini.modules.distribution",  "iniD");
		fixture.put("ini.modules.maven.plugin",  "iniMvn");
		fixture.put("ini.modules.wars",  "iniSCI, iniCoreHttp");
		fixture.put("ini.modules.jars",  "iniCore, iniSpring");
		return fixture;
	} 
	
	@Test
	public void testGetClients() throws Exception{
		
		Properties fixture = prepareIniModel();
		impl.setIniRuntimeProperties(fixture);
		
		IniSystemModel result = impl.getIniSystemModel();
		
		assertNotNull(result);
		assertNotNull(result.getClients());
		assertEquals(2, result.getClients().size());
		assertTrue(result.getClients().contains("acme"));
		assertTrue(result.getClients().contains("snakeoil-inc"));
		
	}
	
	
	@Test
	public void testGetStages() throws Exception{
		
		Properties fixture = prepareIniModel();
		impl.setIniRuntimeProperties(fixture);
		
		IniSystemModel result = impl.getIniSystemModel();
		
		assertNotNull(result);
		assertNotNull(result.getStages());
		assertEquals(3, result.getStages().size());
		assertTrue(result.getStages().contains("local"));
		assertTrue(result.getStages().contains("prod"));
		assertTrue(result.getStages().contains("integ"));
		
	}
	

	@Test
	public void testWarModules() throws Exception{
		
		Properties fixture = prepareIniModel();
		impl.setIniRuntimeProperties(fixture);
		
		IniSystemModel result = impl.getIniSystemModel();
		
		assertNotNull(result);
		assertNotNull(result.getWarModules());
		assertEquals(2, result.getWarModules().size());
		assertTrue(result.getWarModules().contains("iniSCI"));
		assertTrue(result.getWarModules().contains("iniCoreHttp"));
		
	}
	
	@Test
	public void testJarModules() throws Exception{
		
		Properties fixture = prepareIniModel();
		impl.setIniRuntimeProperties(fixture);
		
		IniSystemModel result = impl.getIniSystemModel();
		
		assertNotNull(result);
		Set<String> results = result.getJarModules();
		assertNotNull(results);
		assertEquals(2, results.size());
		assertTrue(results.contains("iniCore"));
		assertTrue(results.contains("iniSpring"));
		
	}
	
	
	@Test
	public void testGetStringProps() throws Exception{
		
		Properties fixture = prepareIniModel();
		impl.setIniRuntimeProperties(fixture);
		
		IniSystemModel result = impl.getIniSystemModel();
		
		assertNotNull(result);
		assertEquals("iniM", result.getMasterModule());
		assertEquals("iniCG", result.getConfigGenModule());
		assertEquals("iniD", result.getDistributionModule());
		assertEquals("iniMvn", result.getMavenPluginModule());
		
	}
	
	
}
