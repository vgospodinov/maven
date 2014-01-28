package com.initech.ini.maven.dao;

import java.io.IOException;
import java.util.Properties;

import com.initech.ini.maven.bo.IniSystemModel;
import com.initech.ini.maven.bo.IniSystemModelImpl;


public class IniSystemModelDaoImpl implements IniSystemModelDAO {

	private static final String MODEL_SOURCE = "ini.model.properties";
	
	private Properties iniRuntimeProperties;
	
	
	@Override
	public IniSystemModel getIniSystemModel() {
		
		try {
			init();
		} catch (Exception e) {
			throw new RuntimeException("cannot load Ini Application Model", e);
		}
		
		IniSystemModelImpl impl = new IniSystemModelImpl();
		
		impl.setClientIDs(extractCsvFromProperties("ini.clients.enabled"));
		impl.setStageIDs(extractCsvFromProperties("ini.stages.all"));
		impl.setWarModules(extractCsvFromProperties("ini.modules.wars"));
		impl.setJarModules(extractCsvFromProperties("ini.modules.jars"));
		impl.setMasterModule(iniRuntimeProperties.getProperty("ini.modules.master"));
		impl.setDistributionModule(iniRuntimeProperties.getProperty("ini.modules.distribution"));
		impl.setConfigGenModule(iniRuntimeProperties.getProperty("ini.modules.config.generator"));
		impl.setMavenPluginModule(iniRuntimeProperties.getProperty("ini.modules.maven.plugin"));
		
		
		return impl;
	}





	private String[] extractCsvFromProperties(String key) {
		
		String val = iniRuntimeProperties.getProperty(key);
		if(val == null || val.length() == 0) {
			return new String[0];
		}
		
		String[] splitted = val.trim().split(",");
		String[] trimmed = new String[splitted.length];
		int i = 0;
		for (String untrimmedVal : splitted) {
			trimmed[i] = untrimmedVal.trim();
			i++;
		}
		return trimmed;
	};
	

	
	private void init() throws IOException {
		if(iniRuntimeProperties == null) {
			iniRuntimeProperties = new Properties();
			iniRuntimeProperties.load(getClass().getResourceAsStream(MODEL_SOURCE));
		}
	}
	
	
	
	/** for unit tests */ 
	void setIniRuntimeProperties(Properties iniRuntimeProperties) {
		this.iniRuntimeProperties = iniRuntimeProperties;
	}


}
