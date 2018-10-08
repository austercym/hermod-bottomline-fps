package com.orwellg.hermod.bottomline.fps.spring.core.env;

import com.netflix.config.DynamicPropertyFactory;
import com.orwellg.umbrella.commons.beans.config.zookeeper.ZkConfigurationParams;
import com.orwellg.umbrella.commons.utils.config.PropertiesUtils;
import com.orwellg.umbrella.commons.utils.config.ZookeeperUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.env.PropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpringArchaiusPropertySource extends PropertySource<Void> {

    private static Logger LOG = LogManager.getLogger(SpringArchaiusPropertySource.class);

	private final static String ZK_HOST_KEY = ZkConfigurationParams.ZK_HOST_KEY;
	private final static String ZK_PATH_KEY = "zookeeper.application.path";
	private final static String ZK_PATH_QOS_KEY = "zookeeper.qos.path";
    private final static String ZK_PATH_FPS_KEY = "zookeeper.dsl.fps";
    private final static String ZK_PATH_IDGENERATOR = "zookeeper.id.generator.config.subbranch";

    private final static String ZK_PATH_KAFKA_KEY = "zookeeper.kafka.config.subbranch";

    private DynamicPropertyFactory dynamicPropertyFactory;
    
    private Map<String, Object> defaultPropertyValues;
    private Integer connectorId;

    private static String zookeeperHost;
    
    public SpringArchaiusPropertySource(String propertiesFileName, Map<String, Object> defaultPropertyValues) {
        super(propertiesFileName);
        try {
            // Load the properties filename from get the spring properties
            PropertiesUtils props = new PropertiesUtils(name);

            connectorId = props.getIntProperty("connector.id");

            LOG.info("[FPS] Reading properties connector {} listening port {}", connectorId, props.getIntProperty("server.port"));

            zookeeperHost = props.getStringProperty(ZK_HOST_KEY);

            List<String> paths = new ArrayList<>();
            paths.add(props.getStringProperty(ZK_PATH_KEY));
            paths.add(props.getStringProperty(ZK_PATH_KAFKA_KEY));
            paths.add(props.getStringProperty(ZK_PATH_FPS_KEY));
            paths.add(props.getStringProperty(ZK_PATH_IDGENERATOR));
            paths.add(props.getStringProperty(ZK_PATH_QOS_KEY));
            ZookeeperUtils.init(zookeeperHost, paths);


            dynamicPropertyFactory = ZookeeperUtils.getDynamicPropertyFactory();

            this.defaultPropertyValues = defaultPropertyValues;
        } catch (Exception e) {
            LOG.error("Cannot initialize the system properties using the properties : {}. Message: {}.", name, e.getMessage(), e);
        }
    }

    public static String getZookeeperHost(){
        return zookeeperHost;
    }

	@Override
	public Object getProperty(String name) {

        String propertyName = name;

        if(propertyName.equals("propertyFileName")){
            return getName();
        }

        if (propertyName.contains("%id")) {
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
            } else if(propertyName.toUpperCase().contains("PASSWORD")) {
                return dynamicPropertyFactory.getSecretProperty(propertyName, (String) defaultPropertyValues.get(propertyName)).get();
            } else {
                return dynamicPropertyFactory.getStringProperty(propertyName, (String) defaultPropertyValues.get(propertyName)).get();
            }
		} else {
			return dynamicPropertyFactory.getStringProperty(propertyName, null).get();
		}
	}

}
