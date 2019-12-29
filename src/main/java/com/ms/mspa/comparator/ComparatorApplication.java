package com.ms.mspa.comparator;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ms.mspa.comparator.engine.DiffEngine;
import com.ms.mspa.comparator.engine.IPlan;
import com.ms.mspa.comparator.engine.ISink;
import com.ms.mspa.comparator.engine.ISource;
import com.ms.mspa.comparator.engine.ITableComparator;
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
		String lhsFilePath = "test35.lhs.csv";
		String rhsFilePath = "test35.rhs.csv";
		String[] keyColumnNames = {"COLUMN1"};
		String sinkFile = "test35.sink.diff";
		///End of configuration
		
		IPlan plan = new MagicPlan(lhsFilePath, rhsFilePath, keyColumnNames, sinkFile);
		ISource lhsSource = plan.getLHSSource();
		ISource rhsSource = plan.getRHSSource();
		ISink sink = plan.getSink();
		ITableComparator tableComparator = plan.getTableComparator();
		
		DiffEngine diffEngine = new DiffEngine();
		diffEngine.diff(lhsSource, rhsSource, sink, tableComparator);
	}
}
