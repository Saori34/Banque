package Model;

/**
 * 
 */
public class LivretEpargne extends Compte {

    /**
     * Default constructor
     */
    public LivretEpargne() {
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
			System.err.println("Vous ne pouvez pas dépasser le montant maximum autorisé sur ce compte");
		}
		super.depot(montant);
	}
	
	
	/* (non-Javadoc)
	 * @see Model.Compte#virementSur(Model.Compte, double)
	 */
	@Override
	public void virementSur(Compte compteB, double montant) {
		if ((getSolde() - montant) < 0){
			System.err.println("Vous ne pouvez pas être à découvert sur ce compte ! "
					+ "Vous pouvez faire un virement maximum de : " + getSolde() + "€" );
		}
		super.virementSur(compteB, montant);
}
	
	/*
	 * Virement des intérets
	 */
	public void virementInterets(){
		setSolde(getSolde()+ (getSolde()*taux));
	}
   
}