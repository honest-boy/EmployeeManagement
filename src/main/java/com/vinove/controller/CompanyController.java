package com.vinove.controller;

import java.security.Principal;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vinove.constant.Mapping;
import com.vinove.dto.CompanyDto;
import com.vinove.dto.ResponseBean;
import com.vinove.entity.PageBean;
import com.vinove.exception.HandleException;
import com.vinove.service.CompanyService;
import com.vinove.utils.CommonUtil;

@RestController
@RequestMapping(value = { "/company" })
public class CompanyController extends BaseController {

	@Autowired
	private CompanyService companyService;

	@PostMapping
	public ResponseEntity<ResponseBean<CompanyDto>> saveCompany(Principal principal,
			@Valid @RequestBody CompanyDto companyDto) {
		ResponseBean<CompanyDto> response;
		LOG.debug("Saving Company.. {}", companyDto.getName());
		try {
			if (CommonUtil.isEmpty(companyDto.getName()) && CommonUtil.isEmpty(companyDto.getDetails())) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(),
						"Provide mandatory detail(Name) to create Company");
			} else if (companyDto.getName().length() > 60) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(),
						"The name field must be a string with a maximum length of 60.");
			} else if (!(companyDto.getDetails().length() > 10 && companyDto.getDetails().length() < 100)) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(),
						"The details field must be a string in between length 10 to 100.");
			} else {
				boolean userExistsFlag = companyService.isAvailable(companyDto.getName());
				if (!userExistsFlag) {
					response = ResponseBean.of(companyService.save(companyDto), HttpStatus.OK);
				} else {
					response = ResponseBean.of(HttpStatus.NOT_ACCEPTABLE.value(),
							"Company already exists with provided name");
				}

			}
		} catch (HandleException e) {
			response = ResponseBean.of(e.getCode().value(),
					CommonUtil.isNotEmpty(e.getMessage()) ? e.getMessage() : e.getCause().getMessage());
		} catch (Exception e) {
			response = ResponseBean.of(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return sendResponse(response);
	}

	@PostMapping(Mapping.LIST)
	public ResponseEntity<ResponseBean<Page<CompanyDto>>> getAllCompanys(Principal principal,
			@RequestParam(required = false) Map<String, String> pageRequest,
			@Valid @RequestBody(required = false) PageBean pageBean) {
		ResponseBean<Page<CompanyDto>> response;
		try {
			if (pageBean == null) {
				pageBean = new PageBean();
			}
			pageBean = validateRequest(principal, pageBean, pageRequest);
			response = ResponseBean.of(companyService.getAll(pageBean), HttpStatus.OK);
		} catch (HandleException e) {
			response = ResponseBean.of(e.getCode().value(), e.getMessage());
		} catch (Exception e) {
			response = ResponseBean.of(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return sendResponse(response);
	}

	@GetMapping(Mapping.ID)
	public ResponseEntity<ResponseBean<CompanyDto>> getCompanyById(Principal principal,
			@PathVariable("id") Integer companyId) {

		ResponseBean<CompanyDto> response;
		try {
			if (companyId == null) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(), "company id is null");
			} else {
				CompanyDto companyDto = companyService.getById(companyId);
				if (companyDto == null || companyDto.getId() == null) {
					response = ResponseBean.of(HttpStatus.NOT_FOUND, "company not found");
				} else {
					response = ResponseBean.of(companyDto, HttpStatus.OK);
				}
			}
		} catch (HandleException e) {
			response = ResponseBean.of(e.getCode().value(), e.getMessage());
		} catch (Exception e) {
			response = ResponseBean.of(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return sendResponse(response);
	}

	/**
	 * Update partner.
	 *
	 * @param partnerId the partner id
	 * @param partner   the partner
	 * @return the response entity
	 */
	@PutMapping(Mapping.ID)
	public ResponseEntity<ResponseBean<CompanyDto>> updateCompany(Principal principal,
			@PathVariable(value = "id", required = false) Integer companyId,
			@Valid @RequestBody CompanyDto companyDto) {
		ResponseBean<CompanyDto> response;
		LOG.debug("Updating Company {}", companyId);
		try {
			if (companyId == null) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(), "company id is null");
			} else if (companyDto == null) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(), "company object is null");
			} else if (CommonUtil.isEmpty(companyDto.getName())) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(),
						"Provide mandatory detail(Name) to update Company");
			} else if (companyDto.getName().length() > 60) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(),
						"The name field must be a string with a maximum length of 30.");
			} else if (!(companyDto.getDetails().length() > 10 && companyDto.getDetails().length() < 100)) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(),
						"The details field must be a string in between length 10 to 100.");
			} else {
				CompanyDto company = companyService.getById(companyId);
				if (company == null) {
					response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(), "Company Not Available");
				} else {
					response = ResponseBean.of(companyService.updateById(companyDto, companyId), HttpStatus.OK);
				}
			}
		} catch (HandleException e) {
			response = ResponseBean.of(e.getCode().value(), e.getMessage());
		} catch (Exception e) {
			response = ResponseBean.of(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return sendResponse(response);
	}

	@DeleteMapping(Mapping.ID)
	public ResponseEntity<ResponseBean<CompanyDto>> deleteCompany(Principal principal,
			@PathVariable("id") Integer companyId) {
		boolean deleted = false;
		ResponseBean<CompanyDto> response;
		try {
			if (companyId == null) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(), "company id is null");
			} else {
				deleted = companyService.deleteById(companyId);
				if (deleted) {
					response = ResponseBean.of(HttpStatus.OK.value(), "Deleted Successfully");
				} else {
					response = ResponseBean.of(HttpStatus.INTERNAL_SERVER_ERROR.value(),
							"There was problem in deleting the data");
				}
			}
		} catch (HandleException e) {
			response = ResponseBean.of(e.getCode().value(), e.getMessage());
		} catch (Exception e) {
			response = ResponseBean.of(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return sendResponse(response);
	}

}
