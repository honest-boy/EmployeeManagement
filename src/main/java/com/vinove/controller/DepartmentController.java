package com.vinove.controller;

import java.security.Principal;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.vinove.dto.DepartmentDto;
import com.vinove.dto.ResponseBean;
import com.vinove.entity.PageBean;
import com.vinove.exception.HandleException;
import com.vinove.service.DepartmentService;
import com.vinove.utils.CommonUtil;

@RestController
@RequestMapping(value = { "/department" })
public class DepartmentController extends BaseController {

	private static final Logger LOG = LoggerFactory.getLogger(DepartmentController.class);

	@Autowired
	private DepartmentService departmentService;

	@PostMapping
	public ResponseEntity<ResponseBean<DepartmentDto>> saveDepartment(Principal principal,
			@Valid @RequestBody DepartmentDto departmentDto) {
		ResponseBean<DepartmentDto> response;
		LOG.debug("Saving Department.. {}", departmentDto.getName());
		try {
			if (departmentDto.getName().length() > 30) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(),
						"The name field must be a string with a maximum length of 30.");
			} else {
				boolean userExistsFlag = departmentService.isAvailable(departmentDto.getName());
				if (!userExistsFlag) {
					response = ResponseBean.of(departmentService.save(departmentDto), HttpStatus.OK);
				} else {
					response = ResponseBean.of(HttpStatus.NOT_ACCEPTABLE.value(),
							"Department already exists with provided name");
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
	public ResponseEntity<ResponseBean<Page<DepartmentDto>>> getAllDepartments(Principal principal,
			@RequestParam(required = false) Map<String, String> pageRequest,
			@Valid @RequestBody(required = false) PageBean pageBean) {
		ResponseBean<Page<DepartmentDto>> response;
		try {
			if (pageBean == null) {
				pageBean = new PageBean();
			}
			pageBean = validateRequest(principal, pageBean, pageRequest);
			response = ResponseBean.of(departmentService.getAll(pageBean), HttpStatus.OK);
		} catch (HandleException e) {
			response = ResponseBean.of(e.getCode().value(), e.getMessage());
		} catch (Exception e) {
			response = ResponseBean.of(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return sendResponse(response);
	}

	@GetMapping(Mapping.ID)
	public ResponseEntity<ResponseBean<DepartmentDto>> getDepartmentById(Principal principal,
			@PathVariable("id") Integer departmentId) {

		ResponseBean<DepartmentDto> response;
		try {
			if (departmentId == null) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(), "department id is null");
			} else {
				DepartmentDto departmentDto = departmentService.getById(departmentId);
				if (departmentDto == null || departmentDto.getId() == null) {
					response = ResponseBean.of(HttpStatus.NOT_FOUND, "department not found");
				} else {
					response = ResponseBean.of(departmentDto, HttpStatus.OK);
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
	public ResponseEntity<ResponseBean<DepartmentDto>> updateDepartment(Principal principal,
			@PathVariable(value = "id", required = false) Integer departmentId,
			@Valid @RequestBody DepartmentDto departmentDto) {
		ResponseBean<DepartmentDto> response;
		LOG.debug("Updating Department {}", departmentId);
		try {
			if (departmentId == null) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(), "Department id is null");
			} else if (departmentDto == null) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(), "Department object is null");
			} else if (CommonUtil.isEmpty(departmentDto.getName())) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(),
						"Provide mandatory detail(Name) to update Department");
			} else if (departmentDto.getName().length() > 30) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(),
						"The name field must be a string with a maximum length of 30.");
			}  else {
				DepartmentDto department= departmentService.getById(departmentId);
				if (department == null) {
					response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(), "Department Not Available");
				} else {
					response = ResponseBean.of(departmentService.updateById(departmentDto, departmentId), HttpStatus.OK);
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
	public ResponseEntity<ResponseBean<DepartmentDto>> deleteDepartment(Principal principal,
			@PathVariable("id") Integer departmentId) {
		boolean deleted = false;
		ResponseBean<DepartmentDto> response;
		try {
			if (departmentId == null) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(), "Department id is null");
			} else {
				deleted = departmentService.deleteById(departmentId);
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
