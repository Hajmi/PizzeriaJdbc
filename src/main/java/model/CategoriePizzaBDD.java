package model;

public class CategoriePizzaBDD {
	
	private int id;
	private String nom;
	
	public CategoriePizzaBDD(int id, String nom) {
		
		this.id = id ;
		this.nom = nom ;
	}

	public CategoriePizzaBDD() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	

}
