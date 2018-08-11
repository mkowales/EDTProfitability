package com.edt.profitability;

public class EDTUsers
{
	private String name = "";
	
	private double amount = 0,
			hours = 0,
			loadedHourlyCost = 0,
					
			billingRate = 0,
			overtimeRate = 0,
			doubleRate = 0,
			
			payableRate = 0,
			payableRateOvertime = 0;

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public double getAmount()
	{
		return this.amount;
	}

	public void setAmount(double amount)
	{
		this.amount += amount;
	}

	public double getHours()
	{
		return this.hours;
	}

	public void setHours(double hours)
	{
		this.hours += hours;
	}

	public double getBillingRate()
	{
		return this.billingRate;
	}

	public void setBillingRate(double billingRate)
	{
//		if ("Zachary O'Dell".equals(this.name))
//			System.out.println("billingRate = " + billingRate);
		
		this.billingRate = billingRate;
	}

	public double getOvertimeRate()
	{
		return this.overtimeRate;
	}

	public void setOvertimeRate(double overtimeRate)
	{
		this.overtimeRate = overtimeRate;
	}

	public double getDoubleRate()
	{
		return this.doubleRate;
	}

	public void setDoubleRate(double doubleRate)
	{
		this.doubleRate = doubleRate;
	}

	public double getLoadedHourlyCost()
	{
		return this.loadedHourlyCost;
	}

	public void setLoadedHourlyCost(double loadedHourlyCost)
	{
		this.loadedHourlyCost = loadedHourlyCost;
	}

	public void setPayableRate(String payableRate)
	{
		double rate = Double.valueOf(payableRate);
		
		if ("null".equals(payableRate))
			this.payableRate = 0;
		
		else if (this.payableRate < rate)
			this.payableRate = rate;
		
//		if ("Zachary O'Dell".equals(this.name))
//			System.out.println("payableRate = " + this.payableRate + " / rate = " + rate);
	}

	public double getPayableRateOvertime()
	{
		return this.payableRateOvertime;
	}

	public void setPayableRateOvertime(double payableRateOvertime)
	{
		this.payableRateOvertime = payableRateOvertime;
	}
	
	public void setPayableRateOvertime(String payableRateOvertime)
	{
		if ("null".equals(payableRateOvertime))
			this.payableRateOvertime = 0;
		else
			Double.valueOf(payableRateOvertime);
	}
	
	public boolean alreadySet()
	{
		return ((this.billingRate > 0) || 
				(this.overtimeRate > 0) || 
				(this.doubleRate > 0) || 
				(this.loadedHourlyCost > 0) || 
				(this.payableRateOvertime > 0)
				);
	}

	public double getLoadedCost()
	{
		return  (0 != this.loadedHourlyCost) ? this.loadedHourlyCost * this.hours : 0;
	}

	public double getBlendedHourlyRate()
	{
		return (0 != this.loadedHourlyCost) ? this.loadedHourlyCost / this.hours : 0;
	}

	public double getPayableRate()
	{
		return this.payableRate;
	}
}
