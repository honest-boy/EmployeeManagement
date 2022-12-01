
package com.vinove.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class ResponseBean.
 *
 * @param <T> the generic type
 */
@JsonInclude(Include.NON_NULL)
public class ResponseBean<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3100942417858738084L;

	/** The version. */
	@JsonProperty(index = Integer.MAX_VALUE)
	private static String version;

	/** The body. */
	@JsonProperty(index = 0)
	private T body;

	/** The at. */
	@JsonProperty(index = Integer.MAX_VALUE - 1)
	private final LocalDateTime at = LocalDateTime.now();

	/** The status code. */
	@JsonProperty(index = 1)
	private Integer statusCode;

	/** The status desc. */
	@JsonProperty(index = 2)
	private String statusDesc;

	/**
	 * Instantiates a new response bean.
	 *
	 * @param statusCode the status code
	 * @param statusDesc the status desc
	 */
	private ResponseBean(Integer statusCode, String statusDesc) {
		this.statusCode = statusCode;
		this.statusDesc = statusDesc;
	}

	/**
	 * Instantiates a new response bean.
	 *
	 * @param body   the body
	 * @param status the status
	 */
	private ResponseBean(T body, HttpStatus status) {
		this(status.value(), status.getReasonPhrase());
		this.body = body;
	}

	/**
	 * Of.
	 *
	 * @param <T>    the generic type
	 * @param body   the body
	 * @param status the status
	 * @return the response bean
	 */
	public static <T> ResponseBean<T> of(T body, HttpStatus status) {
		return new ResponseBean<>(body, status);
	}

	/**
	 * Of.
	 *
	 * @param <T>        the generic type
	 * @param statusCode the status code
	 * @param statusDesc the status desc
	 * @return the response bean
	 */
	public static <T> ResponseBean<T> of(Integer statusCode, String statusDesc) {
		return new ResponseBean<>(statusCode, statusDesc);
	}

	/**
	 * Gets the single instance of ResponseBean.
	 *
	 * @param <T>        the generic type
	 * @param status     the status
	 * @param statusDesc the status desc
	 * @return single instance of ResponseBean
	 */
	public static <T> ResponseBean<T> of(HttpStatus status, String statusDesc) {
		return new ResponseBean<>(status.value(), statusDesc == null ? status.getReasonPhrase() : statusDesc);
	}

	public static <T> ResponseBean<T> of(HttpStatus status) {
		return new ResponseBean<>(status.value(), status.getReasonPhrase());
	}

	/**
	 * Gets the single instance of ResponseBean.
	 *
	 * @param <T>    the generic type
	 * @param body   the body
	 * @param status the status
	 * @return single instance of ResponseBean
	 */
	public static <T> ResponseBean<T> getInstance(T body, HttpStatus status) {
		return new ResponseBean<>(body, status);
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public static String getVersion() {
		return version;
	}

	/**
	 * Sets the version.
	 *
	 * @param version the version to set
	 */
	public static void setVersion(String version) {
		ResponseBean.version = version;
	}

	/**
	 * Gets the body.
	 *
	 * @return the body
	 */
	public T getBody() {
		return body;
	}

	/**
	 * Sets the body.
	 *
	 * @param body the body to set
	 */
	public void setBody(T body) {
		this.body = body;
	}

	/**
	 * Gets the at.
	 *
	 * @return the at
	 */
	public LocalDateTime getAt() {
		return at;
	}

	/**
	 * Gets the status code.
	 *
	 * @return the statusCode
	 */
	public Integer getStatusCode() {
		return statusCode;
	}

	/**
	 * Sets the status code.
	 *
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * Gets the status desc.
	 *
	 * @return the statusDesc
	 */
	public String getStatusDesc() {
		return statusDesc;
	}

	/**
	 * Sets the status desc.
	 *
	 * @param statusDesc the statusDesc to set
	 */
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		return Objects.hash(at, body, statusCode);
	}

	/**
	 * Equals.
	 *
	 * @param obj the obj
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ResponseBean)) {
			return false;
		}
		@SuppressWarnings("rawtypes")
		ResponseBean other = (ResponseBean) obj;
		return Objects.equals(at, other.at) && Objects.equals(body, other.body)
				&& Objects.equals(statusCode, other.statusCode);
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "ResponseBean [" + (body != null ? "body=" + body + ", " : "") + "at=" + at + " : "
				+ (statusCode != null ? "statusCode=" + statusCode + ", " : "")
				+ (statusDesc != null ? "statusDesc=" + statusDesc + ", " : "") + "]";
	}

}
