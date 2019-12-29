package com.ms.mspa.comparator.engine.comparators;

import java.util.Comparator;

/**
 * @author Deepa
 *
 */
public class StringComparator implements Comparator<String>{
	private static final StringComparator instance = new StringComparator();
	
	public static StringComparator getStringComparator() {
		return instance;
	}
	
	@Override
	public int compare(String left, String right) {
		return left.compareTo(right);
	}
}
