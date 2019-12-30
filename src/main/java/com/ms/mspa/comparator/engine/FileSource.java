package com.ms.mspa.comparator.engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.text.ParseException;
import java.util.Arrays;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

public class FileSource implements ISource {
	public static final String DEFAULT_DELIMITER = ",";

	private boolean isOpen;
	private File fileObj;
	private LineNumberReader lineReader;
	private String[] columnNames;
	private int[] keyColumnIndices;
	private String[] keyColumnNames;
	private TableSpec tableSpec;
	
	private String delimiter;
	
	public FileSource(String filePath, String[] keyColumnNames, String[] columnTypes) throws IOException {
		this.delimiter = DEFAULT_DELIMITER;
		this.fileObj = findFile(filePath);
		this.keyColumnNames = keyColumnNames;
		this.open();
		this.keyColumnIndices = this.generateKeyColumnIndices(keyColumnNames);
		this.tableSpec = TableSpec.createTableSpec(columnNames, keyColumnIndices, columnTypes);
	}

	@Override
	public void open() throws IOException {
		if (isOpen)
			return;
		isOpen = true;

		lineReader = new LineNumberReader(new BufferedReader(new FileReader(fileObj)));
		this.readHeader();
	}

	private int[] generateKeyColumnIndices(String[] keyColumnNames) {
		if (keyColumnNames == null) {
			return new int[] { 0 };
		}
		int[] indices = new int[keyColumnNames.length];
		Arrays.fill(indices, -1);
		int indicesIndex = 0;
		for (String keyColumnName : keyColumnNames) {
			int foundAt = ArrayUtils.indexOf(columnNames, keyColumnName);
			if (foundAt < 0)
				throw new RuntimeException(String.format("no value in columnNames for %s", keyColumnName));
			indices[indicesIndex++] = foundAt;
		}
		return indices;
	}

	/**
	 * The first line is always header row.
	 * 
	 * @throws IOException
	 */
	private void readHeader() throws IOException {
		String line = this.readLine();
		System.out.println(String.format("header->{%s}", line));
		this.columnNames = line.split(DEFAULT_DELIMITER);
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
		if (!this.isOpen)
			throw new RuntimeException("not open!");

		this.lineReader.close();
		this.lineReader = null;
		this.isOpen = false;
	}

	@Override
	public Object[] getNextRow() throws IOException {
		this.ensureOpen();
		String line = this.readLine();
		if (line == null)
			return null;

		return createRow(line);
	}

	private Object[] createRow(String line) throws IOException {
		if (line == null)
			return null;
		
		// Each field may or may not be enclosed in double quotes
		String[] strings = line.split(delimiter + "(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
		ColumnSpec[] columnSpec = this.tableSpec.getColumns();
		if (strings.length != columnSpec.length)
			throw new RuntimeException(String.format("columnCount->%s in row->%s does not match modelled table->%s",
					strings.length, Arrays.toString(strings), columnSpec.length));
		try {
			Object[] row = new Object[strings.length];
			for (int i = 0; i < strings.length; i++) {
				// When strings are enclosed by quotes, remove them.
				final String s;
				if (strings[i].startsWith("\"") && strings[i].endsWith("\""))
					s = strings[i].substring(1, strings[i].length() - 1);
				else
					s = strings[i];
				row[i] = columnSpec[i].parseObject(s);
			}
			return row;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	private void ensureOpen() {
		if (!isOpen)
			throw new RuntimeException("not open!");
	}
	public int[] getKeyColumnIndices() {
		return keyColumnIndices;
	}
	public String[] getColumnNames() {
		return columnNames;
	}
	
	
	public File findFile(String filePath) {
		if (filePath == null)
			return null;
		File fsFile = new File(getClass().getClassLoader().getResource(filePath).getFile());
		if (fsFile.exists())
			return fsFile;
		else
			return null;
	}
	
	@Override
	public TableSpec getTableSpec() {
		return tableSpec;
	}

	@Override
	public Kind getKind() {
		return null;
	}
}
