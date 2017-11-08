package com.hermod.bottonline.fps.services.transform.test;

import org.junit.Test;

import com.hermod.bottonline.fps.services.transform.helper.ConversionException;
import com.hermod.bottonline.fps.services.transform.helper.TransformationHelper;

public class ConvertObjectsWithCollectionTest {

	static {
		try {
			TransformationHelper.registerMapping(
				input.types.ObjectWithCollection.class, 
				output.types.ObjectWithCollection.class);
		}
		catch (Exception err) {
			err.printStackTrace();
		}
	}
	
	@Test
	public void objectIsProperlyMapped()
	{
		input.types.ObjectWithCollection input = new input.types.ObjectWithCollection();
		
		output.types.ObjectWithCollection output = new output.types.ObjectWithCollection();
				
		try {
			TransformationHelper.updateTargetValues(input, output);
			assert(output.getStrings().size() == input.getStrings().size());
			assert(output.getNumbers().size() == input.getNumbers().size());
		} catch (ConversionException e) {
			e.printStackTrace();
			
			assert(false);
		}
	}
}
