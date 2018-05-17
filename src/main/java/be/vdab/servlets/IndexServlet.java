package be.vdab.servlets;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

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
	private static final String REDIRECT_URL = "%s/unshipped.html";
	private static transient OrderService os = new OrderService();
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
		request.setAttribute("orders", os.findWhenNotShippedOrCancelled());
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if (req.getParameterValues("id") != null) {

			List<Order> orders = new LinkedList<>();
			List<Order> failedOrders = new LinkedList<>();
			String[] ids = req.getParameterValues("id");

			for (int i=0; i<ids.length; i++) {
				long id = Long.parseLong(ids[i]);
				try {
					os.shipOrder(id);
				}
				catch (Exception e) {
					if (e.getMessage().equalsIgnoreCase("unshipped")) {
						os.findByOrderId(id).ifPresent(order -> orders.add(order));
						
					}
					else {
						os.findByOrderId(id).ifPresent(order -> failedOrders.add(order));
					}
						
				}
			}
			if (orders.isEmpty() && failedOrders.isEmpty()) {
				req.setAttribute("orders", os.findWhenNotShippedOrCancelled());
				req.getRequestDispatcher(VIEW).forward(req, resp);
			}
			else {
				req.setAttribute("aantalUnshipped", orders.size());
				req.setAttribute("fouten", failedOrders);
				req.setAttribute("aantalfouten", failedOrders.size());
				resp.sendRedirect(resp.encodeRedirectURL(String.format(REDIRECT_URL, req.getContextPath())));
			}
		}
		req.setAttribute("orders", os.findWhenNotShippedOrCancelled());
		req.getRequestDispatcher(VIEW).forward(req, resp);
			
	}

}
