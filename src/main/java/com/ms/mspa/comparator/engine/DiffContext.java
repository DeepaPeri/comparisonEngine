package com.ms.mspa.comparator.engine;

import java.io.IOException;
import org.apache.commons.lang.time.StopWatch;

public class DiffContext {
	public ISource lhsSource;
	public ISource rhsSource; 
	public ISink sink;
	public ITableComparator tableComparison;
	private final StopWatch stopwatch = new StopWatch();
	
	public DiffContext(ISource lhsSource, ISource rhsSource, ISink sink, ITableComparator tableComparison) {
		this.lhsSource = lhsSource;
		this.rhsSource = rhsSource;
		this.sink = sink;
		this.tableComparison = tableComparison;
	}
	
	public void open() throws IOException {
		this.sink.open();
		this.lhsSource.open();
		this.rhsSource.open();
		this.stopwatch.start();
	}
	
	public void close() throws IOException{
		this.stopwatch.stop();
		this.lhsSource.close();
		this.rhsSource.close();
		this.sink.close();
	}
}
