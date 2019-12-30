package com.ms.mspa.comparator.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BasicTableComparator implements ITableComparator {
	private KeyComparator keyComparator;
	private NonKeyRowComparator rowComparator;
	
	
	public BasicTableComparator(TableSpec lhsTableSpec, TableSpec rhsTableSpec) {
		this.keyComparator = buildKeyComparatorChain(lhsTableSpec, rhsTableSpec);
		this.rowComparator = new NonKeyRowComparator(buildColumnComparators(lhsTableSpec, rhsTableSpec));
	}
	
	private KeyComparator buildKeyComparatorChain(TableSpec lhsTableSpec, TableSpec rhsTableSpec) {
		int keyIndices[] = lhsTableSpec.getPrimaryKeys();
		boolean keyOrders[] = new boolean[keyIndices.length];
		
		//TODO: Assuming ascending order sorting of all keys.
		Arrays.fill(keyOrders, true);
		KeyComparator firstComparator = new KeyComparator(0, keyOrders[0], lhsTableSpec.getColumn(keyIndices[0]).getComparator());
		KeyComparator previousComparator = firstComparator;
		for(int i = 1; i < keyOrders.length; i++) {
			KeyComparator temp = new KeyComparator(i, keyOrders[i], lhsTableSpec.getColumn(keyIndices[i]).getComparator());
			previousComparator.setNextComparator(temp);
			previousComparator = temp;
		}
		return firstComparator;
	}
	
	private ColumnComparator[] buildColumnComparators(TableSpec lhsTableSpec, TableSpec rhsTableSpec) {
		int lhsKeyIndices[] = lhsTableSpec.getPrimaryKeys();
		List<ColumnComparator> comparators = new ArrayList<ColumnComparator>();
		
		for(ColumnSpec lhsColumnSpec: lhsTableSpec.getColumns()) {
			if(lhsColumnSpec.isColumnPartofKey(lhsKeyIndices)) {
				continue;
			}
			ColumnSpec rhsColumnSpec = rhsTableSpec.getColumn(lhsColumnSpec.name);
			if(rhsColumnSpec == null) {
				continue;
			}
			ColumnComparator comparator = new ColumnComparator(lhsColumnSpec, rhsColumnSpec, lhsColumnSpec.getDiffor() );
			comparators.add(comparator);
		}
		ColumnComparator[] comparatorArray = new ColumnComparator[comparators.size()];
		return comparators.toArray(comparatorArray);
	}

	@Override
	public KeyComparator getKeyComparator() {
		return keyComparator;
	}

	@Override
	public NonKeyRowComparator getRowComparator() {
		return rowComparator;
	}
}
