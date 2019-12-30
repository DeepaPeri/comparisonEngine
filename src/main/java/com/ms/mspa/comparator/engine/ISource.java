package com.ms.mspa.comparator.engine;

import java.io.IOException;
import com.ms.mspa.comparator.engine.ISourceSink;

public interface ISource extends ISourceSink {
	public TableSpec getTableSpec();
 	public Object[] getNextRow() throws IOException;
	public void open() throws IOException;
	public void close() throws IOException;
}
