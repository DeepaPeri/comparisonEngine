package com.ms.mspa.comparator.engine;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class DiffWriter extends AbstractSink{
	private Writer writer;
	protected void init(Writer writer) {
		this.writer = writer;
	}
	@Override
	public void record(Diff diff) throws IOException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void writeData(List<String[]> diff) throws IOException {
		// TODO Auto-generated method stub
		
	}
}
