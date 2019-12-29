/**
 * 
 */
package com.ms.mspa.comparator.engine.diffors;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * @author Deepa
 *
 */
class TextDifforTest {
	
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
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.ms.mspa.comparator.engine.diffors.TextDiffor#TextDiffor()}.
	 * When we create TextDiffor using no-arg constructor, the 'shouldNormalize' property should be set to false.
	 * withEmptyParams_CreatingTextDiffor_ShouldSet_shouldNormalize_ToFalse
	 */
	@Test
	void withEmptyParams_CreatingTextDiffor_ShouldSet_shouldNormalize_ToFalse() {
		TextDiffor td = new TextDiffor();
		Assertions.assertFalse(td.isNormalizationEnabled(), "TextDiffor should initialize 'shouldNormalize' to false by default");
	}

	@Test
	void withTrueAsParam_CreatingTextDiffor_ShouldSet_shouldNormalize_ToTrue() {
		TextDiffor td = new TextDiffor(true);
		Assertions.assertTrue(td.isNormalizationEnabled());
	}
	
	@Test
	void withFalseAsParam_CreatingTextDiffor_ShouldSet_shouldNormalize_ToFalse() {
		TextDiffor td = new TextDiffor(false);
		Assertions.assertFalse(td.isNormalizationEnabled());
	}
	
	/**
	 * Test method for {@link com.ms.mspa.comparator.engine.diffors.TextDiffor#isDifferent(java.lang.Object, java.lang.Object)}.
	 */
	@ParameterizedTest (name = "{index} => Normalization[{0}] - {1}, {2} - Expected {3}")
	@CsvSource({
		// with normalization same strings
		"true, xyz, xyz, false",
		// without normalization same strings
		"false, xyz, xyz, false",
		
		/* Normalization is considered only for non space white space characters and removes contiguous white spaces*/
		// with normalization for single white space
		"true, xy z, xyz, true",
		// without normalization for single white space
		"false, xy z, xyz, true",
		
		// with non space white space characters, with normalization 
		"true, xy		z, xy z, false", //xy z, x yz
		// with non space white space characters, without normalization 
		"false, xy			z, xy		z, true",
		
		// with normalization and continuous white space characters
		"true, xy  z, xy    z, false",
		// without normalization and continuous white space characters
		"false, xy  z, xy    z, true",
		
		//with multiple white space characters, with normalization
		"true, xy			z, xy  z, false",
		//with multiple white space characters, without normalization
		"false, xy	z, xy				z, true",
		
	})
	void testIsDifferent(boolean normalize, String lhs, String rhs, boolean expected) {
		TextDiffor td = new TextDiffor(normalize);
		if(expected)
			Assertions.assertTrue(td.isDifferent(lhs, rhs));
		else
			Assertions.assertFalse(td.isDifferent(lhs, rhs));
	}

}
