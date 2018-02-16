package com.orwellg.hermod.bottomline.fps;

import com.orwellg.hermod.bottomline.fps.spring.context.SpringProfileSettingApplicationContextInitializer;
import com.orwellg.hermod.bottomline.fps.spring.core.env.SpringArchaiusPropertySource;
import com.orwellg.hermod.bottomline.fps.utils.properties.DefaultPropertyValues;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class FpsBootApplication {
	private static final Logger LOG = LogManager.getLogger(FpsBootApplication.class);

    public static void main(String[] args) {
    	try {
			new SpringApplicationBuilder(FpsBootApplication.class).initializers(new SpringProfileSettingApplicationContextInitializer("hermod-bottomline-fps.properties", DefaultPropertyValues.getDefaultValues())).run(args);
		}catch(Exception e){
    		LOG.error("[FPS] Error running connector {}", e.getMessage(), e);
		}
    }

}
