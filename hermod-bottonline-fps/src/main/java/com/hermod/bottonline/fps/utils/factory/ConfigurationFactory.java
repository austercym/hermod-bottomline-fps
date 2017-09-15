package com.hermod.bottonline.fps.utils.factory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hermod.bottonline.fps.bean.config.ConfigurationParams;

public class ConfigurationFactory {

	private final static Logger LOG = LogManager.getLogger(ConfigurationFactory.class);
	
	private static ConfigurationParams configurationParams;
	
	
	public static void initConfigurationParams() {
		
		if (configurationParams == null) {
			configurationParams = new ConfigurationParams();
			try {
				configurationParams.start();
			} catch (Exception e) {
				LOG.error("The jms configuration params cannot be started. The system work with the parameters for default. Message: {}",  e.getMessage(),  e);
			}
		}		
	}
	
	public static ConfigurationParams getConfigurationParams() { return configurationParams; }

}
