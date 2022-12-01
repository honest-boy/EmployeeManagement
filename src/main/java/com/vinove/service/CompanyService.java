package com.vinove.service;

import org.springframework.data.domain.Page;

import com.vinove.dto.CompanyDto;
import com.vinove.entity.PageBean;
import com.vinove.exception.HandleException;

public interface CompanyService {

	public boolean isAvailable(String name) throws HandleException;

	public CompanyDto save(CompanyDto companyDto) throws HandleException;

	public Page<CompanyDto> getAll(PageBean pageBean) throws HandleException;

	public CompanyDto getById(Integer companyId) throws HandleException;

	public CompanyDto getByName(String companyName) throws HandleException;

	public CompanyDto updateById(CompanyDto companyDto, Integer companyId) throws HandleException;

	public Boolean deleteById(Integer companyId) throws HandleException;

}
