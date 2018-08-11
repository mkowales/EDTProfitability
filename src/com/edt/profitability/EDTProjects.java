package com.edt.profitability;

import java.text.DecimalFormat;
import java.util.ArrayList;
import com.edt.b4t.B4TInvoices;
import com.edt.b4t.B4TProjects;
import com.edt.b4t.util.DemoFile;
import com.edt.b4t.util.TimeDate;

public class EDTProjects
{
	public static DecimalFormat dollarFormat = new DecimalFormat("$#,###.00"),
								percentFormat = new DecimalFormat("#,###%"),
								numericFormat = new DecimalFormat("#,###.00");

	private String projectName = "",
			clientName = "",
			billingMethod = "",
			createdDate = "";
	
	private ArrayList<EDTUsers> users = new ArrayList<EDTUsers>();
	
	private boolean fixedFee = false;
	
	private double revenue = 0,
			totalHoursLogged = 0,
			averageHourlyRate = 0,
			totalExpenses = 0,
			totalLoadedCost = 0,
			averageHourlyLoadedCost = 0,
			grossProfit = 0,
			grossProfitMargin = 0,

			blendedHoulrRate = 0;
	
	private String projectId = "";
	
	public String crlf = "\r\n",
				  crlftb = "\r\n\t",
				  tab = "\t";
	
	public EDTProjects(B4TProjects projects, int index)
	{
		if (null == projects)
			throw new java.lang.RuntimeException("NULL projects item");
		
		if (0 > index)
			index = 0;
		
		DemoFile dfile = new DemoFile();

//		skip all internal projects
		if (projects.getClientNames().get(index).equals("EagleDream Technologies"))
			dfile.writeLine("Skipping " + projects.getProjectNames().get(index) + " for " + projects.getClientNames().get(index));
		else
		{
			this.projectName = projects.getProjectNames().get(index);
			
//			System.out.println("projectName: " + this.projectName);
			
			this.createdDate = projects.getCreatedDates().get(index);
			
			this.projectId = projects.getIds().get(index);
			
			this.clientName = projects.getClientNames().get(index);
			this.billingMethod = projects.getBillingMethods().get(index);				
			this.fixedFee = "Flat Fee".equals(this.billingMethod);
			
			this.setLaborDetail();
		}
		
		dfile.close();
	}
	
	public void setLaborDetail()
	{
		EDTB4TLabor labor = new EDTB4TLabor(this.projectId);
		
		this.users = labor.getUsers();
		
		for (int index = 0; index < this.users.size(); index++)
		{
			this.totalHoursLogged += Double.valueOf(this.users.get(index).getHours());
			this.totalLoadedCost += this.users.get(index).getHours() * this.users.get(index).getPayableRate();
		}
		
		this.setInvoiceAmount();
		
		this.grossProfit = this.revenue - totalLoadedCost;
		
		if ((0 != this.revenue) && (0 != this.grossProfit))
			this.grossProfitMargin = this.grossProfit / this.revenue;
		
		if (0 != this.totalHoursLogged)
		{
			this.blendedHoulrRate = this.revenue / this.totalHoursLogged;
			
			this.averageHourlyRate = this.revenue / this.totalHoursLogged;
			this.averageHourlyLoadedCost = this.totalLoadedCost / this.totalHoursLogged;
		}
	}
	
	public void displayDetails()
	{
		System.out.println("Client: " + this.clientName 
							+ this.crlf 
							+ "Project: " + this.projectName);
		
		System.out.println("Total Budget: " + EDTProjects.dollarFormat.format(this.getRevenue()));
		System.out.println("Gross Profit: " + EDTProjects.dollarFormat.format(this.grossProfit));
		System.out.println("Gross Margin Percentage: " + EDTProjects.percentFormat.format(this.grossProfitMargin));
		
		for (int index = 0; index < this.users.size(); index++)
		{
			System.out.println(this.users.get(index).getName()
					+ " --> " + EDTProjects.numericFormat.format(this.users.get(index).getHours())
					+ " * " + this.users.get(index).getBillingRate()
					+ " = " + EDTProjects.dollarFormat.format(this.users.get(index).getHours()
							* this.users.get(index).getBillingRate())
					);
			
//			System.out.println("\tRate: " + this.users.get(index).getLoadedHourlyCost());
//			System.out.println("\tBlended Hourlu Rate: " + this.users.get(index).getBlendedHourlyRate());
		}
		
		System.out.println("Summary: ==============================================");
		System.out.println("Total Hours Logged: " + EDTProjects.numericFormat.format(this.totalHoursLogged));
		System.out.println("Hourly Rate: " + EDTProjects.dollarFormat.format(this.averageHourlyRate));
		System.out.println("Total Expenses: " + EDTProjects.dollarFormat.format(this.totalExpenses));
		System.out.println("Total Cost: " + EDTProjects.dollarFormat.format(this.totalLoadedCost));
		System.out.println("Hourly Cost: " + EDTProjects.dollarFormat.format(this.averageHourlyLoadedCost));
		
//		System.out.println(
//				"\tRevenue: " + EDTB4TProfitability.dollarFormat.format(this.revenue)
//				+ this.crlftb + "Loaded Cost: " + EDTB4TProfitability.dollarFormat.format(this.totalLoadedCost)
//				+ this.crlftb + "Net Profit: " + EDTB4TProfitability.dollarFormat.format(this.grossProfit)
//				);
//		
//		if (0 != this.grossProfitMargin)
//			System.out.println("\tNet Profit Margin: " + EDTB4TProfitability.percentFormat.format(this.grossProfitMargin));
//		
//		if (0 != this.totalHoursLogged)
//		{
//			System.out.println("\tTotal Time: " + this.totalHoursLogged
//								+ this.crlftb + "Blended Hourly Rate: " + EDTB4TProfitability.dollarFormat.format(this.blendedHoulrRate));
//		}
	}
	
	public void setInvoiceAmount()
	{
		if (null != this.projectId)
		{
			String prefs = "?$filter=projectId%20eq%20" + this.projectId;
			
			B4TInvoices invoices = new B4TInvoices(prefs);
			
			for (int index = 0; index < invoices.getIds().size(); index++)
			{
				this.revenue += Double.parseDouble(invoices.getInvoiceAmouts().get(index));
				System.out.println("getInvoiceDate: " + TimeDate.convertToDate(invoices.getInvoiceDates().get(index)));
			}
		}
	}

	public String getProjectName()
	{
		return this.projectName;
	}

	public String getClientName()
	{
		return this.clientName;
	}

	public String getBillingMethod()
	{
		return this.billingMethod;
	}

	public ArrayList<EDTUsers> getUsers()
	{
		return this.users;
	}

	public double getRevenue()
	{
		return this.revenue;
	}

	public double getTotalLoadedCost()
	{
		return this.totalLoadedCost;
	}

	public double getNetProfit()
	{
		return this.grossProfit;
	}

	public double getNetProfitMargin()
	{
		return this.grossProfitMargin;
	}

	public double getTotalTime()
	{
		return this.totalHoursLogged;
	}

	public double getBlendedHoulrRate()
	{
		return this.blendedHoulrRate;
	}

	public double getTotalExpenses()
	{
		return this.totalExpenses;
	}

	public void setTotalExpenses(double totalExpenses)
	{
		this.totalExpenses = totalExpenses;
	}

	public double getAverageHourlyLoadedCost()
	{
		return this.averageHourlyLoadedCost;
	}

	public void setAverageHourlyLoadedCost(double averageHourlyLoadedCost)
	{
		this.averageHourlyLoadedCost = averageHourlyLoadedCost;
	}

	public String getCreatedDate()
	{
	    return this.createdDate;
	}

	public boolean isFixedFee()
	{
		return this.fixedFee;
	}
}
