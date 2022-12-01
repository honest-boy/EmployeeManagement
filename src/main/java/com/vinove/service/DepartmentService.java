package com.vinove.service;

import org.springframework.data.domain.Page;

import com.vinove.dto.DepartmentDto;
import com.vinove.entity.PageBean;
import com.vinove.exception.HandleException;

public interface DepartmentService {
	
	public boolean isAvailable(String departmentName) throws HandleException;

	public DepartmentDto save(DepartmentDto departmentDto) throws HandleException;

	public Page<DepartmentDto> getAll(PageBean pageBean) throws HandleException;

	public DepartmentDto getById(Integer departmentId) throws HandleException;

	public DepartmentDto getByName(String departmentName) throws HandleException;

	public DepartmentDto updateById(DepartmentDto departmentDto, Integer departmentId) throws HandleException;

	public Boolean deleteById(Integer departmentId) throws HandleException;

}
