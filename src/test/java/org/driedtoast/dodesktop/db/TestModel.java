package org.driedtoast.dodesktop.db;

public class TestModel 
{
	@Primary()
	@Indexed(name="id", fieldNames={"id"})
	private Integer id;
	
	
	@Indexed(name="name", fieldNames={"name"})
	private String name;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}