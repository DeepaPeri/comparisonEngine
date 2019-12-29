package com.ms.mspa.comparator.engine;

import java.util.Comparator;

/**
 * 
 * @author Deepa
 * Each instance of this class compares one key of the composite key.
 * First in the chain is the first element of the composite key.
 * 
 */
public class KeyComparator implements Comparator<Comparable[]>{
	private boolean isAscending;
	private KeyComparator nextComparator;
	private int keyLevel; //In a composite, the index of the key in the order of sorting levels.
	
	public KeyComparator() {
		this(0, false);
	}
	
	public KeyComparator(int keyLevel, boolean isAscending) {
		this.keyLevel = keyLevel;
		this.isAscending = isAscending;
	}
	
	@Override
	public int compare(Comparable[] lhs, Comparable[] rhs) {
		int result = lhs[keyLevel].compareTo(rhs[keyLevel]);
		if(result != 0) {
			//Keys did not match. We can break the chain here.
			return isAscending ? result : result * -1;
		}
		if(nextComparator != null) {
			return nextComparator.compare(lhs, rhs);
		}else {
			return 0;
		}
	}

	public void setNextComparator(KeyComparator nextComparator) {
		this.nextComparator = nextComparator;
	}
}
