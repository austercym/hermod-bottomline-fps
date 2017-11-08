package input.types;

import java.util.ArrayList;
import java.util.Arrays;

public class ObjectWithCollection {

	private ArrayList<String> strings;
	
	private ArrayList<Long> numbers;
	
	private ArrayList<ChildType> objects;
	
	public ObjectWithCollection() {
		strings = new ArrayList<String>(Arrays.asList("a", "b", "c"));
		numbers = new ArrayList<Long>(Arrays.asList(new Long(1), new Long(2)));
		setObjects(new ArrayList<ChildType>(Arrays.asList(new ChildType())));
	}

	public ArrayList<String> getStrings() {
		return strings;
	}

	public void setStrings(ArrayList<String> strings) {
		this.strings = strings;
	}

	public ArrayList<Long> getNumbers() {
		return numbers;
	}

	public void setNumbers(ArrayList<Long> numbers) {
		this.numbers = numbers;
	}

	public ArrayList<ChildType> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<ChildType> objects) {
		this.objects = objects;
	}
	
	
}
