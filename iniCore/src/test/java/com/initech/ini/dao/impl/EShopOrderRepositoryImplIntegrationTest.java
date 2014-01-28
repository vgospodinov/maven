package com.initech.ini.dao.impl;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.initech.ini.model.EShopOrder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
    "classpath:/applicationContext.xml" 
})
public class EShopOrderRepositoryImplIntegrationTest {

	private EShopOrderRepositoryImpl impl;
	

	@Test
	public void testSave() {
		
		EShopOrder newOrder = new EShopOrder();
    	newOrder.setDateImported(new Date());
    	newOrder.setEshopOrderNo("435347");
    	
    	impl.save(newOrder);
    	
    	assertNotNull(newOrder.getId());
    	
	}
	
	
	@Autowired @Required
	public void setImpl(EShopOrderRepositoryImpl impl) {
		this.impl = impl;
	}

}
