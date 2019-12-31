package com.ms.mspa.comparator.engine;

import java.io.BufferedWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class FileSink extends DiffWriter implements ISink{
	private final File sinkFile;
	//private DiffWriter diffWriter;
	private FileWriter writer;
	public FileSink(String sinkFilePath) throws Exception{
		this(new File(sinkFilePath), false, null);
	}
	
	public FileSink(File file, boolean b, Object object) throws IOException {
		if(file.exists()) {
			file.delete();
		}
		this.sinkFile = file;
		this.writer = new FileWriter(sinkFile);
	}
	
	@Override
	public void open() throws IOException{
		this.init(new BufferedWriter(new FileWriter(sinkFile)));
	}
	
	@Override
	public void close() throws IOException {
		
	}	
	@Override
	public Kind getKind() {
		return Kind.FILE;
	}
	
	public File getFile() {
		return sinkFile;
	}
	
	@Override
	public void writeData(List<String[]> data) throws IOException {
		Iterator<String[]> dataIterator = data.iterator();
		String[] headerRow = {"PRIMARY KEYS", "DESCRIPTION"};
		writer.append(headerRow[0]).append(",").append(headerRow[1]).append("\n");
		while (dataIterator.hasNext()) {
			String[] values = dataIterator.next();
			writer.append(values[0]).append(",").append(values[1]).append("\n");
		}
	    writer.close();
	}

}
