package Model;

import java.io.Serializable;

import View.FenetreAffichage;

/**
 * 
 */
public class CompteCourant extends Compte implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -8924301111130660505L;

	/**
     * Default constructor
     */
    public CompteCourant() {
    	this.setSolde(Math.random()*(100000.00 - (-1000.00)) + (-1000.00));
    	this.setType("Compte courant");
    }

    /**
     * Variables
     */
    private double decouvertMax = -1000;
    private double retraitMax = 2000;
    
    public CompteCourant(double decouvertMax, double retraitMax){
    	this.decouvertMax = decouvertMax;
    	//this.retraitMax = retraitMax;
    	this.setType("Compte courant");
    }
    
    //GETTERS AND SETTERS

	/**
	 * @return the decouvertMax
	 */
	public double getDecouvertMax() {
		return decouvertMax;
	}

	/**
	 * @param decouvertMax the decouvertMax to set
	 */
	public void setDecouvertMax(double decouvertMax) {
		this.decouvertMax = decouvertMax;
	}

	/**
	 * @return the retraitMax
	 */
	public double getRetraitMax() {
		return retraitMax;
	}

	/**
	 * @param retraitMax the retraitMax to set
	 */
	public void setRetraitMax(double retraitMax) {
		this.retraitMax = retraitMax;
	}
 


	/* (non-Javadoc)
	 * Retirer un montant à un compte
	 */
	@Override
	public boolean retrait(double montant) {
		boolean retrait = false;
		if ((this.getSolde() - montant) < decouvertMax){
			FenetreAffichage.dialogErreur("Vous ne pouvez pas dépasser votre découvert autorisé qui est de : " + this.decouvertMax);
	
		}else if(montant > retraitMax){
			FenetreAffichage.dialogErreur("Vous ne pouvez pas retirer plus de " + this.retraitMax + " euros à la fois");
		}else if(montant < 0 ){
			FenetreAffichage.dialogErreur("Le montant doit être positif");
		}
		else{
			setSolde(getSolde() - montant);
			retrait = true;
		}
		return retrait;
	}

	/* (non-Javadoc)
	 * @see Model.Compte#virementSur(Model.Compte, double)
	 */
	@Override
	public boolean virementSur(Compte compteB, double montant) {
		boolean ok = false;
		if((getSolde() - montant) < decouvertMax){
			FenetreAffichage.dialogErreur("Vous ne pouvez pas dépasser votre découvert autorisé qui est de : " + this.decouvertMax
					+ " . Vous pouvez faire un virement maximum de : " + (getSolde() + decouvertMax) + "€");
		}
		else if(montant > retraitMax){
			FenetreAffichage.dialogErreur("Vous ne pouvez pas retirer plus de " + this.retraitMax + " euros à la fois");
		}else if(montant < 0 ){
			FenetreAffichage.dialogErreur("Le montant doit être positif");
		}
		else {
			super.virementSur(compteB, montant);
			ok = true;
		}
		return ok;
	}
	
	
	
    
    


    

}