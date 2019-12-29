package com.ms.mspa.comparator.engine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class KeyComparatorTest {
	class IntegerKey implements Comparable<IntegerKey>{
		private int value;
		
		public IntegerKey(int value) {
			this.value = value;
		}
		
		@Override
		public int compareTo(IntegerKey o) {
			return this.value - o.getValue();
		}
		
		public int getValue() {
			return this.value;
		}
	}
	
	@Test
	void testCompare() {
		boolean keyOrders[] = {true, false};
		
		KeyComparator firstComparator = new KeyComparator(0, keyOrders[0]);
		KeyComparator previousComparator = firstComparator;
		for(int i = 1; i < keyOrders.length; i++) {
			KeyComparator temp = new KeyComparator(i, keyOrders[i]);
			previousComparator.setNextComparator(temp);
			previousComparator = temp;
		}
		
		IntegerKey lhs[] = {new IntegerKey(1), new IntegerKey(36)};
		IntegerKey rhs[] = {new IntegerKey(1), new IntegerKey(36)};
		
		int result = firstComparator.compare(lhs, rhs);
		Assertions.assertEquals(0, result);
		
		IntegerKey lhs2[] = {new IntegerKey(1), new IntegerKey(36)};
		IntegerKey rhs2[] = {new IntegerKey(1), new IntegerKey(37)};
		
		result = firstComparator.compare(lhs2, rhs2);
		Assertions.assertTrue(result > 0);
		
		IntegerKey lhs3[] = {new IntegerKey(1), new IntegerKey(39)};
		IntegerKey rhs3[] = {new IntegerKey(1), new IntegerKey(37)};
		
		result = firstComparator.compare(lhs3, rhs3);
		Assertions.assertTrue(result < 0);
		
		IntegerKey lhs4[] = {new IntegerKey(1), new IntegerKey(36)};
		IntegerKey rhs4[] = {new IntegerKey(2), new IntegerKey(37)};
		
		result = firstComparator.compare(lhs4, rhs4);
		Assertions.assertTrue(result < 0);
	}

}
