package model;

import pizzeriaBDD.exception.StockageException;

//import fr.pizzeria.exception.StockageException;
//import fr.pizzeria.model.CategoriePizzaEnum;

/**
 * 
 * @author Samir BENAKCHA
 * @since 07/12/2018
 *
 */
public class Pizza {
	/**
	 * Création de la classe Pizza 
	 * 
	 */
	public int id;
	public String code;
	public String designation;
	public Double prix;
	public static int count = 0;
	public CategoriePizzaBDD categoriePizza; 
	

	public static final Double PRIX_MAX = 100.0;
	public static final Double PRIX_MIN = 5.0;
	public static final int LONG_CODE = 3;
	public static final int MAX_NAME = 15;
	
	/**
	 * Mise en place d'un constructeur pour la classe Pizza
	 * @param id est l'identifiant 
	 * @param code c'est le code de chaque pizza
	 * @param designation c'est le nom de la pizza
	 * @param prix désigne le prix de la pizza
	 */
	public Pizza(String code, String designation, Double prix, CategoriePizzaBDD  categoriePizza) {
		
		this.id = count++;
		this.code = code;
		this.designation = designation;
		this.prix = prix;
		this.categoriePizza = categoriePizza; 
	}
	/**
	 * Constructeur vide
	 */
	public Pizza() {
		
	}
	/**
	 * Retourne l'identifiant de la pizza désignée
	 * @return l'identifiant de chaque pizza
	 */
	public int getId() {
		return id;
	}
	/**
	 * Met à jour l'identifiant de la pizza
	 * @param id est l'identifiant de la pizza
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Retourne le code de la pizza
	 * @return Retourne le code de la pizza
	 */
	public String getCode() {
		return code;
	}
	/**
	 * Met à jour le code de la pizza
	 * @param code est le code de la pizza
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * Retourne la designation de la pizza
	 * @return retourne la désignation de la pizza
	 */
	public String getDesignation() {
		return designation;
	}
	/**
	 * Met à jour le nom de la pizza
	 * @param designation est le nom de la pizza
	 */
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	/**
	 * Retourne le prix de la pizza
	 * @return retourne le prix de la pizza
	 */
	public Double getPrix() {
		return prix;
	}
	/**
	 * Met à jour le prix de la pizza
	 * @param prix est le prix de la pizza
	 */
	public void setPrix(Double prix) {
		this.prix = prix;
	}
	/**
	 * Retourne la categorie de la pizza
	 * @return la categorie de la pizza
	 */
	public CategoriePizzaBDD getCategoriePizza() {
		return categoriePizza;
	}
	
	/**
	 * Met à jour la categorie de la pizza
	 * @param categoriePizza
	 */
	public void setCategoriePizza(CategoriePizzaBDD categoriePizza) {
		this.categoriePizza = categoriePizza;
	}
	
	
	public String toString () {
		return " "+this.code+" -> "+this.designation+" ("+this.prix+"€)"+" Catégorie : "+this.categoriePizza.getNom();
	}
	

	
	public void dataController() throws StockageException {
		
		String message = "";
		boolean categorieB = false;
		
		if( code == null|| code.trim().length() != LONG_CODE ) {
			message += "Veuillez saisir un autre code avec 3 caractères svp. \r\n";
		}
		if(designation.trim().length() < 1 || designation == null || designation.trim().length() > MAX_NAME) {
			message += "Veuillez saisir un autre nom avec 15 charactères max. \r\n";
		}
		if(prix < PRIX_MIN || prix > PRIX_MAX) {
			message += "Veuillez saisir un prix compris entre "+PRIX_MIN+" et "+PRIX_MAX+"\r\n";
		}
		
		if (categoriePizza.getId() > 4 	|| 	categoriePizza.getId() < 1) {
			categorieB = false;
			categoriePizza.setNom("AUTRE"); 
			categoriePizza.setId(4);
			message += "catégorie par défaut. \r\n";
		}
		
			 
		if (message.trim().length() > 0) {
			throw new StockageException(message);
		}
	}
	
}
