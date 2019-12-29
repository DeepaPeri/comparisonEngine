/**
 * 
 */
package com.ms.mspa.comparator.engine;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Deepa
 *
 * This class can compare a row from LHS table and its corresponding row from RHS table.
 * 
 */
public class NonKeyRowComparator{
	private ColumnComparator[] columnComparators;
		
	public NonKeyRowComparator(ColumnComparator[] columnComparators) {
		this.columnComparators = columnComparators;
	}
	
	/**
	 * @param lhsRow is an Object array holding values from LHS table row
	 * @param rhsRow is an Object array holding values from RHS table row
	 * @return The list of column names that failed to match.
	 */
	public List<String> compare(Object[] lhsRow, Object[] rhsRow) {
		List<String> diffColumns = new ArrayList<String>();
		for(ColumnComparator comparator : columnComparators) {
			if(comparator.isDifferent(comparator.getLHSValue(lhsRow), comparator.getRHSValue(rhsRow))) {
				diffColumns.add(comparator.getColumnName());
			}
		}
		return diffColumns;
	}
	
}
