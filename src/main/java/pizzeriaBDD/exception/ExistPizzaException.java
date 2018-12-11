package pizzeriaBDD.exception;

public class ExistPizzaException extends StockageException { 
		
		private static final long serialVersionUID = 1L;

		public ExistPizzaException () {
			
		}
		
		public ExistPizzaException (String msg) {
			super(msg);
		}



}
