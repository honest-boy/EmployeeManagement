package com.vinove.dto;

public class DepartmentDto {

	private Integer id;

	private String name;

	public DepartmentDto() {

	}

	public DepartmentDto(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

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

	@Override
	public String toString() {
		return "DepartmentDto [id=" + id + ", name=" + name + "]";
	}

}
