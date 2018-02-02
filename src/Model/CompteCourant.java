package Model;

import java.io.Serializable;

/**
 * 
 */
public class CompteCourant extends Compte implements Serializable{

    /**
     * Default constructor
     */
    public CompteCourant() {
    	this.setSolde(Math.random()*(100000.00 - (-1000.00)) + (-1000.00));
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
	 * Retirer un montant � un compte
	 */
	@Override
	public boolean retrait(double montant) {
		boolean retrait = false;
		if ((this.getSolde() - montant) < decouvertMax){
			System.err.println("Vous ne pouvez pas d�passer votre d�couvert autoris� qui est de : " + this.decouvertMax);
	
		}else if(montant > retraitMax){
			System.err.println("Vous ne pouvez pas retirer plus de " + this.retraitMax + " euros � la fois");
		}else if(montant < 0 ){
	        	System.err.println("Le montant doit �tre positif");
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
	public void virementSur(Compte compteB, double montant) {
		if((getSolde() - montant) < decouvertMax){
			System.err.println("Vous ne pouvez pas d�passer votre d�couvert autoris� qui est de : " + this.decouvertMax
					+ " . Vous pouvez faire un virement maximum de : " + (getSolde() + decouvertMax) + "�");
		}
		super.virementSur(compteB, montant);
	}
	
	
	
    
    


    

}