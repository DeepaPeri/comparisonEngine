package com.ms.mspa.comparator.engine;

public interface IPlan {
	ISource getRHSSource();
	ISource getLHSSource();
	ISink getSink();
	public ITableComparator getTableComparator();
}
