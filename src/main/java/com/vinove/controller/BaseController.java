package com.vinove.controller;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.vinove.dto.ResponseBean;
import com.vinove.entity.PageBean;
import com.vinove.exception.HandleException;
import com.vinove.utils.CommonUtil;

public abstract class BaseController {

	protected static final Logger LOG = LoggerFactory.getLogger(BaseController.class);

	protected <T> ResponseEntity<ResponseBean<T>> sendResponse(ResponseBean<T> response) {
		ResponseEntity<ResponseBean<T>> responseEntity;
		if (response.getStatusCode().equals(HttpStatus.OK.value())) {
			responseEntity = ResponseEntity.of(Optional.of(response));
		} else {
			if (!response.getStatusCode().equals(HttpStatus.ACCEPTED.value())) {
				response.setBody(null);
			}
			responseEntity = ResponseEntity.status(HttpStatus.valueOf(response.getStatusCode())).body(response);
		}
		return responseEntity;
	}

	protected PageBean validateRequest(final Principal principal, final PageBean pageBean,
			final Map<String, String> pageRequest) throws HandleException {
		PageBean result = pageBean;
		String page = pageRequest.get("page");
		String size = pageRequest.get("size");
		String sort = pageRequest.get("sort");

		if (result == null) {
			result = new PageBean();
		}
		if (CommonUtil.isNotEmpty(page)) {
			result.setPage(Integer.parseInt(page));
		}
		if (CommonUtil.isNotEmpty(size)) {
			result.setRows(Integer.parseInt(size));
		}
		if (CommonUtil.isNotEmpty(sort)) {
			String[] orderByArr = sort.split(",");
			result.setOrderBy(orderByArr[0].trim());
			if (orderByArr.length > 1) {
				result.setDirection(Direction.fromString(orderByArr[1].trim().toUpperCase()));
			}
		}
		return result;
	}

}
