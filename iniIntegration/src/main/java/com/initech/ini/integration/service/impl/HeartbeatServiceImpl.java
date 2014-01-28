package com.initech.ini.integration.service.impl;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.initech.ini.integration.service.HeartbeatService;

@Service("heartbeatService")
public class HeartbeatServiceImpl implements HeartbeatService {

    private static Logger LOG = Logger.getLogger(HeartbeatServiceImpl.class);

    private static final int SECOND = 1000;
    private static final int MINUTE = 60 * SECOND;

    @PostConstruct
    public void alive() {
    	LOG.info("I am born");
    }
	
	/* (non-Javadoc)
	 * @see com.initech.ios.integration.service.HeartbeatService#beat()
	 */
	@Override
	@Scheduled(fixedDelay = 2 * MINUTE)
	public void beat() {
		LOG.info(" * iniIntegration heart beat * ");
	}
	
	
	@Scheduled(cron = "0 0,10,20,30,40,50 * * * ?")
	public void cronExample() {
		LOG.info("this is a job scheduled by cron trigger");
	}
	

	
}
