package com.initech.ini.integration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.initech.ini.integration.service.HeartbeatService;
import com.initech.ini.service.IniRuntime;
import com.initech.ini.service.OrderSplitter;
import com.initech.ini.service.ServiceFactory;


import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;


/**
 * 
 * 
 */
@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
    "classpath:/applicationContext.xml",
    "classpath:/integrationContext.xml",
})
public class IniIntegrationSpringIntegrationTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private ApplicationContext ctx;
	
	@Autowired
	private HeartbeatService heartbeatService;

	@Test
	public void testContext() throws Exception {
		assertNotNull(ctx);
	}
	
	@Test
	public void testHeartbeatService() throws Exception {
		assertNotNull(heartbeatService);
	}
	
	
	
	
	public void setCtx(ApplicationContext ctx) {
		this.ctx = ctx;
	}
	
	public void setHeartbeatService(HeartbeatService heartbeatService) {
		this.heartbeatService = heartbeatService;
	}
	
	
}
