package com.vinove.service;

import org.springframework.data.domain.Page;

import com.vinove.dto.RoleDto;
import com.vinove.entity.PageBean;
import com.vinove.exception.HandleException;

public interface RoleService {
	
	public boolean isAvailable(String role) throws HandleException;

	public RoleDto save(RoleDto roleDto) throws HandleException;

	public Page<RoleDto> getAll(PageBean pageBean) throws HandleException;

	public RoleDto getById(Integer roleId) throws HandleException;

	public RoleDto getByName(String role) throws HandleException;

	public RoleDto updateById(RoleDto roleDto, Integer roleId) throws HandleException;

	public Boolean deleteById(Integer roleId) throws HandleException;

}
