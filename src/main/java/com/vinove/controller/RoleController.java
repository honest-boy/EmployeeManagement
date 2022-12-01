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
import com.vinove.dto.ResponseBean;
import com.vinove.dto.RoleDto;
import com.vinove.entity.PageBean;
import com.vinove.exception.HandleException;
import com.vinove.service.RoleService;
import com.vinove.utils.CommonUtil;

@RestController
@RequestMapping(value = { "/role" })
public class RoleController extends BaseController {

	private static final Logger LOG = LoggerFactory.getLogger(RoleController.class);

	@Autowired
	private RoleService roleService;

	@PostMapping
	public ResponseEntity<ResponseBean<RoleDto>> saveRole(Principal principal,
			@Valid @RequestBody RoleDto roleDto) {
		ResponseBean<RoleDto> response;
		LOG.debug("Saving Role.. {}", roleDto.getName());
		try {
			if (roleDto.getName().length() > 20) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(),
						"The name field must be a string with a maximum length of 30.");
			} else {
				boolean userExistsFlag = roleService.isAvailable(roleDto.getName());
				if (!userExistsFlag) {
					response = ResponseBean.of(roleService.save(roleDto), HttpStatus.OK);
				} else {
					response = ResponseBean.of(HttpStatus.NOT_ACCEPTABLE.value(),
							"Role already exists with provided name");
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
	public ResponseEntity<ResponseBean<Page<RoleDto>>> getAllRoles(Principal principal,
			@RequestParam(required = false) Map<String, String> pageRequest,
			@Valid @RequestBody(required = false) PageBean pageBean) {
		ResponseBean<Page<RoleDto>> response;
		try {
			if (pageBean == null) {
				pageBean = new PageBean();
			}
			pageBean = validateRequest(principal, pageBean, pageRequest);
			response = ResponseBean.of(roleService.getAll(pageBean), HttpStatus.OK);
		} catch (HandleException e) {
			response = ResponseBean.of(e.getCode().value(), e.getMessage());
		} catch (Exception e) {
			response = ResponseBean.of(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return sendResponse(response);
	}

	@GetMapping(Mapping.ID)
	public ResponseEntity<ResponseBean<RoleDto>> getRoleById(Principal principal,
			@PathVariable("id") Integer roleId) {

		ResponseBean<RoleDto> response;
		try {
			if (roleId == null) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(), "role id is null");
			} else {
				RoleDto roleDto = roleService.getById(roleId);
				if (roleDto == null || roleDto.getId() == null) {
					response = ResponseBean.of(HttpStatus.NOT_FOUND, "role not found");
				} else {
					response = ResponseBean.of(roleDto, HttpStatus.OK);
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
	public ResponseEntity<ResponseBean<RoleDto>> updateRole(Principal principal,
			@PathVariable(value = "id", required = false) Integer roleId, @Valid @RequestBody RoleDto roleDto) {
		ResponseBean<RoleDto> response;
		LOG.debug("Updating Role {}", roleId);
		try {
			if (roleId == null) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(), "Role id is null");
			} else if (roleDto == null) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(), "Role object is null");
			} else if (CommonUtil.isEmpty(roleDto.getName())) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(),
						"Provide mandatory detail(Name) to update Role");
			} else if (roleDto.getName().length() > 20) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(),
						"The name field must be a string with a maximum length of 30.");
			} else {
				RoleDto role = roleService.getById(roleId);
				if (role == null) {
					response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(), "Role Not Available");
				} else {
					response = ResponseBean.of(roleService.updateById(roleDto, roleId), HttpStatus.OK);
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
	public ResponseEntity<ResponseBean<RoleDto>> deleteRole(Principal principal,
			@PathVariable("id") Integer roleId) {
		boolean deleted = false;
		ResponseBean<RoleDto> response;
		try {
			if (roleId == null) {
				response = ResponseBean.of(HttpStatus.BAD_REQUEST.value(), "Role id is null");
			} else {
				deleted = roleService.deleteById(roleId);
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
