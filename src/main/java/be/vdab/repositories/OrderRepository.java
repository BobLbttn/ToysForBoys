package be.vdab.repositories;

import java.util.List;
import java.util.Optional;

import be.vdab.entities.Order;

public class OrderRepository extends AbstractRepository {

	public Optional<Order> read(long id) {
		return Optional.ofNullable(getEntityManager().find(Order.class, id));
	}

	public void create(Order order) {
		getEntityManager().persist(order);
	}

	public void delete(long id) {
		read(id).ifPresent(order -> getEntityManager().remove(order));
	}
	
	public List<Order>findWhenNotShippedOrCancelled()
	{
		return getEntityManager().createNamedQuery("Order.findWhenNotShippedOrCancelled", Order.class)
				.setHint("javax.persistence.loadgraph", getEntityManager().createEntityGraph("Order.metCustomer"))
				.getResultList();
	}

	public Optional<Order> findByOrderId(long id) {
		return Optional.ofNullable(getEntityManager().createNamedQuery("Order.findByOrderId", Order.class)
				.setParameter("id", id)
				.setHint("javax.persistence.loadgraph", getEntityManager().createEntityGraph("Order.metCustomerEnLand"))
				.getSingleResult());
	}
}
