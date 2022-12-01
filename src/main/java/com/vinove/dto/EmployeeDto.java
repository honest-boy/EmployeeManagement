package com.vinove.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

public class EmployeeDto {

	private Integer id;

	@NotEmpty
	@Length(min = 2, max = 25)
	@Pattern(regexp = ".*([a-zA-Z]{2}$)")
	private String firstName;

	@NotEmpty
	@Length(min = 2, max = 25)
	@Pattern(regexp = ".*([a-zA-Z]{2}$)")
	private String lastName;

	@NotEmpty
	@Email
	private String email;

	@NotEmpty
	private String password;

	private DepartmentDto department;

	private CompanyDto company;

	private RoleDto role;

	public EmployeeDto() {
	}

	public EmployeeDto(Integer id, String firstName, String lastName, String email, String password,
			DepartmentDto department, CompanyDto company, RoleDto role) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.department = department;
		this.company = company;
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public DepartmentDto getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentDto department) {
		this.department = department;
	}

	public CompanyDto getCompany() {
		return company;
	}

	public void setCompany(CompanyDto company) {
		this.company = company;
	}

	public RoleDto getRole() {
		return role;
	}

	public void setRole(RoleDto role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "EmployeeDto [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", department=" + department + ", company=" + company + ", role=" + role
				+ "]";
	}

}
