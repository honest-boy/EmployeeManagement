package com.vinove.entity;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.JpaSort;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vinove.utils.CommonUtil;

public class PageBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4433332211025444339L;

	private static final int defaultSize = 10;

	private static final String defaultOrderBy = "id";

	private Integer page = 0;

	private Integer rows = defaultSize;

	private String orderBy = defaultOrderBy;

	@JsonIgnore
	private Boolean isAsccendingOrder = true;

	private Boolean ascendingOrder = true;

	protected Map<String, Object> filters;

	protected String searchParam;

	@Enumerated(EnumType.STRING)
	private Direction direction;

	public static PageBean of(int rows) {
		return new PageBean(rows);
	}

	public static PageBean of(int rows, String orderBy) {
		return new PageBean(rows, orderBy);
	}

	public static PageBean of(String page, String rows, String orderBy) {
		return new PageBean(page, rows, orderBy);
	}

	public PageBean() {
	}

	public PageBean(int rows) {
		this.rows = rows;
	}

	public PageBean(int rows, String orderBy) {
		this.rows = rows;
		this.orderBy = orderBy;
	}

	public PageBean(String page, String size, String orderBy) {
		if (CommonUtil.isNotEmpty(page)) {
			setPage(Integer.parseInt(page));
		}
		if (CommonUtil.isNotEmpty(size)) {
			setRows(Integer.parseInt(size));
		}
		if (CommonUtil.isNotEmpty(orderBy)) {
			String[] orderByArr = orderBy.split(",");
			setOrderBy(orderByArr[0].trim());
			if (orderByArr.length > 1) {
				setDirection(Direction.fromString(orderByArr[1].trim().toUpperCase()));
			}
		}
	}

	public Boolean getAscendingOrder() {
		return ascendingOrder;
	}

	public void setAscendingOrder(Boolean ascendingOrder) {
		this.ascendingOrder = ascendingOrder;
	}

	public Integer getPage() {
		if (page == null || page <= 0) {
			page = 0;
		}
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		if (rows == null || rows <= 0) {
			rows = defaultSize;
		}
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getOrderBy() {
		if (CommonUtil.isEmpty(orderBy)) {
			orderBy = defaultOrderBy;
		}
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public boolean isDefaultOrderBy() {
		return (orderBy != null && orderBy.equals(defaultOrderBy));
	}

	public Boolean getIsAsccendingOrder() {
		return isAsccendingOrder;
	}

	public void setIsAsccendingOrder(Boolean isAsccendingOrder) {
		this.isAsccendingOrder = isAsccendingOrder;
	}

	public Direction getDirection() {
		if (direction != null) {
			return direction;
		} else {
			return isAsccendingOrder && ascendingOrder ? Direction.ASC : Direction.DESC;
		}
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Map<String, Object> getFilters() {
		return filters;
	}

	public void setFilters(Map<String, Object> filters) {
		this.filters = filters;
	}

	public String getSearchParam() {
		return searchParam;
	}

	public void setSearchParam(String searchParam) {
		this.searchParam = searchParam;
	}

	@Override
	public String toString() {
		return "PageBean [page=" + page + ", rows=" + rows + ", orderBy=" + orderBy + ", isAsccendingOrder="
				+ isAsccendingOrder + ", ascendingOrder=" + ascendingOrder + ", filters=" + filters + ", searchParam="
				+ searchParam + ", direction=" + direction + "]";
	}

	public Pageable toPage() {
		return PageRequest.of(getPage(), getRows(), JpaSort.unsafe(getDirection(), getOrderBy()));
	}

}
