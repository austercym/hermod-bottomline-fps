package com.hermod.bottomline.fps.spring.core.env;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.env.PropertySource;

import com.netflix.config.DynamicPropertyFactory;
import com.orwellg.umbrella.commons.beans.config.zookeeper.ZkConfigurationParams;
import com.orwellg.umbrella.commons.utils.config.PropertiesUtils;
import com.orwellg.umbrella.commons.utils.config.ZookeeperUtils;

public class SpringArchaiusPropertySource extends PropertySource<Void> {

	private final static String ZK_HOST_KEY = ZkConfigurationParams.ZK_HOST_KEY;
	private final static String ZK_PATH_KEY = "zookeeper.application.path";
	
    private static final Logger LOG = LogManager.getLogger(SpringArchaiusPropertySource.class);
    
    private DynamicPropertyFactory dynamicPropertyFactory;
    
    private Map<String, Object> defaultPropertyValues;
    
    public SpringArchaiusPropertySource(String propertiesFileName, Map<String, Object> defaultPropertyValues) {
        super(propertiesFileName);
        try {
        		// Load the properties filename from get the spring properties
        		PropertiesUtils props = new PropertiesUtils(name);
        		
        		String zookeeperHost = props.getStringProperty(ZK_HOST_KEY);
        		String zookeeperPath = props.getStringProperty(ZK_PATH_KEY);
        		ZookeeperUtils.init(zookeeperHost, zookeeperPath);
        		dynamicPropertyFactory = ZookeeperUtils.getDynamicPropertyFactory();
        		this.defaultPropertyValues = defaultPropertyValues;
        } catch (Exception e) {
            LOG.warn("Cannot initialize the system properties using the properties : {}. Message: {}.", name, e.getMessage(), e);
        }
    }
    
	@Override
	public Object getProperty(String name) {
    		
    		if (defaultPropertyValues != null && defaultPropertyValues.containsKey(name)) {
    			if (defaultPropertyValues.get(name) instanceof Integer) {
    				return dynamicPropertyFactory.getIntProperty(name, (Integer) defaultPropertyValues.get(name)).get();
    			} else if (defaultPropertyValues.get(name) instanceof Double) {
    				return dynamicPropertyFactory.getDoubleProperty(name, (Double) defaultPropertyValues.get(name)).get();
    			} else if (defaultPropertyValues.get(name) instanceof Long) {
    				return dynamicPropertyFactory.getLongProperty(name, (Long) defaultPropertyValues.get(name)).get();
    			} else if (defaultPropertyValues.get(name) instanceof Boolean) {
    				return dynamicPropertyFactory.getBooleanProperty(name, (Boolean) defaultPropertyValues.get(name)).get();
    			} else if (defaultPropertyValues.get(name) instanceof Float) {
    				return dynamicPropertyFactory.getFloatProperty(name, (Float) defaultPropertyValues.get(name)).get();
    			} else {
    				return dynamicPropertyFactory.getStringProperty(name, (String) defaultPropertyValues.get(name)).get();
    			}
		} else {
			return dynamicPropertyFactory.getStringProperty(name, null).get();
		}
	}

}
