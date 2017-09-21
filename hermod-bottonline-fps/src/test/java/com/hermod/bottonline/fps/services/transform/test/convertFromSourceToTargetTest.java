package com.hermod.bottonline.fps.services.transform.test;

import org.junit.Test;

import com.hermod.bottonline.fps.services.transform.helper.TransformationHelper;

public class convertFromSourceToTargetTest {

	static {
		TransformationHelper.registerMapping(
			input.types.ContainerType.class, 
			output.types.ContainerType.class);
	}
	
	@Test
	public void testConversion() {
		input.types.ContainerType source = new input.types.ContainerType();
		output.types.ContainerType target = new output.types.ContainerType();
		try {
			TransformationHelper.updateTargetValues(source, target);
			assert(target.getBar() == source.getBar());
			assert(target.getFoo().equals(source.getFoo()));
			assert(target.getChild() != null);
			assert(target.getChild().getEnumValue().equals(source.getChild().getEnumValue().toString()));
			assert(target.getSomeStrings().iterator().next().equals( source.getSomeStrings().iterator().next()));
			assert(target.getItems().iterator().next().getId().equals( source.getItems().iterator().next().getId() ));
		}
		catch (Exception err) {
			err.printStackTrace();
			assert(false);
		}
	}
}
