package com.ms.mspa.comparator.engine;

import java.io.IOException;

public class MagicPlan implements IPlan{
	private ISource lhsSource;
	private ISource rhsSource;
	private ISink sink;
	private ITableComparison tableComparison;
	
	public MagicPlan(String lhsFilePath, String rhsFilePath, String sinkFilePath) throws IOException{
		this.lhsSource = new FileSource(lhsFilePath);
		this.rhsSource = new FileSource(rhsFilePath);
		this.sink = new FileSink(sinkFilePath);
		this.tableComparison = new AutomaticTableComparison();
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
	public ITableComparison getTableComparison() {
		return this.tableComparison;
	}
}
