package com.ms.mspa.comparator.engine;

import java.io.IOException;

public class FileSink implements ISink{
	private String sinkFilePath;
	
	public FileSink(String sinkFilePath) {
		this.sinkFilePath = sinkFilePath;
	}
	
	@Override
	public void open() throws IOException {
		// TODO Auto-generated method stub
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}
}
