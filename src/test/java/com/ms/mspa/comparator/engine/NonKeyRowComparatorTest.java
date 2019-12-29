/**
 * 
 */
package com.ms.mspa.comparator.engine;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ms.mspa.comparator.engine.diffors.TextDiffor;

/**
 * @author Deepa
 *
 */
class NonKeyRowComparatorTest {
	private static NonKeyRowComparator rowComparator;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		//Instantiate rowComprator.
		ColumnSpec lhsColumn = new ColumnSpec(3, "rank");
		ColumnSpec rhsColumn = new ColumnSpec(3, "rank");
		TextDiffor diffor = new TextDiffor();
		ColumnComparator columnComparator = new ColumnComparator(lhsColumn, rhsColumn, diffor);
		ColumnComparator[] columnComparators = new ColumnComparator[1];
		columnComparators[0] = columnComparator;
		
		rowComparator = new NonKeyRowComparator(columnComparators);
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
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.ms.mspa.comparator.engine.NonKeyRowComparator#compare(java.lang.Object[], java.lang.Object[])}.
	 */
	@Test
	void testCompare() {
		String[] lhsRow = {"1", "36", "l", "2"};
		String[] rhsRow = {"36", "1", "l", "2"};
		List<String> result = rowComparator.compare(lhsRow, rhsRow);
		Assertions.assertEquals(0, result.size());
		//fail("Not yet implemented");
	}

}
