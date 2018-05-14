package be.vdab.servlets;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.vdab.services.OrderService;
import be.vdab.util.StringUtils;

/**
 * Servlet implementation class DetailOrderServlet
 */
@WebServlet("/detail.html")
public class DetailOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/detail.jsp";
	private static transient OrderService os = new OrderService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getQueryString() != null) {
			String id = request.getParameter("id");
			if (StringUtils.isLong(id)) {
				os.findByOrderId(Long.parseLong(id))
								.ifPresent(order-> request.setAttribute("order",order));
			} 
			else {
				request.setAttribute("fouten",
						Collections.singletonMap("id", "id onbekend"));
			}
		}

		request.getRequestDispatcher(VIEW).forward(request, response);
	}

}
