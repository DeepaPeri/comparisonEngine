package com.ms.mspa.comparator.engine;

import java.util.Comparator;

public class DiffEngine {
	public DiffContext diff(ISource lhsSource, ISource rhsSource, ISink sink, ITableComparison tableComparison) {
		DiffContext context = new DiffContext(lhsSource, rhsSource, sink, tableComparison);
		this.diff(context);
		return context;
	}

	protected void diff(DiffContext context) {
		context.open();
		int oneSide = -1;
		
		Object[][] leftAndRightRows = new Object[2][];
		Comparator<Object[]> rowComparator = context.tableComparison.getRowComparator();
		_log.debug("rowComparator->{}", rowComparator);
		while (true) { // Removed max diffs check.
			if (_isDebug)
				_log.debug("diffCount->{}", context_._sink.getDiffCount());
			boolean oneSided = false;
			context_._rowStep++;
			context_._columnStep = 0;
			if (context_._rowStep % PROGRESS_BATCH_SIZE == 0)
				USER_LOG.info("->{}", context_._rowStep);
			if (rows[DKSide.LEFT_INDEX] == null)
				rows[DKSide.LEFT_INDEX] = context_._lhs.getNextRow();
			if (rows[DKSide.LEFT_INDEX] == null) {
				oneSided = true;
				oneSide = DKSide.RIGHT_INDEX;
			}
			if (rows[DKSide.RIGHT_INDEX] == null)
				rows[DKSide.RIGHT_INDEX] = context_._rhs.getNextRow();
			if (rows[DKSide.RIGHT_INDEX] == null) {
				if (oneSided)
					break;
				oneSided = true;
				oneSide = DKSide.LEFT_INDEX;
			}
			if (_isDebug) {
				_log.debug("oneSided->{}", oneSided);
				_log.debug("oneSide->{}", oneSide);
			}
			if (oneSided) {
				this.recordRowDiff(rows[oneSide], oneSide, context_, context_._sink);
				rows[oneSide] = null;
				continue;
			}
			assert ((rows[DKSide.LEFT_INDEX] != null) && (rows[DKSide.RIGHT_INDEX] != null));
			int comparison = rowComparator.compare(rows[DKSide.LEFT_INDEX], rows[DKSide.RIGHT_INDEX]);
			// LEFT < RIGHT
			if (comparison < 0) {
				this.recordRowDiff(rows[DKSide.LEFT_INDEX], DKSide.LEFT_INDEX, context_, context_._sink);
				rows[DKSide.LEFT_INDEX] = null;
			}
			// LEFT > RIGHT
			else if (comparison > 0) {
				this.recordRowDiff(rows[DKSide.RIGHT_INDEX], DKSide.RIGHT_INDEX, context_, context_._sink);
				rows[DKSide.RIGHT_INDEX] = null;
			}
			// at this point you know the keys are aligned
			else {
				this.diffRow(rows[DKSide.LEFT_INDEX], rows[DKSide.RIGHT_INDEX], context_, context_._sink);
				rows[DKSide.LEFT_INDEX] = null;
				rows[DKSide.RIGHT_INDEX] = null;
			}
		}
		context_.close();
	}
}
