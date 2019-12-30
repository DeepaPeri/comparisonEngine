package com.ms.mspa.comparator.engine;

import org.apache.commons.lang.ClassUtils;

import com.ms.mspa.comparator.engine.diffors.IDiffor;

public class NumberDiffor implements IDiffor{
	private final double tolerance;
	private final boolean nullIsZero;

	public NumberDiffor() {
	      this(0, false);
	   }

	public NumberDiffor(double tolerance, boolean nullIsZero) {
	      this.tolerance = tolerance;
	      this.nullIsZero = nullIsZero;
	      if (this.tolerance < 0)
	         throw new IllegalArgumentException(String.format("tolerance->%s cannot be < 0",
	            this.tolerance));
	   }

	@Override
	public boolean isDifferent(Object lhs, Object rhs) {
		Number lhsNumber = (Number) lhs;
		Number rhsNumber = (Number) rhs;
		boolean lhsIsNull = (lhsNumber == null);
		boolean rhsIsNull = (rhsNumber == null);

		if (lhsIsNull && rhsIsNull)
			return false;
		if (!this.nullIsZero) {
			if (lhsIsNull || (rhsIsNull))
				return true;
		}
		double lhValue = (lhsIsNull ? 0 : lhsNumber.doubleValue());
		double rhValue = (rhsIsNull ? 0 : rhsNumber.doubleValue());
		double diff = lhValue - rhValue;
		if (diff == 0)
			return false;
		diff = (diff >= 0 ? diff : -1 * diff);
		return (diff > this.tolerance);
	}

	public String toString() {
		return String.format("%s[%s]", ClassUtils.getShortClassName(this.getClass()), this.tolerance);
	}

	// used for keys
	@Override
	public int compare(Object lhs, Object rhs) {
		double lhValue = ((Number)lhs).doubleValue();
		double rhValue = ((Number)rhs).doubleValue();
		double diff = lhValue - rhValue;
		
		double difference = (diff >= 0 ? diff : -1 * diff);
		if(difference <= this.tolerance) {
			return 0;
		}
		
		return diff > 0 ? 1 : -1;
	}
}
