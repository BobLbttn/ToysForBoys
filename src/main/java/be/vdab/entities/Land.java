package be.vdab.entities;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="countries")
public class Land implements Serializable{
	private final static long serialVersionUID=1L;
	
	protected Land() {};
	
	@Id
	private long id;
	private String name;
	
	@Version
	private int versie;

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	
}
