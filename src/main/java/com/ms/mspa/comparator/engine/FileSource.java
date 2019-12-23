package com.ms.mspa.comparator.engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import org.apache.commons.lang.StringUtils;

public class FileSource implements ISource {
	public static final String DEFAULT_DELIMITER = ",";
	
	private String filePath;
	private boolean isOpen;
	private File fileObj;
	private LineNumberReader lineReader;
	private String[] headerColumnNames;
	
	public FileSource(String filePath) throws IOException{
		this.filePath = filePath;
		this.fileObj = findFile(filePath);
		this.open();
	}

	@Override
	public void open() throws IOException {
		if (isOpen)
			return;
		isOpen = true;

		lineReader = new LineNumberReader(new BufferedReader(new FileReader(fileObj)));
		this.readHeader();
	}

	/**
	 * The first line is always header row.
	 * 
	 * @throws IOException
	 */
	private void readHeader() throws IOException {
		String line = this.readLine();
		System.out.println(String.format("header->{%s}", line));
		headerColumnNames = line.split(DEFAULT_DELIMITER);
	}

	/**
	 * skips blank lines
	 * 
	 * @return null only when EOF is reached
	 */
	private String readLine() throws IOException {
		while (true) {
			String line = lineReader.readLine();
			if (line == null)
				return null;
			line = StringUtils.trimToNull(line);
			if (line != null)
				return line;
		}
	}
	@Override
	public void close() throws IOException {
		if(!this.isOpen)
			throw new RuntimeException("not open!");
		
		this.lineReader.close();
		this.lineReader = null;
		this.isOpen = false;
	}
	///////////////////////// Utility //////////////////////////////
	public static File findFile(String filePath) {
		if (filePath == null)
			return null;
		File fsFile = new File(filePath);
		if (fsFile.exists())
			return fsFile;
		else
			return null;
	}
}
