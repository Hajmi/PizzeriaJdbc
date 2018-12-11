package pizzeriaBDD.exception;

public class SavePizzaException extends StockageException {
	
	private static final long serialVersionUID = 1L;
	

	public SavePizzaException() {
		
	}
	
	public SavePizzaException(String msg) {
		super(msg);
	}
}
