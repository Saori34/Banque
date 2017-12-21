package Model;

import java.util.Collection;
import java.util.TreeSet;

/**
 * 
 */
public abstract class Personne {

    /**
     * Default constructor
     */
    public Personne() {
    }

    /**
     * Variables
     */
    
    private String prenom;
    private String profil;
    
    
    
    
	public Personne( String prenom, String profil) {
		this.prenom = prenom;
		this.profil = profil;
	}

    
    //GETTERS AND SETTERS


	/**
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}


	/**
	 * @param prenom the prenom to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	
	//METHODES
	
	/**
	 * return all the comptes
	 */
	public abstract void listerComptes();


	

	
	
	
	
	

}