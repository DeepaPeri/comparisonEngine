/**
 * 
 */
package com.ms.mspa.comparator.engine;

/**
 * @author Deepa
 * We are now assuming all columns are of type String.
 * Todo: Need to add support for other data types.
 */
public class ColumnSpec {
	public final int index;
	public final String name;
	
	public ColumnSpec(int index, String name) {
		this.index = index;
		this.name = name;
	}
}