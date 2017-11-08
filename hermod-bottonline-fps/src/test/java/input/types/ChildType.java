package input.types;

public class ChildType {

	private String id;
	private ChildEnum enumValue;

	public String getId() {
		return id;
	}
	
	public ChildType() {
		id = "child_id";
		enumValue = ChildEnum.VALUE2;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ChildEnum getEnumValue() {
		return enumValue;
	}

	public void setEnumValue(ChildEnum enumValue) {
		this.enumValue = enumValue;
	}
}
