package output.types;

import java.util.Collection;

public class ContainerType {

	private String foo;
	private int bar;
	private ChildType child;
		
	private Collection<String> someStrings;
	
	private Collection<ChildType> items;

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
	
	public void setSomeStrings(Collection<String> strings) {
		someStrings = strings;
	}

	public Collection<ChildType> getItems() {
		return items;
	}
	
	public void setItems(Collection<ChildType> items) {
		this.items = items;
	}

	public ChildType getChild() {
		return child;
	}
	
	public void setChild(ChildType child) {
		this.child = child;
	}
	
}
