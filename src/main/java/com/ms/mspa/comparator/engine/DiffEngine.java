package com.ms.mspa.comparator.engine;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiffEngine {
	public static final int LEFT_INDEX = 0;
	public static final int RIGHT_INDEX = 1;
	
	//Todo: returning void instead of context.
	public void diff(ISource lhsSource, ISource rhsSource, ISink sink, ITableComparator tableComparator) throws IOException {
		Map<String, Object> diff = new HashMap<String, Object>();
		TableSpec lhsTable = lhsSource.getTableSpec();
		TableSpec rhsTable = rhsSource.getTableSpec();
		
		int oneSide = -1;
		int rowStep = 0;
		int columnStep = 0;
		Object[][] leftAndRightRows = new Object[2][];
		
		while (true) { //Todo: Removed max diffs check.
			boolean oneSided = false;
			rowStep++;
			columnStep = 0;
			
			if (leftAndRightRows[LEFT_INDEX] == null)
				leftAndRightRows[LEFT_INDEX] = lhsSource.getNextRow();
			
			if (leftAndRightRows[LEFT_INDEX] == null) {
				//Rows exhausted in left table.
				oneSided = true;
				oneSide = RIGHT_INDEX;
			}
			if (leftAndRightRows[RIGHT_INDEX] == null)
				leftAndRightRows[RIGHT_INDEX] = rhsSource.getNextRow();
			if (leftAndRightRows[RIGHT_INDEX] == null) {
				//Rows exhausted in right table.
				if (oneSided)
					break; //Rows from both left and right tables are exhausted.
				
				oneSided = true;
				oneSide = LEFT_INDEX;
			}
			
			if (oneSided) {
				if(oneSide == LEFT_INDEX) {
					//Todo: Record left exclusive row.
					System.out.println(lhsTable.getCombinedKeyString(leftAndRightRows[LEFT_INDEX]) + ":: Left only..");
				}else {
					//Todo: Record right exclusive row.
					System.out.println(rhsTable.getCombinedKeyString(leftAndRightRows[RIGHT_INDEX]) + ":: Right only.");
				}
				leftAndRightRows[oneSide] = null;
				continue;
			}
			
			int comparison = tableComparator.getKeyComparator().compare(leftAndRightRows[0], leftAndRightRows[1]);
			if (comparison < 0) {
				//Left cursor to be advanced. Keys did not match.
				//TODO: record diff.
				System.out.println(lhsTable.getCombinedKeyString(leftAndRightRows[LEFT_INDEX]) + ":: Match not found on right.");
				leftAndRightRows[LEFT_INDEX] = null;
			}else if (comparison > 0) {
				//Right cursor to be advanced. Keys did not match.
				//TODO: record diff.
				System.out.println(rhsTable.getCombinedKeyString(leftAndRightRows[RIGHT_INDEX]) + ":: Match not found on left.");
				leftAndRightRows[RIGHT_INDEX] = null;
			}else {
				//Keys matched.
				List<String> differences = tableComparator.getRowComparator().compare(leftAndRightRows[0], leftAndRightRows[1]);
				//TODO: record diff.
				if(differences.size() == 0) {
					//System.out.println(rhsTable.getCombinedKeyString(leftAndRightRows[RIGHT_INDEX]) + ":: Matched");
				}else {
					System.out.println(rhsTable.getCombinedKeyString(leftAndRightRows[RIGHT_INDEX]) + ":: Differs in " + String.join(", ", differences));
				}
				
				leftAndRightRows[LEFT_INDEX] = null;
				leftAndRightRows[RIGHT_INDEX] = null;
			}
		}
	}
}
