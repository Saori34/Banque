package Model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;

import View.FenetreAffichage;

/**
 * 
 */
public abstract class Compte implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 8355475238212974154L;
	/**
     * Variables
     */
	private final long numCompte;
	private double solde;
	private String type;
    public static Collection<Compte> accounts = new ArrayList<>();
	
	DecimalFormat df = new DecimalFormat("##0.##");
	
	
	
    /**
     * Default constructor
     */
    public Compte() {
    	numCompte = (long) Math.floor(Math.random() * 10000000);
    }
    
   
    
    //GETTERS AND SETTERS
    
    /**
	 * @return the solde
	 */
	public double getSolde() {
		return solde;
	}

	/**
	 * @param solde the solde to set
	 */
	public void setSolde(double solde) {
		this.solde = solde;
	}


	/**
	 * @return the numCompte
	 */
	public Long getNumCompte() {
		return numCompte;
	}
	/**
	 * 
	 * @return the type
	 */
	
	public String getType() {
		return type;
	}

	/**
	 * 
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	
	 	//METHODES

    /**Ajouter un montant à un compte
     * @param montant 
     * 
     */
    public void depot(double montant) {
        if(montant < 0)
        	FenetreAffichage.dialogErreur("Le montant doit être positif");
        solde += montant;
    }
    
    /**
     * Faire un retrait d'un compte
     * @param montant
     * @return 
     */
    public boolean retrait(double montant){
    	if(type.equals("Livret d'épargne")){
    		FenetreAffichage.dialogErreur("Vous ne pouvez pas faire de retrait depuis un livret d'épargne");
    		return false;
    	}
    	else {
    		solde -= montant;
    		return true;
    	}
    }


	/**Transférer un montant du compte manipulé sur un autre compte
     * @param compteB 
     * @param montant 
     * 
     */
    public boolean virementSur(Compte compteB, double montant) {
    	boolean ok = false;
        if(montant < 0)
        	FenetreAffichage.dialogErreur("Le montant doit être positif");
        else if(type.equals("Livret d'épargne"))
    		FenetreAffichage.dialogErreur("Vous ne pouvez pas faire de retrait depuis un livret d'épargne");
        else{
        	compteB.depot(montant);
        	this.retrait(montant);
            ok = true;
        }
       return ok;
    }
    


	/* Description du compte
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Le solde du " + getType() + " n°" + getNumCompte() + " est actuellement de : " + df.format(getSolde()) + " euros";
	}
	
	     

}