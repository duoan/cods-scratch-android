package xyz.anduo.city;

import java.io.Serializable;

public class AreaEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9109987344109677382L;
	private String id;
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "CityEntity [id=" + id + ", name=" + name + "]";
	}

}
