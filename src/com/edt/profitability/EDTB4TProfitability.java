package com.edt.profitability;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.edt.b4t.B4TProjects;
import com.edt.b4t.util.Str;
import com.edt.b4t.util.TimeDate;

public class EDTB4TProfitability
{
	public static void main(String[] args)
			throws IOException
	{
//		B4TUsers employees = new B4TUsers("");
//		
//		for (int index = 0; index < employees.getIds().size(); index++)
//		{
//			if ("Lisa".equals(employees.getFnames().get(index)) && "Chapin".equals(employees.getLnames().get(index)))
//			{
//				System.out.println("getIds: " + employees.getIds().get(index) +
//						"\r\ngetUserTypes: " + employees.getUserTypes().get(index) +
//						"\r\ngetBilligRates: " + employees.getBilligRates().get(index) +
//						"\r\ngetPayableRates: " + employees.getPayableRates().get(index) +
//						"");
//			}
//		}
		
		String demoFile = "main-report.html";
		
		File file = new File(demoFile);
		
		if (file.exists())
			file.delete();
		
		FileWriter fname = new FileWriter(demoFile);
		
		PrintWriter buffer = new PrintWriter(fname);
		
		EDTB4TProfitability prof = new EDTB4TProfitability();
		prof.buildReport(buffer, "2017-01-01", "2017-11-01");
	}
	
	public void buildReport(PrintWriter out, String startDate, String endDate) 
			throws IOException
	{
		if (TimeDate.convertStringToDate(startDate).after(TimeDate.convertStringToDate(endDate)))
		{
			System.out.println(TimeDate.convertStringToDate(startDate)
								+ " - "
								+ TimeDate.convertStringToDate(endDate));
			
			throw new IOException("The Start Date is greater than End Date");
		}
		
		String prefs = Str.convertToURL("?$filter=createdDate ge '" + startDate
										+ "' AND createdDate lt '" + endDate
										+ "'&$orderby=createdDate asc");		
		
		B4TProjects projects = new B4TProjects(prefs);
		EDTProfitabilityReport report = new EDTProfitabilityReport(out, projects.getIds().size());
		
		int fixedFeeProjects = 0;
		
		for (int index = 0; index < projects.getIds().size(); index++)
		{
		 	EDTProjects projectProifitability = new EDTProjects(projects, index);
		 	
	 		report.tableMainRow(projectProifitability, index);
	 		report.tableDetailsRow(projectProifitability, index);
	 		
	 		if (projectProifitability.isFixedFee())
	 			fixedFeeProjects++;
	 		
//			projectProifitability.displayDetails();
		}
		
		report.tableTotalRow();
		report.closeTable();
		
		System.out.println("Done! " + fixedFeeProjects 
							+ " fixed fee projects out of "
							+ projects.getIds().size() + " projects"
							);
	}
}