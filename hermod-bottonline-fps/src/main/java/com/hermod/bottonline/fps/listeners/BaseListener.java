package com.hermod.bottonline.fps.listeners;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hermod.bottonline.fps.services.transform.FPSTransform;

public class BaseListener {

	@Autowired
	protected Map<String, FPSTransform> transforms;
	
	protected FPSTransform getTransform(String pagkageName) {
		String beanKey = "transform_" + pagkageName.substring(pagkageName.lastIndexOf("."));
		return (transforms.containsKey(beanKey)) ? transforms.get(beanKey) : null;
	}
}
