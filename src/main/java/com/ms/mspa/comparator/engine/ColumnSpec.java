/**
 * 
 */
package com.ms.mspa.comparator.engine;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.util.Comparator;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.ms.mspa.comparator.engine.diffors.IDiffor;
import com.ms.mspa.comparator.engine.diffors.TextDiffor;

/**
 * @author Deepa We are now assuming all columns are of type String. Todo: Need
 *         to add support for other data types.
 */
public class ColumnSpec {
	public enum Type {
		STRING, INTEGER(true), REAL(true), DECIMAL(true), DATE, TIME, TIMESTAMP, BOOLEAN, TEXT, MIXED;

		public final boolean _isNumber;

		private Type() {
			this(false);
		}

		private Type(boolean isNumber_) {
			_isNumber = isNumber_;
		}

		public static Type getType(String typeName) {
			switch (typeName) {
			case "STRING":
				return Type.STRING;
			case "REAL":
				return Type.REAL;
			default:
				return Type.STRING;
			}

		}
	}

	public final boolean shouldNormalize;
	public final int index;
	public final String name;
	public final Format format;
	public Type type;
	public final IDiffor diffor;
	public double tolerance;

	public ColumnSpec(int index, String name, String columnType) {
		this.index = index;
		this.name = name;
		this.format = null;
		this.initializeType(columnType);
		this.shouldNormalize = true;// TODO: Make it configurable
		this.diffor = createDiffor(type);
	}

	private void initializeType(String columnType) {
		String tokens[] = columnType.split(";"); // Type name will be followed by any configuration after a ;
		this.type = Type.getType(tokens[0]);

		if (this.type == Type.REAL) {
			this.tolerance = Double.parseDouble(tokens[1]);
		}
	}

	/**
	 * @param stringValue
	 * @return
	 * @throws ParseException
	 */
	public Object parseObject(String stringValue) throws ParseException {
		if (StringUtils.isEmpty(stringValue))
			return null;
		if (format == null) {
			switch(type) {
			case STRING: return stringValue;
			case REAL: return Double.parseDouble(stringValue);
			default: return stringValue;
			}
		}
		
		return format.parseObject(stringValue);
	}

	public boolean isColumnPartofKey(int[] keyIndices) {
		return ArrayUtils.indexOf(keyIndices, index) >= 0;
	}

	public Comparator getComparator() {
		return diffor;
	}

	public IDiffor getDiffor() {
		return diffor;
	}

	private IDiffor createDiffor(Type type) {
		switch (type) {
		case STRING:
			return new TextDiffor(shouldNormalize);
		case REAL:
			return new NumberDiffor(tolerance, false);
		default:
			return new TextDiffor(shouldNormalize);
		}

	}

	private Format createFormat(Type type, String formatString) {
		switch (type) {
		case STRING:
			return null;
		case REAL: {
			if (formatString == null)
				return null;
			return new DecimalFormat(formatString);
		}
		/*case DECIMAL: {
			if (formatString == null)
				return null;
			return new DecimalFormat(formatString);
		}
		case INTEGER: {
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
		*/
		default:
			throw new RuntimeException(String.format("unhandled type_->%s", type));
		}
	}
}