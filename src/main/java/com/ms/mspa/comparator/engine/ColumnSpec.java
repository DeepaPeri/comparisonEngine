/**
 * 
 */
package com.ms.mspa.comparator.engine;

import java.text.DecimalFormat;
import java.text.Format;

/**
 * @author Pperi
 *
 */
public class ColumnSpec {
	public static final String ROW_NUM_COLUMN_NAME = "ROW_NUM";
	/**
	 * should be ISO 8601 compliant
	 */
	public static final String DEFAULT_DATE_FORMAT_STRING = "yyyy-MM-dd";
	/**
	 * should be ISO 8601 compliant
	 */
	public static final String DEFAULT_TIME_FORMAT_STRING = "hh:mm:ss";
	/**
	 * should be ISO 8601 compliant
	 */
	private static final String DEFAULT_TIMESTAMP_FORMAT_STRING = DEFAULT_DATE_FORMAT_STRING + "T"
			+ DEFAULT_TIME_FORMAT_STRING;

	public enum Type {
		STRING, INTEGER(true), REAL(true), DECIMAL(true), DATE, TIME, TIMESTAMP, BOOLEAN, TEXT, MIXED;

		public final boolean isNumber;

		private Type() {
			this(false);
		}

		private Type(boolean isNumber) {
			this.isNumber = isNumber;
		}
	}

	public final int index;
	public final String name;
	public final Type type;
	public final Format format;
	public final String formatString;
	private final boolean isRowNum;
	private TableSpec table;

	public ColumnSpec(int index, String name, Type type) {
		this(index, name, type, null);
	}

	public ColumnSpec(int index, String name, Type type, String formatString) {
		this.index = index;
		this.name = name;
		this.type = type;
		this.formatString = formatString;
		this.format = this.createFormat(this.type, this.formatString);
		this.isRowNum = name.equals(ROW_NUM_COLUMN_NAME);
	}

	private Format createFormat(Type type, String formatString) {
		switch (type) {

		case STRING:
			return null;
		case INTEGER: {
			if (formatString == null)
				return null;
			return new DecimalFormat(formatString);
		}
		case REAL: {
			if (formatString == null)
				return null;
			return new DecimalFormat(formatString);
		}
		case DECIMAL: {
			if (formatString == null)
				return null;
			return new DecimalFormat(formatString);
		}
		case DATE: {
			if (formatString == null)
				formatString = DEFAULT_DATE_FORMAT_STRING;
			return new DecimalFormat(formatString);
		}
		case TIME: {
			if (formatString == null)
				formatString = DEFAULT_TIME_FORMAT_STRING;
			return new DecimalFormat(formatString);
		}
		case TIMESTAMP: {
			if (formatString == null)
				formatString = DEFAULT_TIMESTAMP_FORMAT_STRING;
			return new DecimalFormat(formatString);
		}
		case BOOLEAN:
			return null;
		case TEXT:
			return null;
		case MIXED:
			return null;

		default:
			throw new RuntimeException(String.format("unhandled type_->%s", type));
		}
	}
}
