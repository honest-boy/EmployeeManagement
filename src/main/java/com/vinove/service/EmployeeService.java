package com.vinove.service;

import org.springframework.data.domain.Page;

import com.vinove.dto.EmployeeDto;
import com.vinove.entity.PageBean;
import com.vinove.exception.HandleException;

public interface EmployeeService {
	
	public boolean isAvailable(String email) throws HandleException;

	public EmployeeDto save(EmployeeDto employeeDto) throws HandleException;

	public Page<EmployeeDto> getAll(PageBean pageBean) throws HandleException;

	public EmployeeDto getById(Integer empId) throws HandleException;

	public EmployeeDto getByEmail(String email) throws HandleException;

	public EmployeeDto updateById(EmployeeDto employeeDto, Integer empId) throws HandleException;

	public Boolean deleteById(Integer empId) throws HandleException;

}
