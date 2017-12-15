package com.hermod.bottomline.fps.listeners;

import com.hermod.bottomline.fps.services.transform.FPSTransform;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class BaseListener {

	@Autowired
	protected Map<String, FPSTransform> transforms;

	protected FPSTransform getTransform(String packageName) {
		String beanKey = "transform_" + packageName.substring(packageName.lastIndexOf(".")+1);
		return (transforms.containsKey(beanKey)) ? transforms.get(beanKey) : null;
	}

	protected FPSTransform getTransformsUML(String className) {
		String beanKey = "transform_" + className.substring(className.lastIndexOf(".")+1);
		return (transforms.containsKey(beanKey)) ? transforms.get(beanKey) : null;
	}
}
