package com.edt.profitability;

import java.util.ArrayList;

import com.edt.b4t.B4TProjects;
import com.edt.b4t.B4TTimesheets;
import com.edt.b4t.B4TUsers;
import com.edt.b4t.util.TimeDate;

public class EDTB4TLabor
{
	private double timesheetAmount = 0;
	
	private ArrayList<EDTUsers> users = new ArrayList<EDTUsers>();
	
	public EDTB4TLabor(String projectId)
	{
		String prefs = "?$filter=projectId%20eq%20" + projectId + "&$orderby=userName";
		
		B4TTimesheets timesheets = new B4TTimesheets(prefs);
		
//		for (int index = 0; index < timesheets.getIds().size(); index++)
//		{
//			if ("Zachary O'Dell".equals(timesheets.getUserNames().get(index)))
//			{
//				System.out.println(timesheets.getClientNames().get(index) + 
//						"\r\n" + timesheets.getProjectNames().get(index) + 
//						"\r\ngetIds: " + timesheets.getIds().get(index) +
//						"\r\ngetBillableAmount: " + timesheets.getBillableAmounts().get(index) +
//						"\r\ngetEntryTypes: " + timesheets.getEntryTypes().get(index) +
//						"\r\ngetHourlyRates: " + timesheets.getHourlyRates().get(index) +
//						"\r\ngetPayableRates: " + timesheets.getPayableRates().get(index)  +
//						"\r\ngetCreatedDates: " + TimeDate.convertToDate(timesheets.getCreatedDates().get(index))
//						);
//			}
//		}
		
		this.setTimesheetData(timesheets);
	}
	
	private void setTimesheetData(B4TTimesheets timesheets)
	{
		String oldName = null;
		
		for (int index = 0; index < timesheets.getIds().size(); index++)
		{
			boolean found = false;
			
			double amount = Double.parseDouble(timesheets.getBillableTimes().get(index))
							* Double.parseDouble(timesheets.getPayableRates().get(index));
//							* Double.parseDouble(timesheets.getBillableAmounts().get(index));
			
			this.timesheetAmount += amount;
			
			String name = timesheets.getUserNames().get(index);
			
			double hours = Double.valueOf(timesheets.getLaborTimes().get(index));
			
			if (null == oldName)
			{
				if ("Lisa Chapin".equals(timesheets.getUserNames().get(index)))
				{
					System.out.println("1 timesheets = (" + timesheets.getIds().get(index)
							+ " [" + name + "] ("
							+ "on " + TimeDate.convertToDate(timesheets.getCreatedDates().get(index))
							+ " on " + TimeDate.convertToDate(timesheets.getEntryDates().get(index))
							+ ") " + timesheets.getPayableRates().get(index)
							+ " - " + timesheets.getBillableAmounts().get(index)
							+ " from " + timesheets.getUserNames().get(index)
							+ " ID " + timesheets.getUserIds().get(index)
							);
				}

				this.users.add(new EDTUsers());
				this.users.get(0).setName(name);
				this.users.get(0).setAmount(amount);
				this.users.get(0).setPayableRate(timesheets.getPayableRates().get(index));
				this.users.get(0).setHours(hours);
				
				this.setBillableAttributes(this.users.get(0));
			}
			else
			{
//				if (oldName.equals(name))
//					this.users.get(element).setAmount(amount);

				for (int element = 0; (!found) && (element < this.users.size()); element++)
				{
					if (found = this.users.get(element).getName().equals(name))
					{
						if ("Lisa Chapin".equals(timesheets.getUserNames().get(element)))
						{
							System.out.println("2 timesheets = (" + timesheets.getIds().get(element)
//									+ "on " + TimeDate.convertToDate(timesheets.getCreatedDates().get(element))
									+ " on " + TimeDate.convertToDate(timesheets.getEntryDates().get(element))
									+ ") " + timesheets.getPayableRates().get(element)
									+ " - " + timesheets.getBillableAmounts().get(element)
									+ " from " + timesheets.getUserNames().get(element)
									+ " ID " + timesheets.getUserIds().get(element)
									);
						}

//						System.out.println(this.users.get(element).getName() + " with a rate of " + 
//								this.users.get(element).getPayableRate());
						this.users.get(element).setAmount(amount);
						this.users.get(element).setPayableRate(
								String.valueOf(this.users.get(element).getPayableRate()));
//						this.users.get(element).setPayableRate(timesheets.getPayableRates().get(index));
						this.users.get(element).setHours(hours);
					}
				}
				
				if (!found)
				{
					int size = this.users.size();
					
					this.users.add(new EDTUsers());
					this.users.get(size).setName(name);
					
					if ("Lisa Chapin".equals(timesheets.getUserNames().get(index)))
					{
						System.out.println("3 timesheets = (" + timesheets.getIds().get(index)
								+ " [" + name + "] ("
								+ "created on " + TimeDate.convertToDate(timesheets.getCreatedDates().get(index))
								+ " for " + TimeDate.convertToDate(timesheets.getEntryDates().get(index))
								+ ") " + timesheets.getPayableRates().get(index)
								+ " / " + timesheets.getBillableAmounts().get(index)
								+ " / " + timesheets.getHourlyRates().get(index)
								+ " / " + timesheets.getPayableRates().get(index)
								+ " from " + timesheets.getUserNames().get(index)
								+ " ID " + timesheets.getUserIds().get(index)
								);
					}
					
					this.users.get(size).setAmount(amount);
					this.users.get(size).setPayableRate(timesheets.getPayableRates().get(index));
					this.users.get(size).setHours(hours);
					
					this.setBillableAttributes(this.users.get(size));
				}
			}
			
			oldName = name;
		}
	}

	public void setBillableAttributes(EDTUsers user)
	{
		if (!user.alreadySet())
		{
			B4TUsers employees = new B4TUsers("");
			
			int spaceIndex = user.getName().indexOf(" ");
			
			String fname = user.getName().substring(0, spaceIndex),
					lname = user.getName().substring(spaceIndex + 1);
					
			boolean found = false;
			
			for (int index = 0; (!found) && (index < employees.getIds().size()); index++)
			{
				if (found = (fname.equals(employees.getFnames().get(index)) && lname.equals(employees.getLnames().get(index))))
				{
					if (null != employees.getBilligRates().get(index))
						user.setBillingRate(Double.valueOf(employees.getBilligRates().get(index)));
					
					if (null != employees.getOvertimeRates().get(index))
						user.setOvertimeRate(Double.valueOf(employees.getOvertimeRates().get(index)));
					
					if (null != employees.getDoubleRates().get(index))
						user.setDoubleRate(Double.valueOf(employees.getDoubleRates().get(index)));
					
//					if (null != employees.getPayableRates().get(index))
//						user.setPayableRate(employees.getPayableRates().get(index));
					
					if (null != employees.getPayableRateOvertime().get(index))
						user.setPayableRateOvertime(employees.getPayableRateOvertime().get(index));
				}
			}
		}
	}
	
	public static double getTimesheetAmount(String project)
	{
		double amount = 0;
		
		String prefs = "";
		
		if (null != project)
			prefs = prefs + "?$filter=projectName%20eq%20" + B4TProjects.convertProjectName(project);
		else
			amount = -1;
		
		if (0 <= amount)
		{
			prefs = prefs + "&$orderby=userName";
			
			B4TTimesheets timesheets = new B4TTimesheets(prefs);
			
			for (int index = 0; index < timesheets.getIds().size(); index++)
			{
				amount += Double.parseDouble(timesheets.getLaborTimes().get(index)) *
						Double.parseDouble(timesheets.getBillableAmounts().get(index));
			}
		}
		
		return (amount);
	}

	public double getTimesheetAmount()
	{
		return this.timesheetAmount;
	}

	public void setTimesheetAmount(double timesheetAmount)
	{
		this.timesheetAmount = timesheetAmount;
	}

	public ArrayList<EDTUsers> getUsers()
	{
		return this.users;
	}
}
