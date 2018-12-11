package pizzeriaBDD.exception;

public class StockageException extends Exception {

	private static final long serialVersionUID = 1L;
	public StockageException() {

	}
	public StockageException(String msg) {
		super(msg);
	}
}
