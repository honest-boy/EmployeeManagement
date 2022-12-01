package com.vinove.utils;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;


public class FilterSpecification {


	private FilterSpecification() {
	}

	
	public static <T> Specification<T> filterData(String key, Object value) {
		return new Specification<T>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 5574964704242666661L;

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				if (key.contains(".")) {
					String[] arr = key.split("\\.");
					if (checkArray(value)) {
						return fillInValues(criteriaBuilder.in(toMultipleJoins(root, arr)), value);
					} else {
						return criteriaBuilder.like(toMultipleJoins(root, arr), "%" + value + "%");
					}
				} else {
					if (checkArray(value)) {
						return fillInValues(criteriaBuilder.in(root.<String>get(key)), value);
					} else {
						return criteriaBuilder.like(root.<String>get(key), "%" + value + "%");
					}
				}
			}
		};
	}

	private static <T> Expression<String> toMultipleJoins(Root<T> root, String[] arr) {
		return toMultipleJoins(root, arr, JoinType.LEFT);
	}

	private static <T> Expression<String> toMultipleJoins(Root<T> root, String[] arr, JoinType joinType) {
		Join<?, ?> join = addJoin(root, arr[0], joinType);
		for (int i = 1; i < arr.length - 1; i++) {
			join = addJoin(join, arr[i], joinType);
		}
		return join.get(arr[arr.length - 1]);
	}

	private static <Z, Y> Join<?, ?> addJoin(From<Z, Y> from, String attribute, JoinType joinType) {
		return from.getJoins().stream().filter(j -> j.getAttribute().getName().equals(attribute)) // &&
																									// j.getJoinType().equals(joinType)
				.findFirst().orElseGet(() -> from.join(attribute, joinType));
	}

	
	public static <T> Specification<T> filterDataEquals(String key, Serializable value) {
		return new Specification<T>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 5574964704242666661L;

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				if (key.contains(".")) {
					String[] arr = key.split("\\.");
					if (checkArray(value)) {
						return fillInValues(criteriaBuilder.in(toMultipleJoins(root, arr)), value);
					} else {
						return criteriaBuilder.equal(toMultipleJoins(root, arr), value);
					}
				} else {
					if (checkArray(value)) {
						return fillInValues(criteriaBuilder.in(root.<String>get(key)), value);
					} else {
						return criteriaBuilder.equal(root.<String>get(key), value);
					}
				}
			}
		};
	}

	
	public static <T> Specification<T> filterDate(String key, Date value) {
		return new Specification<T>() {

			/**
			 *
			 */
			private static final long serialVersionUID = 5574964704242666661L;

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				if (key.contains(".")) {
					String[] arr = key.split("\\.");
					return criteriaBuilder.equal(addJoin(root, arr[0], JoinType.INNER).<Date>get(arr[1]),
							localToTimeStamp(value.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
				} else {
					return criteriaBuilder.equal(root.<Date>get(key),
							localToTimeStamp(value.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
				}
			}
		};
	}

	public static <T> Specification<T> notNull(String key) {
		return new Specification<T>() {

			/**
			 *
			 */
			private static final long serialVersionUID = 5574964704242666661L;

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				if (key.contains(".")) {
					String[] arr = key.split("\\.");
					return criteriaBuilder.isNotNull(addJoin(root, arr[0], JoinType.INNER).get(arr[1]));
				} else {
					return criteriaBuilder.isNotNull(root.get(key));
				}
			}
		};
	}

	public static <T> Specification<T> isNull(String key) {
		return new Specification<T>() {

			/**
			 *
			 */
			private static final long serialVersionUID = 5574964704242676661L;

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				if (key.contains(".")) {
					String[] arr = key.split("\\.");
					return criteriaBuilder.isNull(addJoin(root, arr[0], JoinType.INNER).get(arr[1]));
				} else {
					return criteriaBuilder.isNull(root.get(key));
				}
			}
		};
	}

	
	private static Timestamp localToTimeStamp(LocalDate date) {
		return Timestamp.from(date.atStartOfDay().toInstant(ZoneOffset.UTC));
	}

	
	private static Predicate fillInValues(In<Object> in, Object value) {
		if (value instanceof Collection) {
			Collection<?> collection = (Collection<?>) value;
			for (Object object : collection) {
				in.value(object);
			}
		} else {
			Object[] array = (Object[]) value;
			for (Object object : array) {
				in.value(object);
			}
		}
		return in;
	}

	
	public static boolean checkArray(Object obj) {
		return obj != null && obj.getClass().isArray() || obj instanceof Collection;
	}

	public static <T> Specification<T> filterGreaterThan(String key, Integer value) {
		return new Specification<T>() {

			/**
			 *
			 */
			private static final long serialVersionUID = 5574964704242666661L;

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				if (key.contains(".")) {
					String[] arr = key.split("\\.");
					return criteriaBuilder.gt(addJoin(root, arr[0], JoinType.INNER).<Number>get(arr[1]), value);
				} else {
					return criteriaBuilder.gt(root.<Number>get(key), value);
				}
			}
		};
	}
}