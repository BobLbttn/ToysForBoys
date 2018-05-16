package be.vdab.exceptions;

public class UnshippedOrderException extends RuntimeException{
	private final static long serialVersionUID = 1L;
	
	public UnshippedOrderException() {
		super();
	}

	public UnshippedOrderException(String message) {
		super(message);
	}

}
