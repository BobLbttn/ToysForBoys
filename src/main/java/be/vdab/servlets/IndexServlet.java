package be.vdab.servlets;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.vdab.entities.Order;
import be.vdab.services.OrderService;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/index.html")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/index.jsp";
	private static transient OrderService os = new OrderService();
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
		try {
			request.setAttribute("orders", os.findWhenNotShippedOrCancelled());
		}
		catch (Exception e){
			String errmsg = e.getMessage();
			request.setAttribute("fout",
					 Collections.singletonMap("msg", errmsg));
		}
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if (req.getParameterValues("id") != null) {

			List<Order> unshippedOrders = new LinkedList<>();
			Map<String, String> failedOrders = new HashMap<>();
			String[] ids = req.getParameterValues("id");

			for (int i=0; i<ids.length; i++) {
				long id = Long.parseLong(ids[i]);
				try {
					os.shipOrder(id);
				}
				catch (Exception e) {
					if (e.getMessage().equalsIgnoreCase("unshipped")) {
						os.findByOrderId(id).ifPresent(order -> unshippedOrders.add(order));
						
					}
					else {
						failedOrders.put(ids[i], e.getMessage());
					}
						
				}
			}
			if (!unshippedOrders.isEmpty() || !failedOrders.isEmpty()) {
				req.setAttribute("unshippedorders", unshippedOrders);
				req.setAttribute("aantalUnshipped", unshippedOrders.size());
				req.setAttribute("failedorders", failedOrders);
				req.setAttribute("aantalfouten", failedOrders.size());
			}
		}
		try {
			req.setAttribute("orders", os.findWhenNotShippedOrCancelled());
		}
		catch (Exception e){
			String errmsg = e.getMessage();
			req.setAttribute("fout",
					 Collections.singletonMap("msg", errmsg));
		}
		req.getRequestDispatcher(VIEW).forward(req, resp);
	}
}
