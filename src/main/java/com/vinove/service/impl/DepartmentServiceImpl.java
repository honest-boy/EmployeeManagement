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

import com.vinove.dto.DepartmentDto;
import com.vinove.entity.Department;
import com.vinove.entity.PageBean;
import com.vinove.exception.HandleException;
import com.vinove.repository.DepartmentRepository;
import com.vinove.service.DepartmentService;
import com.vinove.utils.CommonUtil;
import com.vinove.utils.FilterSpecification;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;

	public boolean isAvailable(String name) throws HandleException {
		boolean flag = true;
		try {
			Department entity = departmentRepository.findByName(name);
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
	public DepartmentDto save(DepartmentDto departmentDto) throws HandleException {
		try {
			Department department = CommonUtil.copyProperties(departmentDto, Department.class);
			department = departmentRepository.save(department);
			if (department.getId() != null) {
				departmentDto.setId(department.getId());
			}
		} catch (Exception e) {
			throw new HandleException(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
		return departmentDto;
	}

	@Override
	public Page<DepartmentDto> getAll(PageBean pageBean) throws HandleException {
		try {
			final Page<Department> page;
			Specification<Department> spec = Specification.where(null);
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
			page = departmentRepository.findAll(spec, pageBean.toPage());
			return page.map(this::convertToDto);
		} catch (Exception e) {
			throw new HandleException(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}

	@Override
	public DepartmentDto getById(Integer departmentId) throws HandleException {
		DepartmentDto departmentDto = null;
		Department department = null;
		try {
			Optional<Department> optionalDepartment = departmentRepository.findById(departmentId);
			if (optionalDepartment.isPresent()) {
				department = optionalDepartment.get();
				departmentDto = convertToDto(department);
			} else {
				throw new HandleException(HttpStatus.NOT_FOUND, "Department not found with given Id : " + departmentId);
			}
			return departmentDto;
		} catch (Exception e) {
			throw new HandleException(HttpStatus.NOT_FOUND, "Department not found with given Id : " + departmentId, e);
		}
	}

	@Override
	public DepartmentDto getByName(String departmentName) throws HandleException {
		DepartmentDto departmentDto = null;
		Department department = null;
		try {
			department = departmentRepository.findByName(departmentName);
			if (department != null) {
				departmentDto = convertToDto(department);
			}
			return departmentDto;
		} catch (Exception e) {
			throw new HandleException(HttpStatus.NOT_FOUND,
					"Department not found with given company name : " + departmentName, e);
		}
	}

	@Override
	public DepartmentDto updateById(DepartmentDto departmentDto, Integer departmentId) throws HandleException {
		Department department = null;
		try {
			Optional<Department> optionalDepartment = departmentRepository.findById(departmentId);
			if (optionalDepartment.isPresent()) {
				department = optionalDepartment.get();
				department.setName(departmentDto.getName());
			} else {
				throw new HandleException(HttpStatus.NOT_FOUND, "Department not found with given Id : " + departmentId);
			}
			return convertToDto(department);
		} catch (Exception e) {
			throw new HandleException(HttpStatus.NOT_FOUND, "Department not found with given Id : " + departmentId, e);
		}

	}

	@Override
	public Boolean deleteById(Integer departmentId) throws HandleException {
		Boolean deleted = false;
		try {
			Optional<Department> optionalDepartment = departmentRepository.findById(departmentId);
			if (optionalDepartment.isPresent()) {
				departmentRepository.deleteById(departmentId);
				deleted = true;
			} else {
				throw new HandleException(HttpStatus.NOT_FOUND, "Department not found with given Id : " + departmentId);
			}
		} catch (Exception e) {
			throw new HandleException(HttpStatus.NOT_FOUND, "Department not found with given Id : " + departmentId, e);
		}
		return deleted;
	}

	public DepartmentDto convertToDto(Department department) {
		DepartmentDto departmentDto = CommonUtil.copyProperties(department, DepartmentDto.class);
		return departmentDto;
	}

}
