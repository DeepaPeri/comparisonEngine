package com.ms.mspa.comparator.engine;

public interface ITableComparator {
	KeyComparator getKeyComparator();
	NonKeyRowComparator getRowComparator();
}
