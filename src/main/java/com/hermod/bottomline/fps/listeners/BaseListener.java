package com.hermod.bottomline.fps.listeners;

import java.util.Map;

import com.hermod.bottomline.fps.services.transform.FPSTransform;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseListener {

	@Autowired
	protected Map<String, FPSTransform> transforms;

	protected FPSTransform getTransform(String packageName) {
		String beanKey = "transform_" + packageName.substring(packageName.lastIndexOf(".")+1);
		return (transforms.containsKey(beanKey)) ? transforms.get(beanKey) : null;
	}
}
