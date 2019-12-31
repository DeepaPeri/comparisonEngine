package com.ms.mspa.comparator.engine;

import java.io.IOException;
import java.util.List;

public interface ISink extends ISourceSink {
	//TODO: implement this.
	//public void record(Diff diff) throws IOException;

	public void writeData(List<String[]> diff) throws IOException;
}
