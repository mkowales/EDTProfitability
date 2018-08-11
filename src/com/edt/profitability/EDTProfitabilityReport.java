package com.edt.profitability;

import java.io.PrintWriter;
import java.util.ArrayList;

public class EDTProfitabilityReport
{
	private PrintWriter out = null;
	
	private boolean DEBUGGER = false;
	
	double totalBudget = 0,
			totalProfit = 0;
	
	public EDTProfitabilityReport(PrintWriter pw, int rows)
	{
		if (null != pw)
		{
			this.out = pw;
			this.out.println("<html>");
			this.out.println("<head>");
			this.out.println("<meta charset=\"UTF-8\">");
			this.out.println("<link rel=\"icon\" href=\"https://edtssl.eagledream-hosting.com/wp-content/uploads/2015/02/edt_fav_2.png\"/>");
			this.out.println("<title>EDT - Project Profitability</title>");
			this.out.println("<script src=\"https://www.w3schools.com/lib/w3.js\"></script>");
			this.out.println("<script type=\"text/javascript\" src=\"https://code.jquery.com/jquery-2.x-git.js\"></script>");		
			this.out.println("<script type=\"text/javascript\">");
			
			this.setJavaScript(rows);
			
			this.out.println("});");
			this.out.println("</script>");
			this.out.println("<style type=\"text/css\">");
			this.out.println(".mainmenu");
			this.out.println("{");
			this.out.println("text-align:center;");
			this.out.println("background-color:#FFFFFF;");
			this.out.println("font-family:Verdana;");
			this.out.println("font-size:12px;");
			this.out.println("font-weight:bold;");
			this.out.println("height:35px;");
			this.out.println("}");
			this.out.println(".submenu");
			this.out.println("{");
			this.out.println("text-align:right;");
			this.out.println("color:midnightblue;");
			this.out.println("background-color:white;");
			this.out.println("font-family:Verdana;");
			this.out.println("font-size:12px;");
			this.out.println("font-weight:bold;");
			this.out.println("margin-left:100px;");
			this.out.println("}");
			this.out.println("</style>");
			this.out.println("</head>");
			
			this.out.println("<body>");
			this.out.println("<center>");
			
			this.out.println("<div id=\"under\" style=\"left: 0px; top: 0px; position: relative\">");
			this.out.println("<img src=\"http://static.wixstatic.com/media/ec9883_3b4237ae544c4fb188e9162b3dbd48ea.jpg\" width=\"50%\" length=\"50%\">");
			this.out.println("<br>");
			this.out.println("</div>");
			
			this.out.println("<div id=\"top\" style=\"left: 250px; top: 20px; position: absolute\">");
			this.out.println("<img src=\"https://d3rdz1nqt7l291.cloudfront.net/wp-content/uploads/2015/02/edt_logo_white_2-e1454088284511.png?x59865\" width=\"50%\" length=\"50%\">");
			this.out.println("</div>");
			
			this.setTableHeader();
		}
	}
	
	private void setJavaScript(int rows)
	{
		this.out.println("$(document).ready(function()");
		this.out.println("{");
		
		for (int index = 0; index < rows; index++)
			this.out.println("$(\"#m" + index + "d\").hide();");
		
		for (int index = 0; index < rows; index++)
		{
			this.out.println("$(\"#m" + index + "\").click(function()");
			this.out.println("{");
			
			for (int show = 0; show < rows; show++)
				this.out.println((show == index) ? "$(\"#m" + show + "d\").show();" : "$(\"#m" + show + "d\").hide();");
			
			this.out.println("});");
		}
	}
	
	private void setTableHeader()
	{
		if (null != this.out)
		{
			this.out.println("<table border=\"1\" removed=#FFFFFF cellspacing=\"1\" cellpadding=\"10\">");
			this.out.println("<thead>");
			this.out.println("<tr>");
			
			this.out.println("<th bgcolor=\"midnightblue\"><font color=\"white\">Client</font></th>");
			this.out.println("<th bgcolor=\"midnightblue\"><font color=\"white\">Project</font></th>");
			this.out.println("<th bgcolor=\"midnightblue\"><font color=\"white\">Total Budget</font></th>");
			this.out.println("<th bgcolor=\"midnightblue\"><font color=\"white\">Gross Profit</font></th>");
			this.out.println("<th bgcolor=\"midnightblue\"><font color=\"white\">Gross Margin</font></th>");
			this.out.println("<th bgcolor=\"midnightblue\"><font color=\"white\">Created Date</font></th>");
			
			this.out.println("</tr>");
			this.out.println("</thead>");
		}
	}
	
	public void setDebugger(boolean test)
	{
		this.DEBUGGER = test;
	}
	
	public boolean getDebugger()
	{
		return this.DEBUGGER;
	}
	
	public void closeTable()
	{
		this.out.println("</table>");
		this.out.println("<hr>");
		this.out.println("<input type=\"button\" value=\"Back\" onclick=\"window.history.back()\"/>");
		this.out.println("</center>");
		this.out.println("</body></html>");
		this.out.close();
	}
	
	public void tableTotalRow()
	{
		String fontColor = (0 < this.totalProfit) ? "white" : "black",
			   rowColor = (0 < this.totalProfit) ? "1E7B1E" : "FF0000";
		
		this.out.println("<tr bgcolor=\"#" + rowColor+ "\">");
		this.out.println("<td colspan=\"2\"></td>");
		this.out.println("<td align=\"right\"><font color=\"" + fontColor + "\"><b>" + 
				EDTProjects.dollarFormat.format(this.totalBudget) + "</b></font></td>");
		this.out.println("<td align=\"right\"><font color=\"" + fontColor + "\"><b>" + 
				EDTProjects.dollarFormat.format(this.totalProfit) + "</b></font></td>");
		this.out.println("<td align=\"center\" colspan=\"2\"><font color=\"" + fontColor + "\"><b>" + 
				EDTProjects.percentFormat.format(this.totalProfit / this.totalBudget) + "</b></font></td>");
//		this.out.println("<td>;nbsp</td>");
		this.out.println("</tr>");
	}
	
	public void tableMainRow(EDTProjects project, int row)
	{
		if (!project.getProjectName().isEmpty())
		{
			this.out.println("<tr id=\"m" + row + "\" class=\"mainmenu\">");
			this.out.println("<td>" +  project.getClientName() + "</td>");
			this.out.println("<td>" + project.getProjectName() + "</td>");
			
			System.out.println("getRevenue = " + this.totalBudget);
			this.totalBudget += project.getRevenue();
			this.out.println("<td align=\"right\">" + EDTProjects.dollarFormat.format(project.getRevenue()) + "</td>");
			
			System.out.println("getNetProfit = " + this.totalProfit);
			this.totalProfit += project.getNetProfit();
			this.out.println("<td align=\"right\">" + EDTProjects.dollarFormat.format(project.getNetProfit()) + "</td>");
			
			this.out.println("<td align=\"right\">" + EDTProjects.percentFormat.format(project.getNetProfitMargin()) + "</td>");
			this.out.println("<td align=\"right\">" + project.getCreatedDate() + "</td>");
			this.out.println("</tr>");
		}
	}
	
	public void tableDetailsRow(EDTProjects project, int row)
	{
		this.out.println("<tr id=\"m" + row + "d\" class=\"submenu\">");
		this.out.println("<td colspan=\"6\">");
		this.out.println("<table cellpadding=\"10\">");

		this.out.println("<tr><td>Total Hours Logged:</td><td>" + EDTProjects.numericFormat.format(project.getTotalTime()) + "</td></tr>");
		this.out.println("<tr><td>Actual Blended Hourly Rate:</td><td>" + EDTProjects.dollarFormat.format(project.getBlendedHoulrRate()) + "</td></tr>");
		this.out.println("<tr><td>Total Expenses:</td><td>" + EDTProjects.dollarFormat.format(project.getTotalExpenses()) + "</td></tr>");
		this.out.println("<tr><td>Total Cost (Loaded):</td><td>" + EDTProjects.dollarFormat.format(project.getTotalLoadedCost()) + "</td></tr>");
		this.out.println("<tr><td>Actual Blended Hourly Cost (Loaded):</td><td>" + EDTProjects.dollarFormat.format(project.getAverageHourlyLoadedCost()) + "</td></tr>");
		
		if (project.isFixedFee())
			this.out.println("<tr><td>Fixed Fee Project</td></tr>");
		else
			this.out.println("<tr><td>Hourly Fee Project</td></tr>");
		
		this.out.println("</table>");
		
		this.out.println("<table cellpadding=\"6\">");
		this.out.println("<thead>");
		this.out.println("<tr>");
		this.out.println("<th align=\"center\" bgcolor=\"green\"><font color=\"white\">Resource</font></th>");
		this.out.println("<th align=\"center\" bgcolor=\"green\"><font color=\"white\">Total Hours</font></th>");
		this.out.println("<th align=\"center\" bgcolor=\"green\"><font color=\"white\">Loaded Hourly Cost</font></th>");
		this.out.println("<th align=\"center\" bgcolor=\"green\"><font color=\"white\">Total Cost (Loaded)</font></th>");
		this.out.println("</tr>");
		this.out.println("</thead>");
		
		ArrayList<EDTUsers> users = project.getUsers();
		
		for (int index = 0; index < users.size(); index++)
		{
			this.out.println("<tr><td><i>" + users.get(index).getName() + "</i></td><td>"
					+ EDTProjects.numericFormat.format(users.get(index).getHours()) + "</td><td>"
//					+ EDTProjects.dollarFormat.format(users.get(index).getBillingRate()) + "</td><td>"
					+ EDTProjects.dollarFormat.format(users.get(index).getPayableRate()) + "</td><td>"
					+ EDTProjects.dollarFormat.format(users.get(index).getHours() * users.get(index).getPayableRate()) 
					+ "</td><td></tr>"
					);
		}

		this.out.println("</table>");
		this.out.println("</td>");
		this.out.println("</tr>");
	}

	public static void log(String str)
	{
		System.out.println(str);
	}
}
