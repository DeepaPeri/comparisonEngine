/**
 * 
 */
package com.ms.mspa.comparator.engine.diffors;

import java.util.Comparator;

/**
 * @author Deepa
 *
 */
public interface IDiffor extends Comparator{
	/**
	 * @param lhs a column value from left table
	 * @param rhs corresponding column value from right table.
	 * @return true if the given column values are different.
	 */
	public boolean isDifferent(Object lhs, Object rhs);
}
