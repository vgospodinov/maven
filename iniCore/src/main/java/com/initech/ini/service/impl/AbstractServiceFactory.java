package com.initech.ini.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import com.initech.ini.service.IniRuntime;
import com.initech.ini.service.ServiceFactory;
import com.initech.ini.util.Acme;
import com.initech.ini.util.Coyote;
import com.initech.ini.util.Daffy;
import com.initech.ini.util.Default;


/**
 * 
 * Basic ServiceFactory implementation that selects Service implementation
 * based on the current client of the partiular ini instance.
 * <p>
 * 
 * 
 * @author pahne01
 *
 * @param <T> The type of the service to select
 */
public abstract class AbstractServiceFactory<T> implements ServiceFactory<T> {
	
	private IniRuntime runtime;
	
	private T defaultService;
	
	private T daffyService;
	
	private T coyoteService;
	
	private T acmeService;
	
	@Override
	public T getService() {
		
		String clientCode = runtime.getClientCode();
		
		if("daffy".equals(clientCode) && daffyService != null ) {
			
			return daffyService;
		
		} else if("coyote".equals(clientCode) && coyoteService != null ) {
			
			return coyoteService;
		
		} else if("acme".equals(clientCode) && acmeService != null ) {
			
			return acmeService;
		
		}
		
		return defaultService;
		
	}
	
	@Autowired(required = true) @Default
	public void setDefaultService(T defaultService) {
		this.defaultService = defaultService;
	}
	
	@Autowired(required = false) @Coyote
	public void setCoyoteService(T coyoteService) {
		this.coyoteService = coyoteService;
	}
	
	@Autowired(required = false) @Daffy
	public void setDaffyService(T daffyService) {
		this.daffyService = daffyService;
	}
	
	@Autowired(required = false) @Acme
	public void setAcmeService(T acmeService) {
		this.acmeService = acmeService;
	}
	
	@Autowired @Required
	public void setRuntime(IniRuntime runtime) {
		this.runtime = runtime;
	}
	
}
