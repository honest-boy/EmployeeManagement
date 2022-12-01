package com.vinove.dto;

public class CompanyDto {

	private Integer id;

	private String name;

	private String details;

	public CompanyDto() {

	}

	public CompanyDto(Integer id, String name, String details) {
		super();
		this.id = id;
		this.name = name;
		this.details = details;
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

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "CompanyDto [id=" + id + ", name=" + name + ", details=" + details + "]";
	}

}
