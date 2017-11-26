package com.hermod.bottomline.fps.services.transform.test;

import com.hermod.bottomline.fps.services.transform.helper.TransformationHelper;
import org.junit.Test;

public class ConvertFromSourceToTargetTest {

	static {
		try {
			TransformationHelper.registerMapping(
				input.types.ContainerType.class, 
				output.types.ContainerType.class);
		}
		catch (Exception err) {
			err.printStackTrace();
		}
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
