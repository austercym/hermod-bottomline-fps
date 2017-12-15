package com.hermod.bottomline.fps.config;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public abstract class ComponentConfig {
	
	@Bean
	public Gson gson() {
		return new Gson();
	}
}
