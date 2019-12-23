package com.ms.mspa.comparator.engine;

import java.io.IOException;

public interface ISourceSink {
	public void open() throws IOException;
	public void close() throws IOException;
}
