package com.ms.mspa.comparator.engine;

import java.io.IOException;

public class MagicPlan implements IPlan{
	private ISource lhsSource;
	private ISource rhsSource;
	private ISink sink;
	private ITableComparator tableComparison;
	
	public MagicPlan(String lhsFilePath, String rhsFilePath, String[] keyColumnNames, String sinkFilePath, String[] columnTypes) throws Exception{
		this.lhsSource = new FileSource(lhsFilePath, keyColumnNames, columnTypes);
		this.rhsSource = new FileSource(rhsFilePath, keyColumnNames, columnTypes);
		this.sink = new FileSink(sinkFilePath);
		this.tableComparison = new BasicTableComparator(lhsSource.getTableSpec(), rhsSource.getTableSpec());
	}
	
	@Override
	public ISource getLHSSource() {
		return this.lhsSource;
	}
	
	@Override
	public ISource getRHSSource() {
		return this.rhsSource;
	}
	
	@Override
	public ISink getSink() {
		return this.sink;
	}

	@Override
	public ITableComparator getTableComparator() {
		return this.tableComparison;
	}
}
