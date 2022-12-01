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

import com.vinove.dto.RoleDto;
import com.vinove.entity.PageBean;
import com.vinove.entity.Role;
import com.vinove.exception.HandleException;
import com.vinove.repository.RoleRepository;
import com.vinove.service.RoleService;
import com.vinove.utils.CommonUtil;
import com.vinove.utils.FilterSpecification;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	public boolean isAvailable(String role) throws HandleException {
		boolean flag = true;
		try {
			Role entity = roleRepository.findByName(role);
			if (entity != null) {
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
	public RoleDto save(RoleDto roleDto) throws HandleException {
		try {
			Role role = CommonUtil.copyProperties(roleDto, Role.class);
			role = roleRepository.save(role);
			if (role.getId() != null) {
				roleDto.setId(role.getId());
			}
		} catch (Exception e) {
			throw new HandleException(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
		return roleDto;

	}

	@Override
	public Page<RoleDto> getAll(PageBean pageBean) throws HandleException {
		try {
			final Page<Role> page;
			Specification<Role> spec = Specification.where(null);
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
				pageBean.setOrderBy("name");
				pageBean.setAscendingOrder(true);
				pageBean.setDirection(Direction.ASC);
			}
			page = roleRepository.findAll(spec, pageBean.toPage());
			return page.map(this::convertToDto);
		} catch (Exception e) {
			throw new HandleException(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}

	@Override
	public RoleDto getById(Integer roleId) throws HandleException {
		RoleDto roleDto = null;
		Role role = null;
		try {
			Optional<Role> optionalRole = roleRepository.findById(roleId);
			if (optionalRole.isPresent()) {
				role = optionalRole.get();
				roleDto = convertToDto(role);
			} else {
				throw new HandleException(HttpStatus.NOT_FOUND, "Role not found with given Id : " + roleId);
			}
			return roleDto;
		} catch (Exception e) {
			throw new HandleException(HttpStatus.NOT_FOUND, "Role not found with given Id : " + roleId, e);
		}
	}

	@Override
	public RoleDto getByName(String roleName) throws HandleException {
		RoleDto roleDto = null;
		Role role = null;
		try {
			role = roleRepository.findByName(roleName);
			if (role != null) {
				roleDto = convertToDto(role);
			}
			return roleDto;
		} catch (Exception e) {
			throw new HandleException(HttpStatus.NOT_FOUND, "Role not found with given role name : " + roleName, e);
		}
	}

	@Override
	public RoleDto updateById(RoleDto roleDto, Integer roleId) throws HandleException {
		Role role = null;
		try {
			Optional<Role> optionalRole = roleRepository.findById(roleId);
			if (optionalRole.isPresent()) {
				role = optionalRole.get();
				role.setName(roleDto.getName());
			} else {
				throw new HandleException(HttpStatus.NOT_FOUND, "Role not found with given Id : " + roleId);
			}
			return convertToDto(role);
		} catch (Exception e) {
			throw new HandleException(HttpStatus.NOT_FOUND, "Role not found with given Id : " + roleId, e);
		}

	}

	@Override
	public Boolean deleteById(Integer roleId) throws HandleException {
		Boolean deleted = false;
		try {
			Optional<Role> optionalRole = roleRepository.findById(roleId);
			if (optionalRole.isPresent()) {
				roleRepository.deleteById(roleId);
				deleted = true;
			} else {
				throw new HandleException(HttpStatus.NOT_FOUND, "Role not found with given Id : " + roleId);
			}
		} catch (Exception e) {
			throw new HandleException(HttpStatus.NOT_FOUND, "Role not found with given Id : " + roleId, e);
		}
		return deleted;
	}

	public RoleDto convertToDto(Role role) {
		RoleDto roleDto = CommonUtil.copyProperties(role, RoleDto.class);
		return roleDto;
	}

}
