package com.ms.mspa.comparator.engine;

import java.io.IOException;

public interface ISource extends ISourceSink {
	public TableSpec getTableSpec();
 	public Object[] getNextRow() throws IOException;
}
