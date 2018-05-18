package be.vdab.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedAttributeNode;
import javax.persistence.Table;
import javax.persistence.Version;

import be.vdab.enums.Status;
import be.vdab.exceptions.UnshippedOrderException;
import be.vdab.valueObjects.OrderDetail;

@Entity
@Table(name="orders")
@NamedEntityGraphs({
@NamedEntityGraph(name = "Order.metCustomer", attributeNodes = @NamedAttributeNode("customer")),
@NamedEntityGraph(name = "Order.metCustomerEnLand", 
							attributeNodes = @NamedAttributeNode(value = "customer", subgraph = "metLand"),
							subgraphs = @NamedSubgraph(name="metLand", attributeNodes=@NamedAttributeNode("country")))
})
public class Order implements Serializable {
	private final static long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private LocalDate orderDate;
	private LocalDate requiredDate;
	private LocalDate shippedDate;
	private String comments;

	@ManyToOne(fetch=FetchType.LAZY, optional = false) 
	@JoinColumn(name = "customerId")
	private Customer customer;
	
	@ElementCollection
	@CollectionTable(name = "orderdetails", joinColumns=@JoinColumn(name = "orderId"))
	private Set<OrderDetail> orderdetails;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Version
	private int version;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public LocalDate getRequiredDate() {
		return requiredDate;
	}

	public void setRequiredDate(LocalDate requiredDate) {
		this.requiredDate = requiredDate;
	}

	public LocalDate getShippedDate() {
		return shippedDate;
	}

	public void setShippedDate(LocalDate shippedDate) {
		this.shippedDate = shippedDate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer c) {
		this.customer = c;
	}

	public Status getStatus() {
		return status;
	}

	public Set<OrderDetail> getOrderdetails() {
		return orderdetails;
	}

	public void setOrderdetails(Set<OrderDetail> orderdetails) {
		this.orderdetails = orderdetails;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getVersion() {
		return version;
	}
	
	public void updateStatusToShipped() {
		setStatus(Status.SHIPPED);
		setShippedDate(LocalDate.now());
	}
	
	public void updateQuantityInOrderAndStock() throws UnshippedOrderException {
		for (Iterator<OrderDetail> i = orderdetails.iterator(); i.hasNext(); ) {
			OrderDetail od = i.next();
			long q_ordered = od.getQuantityOrdered();
			long q_inOrder = od.getProduct().getQuantityInOrder();
			long q_inStock = od.getProduct().getQuantityInStock();
			
			if (q_ordered <= q_inStock) {
				q_inStock -= q_ordered;
				q_inOrder -= q_ordered;
				od.getProduct().setQuantityInOrder(q_inOrder);
				od.getProduct().setQuantityInStock(q_inStock);
			}
			else
				throw new UnshippedOrderException("unshipped");
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
