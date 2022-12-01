package com.vinove.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

public class CommonUtil {

	private static final Logger LOG = LoggerFactory.getLogger(CommonUtil.class);

	public static boolean isEmpty(String string) {
		return (string == null || string.trim().isEmpty() || string.trim().equals("null"));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean isEmpty(Object obj) {
		boolean result = (obj == null);
		if (!result) {
			if (obj instanceof Collection) {
				result = isEmpty((Collection) obj);
			} else if (obj instanceof String) {
				result = isEmpty((String) obj);
			} else if (obj instanceof Map) {
				result = isEmpty((Map) obj);
			} else if (obj.getClass().isArray()) {
				result = isEmpty((Object[]) obj);
			}
		}
		return result;
	}

	public static boolean isNotEmpty(String string) {
		return !isEmpty(string);
	}

	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}

	public static boolean isNotEmpty(Object[] obj) {
		return !isEmpty(obj);
	}

	public static boolean isEmpty(Object[] objArr) {
		return objArr == null || objArr.length == 0;
	}

	public static boolean isNotEmpty(Map<?, ?> obj) {
		return !isEmpty(obj);
	}

	public static boolean isEmpty(Map<?, ?> map) {
		return (map == null || map.isEmpty());
	}

	public static boolean isEmpty(Number number) {
		return (number == null || number.equals(0));
	}

	public static boolean isNotEmpty(Number number) {
		return !isEmpty(number);
	}

	public static <T> boolean isEmpty(Collection<T> collection) {
		return (collection == null || collection.isEmpty() || collection.contains(null) || collection.contains(""));
	}

	public static <T> boolean isNotEmpty(Collection<T> collection) {
		return !isEmpty(collection);
	}

	public static boolean isNotEmpty(String toTest, boolean trim) {
		return !isEmpty(toTest, trim);
	}

	public static boolean isEmpty(String toTest, boolean trim) {
		boolean result = true;
		if (toTest != null) {
			if (trim) {
				result = toTest.trim().isEmpty();
			} else {
				result = toTest.isEmpty();
			}
		}
		return result;
	}

	public static boolean isInteger(String s) {
		boolean result = isNotEmpty(s);
		if (result) {
			try {
				Integer.parseInt(s);
				result = true;
			} catch (NumberFormatException | NullPointerException e) {
				result = false;
			}
		}
		return result;
	}

	public static <S, D> void copyProperties(final S source, final D destination, String... ignoreProperties) {
		try {
			assert destination != null : "Destination should not be null.";
			assert source != null : "Source should not be null.";
			BeanUtils.copyProperties(source, destination, ignoreProperties);
		} catch (Exception e) {
			LOG.error("Exception", e);
		}
	}

	public static <S, D> D copyProperties(final S source, final Class<D> destinationClass) {
		try {
			D destination = null;
			if (source != null && destinationClass != null) {
				destination = destinationClass.getDeclaredConstructor().newInstance();
				copyProperties(source, destination);
			}
			return destination;
		} catch (IllegalAccessException | InstantiationException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			return null;
		}
	}

}
