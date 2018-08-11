package com.edt.profitability;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edt.b4t.util.TimeDate;

/**
 * Servlet implementation class EDTPPReport
 */
@WebServlet("/EDTReport")

public class EDTReport extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EDTReport() 
    {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		Enumeration<String> parameterNames = request.getParameterNames();
		
		String paramName = null,
				startDate = null,
				endDate = null;
		
        while (parameterNames.hasMoreElements())
        {
        	paramName = parameterNames.nextElement();
        	
        	String[] paramValues = request.getParameterValues(paramName);
        	
        	if("startDate".equals(paramName))
        		startDate = paramValues[0];
        	
        	else if ("endDate".equals(paramName))
        		endDate = paramValues[0];
        }
        
        response.setContentType("text/html");
        
        if (0 == startDate.length())
        	startDate = TimeDate.convertDate(TimeDate.todayMinus(90));
        
        if (0 == endDate.length())
        	endDate = TimeDate.convertDate(TimeDate.todayMinus(14));
        
        EDTB4TProfitability profitability = new EDTB4TProfitability();
        profitability.buildReport(response.getWriter(), startDate, endDate);
	}
}
