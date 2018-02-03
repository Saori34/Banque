package Model;

import View.FenetreAffichage;

/**
 * 
 */
public class LivretEpargne extends Compte {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2565156834603454047L;


	/**
     * Default constructor
     */
    public LivretEpargne() {
    	this.setSolde(Math.random()*(100000.00 - (-1000.00)) + (-1000.00));
    	this.setType("Livret d'épargne");
    }

    /**
     * Variables
     */
    private double taux;
    private double max;
    
    
   public LivretEpargne(double taux, double max){
	   this.taux = taux;
	   this.max = max;
	   this.setType("Livret d'épargne");
   }


   //GETTERS AND SETTERS
   /**
    * @return the taux
    */
   public double getTaux() {
   	return taux;
   }


   /**
    * @param taux the taux to set
    */
   public void setTaux(double taux) {
   	this.taux = taux;
   }

   /**
    * @return the max
    */
   public double getMax() {
   	return max;
   }


   /**
    * @param max the max to set
    */
   public void setMax(double max) {
   	this.max = max;
   }


      
   //METHODES
	
	
	/* (non-Javadoc)
	 * @see Model.Compte#depot(double)
	 */
	@Override
	public void depot(double montant) {
		if (getSolde() + montant > max){
			FenetreAffichage.dialogErreur("Vous ne pouvez pas dépasser le montant maximum autorisé sur ce compte");
		}
		super.depot(montant);
	}
	
	
	/* (non-Javadoc)
	 * @see Model.Compte#virementSur(Model.Compte, double)
	 */
	@Override
	public boolean virementSur(Compte compteB, double montant) {
		boolean ok = false;
		if ((getSolde() - montant) < 0){
			FenetreAffichage.dialogErreur("Vous ne pouvez pas être à découvert sur ce compte ! "
					+ "Vous pouvez faire un virement maximum de : " + getSolde() + "€" );
		}
		else{
			super.virementSur(compteB, montant);
			ok = true;
		}
		return ok;
}
	
	/*
	 * Virement des intérets
	 */
	public void virementInterets(){
		setSolde(getSolde()+ (getSolde()*taux));
	}
   
}