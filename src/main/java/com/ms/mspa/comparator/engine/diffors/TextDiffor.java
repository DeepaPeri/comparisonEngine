/**
 * 
 */
package com.ms.mspa.comparator.engine.diffors;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * @author Deepa
 * Diffor for columns holding text data.
 */
public class TextDiffor implements IDiffor {
	//Refer to: https://regex101.com/ for verification.
	
	//Regex pattern that matches any one of \n or \r or \t
	private static final Pattern NEWLINE_TAB_PATTERN = Pattern.compile("[\n\r\t]{1}");
	
	//Regex pattern that matches 'one ore more spaces'
	private static final Pattern SPACE_RUN_PATTERN = Pattern.compile("[ ]+");

	private boolean shouldNormalize;
	private boolean toleranceCheck;
	
	public TextDiffor() {
		this(false);
	}
	
	public TextDiffor(boolean shouldNormalize) {
		this.shouldNormalize = shouldNormalize;
		this.toleranceCheck = true;
	}
	
	/**
	 * We rely on equals method of the object (String) holding column data.
	 * If 'equals' return false, then, we normalize the text and then compare for the strings for equality again.
	 * 
	 * Both the parameters must be 'String' instances.
	 */
	
	@Override
	public boolean isDifferent(Object lhs, Object rhs) {
		boolean lhsNull = (lhs == null);
		boolean rhsNull = (rhs == null);
		if (lhsNull && rhsNull)
			return false; //both are null and hence not different.
		if (lhsNull || rhsNull)
			return true; //one of them is null and hence they are different.
		
		boolean equals = lhs.equals(rhs);
		if (equals)
			return false; //Concluded that they are same.
		if(!this.shouldNormalize) {
			return true; //Concluded that they are different.
		}
		
		//We have not concluded it they are not same. Let us normalize and then compare.
		String normalizedLhs = this.normalize((String) lhs);
		String normalizedRhs = this.normalize((String) rhs);
		lhsNull = (normalizedLhs == null);
		rhsNull = (normalizedRhs == null);
		if (lhsNull && rhsNull)
			return false;
		if (lhsNull || rhsNull)
			return true;
		return !normalizedLhs.equals(normalizedRhs);
	}
	
	
	/**
	 * Strings might be same if we do not consider \n, \r, \t into comparison.
	 * Also, condense multiple spaces into one, for comparison purpose.
	 * @param target
	 * @return the normalized string.
	 */
	private String normalize(String target) {
		//Replace each non space "white space" characters with a "space" character. 
		String normalizedString = NEWLINE_TAB_PATTERN.matcher(target).replaceAll(" ");
		//Collapse contiguous spaces into one space.
		normalizedString = SPACE_RUN_PATTERN.matcher(normalizedString).replaceAll(" ");
		return StringUtils.trimToNull(normalizedString);
	}
	
	public boolean isNormalizationEnabled() {
		return this.shouldNormalize;
	}

	@Override
	public int compare(Object lhs, Object rhs) {
		String left = (String)lhs;
		String right = (String)rhs;
		return left.compareTo(right);
		
	}
}