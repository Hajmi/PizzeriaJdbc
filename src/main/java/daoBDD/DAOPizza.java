package daoBDD;

import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;

import pizzeriaBDD.exception.DeletePizzaException;
import pizzeriaBDD.exception.ExistPizzaException;
import pizzeriaBDD.exception.SavePizzaException;
import pizzeriaBDD.exception.StockageException;
import pizzeriaBDD.exception.UpdatePizzaException;
import model.CategoriePizzaBDD;
import model.Pizza;;


/**
*
* Classe DAOEditeur permettant de se connecter à la base de données pizzeria afin d'accéder à la table pizza
*
* @author Samir Benakcha
* @since 07/12/2018
*/
public class DAOPizza implements IPizzaDaoBDD {
	
/*ArrayList<Pizza> pizzas = new ArrayList<Pizza>();
	
   public static void main(String[] args) 
	
	{
	DAOPizza dao = new DAOPizza();
	dao.findAllPizzas();
	dao.affichePizza();
	try {
		dao.findPizzaByID(8);
	} catch (StockageException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	*/
	public void affichePizza(ArrayList<Pizza> pizzas) {
		for (Pizza pizza : pizzas) {
			System.out.println(pizza);
		}
	}
	/**
	 * Méthode pour récupérer toutes les pizzas à partir de la base de données pizzeria table pizza et categorie
	 * @throws SQLException 
	 */
	public ArrayList<Pizza> findAllPizzas() {
		
		ArrayList<Pizza> pizzas = new ArrayList<Pizza>();
		Connection conn = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try{
			conn = ConnectionPizza.getConnection();
			statement = conn.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM Pizza JOIN categorie ON  pizza.ID_CATEGORIE = categorie.ID ");
	
			ResultSetMetaData resultMetaData = resultSet.getMetaData();
			
			System.out.println("\r\n//****************Pizza****************//");

			for (int i = 1; i <= resultMetaData.getColumnCount(); i++){
				System.out.print("\t" + resultMetaData.getColumnName(i).toUpperCase() + "\t");
			}
			System.out.println();

			while (resultSet.next()){
				Pizza pizza = new Pizza();
				pizza.setId(resultSet.getInt("id"));
				pizza.setCode(resultSet.getString("code"));
				pizza.setDesignation(resultSet.getString("designation"));
				pizza.setPrix(resultSet.getDouble("prix"));
				pizza.setCategoriePizza(new CategoriePizzaBDD(resultSet.getInt("categorie.id"), resultSet.getString("categorie.nom")));
				System.out.print("\t" + resultSet.getInt("id") 
				+ "\t\t" + resultSet.getString("code")
				+ "\t\t" + resultSet.getString("designation")
				+ "\t\t" + resultSet.getDouble("prix")
				+ "\t\t" + resultSet.getInt("id_categorie")
				+ "\t\t\t" + resultSet.getInt("categorie.id")
				+ "\t\t" + resultSet.getString("categorie.nom")
				+ "\r\n");
				pizzas.add(pizza);
			}
			System.out.println("\r\n//***************************************//");	
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			
				try {
					resultSet.close();
					statement.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		return pizzas;
		
	}
	
	/**
	 * Méthode boolean permettant de savoir si une pizza existe dans la BDD
	 * @param id de la pizza
	 * @return boolean exist
	 * @throws StockageException 
	 */
	public boolean isPizzaExists(int id) throws ExistPizzaException {
		
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		boolean exist = true ;
		String query ;
		
		try{
			conn = ConnectionPizza.getConnection();
			query = "Select * from Pizza JOIN categorie ON pizza.id_categorie = categorie.id where pizza.id =?" ;
			statement = conn.prepareStatement(query);
			statement.setInt(1, id );
			resultSet = statement.executeQuery();
			if(!resultSet.isBeforeFirst()) {
				exist = false;
			}
		}catch (Exception e){
			e.printStackTrace();
			throw new ExistPizzaException("Cette pizza n'existe pas");
		}
		
		finally {
			
			try {
				resultSet.close();
				statement.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return exist ;
		
	}
	
	/**
	 * Méthode pour trouver une pizza dans la BDD en passant son ID en parmaètre
	 * @param id
	 * @return une pizza avec le ID passé en param
	 * @throws StockageException
	 */
	public Pizza findPizzaByID(int id) throws ExistPizzaException {
		
		Connection conn = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		Pizza pizza = new Pizza();
		
		try{
			conn = ConnectionPizza.getConnection();
			statement = conn.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM Pizza JOIN categorie ON  pizza.ID_CATEGORIE = categorie.ID where pizza.id = "+id);
			
			ResultSetMetaData resultMetaData = resultSet.getMetaData();
			
			while(resultSet.next()) {
				
				pizza.setId(resultSet.getInt("id"));
				pizza.setCode(resultSet.getString("code"));
				pizza.setDesignation(resultSet.getString("designation"));
				pizza.setPrix(resultSet.getDouble("prix"));
				pizza.setCategoriePizza(new CategoriePizzaBDD(resultSet.getInt("categorie.id"), resultSet.getString("categorie.nom")));
				
			}
			System.out.println(pizza.toString());
		}
		catch (Exception e){
				e.printStackTrace();
				throw new ExistPizzaException("Cette pizza n'existe pas");
		}
		finally{
			try {
				resultSet.close();
				statement.close();
				conn.close();
			}
			catch (SQLException e){

				e.printStackTrace();
			}
		}
		return pizza;
			
	}
	/**
	 * Méthode pour rajouter une pizza à la base de données
	 * @param pizza
	 * @throws Exception 
	 */
	public void addPizza(Pizza pizza) throws StockageException {
		
		Connection conn = null;
		PreparedStatement statement = null;
		
		boolean controleOk = false;
		String query = "" ;
		try {
			pizza.dataController();
			controleOk = true;
		}
	
		catch (StockageException e) 
		{
			System.err.println(e.getMessage());
		}
		
		try{
			conn = ConnectionPizza.getConnection();
			query = "INSERT INTO pizza(CODE,DESIGNATION, PRIX, ID_CATEGORIE)VALUES(?,?,?,?)";
			statement = conn.prepareStatement(query);
			statement.setString(1, pizza.getCode());
			statement.setString(2, pizza.getDesignation());
			statement.setDouble(3, pizza.getPrix());
			statement.setInt(4, pizza.getCategoriePizza().getId());
			statement.executeUpdate();
		}catch (Exception e){
			e.printStackTrace();
		}
		
		finally {
			try {
				statement.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	/**
	 * Méthode pour supprimer une pizza dans la BDD pizza en passant par l'ID
	 * @param id
	 * @throws DeletePizzaException
	 */
	public void deletePizza(int id) throws DeletePizzaException {
		
		Connection conn = null;
		Statement statement = null;
		boolean controleOk = false;
		String query = "" ;
		
		try {
			controleOk = isPizzaExists(id);
		} catch (ExistPizzaException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println(e1.getMessage());
		}
		if (controleOk) {
			
			
			try{
				conn = ConnectionPizza.getConnection();
				statement = conn.createStatement();
				query = "DELETE FROM pizza WHERE id="+id;
				statement.executeUpdate(query);
				System.out.println("Pizza supprimé");
				
			}catch (Exception e){
				e.printStackTrace();
			}
			
			finally {
				try {
					statement.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
		}
	}
	
	
	/**
	 * Méthode pour modifier une pizza dans la BDD pizza en passant par son ID
	 * @param id
	 * @param pizza
	 * @throws UpdatePizzaException
	 */
	public void updatePizza(int id, Pizza pizza) throws UpdatePizzaException {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement statement = null;
		String query = "" ;
		
		boolean controleOk = false;
		boolean codeBon = false;
		
		try {
			controleOk = isPizzaExists(id);
		} catch (ExistPizzaException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println(e1.getMessage());
		}
		if (controleOk == true) {
			
			try {
				pizza.dataController();
				codeBon = true;
			}
			catch (StockageException e) {
				String msg = "MODIFICATION DE LA PIZZA => " + id + "\r\n";
				msg += "La pizza à modifier n'existe pas \r\n"+e.getMessage();
				throw new UpdatePizzaException(msg);
			}
			
			if (codeBon == true) {
				try {

				conn = ConnectionPizza.getConnection();
				query = "UPDATE pizza SET CODE = ?, DESIGNATION = ?, PRIX = ?, ID_CATEGORIE = ? WHERE pizza.id ="+id ;
				statement = conn.prepareStatement(query);
				statement.setString(1, pizza.getCode());
				statement.setString(2, pizza.getDesignation());
				statement.setDouble(3, pizza.getPrix());
				statement.setInt(4, pizza.getCategoriePizza().getId());
				statement.executeUpdate();
				System.out.println("Mise à jour réussie");
				
				}catch (Exception e) {
					e.printStackTrace();
				
				}finally {
					try {
						statement.close();
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
					
			}
			else 
			{
				String msg = "MODIFICATION DE LA PIZZA => " + id + "\r\n";
				msg += "La pizza à modifier n'existe pas \r\n";
				throw new UpdatePizzaException(msg);
			}
		
		}
	}
	

}
