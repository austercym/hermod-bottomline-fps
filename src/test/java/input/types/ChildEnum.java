package input.types;

public enum ChildEnum {

	VALUE1("ABC"),
	VALUE2("DEF");
			
	private String value;
	
	private ChildEnum(String v) {
		value = v;
	}

	private String getValue() {
		return value;
	}

}
