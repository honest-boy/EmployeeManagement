package com.vinove.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.sun.istack.NotNull;

@Entity
@Table(name = "employee")
public class Employee implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6428296785873835796L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotEmpty
	@Length(min = 2, max = 25)
	@Pattern(regexp = ".*([a-zA-Z]{2}$)")
	@Column(nullable = false)
	private String firstName;

	@NotEmpty
	@Length(min = 2, max = 25)
	@Pattern(regexp = ".*([a-zA-Z]{2}$)")
	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false, unique = true)
	@NotEmpty
	@Email
	private String email;

	@NotEmpty
	@Column(nullable = false)
	private String password;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;

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

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Employee() {
	}

	public Employee(Employee employee) {
		super();
		this.firstName = employee.getFirstName();
		this.lastName = employee.getLastName();
		this.email = employee.getEmail();
		this.password = employee.getPassword();
		this.role = employee.getRole();
		this.department = employee.getDepartment();
		this.company = employee.getCompany();
	}

}
