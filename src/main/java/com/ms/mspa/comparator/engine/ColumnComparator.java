/**
 * 
 */
package com.ms.mspa.comparator.engine;

import com.ms.mspa.comparator.engine.diffors.IDiffor;

/**
 * @author Deepa
 * This class holds corresponding ColumnSpec objects from LHS and RHS tables.
 * It also holds a reference to the diffor that is used to compare these columns.
 */
public class ColumnComparator implements IDiffor{
	private ColumnSpec lhsColumn;
	private ColumnSpec rhsColumn;
	private IDiffor diffor;
	
	public ColumnComparator(ColumnSpec lhsColumn, ColumnSpec rhsColumn, IDiffor diffor) {
		super();
		this.lhsColumn = lhsColumn;
		this.rhsColumn = rhsColumn;
		this.diffor = diffor;
	}

	@Override
	public boolean isDifferent(Object lhs, Object rhs) {
		return diffor.isDifferent(lhs, rhs);
	}
	
	public Object getLHSValue(Object[] lhsRow) {
	      return lhsRow[lhsColumn.index];
	}

	public Object getRHSValue(Object[] rhsRow) {
	      return rhsRow[rhsColumn.index];
	}

	public String getColumnName() {
		return lhsColumn.name;
	}
}
