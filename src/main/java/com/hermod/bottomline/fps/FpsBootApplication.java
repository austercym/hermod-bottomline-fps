package com.hermod.bottomline.fps;

import com.hermod.bottomline.fps.spring.context.SpringProfileSettingApplicationContextInitializer;
import com.hermod.bottomline.fps.utils.properties.DefaultPropertyValues;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class FpsBootApplication {

    public static void main(String[] args) {

		System.getProperties().put( "server.port", 9595 );
    		new SpringApplicationBuilder(FpsBootApplication.class)
    			.initializers(new SpringProfileSettingApplicationContextInitializer("hermod-bottomline-fps.properties", DefaultPropertyValues.getDefaultValues()))
    			.run(args);
    }

}
