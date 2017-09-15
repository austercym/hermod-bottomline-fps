package com.hermod.bottonline.fps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.hermod.bottonline.fps.utils.factory.ConfigurationFactory;

@SpringBootApplication
public class FpsBootApplication {

    public static void main(String[] args) {
    		
    		ConfigurationFactory.initConfigurationParams();
    		
        SpringApplication.run(FpsBootApplication.class, args);
    }

}
