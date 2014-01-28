package com.initech.ini.integration;

import org.springframework.context.support.FileSystemXmlApplicationContext;

public class IOSCoreRunner {

	/**
	 * Bootstrap ini Springframework application context. 
	 */
	public static void main(String[] args) {
		
		String[] contextDefinitions = new String[] {"classpath:/applicationContext.xml", 
				"classpath:/integrationContext.xml"};
		new FileSystemXmlApplicationContext(contextDefinitions);
	}

}
