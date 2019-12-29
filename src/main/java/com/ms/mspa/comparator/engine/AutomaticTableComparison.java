package com.ms.mspa.comparator.engine;

import java.util.Comparator;

public class AutomaticTableComparison implements ITableComparison {
	private Comparator<Object[]> buildRowComparator(TableSpec lhsTableSpec, TableSpec rhsTableSpec) {
		this.validateTableModels(lhsTableSpec, rhsTableSpec);
		
		ColumnSpec[] lhsColumns = lhsTableSpec.getColumns();
		int[] lhsKeys = lhsTableSpec.getPrimaryKeys();
		
		ColumnSpec[] rhsColumns = rhsTableSpec.getColumns();
		int[] rhsKeys = rhsTableSpec.getPrimaryKeys();
		
		/*DKComparatorChain chain = new DKComparatorChain();
		for (int i = 0; i < lhsKeys.length; i++) {
			Comparator<Comparable[]> comparator = new DKElementComparator<Comparable>(lhsColumns[lhsKey[i]]._index,
					rhsColumns[rhsKey[i]]._index, DKComparableComparator.getInstance());
			chain.addComparator(comparator);
		}*/
		return null;
	}

	/**
	 * Validate the table keys.
	 * 		Keys must exist. 
	 * 		Both tables must have same number of keys.
	 * 		The corresponding key type must match between the tables.
	 * @param lhsTableSpec
	 * @param rhsTableSpec
	 */
	private void validateTableModels(TableSpec lhsTableSpec, TableSpec rhsTableSpec) {
		int[] lhsKeys = lhsTableSpec.getPrimaryKeys();
		if ((lhsKeys == null) || (lhsKeys.length == 0))
			throw new RuntimeException(String.format("no key columns for lhs_->%s", lhsTableSpec));
		int[] rhsKeys = rhsTableSpec.getPrimaryKeys();
		if ((rhsKeys == null) || (rhsKeys.length == 0))
			throw new RuntimeException(String.format("no key columns for rhs_->%s", rhsTableSpec));
		if (lhsKeys.length != rhsKeys.length)
			throw new RuntimeException(
					String.format("lhsKey length->%s does not match rhsKey length->%s", lhsKeys.length, rhsKeys.length));
		ColumnSpec[] lhsColumns = lhsTableSpec.getColumns();
		ColumnSpec[] rhsColumns = rhsTableSpec.getColumns();
		
		//now check that the types match
		/*for (int i = 0; i < lhsKeys.length; i++) {
			if (lhsColumns[lhsKeys[i]].type != rhsColumns[rhsKeys[i]].type)
				throw new RuntimeException(
						String.format("lhs key component->%s type->%s must match rhs key component->%s type->%s",
								lhsColumns[lhsKeys[i]], lhsColumns[lhsKeys[i]].type, rhsColumns[rhsKeys[i]],
								rhsColumns[rhsKeys[i]].type));
		}*/
	}
}
