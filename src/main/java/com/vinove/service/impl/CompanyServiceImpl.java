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
import com.vinove.entity.Company;
import com.vinove.entity.PageBean;
import com.vinove.exception.HandleException;
import com.vinove.repository.CompanyRepository;
import com.vinove.service.CompanyService;
import com.vinove.utils.CommonUtil;
import com.vinove.utils.FilterSpecification;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	public boolean isAvailable(String name) throws HandleException {

		boolean flag = true;
		try {
			Company entity = companyRepository.findByName(name);
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
	public CompanyDto save(CompanyDto companyDto) throws HandleException {
		try {
			Company company = CommonUtil.copyProperties(companyDto, Company.class);
			company = companyRepository.save(company);
			if (company.getId() != null) {
				companyDto.setId(company.getId());
			}
		} catch (Exception e) {
			throw new HandleException(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
		return companyDto;

	}

	@Override
	public Page<CompanyDto> getAll(PageBean pageBean) throws HandleException {
		try {
			final Page<Company> page;
			Specification<Company> spec = Specification.where(null);
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
			page = companyRepository.findAll(spec, pageBean.toPage());
			return page.map(this::convertToDto);
		} catch (Exception e) {
			throw new HandleException(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}

	@Override
	public CompanyDto getById(Integer companyId) throws HandleException {
		CompanyDto companyDto = null;
		Company company = null;
		try {
			Optional<Company> optionalCompany = companyRepository.findById(companyId);
			if (optionalCompany.isPresent()) {
				company = optionalCompany.get();
				companyDto = convertToDto(company);
			} else {
				throw new HandleException(HttpStatus.NOT_FOUND, "Company not found with given Id : " + companyId);
			}
			return companyDto;
		} catch (Exception e) {
			throw new HandleException(HttpStatus.NOT_FOUND, "Company not found with given Id : " + companyId, e);
		}
	}

	@Override
	public CompanyDto getByName(String companyName) throws HandleException {
		CompanyDto companyDto = null;
		Company company = null;
		try {
			company = companyRepository.findByName(companyName);
			if (company != null) {
				companyDto = convertToDto(company);
			}
			return companyDto;
		} catch (Exception e) {
			throw new HandleException(HttpStatus.NOT_FOUND,
					"Company not found with given company name : " + companyName, e);
		}
	}

	@Override
	public CompanyDto updateById(CompanyDto companyDto, Integer companyId) throws HandleException {
		Company company = null;
		try {
			Optional<Company> optionalCompany = companyRepository.findById(companyId);
			if (optionalCompany.isPresent()) {
				company = optionalCompany.get();
				company.setName(companyDto.getName());
				company.setDetails(companyDto.getDetails());
			} else {
				throw new HandleException(HttpStatus.NOT_FOUND, "Company not found with given Id : " + companyId);
			}
			return convertToDto(company);
		} catch (Exception e) {
			throw new HandleException(HttpStatus.NOT_FOUND, "Company not found with given Id : " + companyId, e);
		}

	}

	@Override
	public Boolean deleteById(Integer companyId) throws HandleException {
		Boolean deleted = false;
		try {
			Optional<Company> optionalCompany = companyRepository.findById(companyId);
			if (optionalCompany.isPresent()) {
				companyRepository.deleteById(companyId);
				deleted = true;
			} else {
				throw new HandleException(HttpStatus.NOT_FOUND, "Company not found with given Id : " + companyId);
			}
		} catch (Exception e) {
			throw new HandleException(HttpStatus.NOT_FOUND, "Company not found with given Id : " + companyId, e);
		}
		return deleted;
	}

	public CompanyDto convertToDto(Company company) {
		CompanyDto companyDto = CommonUtil.copyProperties(company, CompanyDto.class);
		return companyDto;
	}

	/* bolow two method I created temprorary on the basis of name */

//	private CompanyDto updateByName(CompanyDto companyDto, String companyName) throws HandleException {
//		Company company = null;
//		try {
//			company = companyRepository.findByName(companyName);
//			if (company != null) {
//				company.setName(companyDto.getName());
//				company.setDetails(companyDto.getDetails());
//			} else {
//				throw new HandleException(HttpStatus.NOT_FOUND, "Company not found with given name : " + companyName);
//			}
//			return companyDto;
//		} catch (Exception e) {
//			throw new HandleException(HttpStatus.NOT_FOUND, "Company not found with given name : " + companyName, e);
//		}
//
//	}
//
//	private void deleteByName(String companyName) throws HandleException {
//		try {
//			Company company = companyRepository.findByName(companyName);
//			if (company != null) {
//				companyRepository.deleteByName(companyName);
//			} else {
//				throw new HandleException(HttpStatus.NOT_FOUND, "Company not found with given name : " + companyName);
//			}
//		} catch (Exception e) {
//			throw new HandleException(HttpStatus.NOT_FOUND, "Company not found with given name : " + companyName, e);
//		}
//
//	}

}
