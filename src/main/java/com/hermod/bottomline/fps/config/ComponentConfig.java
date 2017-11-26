package com.hermod.bottomline.fps.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;

@Configuration
public abstract class ComponentConfig {
	
	@Bean
	public Gson gson() {
		return new Gson();
	}
}
