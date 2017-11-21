package com.hermod.bottonline.fps;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.hermod.bottonline.fps.spring.context.SpringProfileSettingApplicationContextInitializer;
import com.hermod.bottonline.fps.utils.properties.DefaultPropertyValues;

@SpringBootApplication
public class FpsBootApplication {

    public static void main(String[] args) {

		System.getProperties().put( "server.port", 9595 );
    		new SpringApplicationBuilder(FpsBootApplication.class)
    			.initializers(new SpringProfileSettingApplicationContextInitializer("hermod-bottonline-fps.properties", DefaultPropertyValues.getDefaultValues()))
    			.run(args);
    }

}
