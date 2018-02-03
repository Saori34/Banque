package Model;

import java.io.Serializable;

/**
 * 
 */
public abstract class Personne implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4216969754888891432L;




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
	 * @return 
	 */
	public abstract String[] listerComptes();
	
	   
	

}