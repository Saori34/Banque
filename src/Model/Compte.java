package Model;

import java.text.DecimalFormat;

/**
 * 
 */
public abstract class Compte {

    /**
     * Variables
     */
	private final long numCompte;
	private double solde;
	private String type;
	
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
	public long getNumCompte() {
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

    /**Ajouter un montant � un compte
     * @param montant 
     * 
     */
    public void depot(double montant) {
        if(montant < 0)
        	System.err.println("Le montant doit �tre positif");
        solde += montant;
    }


	/**Transf�rer un montant du compte manipul� sur un autre compte
     * @param compteB 
     * @param montant 
     * 
     */
    public void virementSur(Compte compteB, double montant) {
        if(montant < 0)
        	System.err.println("Le montant doit �tre positif");
       compteB.solde += montant;
       this.solde -= montant;
    }
    


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Le solde du compte n�" + getNumCompte() + " est actuellement de : " + df.format(getSolde()) + " euros";
	}



    
    

}