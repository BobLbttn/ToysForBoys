package be.vdab.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;

import be.vdab.entities.Order;
import be.vdab.exceptions.UnshippedOrderException;
import be.vdab.repositories.OrderRepository;

public class OrderService extends AbstractService {
	private OrderRepository or = new OrderRepository();
	
	public List<Order> findWhenNotShippedOrCancelled() {
		return or.findWhenNotShippedOrCancelled();
	}

	public Optional<Order> findByOrderId(long id) {
		return or.findByOrderId(id);
	}
	
	public void shipOrder(long id) throws Exception{
		beginTransaction();
		try {
			or.readWithLock(id).ifPresent(order -> {order.updateStatusToShipped();order.updateQuantityInOrderAndStock();} );
			commit();
		}
		catch (PersistenceException e) {
			rollback();
			throw new Exception("database error");
		}
		catch (UnshippedOrderException e) {
			rollback();
			throw new Exception("unshipped");
		}
	}
}
