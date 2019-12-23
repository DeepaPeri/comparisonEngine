/**
 * 
 */
package com.ms.mspa.comparator.engine;

import java.util.Arrays;

/**
 * @author Deepa
 *
 */
public class TableSpec {
	private final String tableName;
	private final ColumnSpec columns[];
	private final int primaryKeys[];
	
	public TableSpec(String tableName, ColumnSpec columns[], int primaryKeys[]) {
		this.tableName = tableName;
		this.columns = (columns == null) ? null : Arrays.copyOf(columns, columns.length);
		this.primaryKeys = (primaryKeys == null) ? null : Arrays.copyOf(primaryKeys, primaryKeys.length);
	}
}
