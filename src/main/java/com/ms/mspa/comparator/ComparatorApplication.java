package com.ms.mspa.comparator;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ms.mspa.comparator.engine.DiffContext;
import com.ms.mspa.comparator.engine.DiffEngine;
import com.ms.mspa.comparator.engine.IPlan;
import com.ms.mspa.comparator.engine.ISink;
import com.ms.mspa.comparator.engine.ISource;
import com.ms.mspa.comparator.engine.ITableComparison;
import com.ms.mspa.comparator.engine.MagicPlan;

@SpringBootApplication
public class ComparatorApplication {

	public static void main(String[] args){
		SpringApplication.run(ComparatorApplication.class, args);
		try {
			runMagicPlan();
		}catch(IOException ex) {
			System.out.println("IO exception occured");
		}
		
	}
	
	private static void runMagicPlan() throws IOException{
		System.out.println("Running the magic plan");
		
		//Configuration data
		String lhsFilePath = "a.csv";
		String rhsFilePath = "b.csv";
		String sinkFilePath = "sink.txt";
		///End of configuration
		
		IPlan plan = new MagicPlan(lhsFilePath, rhsFilePath, sinkFilePath);
		ISource lhsSource = plan.getLHSSource();
		ISource rhsSource = plan.getRHSSource();
		ISink sink = plan.getSink();
		ITableComparison tableComparison = plan.getTableComparison();
		doDiff(lhsSource, rhsSource, sink, tableComparison);
	}
	
	private static DiffContext doDiff(ISource lhsSource, ISource rhsSource, ISink sink, ITableComparison tableComparison) {
		DiffEngine diffEngine = new DiffEngine();
		return diffEngine.diff(lhsSource, rhsSource, sink, tableComparison);
	}
}
