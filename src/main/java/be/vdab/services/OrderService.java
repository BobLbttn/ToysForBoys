package be.vdab.services;

import java.util.List;

import be.vdab.entities.Order;
import be.vdab.repositories.OrderRepository;

public class OrderService extends AbstractService {
	private OrderRepository or = new OrderRepository();
	
	public List<Order> findWhenNotShippedOrCancelled() {
		return or.findWhenNotShippedOrCancelled();
	}
}
