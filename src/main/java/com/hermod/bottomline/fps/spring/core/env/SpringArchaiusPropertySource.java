package com.hermod.bottomline.fps.spring.core.env;

import com.netflix.config.DynamicPropertyFactory;
import com.orwellg.umbrella.commons.beans.config.zookeeper.ZkConfigurationParams;
import com.orwellg.umbrella.commons.utils.config.PropertiesUtils;
import com.orwellg.umbrella.commons.utils.config.ZookeeperUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.env.PropertySource;

import java.util.Map;

public class SpringArchaiusPropertySource extends PropertySource<Void> {

	private final static String ZK_HOST_KEY = ZkConfigurationParams.ZK_HOST_KEY;
	private final static String ZK_PATH_KEY = "zookeeper.application.path";
	
    private static final Logger LOG = LogManager.getLogger(SpringArchaiusPropertySource.class);
    
    private DynamicPropertyFactory dynamicPropertyFactory;
    
    private Map<String, Object> defaultPropertyValues;
    private Integer connectorId;
    
    public SpringArchaiusPropertySource(String propertiesFileName, Map<String, Object> defaultPropertyValues) {
        super(propertiesFileName);
        try {
        		// Load the properties filename from get the spring properties
        		PropertiesUtils props = new PropertiesUtils(name);

        		connectorId = props.getIntProperty("connector.id");
        		
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

    	String propertyName = name;

    	if(propertyName.contains("%id")){
    		propertyName = propertyName.replace("%id", connectorId.toString());
		}
        if (defaultPropertyValues != null && defaultPropertyValues.containsKey(propertyName)) {
            if (defaultPropertyValues.get(propertyName) instanceof Integer) {
                return dynamicPropertyFactory.getIntProperty(propertyName, (Integer) defaultPropertyValues.get(propertyName)).get();
            } else if (defaultPropertyValues.get(propertyName) instanceof Double) {
                return dynamicPropertyFactory.getDoubleProperty(propertyName, (Double) defaultPropertyValues.get(propertyName)).get();
            } else if (defaultPropertyValues.get(propertyName) instanceof Long) {
                return dynamicPropertyFactory.getLongProperty(propertyName, (Long) defaultPropertyValues.get(propertyName)).get();
            } else if (defaultPropertyValues.get(propertyName) instanceof Boolean) {
                return dynamicPropertyFactory.getBooleanProperty(propertyName, (Boolean) defaultPropertyValues.get(propertyName)).get();
            } else if (defaultPropertyValues.get(propertyName) instanceof Float) {
                return dynamicPropertyFactory.getFloatProperty(propertyName, (Float) defaultPropertyValues.get(propertyName)).get();
            } else {
                return dynamicPropertyFactory.getStringProperty(propertyName, (String) defaultPropertyValues.get(propertyName)).get();
            }
		} else {
			return dynamicPropertyFactory.getStringProperty(propertyName, null).get();
		}
	}

}
