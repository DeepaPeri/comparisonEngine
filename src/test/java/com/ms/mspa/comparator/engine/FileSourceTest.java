/**
 * 
 */
package com.ms.mspa.comparator.engine;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Deepa
 *
 */
class FileSourceTest {
	private static FileSource fs;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		String[] keyColumns = {"COLUMN1"};
		fs = new FileSource("test35.rhs.csv", keyColumns);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		fs.close();
	}

	/**
	 * Test method for {@link com.ms.mspa.comparator.engine.FileSource#FileSource()}.
	 */
	@Test
	void testKeyColumnIndices() {
		int[] indices = fs.getKeyColumnIndices();
		Assertions.assertEquals(1, indices.length);
		Assertions.assertEquals(0,  indices[0]);
	}
	
	/**
	 * Test method for {@link com.ms.mspa.comparator.engine.FileSource#FileSource()}.
	 */
	@Test
	void testColumnNames() {
		String[] columnNames = fs.getColumnNames();
		String[] expectedColumnNames = {
				"COLUMN1", "COLUMN2", "COLUMN3", "COLUMN4",
				"COLUMN5", "COLUMN6", "COLUMN7", "COLUMN8",
				"COLUMN9", "COLUMN10", "COLUMN11", "COLUMN12",
				"COLUMN13", "COLUMN14", "COLUMN15"
		};
		String[] s = {
				"bbbb","2221", "2008-01-01", "zzzz", "12:00:00", "1234", 
				"123456.78", "1234.5678", "1234.57", "TRUE", "10", "12345",
				"AAAAA", "BBBB", "my clobby text"
		};
		
		Assertions.assertArrayEquals(expectedColumnNames, columnNames);
	}

	@Test
	void testReadRow() throws IOException{
		String[] expectedRow = {
				"bbbb","2221", "2008-01-01", "zzzz", "12:00:00", "1234", 
				"123456.78", "1234.5678", "1234.57", "TRUE", "10", "12345",
				"AAAAA", "BBBB", "my clobby text"
		};
		Object[] actualRow = fs.getNextRow();
		Assertions.assertArrayEquals(expectedRow, actualRow);
	}
	/**
	 * Test method for {@link com.ms.mspa.comparator.engine.FileSource#close()}.
	 */
	@Test
	void testClose() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ms.mspa.comparator.engine.FileSource#getNextRow()}.
	 */
	@Test
	void testGetNextRow() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ms.mspa.comparator.engine.FileSource#findFile(java.lang.String)}.
	 */
	@Test
	void testFindFile() {
		fail("Not yet implemented");
	}

}
