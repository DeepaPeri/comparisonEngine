package com.ms.mspa.comparator.engine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.ms.mspa.comparator.engine.diffors.TextDiffor;

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
		
		KeyComparator firstComparator = new KeyComparator(0, keyOrders[0], new TextDiffor());
		KeyComparator previousComparator = firstComparator;
		for(int i = 1; i < keyOrders.length; i++) {
			KeyComparator temp = new KeyComparator(i, keyOrders[i], new TextDiffor());
			previousComparator.setNextComparator(temp);
			previousComparator = temp;
		}
		
		Object lhs[] = {"1", "36"};
		Object rhs[] = {"1", "36"};
		
		int result = firstComparator.compare(lhs, rhs);
		Assertions.assertEquals(0, result);
		
		Object lhs2[] = {"1", "36"};
		Object rhs2[] = {"1", "37"};
		
		result = firstComparator.compare(lhs2, rhs2);
		Assertions.assertTrue(result > 0);
		
		Object lhs3[] = {"1", "39"};
		Object rhs3[] = {"1", "37"};
		
		result = firstComparator.compare(lhs3, rhs3);
		Assertions.assertTrue(result < 0);
		
		Object lhs4[] = {"1", "36"};
		Object rhs4[] = {"2", "37"};
		
		result = firstComparator.compare(lhs4, rhs4);
		Assertions.assertTrue(result < 0);
	}

}
