package com.initech.ini;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.initech.ini.service.IniRuntime;
import com.initech.ini.service.OrderSplitter;
import com.initech.ini.service.ServiceFactory;


import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;


/**
 * 
 * This is NOT a unit test, but an integration test that just happens to 
 * use jUnit as test runner.
 * <p>
 * Remember the golden rule: if the test interacts with a database or 
 * instantiates a full blown spring application context, then it is not
 * a unit test by definition.
 * 
 */
@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
    "classpath:/applicationContext.xml" 
})
public class SpringContextIntegrationTest extends AbstractJUnit4SpringContextTests {

    private IniRuntime runtime;
    
    private ServiceFactory<OrderSplitter> orderSplitterFactory;
    
    
    @Test
    public void testSanity() throws Exception{
        assertNotNull(runtime);
        System.out.println("client-code   = " + runtime.getClientCode());
        System.out.println("client-name   = " + runtime.getClientName());
        System.out.println("stage         = " + runtime.getStage());
        System.out.println("db-host       = " + runtime.getDbHost());
        System.out.println("sql-dialect   = " + runtime.getHibernateSQLDialect());
        System.out.println("sgw-url       = " + runtime.getServiceGatewayURL());
        System.out.println("ini-version   = " + runtime.getVersion());
    } 
    
    
    @Test
    public void testOrderSplitterFactory() {
    	assertNotNull(orderSplitterFactory);
    	
    	OrderSplitter splitter = orderSplitterFactory.getService();
    	assertNotNull(splitter);
    	
    }
    
    
    @Autowired @Required
    public void setOrderSplitterFactory(
			ServiceFactory<OrderSplitter> orderSplitterFactory) {
		this.orderSplitterFactory = orderSplitterFactory;
	}
    
    @Autowired @Required
    public void setRuntime(IniRuntime runtime) {
        this.runtime = runtime;
    }
    
}
