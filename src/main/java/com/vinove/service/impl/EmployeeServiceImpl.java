package com.vinove.service.impl;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.vinove.dto.CompanyDto;
import com.vinove.dto.DepartmentDto;
import com.vinove.dto.EmployeeDto;
import com.vinove.dto.RoleDto;
import com.vinove.entity.Company;
import com.vinove.entity.Department;
import com.vinove.entity.Employee;
import com.vinove.entity.PageBean;
import com.vinove.entity.Role;
import com.vinove.exception.HandleException;
import com.vinove.repository.CompanyRepository;
import com.vinove.repository.DepartmentRepository;
import com.vinove.repository.EmployeeRepository;
import com.vinove.repository.RoleRepository;
import com.vinove.service.EmployeeService;
import com.vinove.utils.CommonUtil;
import com.vinove.utils.FilterSpecification;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

//	@Autowired
//	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public boolean isAvailable(String email) throws HandleException {
		boolean flag = true;
		try {
			Optional<Employee> optionalEmployee = employeeRepository.findByEmail(email);
			if (optionalEmployee.isPresent()) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (Exception e) {
			throw new HandleException(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
		return flag;
	}

	@Override
	public EmployeeDto save(EmployeeDto employeeDto) throws HandleException {
		try {
			Employee employee = convertToEntity(employeeDto);
			if (employee != null) {
				employee.setPassword(employeeDto.getPassword());
//				employee.setPassword(bCryptPasswordEncoder.encode(employeeDto.getPassword()));
			} else {
				throw new HandleException(HttpStatus.NOT_FOUND, "Employee object is empty: " + employee);
			}
			if (employee.getRole() != null) {
				if (employee.getRole().getId() != null) {
					Optional<Role> role = roleRepository.findById(employee.getRole().getId());
					if (!role.isPresent()) {
						throw new HandleException(HttpStatus.INTERNAL_SERVER_ERROR,
								"role not available with ths given id");
					} else {
						employee.setRole(role.get());
					}
				} else if (employee.getRole().getName() != null) {
					Role role = roleRepository.findByName(employee.getRole().getName());
					if (role == null) {
						throw new HandleException(HttpStatus.INTERNAL_SERVER_ERROR,
								"role not available with this given name");
					} else {
						employee.setRole(role);
					}
				}
			} else {
				throw new HandleException(HttpStatus.INTERNAL_SERVER_ERROR, "role object is empty");
			}

			if (employee.getCompany() != null) {
				if (employee.getCompany().getId() != null) {
					Optional<Company> company = companyRepository.findById(employee.getCompany().getId());
					if (!company.isPresent()) {
						throw new HandleException(HttpStatus.INTERNAL_SERVER_ERROR,
								"company not available with ths given id");
					} else {
						employee.setCompany(company.get());
					}
				} else if (employee.getCompany().getName() != null) {
					Company company = companyRepository.findByName(employee.getCompany().getName());
					if (company == null) {
						throw new HandleException(HttpStatus.INTERNAL_SERVER_ERROR,
								"company not available with this given name");
					} else {
						employee.setCompany(company);
					}
				}
			} else {
				throw new HandleException(HttpStatus.INTERNAL_SERVER_ERROR, "company object is empty");
			}

			if (employee.getDepartment() != null) {
				if (employee.getDepartment().getId() != null) {
					Optional<Department> department = departmentRepository.findById(employee.getDepartment().getId());
					if (!department.isPresent()) {
						throw new HandleException(HttpStatus.INTERNAL_SERVER_ERROR,
								"department not available with ths given id");
					} else {
						employee.setDepartment(department.get());
					}
				} else if (employee.getDepartment().getName() != null) {
					Department department = departmentRepository.findByName(employee.getDepartment().getName());
					if (department == null) {
						throw new HandleException(HttpStatus.INTERNAL_SERVER_ERROR,
								"department not available with this given name");
					} else {
						employee.setDepartment(department);
					}
				}
			} else {
				throw new HandleException(HttpStatus.INTERNAL_SERVER_ERROR, "department object is empty");
			}

			employee = employeeRepository.save(employee);
			if (employee.getId() != null) {
				employeeDto.setId(employee.getId());
				employeeDto.setRole(CommonUtil.copyProperties(employee.getRole(), RoleDto.class));
				employeeDto.setCompany(CommonUtil.copyProperties(employee.getCompany(), CompanyDto.class));
				employeeDto.setDepartment(CommonUtil.copyProperties(employee.getDepartment(), DepartmentDto.class));
			}
		} catch (Exception e) {
			throw new HandleException(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
		return employeeDto;

	}

	@Override
	public Page<EmployeeDto> getAll(PageBean pageBean) throws HandleException {
		try {
			final Page<Employee> page;
			Specification<Employee> spec = Specification.where(null);
			if (CommonUtil.isNotEmpty(pageBean.getFilters())) {
				Map<String, Object> map = pageBean.getFilters();
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					String keyName = entry.getKey();
					Object value = entry.getValue();
					if (keyName != null && value != null) {
						if (value instanceof String) {
							spec = spec.and(FilterSpecification.filterData(keyName, value));
						} else {
							spec = spec.and(FilterSpecification.filterDataEquals(keyName, (Serializable) value));
						}
					}
				}
			}
			if (pageBean.isDefaultOrderBy()) {
				pageBean.setOrderBy("id");
				pageBean.setAscendingOrder(true);
				pageBean.setDirection(Direction.ASC);
			}
			page = employeeRepository.findAll(spec, pageBean.toPage());
			return page.map(this::convertToDto);
		} catch (Exception e) {
			throw new HandleException(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}

	@Override
	public EmployeeDto getById(Integer empId) throws HandleException {
		EmployeeDto employeeDto = null;
		Employee employee = null;
		try {
			Optional<Employee> optionalEmployee = employeeRepository.findById(empId);
			if (optionalEmployee.isPresent()) {
				employee = optionalEmployee.get();
				employeeDto = convertToDto(employee);
			} else {
				throw new HandleException(HttpStatus.NOT_FOUND, "Employee not found with given Id : " + empId);
			}
			return employeeDto;
		} catch (Exception e) {
			throw new HandleException(HttpStatus.NOT_FOUND, "Employee not found with given Id : " + empId, e);
		}
	}

	@Override
	public EmployeeDto getByEmail(String email) throws HandleException {
		EmployeeDto employeeDto = null;
		Optional<Employee> employeeOptional = null;
		try {
			employeeOptional = employeeRepository.findByEmail(email);
			if (employeeOptional.isPresent()) {
				employeeDto = convertToDto(employeeOptional.get());
			}
			return employeeDto;
		} catch (Exception e) {
			throw new HandleException(HttpStatus.NOT_FOUND, "Company not found with given company name : " + email, e);
		}
	}

	@Override
	public EmployeeDto updateById(EmployeeDto employeeDto, Integer empId) throws HandleException {
		Employee employee = null;
		try {
			Optional<Employee> optionalEmployee = employeeRepository.findById(empId);
			if (optionalEmployee.isPresent()) {
				employee = optionalEmployee.get();
				employee.setFirstName(employeeDto.getFirstName());
				employee.setLastName(employeeDto.getLastName());
				employee.setEmail(employeeDto.getEmail());
//				employee.setPassword(bCryptPasswordEncoder.encode(employeeDto.getPassword()));
				employee.setPassword(employeeDto.getPassword());
			} else {
				throw new HandleException(HttpStatus.NOT_FOUND, "Employee not found with given Id : " + empId);
			}
			if (employeeDto.getRole() != null) {
				if (employeeDto.getRole().getId() != null) {
					Optional<Role> role = roleRepository.findById(employeeDto.getRole().getId());
					if (!role.isPresent()) {
						throw new HandleException(HttpStatus.INTERNAL_SERVER_ERROR,
								"role not available with ths given id");
					} else {
						employee.setRole(role.get());
					}
				} else if (employeeDto.getRole().getName() != null) {
					Role role = roleRepository.findByName(employeeDto.getRole().getName());
					if (role == null) {
						throw new HandleException(HttpStatus.INTERNAL_SERVER_ERROR,
								"role not available with this given name");
					} else {
						employee.setRole(role);
					}
				}
			} else {
				throw new HandleException(HttpStatus.INTERNAL_SERVER_ERROR, "role object is empty");
			}

			if (employeeDto.getCompany() != null) {
				if (employeeDto.getCompany().getId() != null) {
					Optional<Company> company = companyRepository.findById(employeeDto.getCompany().getId());
					if (!company.isPresent()) {
						throw new HandleException(HttpStatus.INTERNAL_SERVER_ERROR,
								"company not available with ths given id");
					} else {
						employee.setCompany(company.get());
					}
				} else if (employeeDto.getCompany().getName() != null) {
					Company company = companyRepository.findByName(employeeDto.getCompany().getName());
					if (company == null) {
						throw new HandleException(HttpStatus.INTERNAL_SERVER_ERROR,
								"company not available with this given name");
					} else {
						employee.setCompany(company);
					}
				}
			} else {
				throw new HandleException(HttpStatus.INTERNAL_SERVER_ERROR, "company object is empty");
			}

			if (employeeDto.getDepartment() != null) {
				if (employeeDto.getDepartment().getId() != null) {
					Optional<Department> department = departmentRepository
							.findById(employeeDto.getDepartment().getId());
					if (!department.isPresent()) {
						throw new HandleException(HttpStatus.INTERNAL_SERVER_ERROR,
								"department not available with ths given id");
					} else {
						employee.setDepartment(department.get());
					}
				} else if (employeeDto.getDepartment().getName() != null) {
					Department department = departmentRepository.findByName(employeeDto.getDepartment().getName());
					if (department == null) {
						throw new HandleException(HttpStatus.INTERNAL_SERVER_ERROR,
								"department not available with this given name");
					} else {
						employee.setDepartment(department);
					}
				}
			} else {
				throw new HandleException(HttpStatus.INTERNAL_SERVER_ERROR, "department object is empty");
			}
			return convertToDto(employee);
		} catch (Exception e) {
			throw new HandleException(HttpStatus.NOT_FOUND, "Employee not found with given Id : " + empId, e);
		}

	}

	@Override
	public Boolean deleteById(Integer empId) throws HandleException {
		Boolean deleted = false;
		try {
			Optional<Employee> optionalCompany = employeeRepository.findById(empId);
			if (optionalCompany.isPresent()) {
				employeeRepository.deleteById(empId);
				deleted = true;
			} else {
				throw new HandleException(HttpStatus.NOT_FOUND, "Employee not found with given Id : " + empId);
			}
		} catch (Exception e) {
			throw new HandleException(HttpStatus.NOT_FOUND, "Employee not found with given Id : " + empId, e);
		}
		return deleted;
	}

	public EmployeeDto convertToDto(Employee employee) {
		EmployeeDto employeeDto = CommonUtil.copyProperties(employee, EmployeeDto.class);
		RoleDto roleDto = CommonUtil.copyProperties(employee.getRole(), RoleDto.class);
		if (roleDto != null) {
			employeeDto.setRole(roleDto);
		}
		CompanyDto companyDto = CommonUtil.copyProperties(employee.getCompany(), CompanyDto.class);
		if (companyDto != null) {
			employeeDto.setCompany(companyDto);
		}
		DepartmentDto departmentDto = CommonUtil.copyProperties(employee.getDepartment(), DepartmentDto.class);
		if (departmentDto != null) {
			employeeDto.setDepartment(departmentDto);
		}
		return employeeDto;
	}

	public Employee convertToEntity(EmployeeDto employeeDto) {
		Employee employee = CommonUtil.copyProperties(employeeDto, Employee.class);
		Role role = CommonUtil.copyProperties(employeeDto.getRole(), Role.class);
		if (role != null) {
			employee.setRole(role);
		}
		Company company = CommonUtil.copyProperties(employeeDto.getCompany(), Company.class);
		if (company != null) {
			employee.setCompany(company);
		}
		Department department = CommonUtil.copyProperties(employeeDto.getDepartment(), Department.class);
		if (department != null) {
			employee.setDepartment(department);
		}
		return employee;
	}

}
