package input.types;

import com.hermod.bottomline.fps.types.FPSMessage;

import java.util.ArrayList;
import java.util.Collection;

public class ContainerType implements FPSMessage{

	private String foo;
	private int bar;
	private ChildType child;
	
	public ContainerType() {
		foo = "foo";
		bar = 69;
		someStrings.add("string 1");
		items.add(new ChildType());
		child = new ChildType();
	}
	
	private Collection<String> someStrings = new ArrayList<String>();
	
	private Collection<ChildType> items = new ArrayList<ChildType>();

	public String getFoo() {
		return foo;
	}

	public void setFoo(String foo) {
		this.foo = foo;
	}

	public int getBar() {
		return bar;
	}

	public void setBar(int bar) {
		this.bar = bar;
	}

	public Collection<String> getSomeStrings() {
		return someStrings;
	}

	public Collection<ChildType> getItems() {
		return items;
	}

	public ChildType getChild() {
		return child;
	}
	
}
