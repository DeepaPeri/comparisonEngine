/**
 * 
 */
package com.ms.mspa.comparator.engine;

import java.text.Format;
import java.text.ParseException;
import java.util.Comparator;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.ms.mspa.comparator.engine.comparators.StringComparator;

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
	}

	public final int index;
	public final String name;
	public final Format format;
	public final Type type;
	
	public ColumnSpec(int index, String name) {
		this.index = index;
		this.name = name;
		this.format = null;
		this.type = Type.STRING; //TODO: Default type is String.
	}

	/**
	 * @param stringValue
	 * @return
	 * @throws ParseException
	 */
	public Object parseObject(String stringValue) throws ParseException {
		if (StringUtils.isEmpty(stringValue))
			return null;
		if (format == null)
			return stringValue;
		return format.parseObject(stringValue);
	}

	public boolean isColumnPartofKey(int[] keyIndices) {
		return ArrayUtils.indexOf(keyIndices, index) >= 0;
	}
	
	public Comparator getComparator(){
		if(type == Type.STRING) {
			return StringComparator.getStringComparator();
		}
		
		return null; //TODO: Add support for other types.
	}
}