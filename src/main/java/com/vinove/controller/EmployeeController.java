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
import com.vinove.dto.EmployeeDto;
import com.vinove.dto.ResponseBean;
import com.vinove.entity.PageBean;
import com.vinove.exception.HandleException;
import com.vinove.service.EmployeeService;
import com.vinove.utils.CommonUtil;

@RestController
@RequestMapping(value = { "/employee" })
public class EmployeeController extends BaseController {

	@Autowired
	private EmployeeService employeeService;

	@PostMapping
	public ResponseEntity<ResponseBean<EmployeeDto>> saveEmployee(Principal principal,
			@Valid @RequestBody EmployeeDto employeeDto) {
		ResponseBean<EmployeeDto> response;
		LOG.debug("Saving Employee.. {}", employeeDto.getEmail());
		try {
			if (CommonUtil.isEmpty(employeeDto.getEmail())) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(),
						"Provide mandatory detail(email should not be empty) to create Employee");
			} else if (CommonUtil.isEmpty(employeeDto.getFirstName())
					&& CommonUtil.isEmpty(employeeDto.getLastName())) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(),
						"Provide mandatory detail(firstname and lastname) to create Employee");
			} else if (employeeDto.getFirstName().length() > 20 && employeeDto.getLastName().length() > 20) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(),
						"The firstname and lastname field must be a string with a maximum length of 20.");
			} else if ((employeeDto.getRole() == null)
					|| (employeeDto.getRole().getId() == null && employeeDto.getRole().getName() == null)) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(),
						"Please assign the role id or role name to this employee");
			} else if ((employeeDto.getCompany() == null)
					|| (employeeDto.getCompany().getId() == null && employeeDto.getCompany().getName() == null)) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(),
						"Please assign the company id or company name to this employee");
			} else if ((employeeDto.getDepartment() == null)
					|| (employeeDto.getDepartment().getId() == null && employeeDto.getDepartment().getName() == null)) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(),
						"Please assign the department id or department name to this employee");
			} else {
				boolean userExistsFlag = employeeService.isAvailable(employeeDto.getEmail());
				if (!userExistsFlag) {
					response = ResponseBean.of(employeeService.save(employeeDto), HttpStatus.OK);
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
	public ResponseEntity<ResponseBean<Page<EmployeeDto>>> getAllEmployees(Principal principal,
			@RequestParam(required = false) Map<String, String> pageRequest,
			@Valid @RequestBody(required = false) PageBean pageBean) {
		ResponseBean<Page<EmployeeDto>> response;
		try {
			if (pageBean == null) {
				pageBean = new PageBean();
			}
			pageBean = validateRequest(principal, pageBean, pageRequest);
			response = ResponseBean.of(employeeService.getAll(pageBean), HttpStatus.OK);
		} catch (HandleException e) {
			response = ResponseBean.of(e.getCode().value(), e.getMessage());
		} catch (Exception e) {
			response = ResponseBean.of(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return sendResponse(response);
	}

	@GetMapping(Mapping.ID)
	public ResponseEntity<ResponseBean<EmployeeDto>> getEmployeeById(Principal principal,
			@PathVariable("id") Integer emapId) {

		ResponseBean<EmployeeDto> response;
		try {
			if (emapId == null) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(), "employee id is null");
			} else {
				EmployeeDto employeeDto = employeeService.getById(emapId);
				if (employeeDto == null || employeeDto.getId() == null) {
					response = ResponseBean.of(HttpStatus.NOT_FOUND, "employee not found");
				} else {
					response = ResponseBean.of(employeeDto, HttpStatus.OK);
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
	public ResponseEntity<ResponseBean<EmployeeDto>> updateEmployee(Principal principal,
			@PathVariable(value = "id", required = false) Integer empId, @Valid @RequestBody EmployeeDto employeeDto) {
		ResponseBean<EmployeeDto> response;
		LOG.debug("Updating Employee {}", empId);
		try {
			if (empId == null) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(), "employee id is null");
			} else if (employeeDto == null) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(), "employee object is null");
			} else if (CommonUtil.isEmpty(employeeDto.getEmail())) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(),
						"Provide mandatory detail(email should not be empty) to create Employee");
			} else if (CommonUtil.isEmpty(employeeDto.getFirstName())
					&& CommonUtil.isEmpty(employeeDto.getLastName())) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(),
						"Provide mandatory detail(firstname and lastname) to create Employee");
			} else if (employeeDto.getFirstName().length() > 20 && employeeDto.getLastName().length() > 20) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(),
						"The firstname and lastname field must be a string with a maximum length of 20.");
			} else if ((employeeDto.getRole() == null)
					|| (employeeDto.getRole().getId() == null && employeeDto.getRole().getName() == null)) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(),
						"Please assign the role id or role name to this employee");
			} else if ((employeeDto.getCompany() == null)
					|| (employeeDto.getCompany().getId() == null && employeeDto.getCompany().getName() == null)) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(),
						"Please assign the company id or company name to this employee");
			} else if ((employeeDto.getDepartment() == null)
					|| (employeeDto.getDepartment().getId() == null && employeeDto.getDepartment().getName() == null)) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(),
						"Please assign the department id or department name to this employee");
			} else {
				EmployeeDto employee = employeeService.getById(empId);
				if (employee == null) {
					response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(), "Company Not Available");
				} else {
					response = ResponseBean.of(employeeService.updateById(employeeDto, empId), HttpStatus.OK);
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
	public ResponseEntity<ResponseBean<EmployeeDto>> deleteEmployee(Principal principal,
			@PathVariable("id") Integer empId) {
		boolean deleted = false;
		ResponseBean<EmployeeDto> response;
		try {
			if (empId == null) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(), "company id is null");
			} else {
				deleted = employeeService.deleteById(empId);
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
