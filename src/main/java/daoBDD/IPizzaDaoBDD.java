package daoBDD;

import java.util.List;

import model.Pizza;
import pizzeriaBDD.exception.DeletePizzaException;
import pizzeriaBDD.exception.ExistPizzaException;
import pizzeriaBDD.exception.SavePizzaException;
import pizzeriaBDD.exception.StockageException;
import pizzeriaBDD.exception.UpdatePizzaException;


public interface IPizzaDaoBDD {
	public interface IPizzaDao {
		
		List<Pizza> findAllPizzas();
		void updatePizza(String codePizza, Pizza pizza) throws UpdatePizzaException;
		Pizza findPizzaByCode(String codePizza) throws ExistPizzaException;
		boolean isPizzaExists(String codePizza) throws ExistPizzaException;
		void addPizza(Pizza pizza) throws SavePizzaException;
		void deletePizza(String codePizza) throws DeletePizzaException ;

}


}
