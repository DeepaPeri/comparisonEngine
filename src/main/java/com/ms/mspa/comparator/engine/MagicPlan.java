package com.ms.mspa.comparator.engine;

import java.io.IOException;

public class MagicPlan implements IPlan{
	private ISource lhsSource;
	private ISource rhsSource;
	private ISink sink;
	private ITableComparator tableComparison;
	
	public MagicPlan(String lhsFilePath, String rhsFilePath, String[] keyColumnNames, String sinkFilePath) throws IOException{
		this.lhsSource = new FileSource(lhsFilePath, keyColumnNames);
		this.rhsSource = new FileSource(rhsFilePath, keyColumnNames);
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
