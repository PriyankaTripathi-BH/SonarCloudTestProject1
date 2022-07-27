package com.bh.realtrack.util;

import java.util.Collection;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import com.bh.realtrack.exception.RealTrackException;

public abstract class AssertUtils {

	private static Logger log = LoggerFactory.getLogger(AssertUtils.class.getName());

	/**
	 * Return true if the string is empty/null
	 *
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(String value) {
		if (null == value || value.isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isEmptyCollection(Collection<?> value) {
		if (null == value || value.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * Check for null or empty value
	 *
	 * @param value
	 *            the String value
	 * @return true if not empty or fails
	 */
	public static boolean isNotEmpty(String value) {
		return !isEmpty(value);
	}

	/**
	 * @param value
	 * @param msg
	 * @throws RTException
	 */
	public static void validateNotEmpty(Object value, String msg) throws RealTrackException {
		if (null == value) {
			throw new RealTrackException(msg);
		}
	}

	/**
	 * Check for null/empty value and custom error message
	 *
	 * @param value
	 *            the String value
	 * @param msg
	 *            a custom error message
	 * @throws RTException
	 *             if fails
	 */
	public static void validateNotEmpty(String value, String msg) throws RealTrackException {
		if (null == value || value.isEmpty()) {
			throw new RealTrackException(msg);
		}
	}

	public static String validateString(String value) throws RealTrackException {
		if (!isEmpty(value)) {
			return value;
		} else {
			log.error("String is null or empty");
			return null;
		}
	}

	public static boolean ifFileNotEmpty(MultipartFile value) {
		if (null == value || value.isEmpty()) {
			return false;
		}
		return true;
	}

	public static MultipartFile validateImage(MultipartFile image) throws RealTrackException {
		String mimeType = image.getContentType();
		String type = mimeType.split("/")[0];
		if (type.equalsIgnoreCase("image")) {
			return image;
		} else {
			throw new RealTrackException("File uploaded is not an image");
		}
	}

	public static boolean isListNotEmpty(Collection<?> value) {
		if (null == value || value.isEmpty()) {
			return false;
		}
		return true;
	}

	public static MultipartFile validateExcel(MultipartFile file) throws RealTrackException {
		if (file.getOriginalFilename().endsWith(".xlsx") || file.getOriginalFilename().endsWith(".xls")) {
			return file;
		} else {
			throw new RealTrackException("File uploaded is not an excel");
		}
	}

	public static MultipartFile validateCSV(MultipartFile file) throws RealTrackException {
		if (file.getOriginalFilename().endsWith(".csv")) {
			return file;
		} else {
			throw new RealTrackException("File uploaded is not an CSV");
		}
	}

	public static long validateLong(Long value) throws RealTrackException {
		if (Objects.isNull(value)) {
			throw new RealTrackException(value + "is null or empty");
		} else {
			return value;
		}
	}

	public static String sanitizeString(String input) {
		if (input != null && !input.isEmpty() && input.equalsIgnoreCase(HtmlUtils.htmlEscape(input))) {
			return HtmlUtils.htmlEscape(input.replaceAll("\\n\\r", ""));
		} else {
			log.error("String is null or empty");
			return null;
		}
	}

	public static String sanitizeToken(String input) {
		if (input != null && !input.isEmpty() && input.equalsIgnoreCase(HtmlUtils.htmlEscape(input))) {
			return HtmlUtils.htmlEscape(input.replaceAll("\\n\\r", ""));
		} else {
			return null;
		}
	}

}
