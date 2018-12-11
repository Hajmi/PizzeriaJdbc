package com.PizzeriaBDD;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;



import daoBDD.DAOPizza;
import model.CategoriePizzaBDD;
import model.Pizza;
import pizzeriaBDD.exception.DeletePizzaException;
import pizzeriaBDD.exception.ExistPizzaException;
import pizzeriaBDD.exception.SavePizzaException;
import pizzeriaBDD.exception.StockageException;
import pizzeriaBDD.exception.UpdatePizzaException;
/**
 * @author Samir BENAKCHA
 * @since	11/12/2018
 * Cette application nous permet de gérer une  pizzeria avec une BDD
 */

public class PizzeriaAdminConsoleApp {
	/**
	 * Menu principal de la pizzeria
	 * 
	 * @param args
	 */
	public static void main(String[] args)  {
		// TODO Auto-generated method stub

		DAOPizza dao = new DAOPizza();
		
		menu();
		
		Integer choix = 0; // variable pour mémoriser le choix de l'utilisateur
		Scanner sc = new Scanner(System.in); // ouverture du scanner

		while (choix != 99) {
			
			choix = sc.nextInt(); //Integer.parseInt(sc.nextLine());
			// choix 1 : listes les pizzas disponible
			if (choix == 1) {
				System.out.println("Liste des pizzas\r\n");
				//listPizzas = dao.findAllPizzas();
				dao.findAllPizzas();
				System.out.println("");
				menu();
			}
			// choix 2 : ajouter une nouvelle pizza
			else if (choix == 2) {
					sc.nextLine();
					System.out.println("Ajout d'une nouvelle pizza\r\n");
					System.out.println("Veuillez saisir le code : \r\n");
					String codePizza = "";
					String nomPizza = "";
					Double prixPizza = 0.0;
					
					CategoriePizzaBDD categoriePizza = new CategoriePizzaBDD();
					codePizza = sc.nextLine();
					System.out.println("Veuillez saisir le nom : \r\n");
					nomPizza = sc.nextLine();
					System.out.println("Veuillez saisir le prix : \r\n");
					prixPizza = sc.nextDouble();
					System.out.println("Veuillez saisir le id catégorie : \r\n");
					sc.nextLine();
					
				try {
					int idCat = sc.nextInt();
					
					categoriePizza.setId(idCat);
					//categoriePizza.setNom("AUTRE");
					Pizza nouvellePizza = new Pizza(codePizza.toUpperCase(), nomPizza, prixPizza, categoriePizza);
					System.out.println(idCat+ " la");
					nouvellePizza.dataController();
					dao.addPizza(nouvellePizza);
					}
				catch(StockageException e1) {
					e1.printStackTrace();
					System.out.println(e1.getMessage());
					
					}
				
				menu();
					
			}
			// choix 3 : modifier une pizza en passant par son code
			else if (choix == 3) {
					
					sc.nextLine();
					String newCodePizza = "";
					String newNomPizza = "";
					Double newPrixPizza = 0.0;
					
					
					
					System.out.println("Mise à jour d'une pizza\r\n");
					System.out.println("Liste des pizzas\r\n");
					dao.affichePizza(dao.findAllPizzas());
					System.out.println("Veuillez choisir le ID de la pizza à modifier.");
					int choixId = sc.nextInt();

					sc.nextLine();
					CategoriePizzaBDD categoriePizza = new CategoriePizzaBDD();
					System.out.println("Veuillez saisir le nouveau  code : \r\n");
					newCodePizza = sc.nextLine();
					System.out.println("Veuillez saisir le nouveau nom : \r\n");
					newNomPizza = sc.nextLine();
					System.out.println("Veuillez saisir le nouveauc prix : \r\n");
					newPrixPizza = sc.nextDouble();
					System.out.println("Veuillez saisir le id de la catégorie : \r\n");
					int idCat = sc.nextInt();
					categoriePizza.setId(idCat);
					//categoriePizza.setNom("AUTRE");
					Pizza newPizza = new Pizza(newCodePizza,newNomPizza,newPrixPizza, categoriePizza);
					try {
						dao.updatePizza(choixId, newPizza);	
						}
					catch (UpdatePizzaException e)
						{
									e.printStackTrace();
									System.out.println(e.getMessage());
						}
				menu();
			}
			// choix 4 : supprimer une pizza en passant par son code
			else if (choix == 4) {
				try {
				sc.nextLine();
				System.out.println("Suppression d'une pizza\r\n");
				System.out.println("Liste des pizzas\r\n");
				dao.affichePizza(dao.findAllPizzas());
				System.out.println("Veuillez choisir l'ID de la pizza à supprimer.");
				int choixId = sc.nextInt();
				dao.deletePizza(choixId);
				}
				catch (DeletePizzaException e)
				{
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
				finally
				{
				menu();
				}
			}
			
			
			else if (choix == 99) {
				System.out.println("Au revoir");
				break;
			}
			
			else {
				System.out.println("Veuillez saisir un choix existant : ");
				menu();
			}
		}
		sc.close(); // Fermeture du scanner
		 
	}
	
	public static void menu()   {
		// Menu
		System.out.println("*****Pizzeria Administration*****");
		System.out.println("1.	Lister les pizzas");
		System.out.println("2.	Ajouter une nouvelle pizza");
		System.out.println("3.	Mettre à jour une pizza");
		System.out.println("4.	Supprimer une pizza");
		System.out.println("99.	Sortir");
	}

}
