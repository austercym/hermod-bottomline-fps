package output.types;

import java.util.ArrayList;

import input.types.ChildType;


public class ObjectWithCollection {

	private ArrayList<String> strings;
	private ArrayList<Long> numbers;
	private ArrayList<ChildType> objects;


	public ArrayList<String> getStrings() {
		if (strings == null) {
			strings = new ArrayList<String>();
		}
		return strings;
	}
	
	public ArrayList<Long> getNumbers() {
		return numbers;
	}

	public void setNumbers(ArrayList<Long> numbers) {
		this.numbers = numbers;
	}
	
	public ArrayList<ChildType> getObjects() {
		if (objects == null) {
			objects = new ArrayList<ChildType>();
		}
		return objects;
	}
	
		
}
