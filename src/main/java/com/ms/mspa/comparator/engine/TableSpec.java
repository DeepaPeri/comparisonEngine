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

	public int[] getPrimaryKeys() {
		return this.primaryKeys;
	}

	public ColumnSpec[] getColumns() {
		return this.columns;
	}

	/**
	 * Return column object having the given column name.
	 * @param columnName
	 * @return
	 */
	public ColumnSpec getColumn(String columnName) {
		for(ColumnSpec columnSpec: columns) {
			if(columnSpec.name.equals(columnName)) {
				return columnSpec;
			}
		}
		return null;
	}
	
	/**
	 * Return column object having the given column index.
	 * @param columnIndex
	 * @return
	 */
	public ColumnSpec getColumn(int columnIndex) {
		for(ColumnSpec columnSpec: columns) {
			if(columnSpec.index == columnIndex) {
				return columnSpec;
			}
		}
		return null;
	}
	
	/**
	 * convenience factory method that creates DKTableModel having String type
	 * columns using columnNames_ and key = key_
	 */
	public static TableSpec createGenericStringModel(String[] columnNames, int[] keyColumnIndices) {
		if ((columnNames == null) || (columnNames.length == 0))
			return null;
		if (keyColumnIndices == null)
			return null;
		
		//Validate that all the keyColumnIndices are in range.
		for (int i = 0; i < keyColumnIndices.length; i++) {
			if ((keyColumnIndices[i] < 0) || (keyColumnIndices[i] >= columnNames.length))
				throw new RuntimeException(String.format("key index->%s not in range for columnNames.length->%s",
						keyColumnIndices[i], columnNames.length));
		}
		
		ColumnSpec[] columns = new ColumnSpec[columnNames.length];
		for (int i = 0; i < columnNames.length; i++)
			columns[i] = new ColumnSpec(i, columnNames[i]);

		return new TableSpec("GENERIC_STRING_TABLE", columns, keyColumnIndices);
	}
	
	/**
	 * 
	 * @param row
	 * @return hyphen separated string of all primary keys.
	 */
	public String getCombinedKeyString(Object[] row) {
		String[] keyValues = new String[primaryKeys.length];
		for(int i = 0; i < keyValues.length; i++) {
			keyValues[i] = (String) row[getColumn(primaryKeys[i]).index];
		}
		return String.join("-", keyValues);
	}
}
