package com.hermod.bottonline.fps.spring.context;

import java.util.Map;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import com.hermod.bottonline.fps.spring.core.env.SpringArchaiusPropertySource;

public class SpringProfileSettingApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	private String propertiesFileName;
	
	private Map<String, Object> defaultPropertyValues;
	
	@Override
	public void initialize(ConfigurableApplicationContext ctx) {
		ctx.getEnvironment()
        		.getPropertySources()
        		.addFirst(new SpringArchaiusPropertySource(propertiesFileName, defaultPropertyValues));		
	}
	
	public SpringProfileSettingApplicationContextInitializer(String propertiesFileName, Map<String, Object> defaultPropertyValues) {
		this.propertiesFileName = propertiesFileName;
		this.defaultPropertyValues = defaultPropertyValues;
	}
}
