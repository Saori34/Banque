package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;

/**
 * 
 */
public class Gestionnaire extends Personne implements Serializable{

    /**
     * Default constructor
     */
    public Gestionnaire() {
    	numEmploye = (long)Math.random() * (100000 - 1);
    }

    /**
     * 
     */
    private final long numEmploye;
    public static Collection<Client> clientele;
    public static Collection<Compte> cptes = new ArrayList<Compte>();
    


//GETTERS AND SETTERS

    /**
	 * 
     * @return the client
	 */
	public Collection<Client>  getClients() {
		return clientele;
	}

	/**
	 * @param client the client to set
	 */
	public void setClients(Collection<Client> clients) {
		this.clientele = clients;
	}

	/**
	 * @return the numEmploye
	 */
	public long getNumEmploye() {
		return numEmploye;
	}
	
	/**
	 * 
     * @return liste de comptes gérées par l'employe
	 */
	public Collection<Compte>  getComptes() {
		return cptes;
	}
	


//METHODES

	/**
     * @param client 
     * @param compte 
     * @return
     */
    public void ouvertureCompteCourant(Client client) {
        client.setCompte(new CompteCourant());
    }
    
    
    /**
     * 
     * @return new client
     */
    
    public Client createClient(){
    	return new Client();
    }
    

    
    /**
     * Liste les clients d'un gestionnaire en GUI
     * @return String liste des clients
     */
    public String[] listerClientsGUI(){
		Collection <Client> clientele = this.getClients();
    	Iterator <Client> it = clientele.iterator();
    	String[] liste = new String[clientele.size()];
    	if (!clientele.isEmpty()){
			while(it.hasNext()){
				for(int i = 0 ; i <clientele.size() ; i++){
					liste[i] = it.next().toString();
				}
			}
    	}else{
    		System.err.println("La liste de client est vide") ;
    	}
    	return liste;
    }

	
	  
    
	/**
	 * Supprime un client de la collection d'un gestionnaire grâce à son ID
	 */
	public boolean supprimerClient(String idClient){
		Collection <Client> clientele = this.getClients();
		Iterator <Client> it = clientele.iterator();
		boolean reussi = false;
		
		if(!clientele.isEmpty()){
			//On va de client en client
			while(it.hasNext()){
				//On cherche si l'id client correspond à l'id client rentré en paramètre, si oui on le retire
				if(it.next().getNumClient().contains(idClient)){
					it.remove();
					System.out.println("Client supprimé de la collection\n");
					reussi =true;
				}
			}
		}
		return reussi;
	}
	
	/**
	 * Supprime un client à partir de la saisie d'un index par l'utilisateur (dans une liste affichée)
	 * @param index
	 * @return true si le client a bien été supprimé
	 */
	public boolean supprimerClient(Client client){
		Collection <Client> clientele = this.getClients();
		boolean reussi = false;
		
		if(!clientele.isEmpty()){
			if(clientele.remove(client)){
				reussi = true;
				System.out.println("Client supprimé");
			}else{
				System.out.println("Client non supprimé");
			}
		}
		return reussi;
	}
	
	/**
	 * Calcule le solde de tous les comptes de tous les clients d'un même gestionnaire
	 * @return soldeTotal
	 */
	public double soldeTotalComptes(){
		double soldeTotal = 0.0;
		
		Collection <Client> clientele = this.getClients();
    	Iterator <Client> it = clientele.iterator();
    	Client client;
    	
		if(!clientele.isEmpty()){
			//On va de client en client dans la collection client
	    	while(it.hasNext()){
				client = it.next();
				Collection <CompteCourant> listeDeComptes = client.getListeComptes();
		    	Iterator <CompteCourant> itBis = listeDeComptes.iterator();
		    	if(!listeDeComptes.isEmpty()){
		    		//On va de compte en compte dans la collection compte
					while(itBis.hasNext()){
						//On ajoute le solde de chaque compte dans soldeTotal
						soldeTotal += itBis.next().getSolde();
					}
		    	}
		    }
		}
		return soldeTotal;
	}
	
	/**
	 * Calcule le solde de tous les comptes d'un client particulier choisi par l'utilisateur
	 * @return soldeTotal
	 */
	public double SoldeTotalClient(Client client){
		double soldeTotal = 0.0;
		Collection<CompteCourant>listeDeComptes = client.getListeComptes();
		Iterator <CompteCourant> it = listeDeComptes.iterator();
		if(!listeDeComptes.isEmpty()){
			while(it.hasNext()){
				soldeTotal += it.next().getSolde();
			 
			}
		}
		return soldeTotal;
	}
		

	/**
	 * Saisir un montant
	 * @return montant
	 */
	public double saisieMontant(){
		Scanner sc = new Scanner(System.in);
		double montant = 0.0;
		do{
			System.out.println("Veuillez saisir un montant positif : ");
			montant = sc.nextDouble();
		}while(montant < 0);
		
		return montant;
	}
	
	@Override
	public String toString(){
		this.listerComptes();
		return " ";
	}

	
	@Override
	public String[] listerComptes() {
		
		Collection<Client> clientele = this.getClients();
    	Iterator <Client> it = clientele.iterator();
    	Collection <CompteCourant> listeCompte = new ArrayList<>();
    	String[] comptes;
    	if(!clientele.isEmpty()){
			while(it.hasNext()){
					listeCompte.addAll(it.next().getListeComptes());
			}
    	}else{
    		System.out.println("La liste de comptes est vide");
    	}
    	Iterator<CompteCourant> itBis = listeCompte.iterator();
		comptes = new String[listeCompte.size()];
		while(itBis.hasNext()) {
			for(int i=0; i<listeCompte.size(); i++) {
				comptes[i] = itBis.next().toString();
			}
		}
    	return comptes;
	}
}