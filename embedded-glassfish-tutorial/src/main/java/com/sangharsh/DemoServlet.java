package com.sangharsh;
 
import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
@WebServlet(name = "demoServlet", urlPatterns = "/demo-servlet")
public class DemoServlet extends HttpServlet {
	@EJB
	private MyServiceEJB myServiceEJB;
 
	@Override
	protected void doGet(final HttpServletRequest req,
			final HttpServletResponse res) throws ServletException, IOException {
		res.getWriter().append(myServiceEJB.printDate());
	}
 
}