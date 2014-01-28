package com.initech.ini.maven.bo;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.initech.ini.maven.bo.IniSystemModel;
import com.initech.ini.maven.bo.IniBuildProfile;
import com.initech.ini.maven.dao.IniSystemModelDaoImpl;


/**
 * This is not strictly a unit test, but more of an integration test, as
 * it does not mock away it's datasource but instead uses the real data.
 * <p>
 * So, after changing the real ini application model, expect some tests 
 * to fail here.
 * 
 */
@SuppressWarnings("unused")
public class IniSystemModelIntegrationTest {

	IniSystemModel impl;
	
	@Before
	public void setup() throws Exception {
		impl = new IniSystemModelDaoImpl().getIniSystemModel();
	}
	
	@Test
	public void testGetClients() throws Exception{
	    	
		Set<String> clients = impl.getClients();
		
		assertNotNull(clients);
		assertEquals(3, clients.size());
		assertTrue(clients.contains("coyote"));
		assertTrue(clients.contains("daffy"));
		assertTrue(clients.contains("acme"));
		
	} 
	
	@Test
	public void testGetStages() throws Exception{
	    	
		Set<String> stages = impl.getStages();
		
		assertNotNull(stages);
		assertEquals(5, stages.size());
		assertTrue(stages.contains("local"));
		
	}
	
	
	@Test
	public void testGetProfiles() throws Exception{
	    	
		int profilesTotal = 15;   // at the time (clients * stages)
		
		Set<IniBuildProfile> profiles = impl.getAllProfiles();
		
		assertNotNull(profiles);
		assertEquals(profilesTotal, profiles.size());
		
		assertContains(profiles, new IniBuildProfile("daffy", "local") );
		assertContains(profiles, new IniBuildProfile("acme", "prod") );
		assertContainsNot(profiles, new IniBuildProfile("acme", "moon") );
		
	}

	
	
	@Test
	public void testGetIniModules() throws Exception{
		
		Set<String> modules = impl.getIniModules();
		
		assertNotNull(modules);
		
		for (String module : modules) {
			System.out.println("module: " + module);
		}
		
		assertTrue(modules.contains("iniMaster"));
		assertTrue(modules.contains("iniDropshipper"));
		assertTrue(modules.contains("ini-maven-plugin")); 
		assertEquals("wrong total module count", 
			15, modules.size());
		
	}
	
	@Test
	public void testGetConfigGenModule() throws Exception {
		assertEquals("iniRuntimeConfig", impl.getConfigGenModule());
	}
	
	
	@Test
	public void testGetMasterModule() throws Exception {
		assertEquals("iniMaster", impl.getMasterModule());
	}
	
	
	/** helper */
	private void assertContains(Set<IniBuildProfile> profiles, IniBuildProfile iniBuildProfile) {
		assertNotNull("no profiles", profiles);
		assertNotNull("no profile to compare", iniBuildProfile);
		for (IniBuildProfile current : profiles) {
			if(iniBuildProfile.equals(current)) {
				return;
			}
		}
		fail("expected ini build profile '"+iniBuildProfile+"', but it was not contained");
		
	}
	
	/** helper */
    private void assertContainsNot(Set<IniBuildProfile> profiles, IniBuildProfile iniBuildProfile) {
		assertNotNull("no profiles", profiles);
		assertNotNull("no profile to compare", iniBuildProfile);
		for (IniBuildProfile current : profiles) {
			if(iniBuildProfile.equals(current)) {
				fail("expected ini build profile '"+iniBuildProfile+"', should not be contained, but it is");
		    }
		}
		return;
		
	}
	
}
