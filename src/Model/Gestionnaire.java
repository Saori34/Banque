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
    private Collection<Client> clients;
    


//GETTERS AND SETTERS

    /**
	 * 
     * @return the client
	 */
	public Collection<Client>  getClients() {
		return clients;
	}

	/**
	 * @param client the client to set
	 */
	public void setClients(Collection<Client> clients) {
		this.clients = clients;
	}

	/**
	 * @return the numEmploye
	 */
	public long getNumEmploye() {
		return numEmploye;
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
    
   // public void ouvertureLivretEpargne(Client client){
    //	client.setCompte(new LivretEpargne());
   // }
    
    /**
     * 
     * @return new client
     */
    
    public Client createClient(){
    	return new Client();
    }
    
    
    
    /**
     * Liste les clients d'un gestionnaire
     */
    public void listerClients(){
		Collection <Client> clientele = this.getClients();
    	Iterator <Client> it = clientele.iterator();
    	if (!clientele.isEmpty()){
			while(it.hasNext()){
				for(int i = 0 ; i <clientele.size() ; i++){
					System.out.println((i+1) + ". " + it.next().toString());
				}
			}
    	}else{
    		System.out.println("La liste de client est vide");
    	}
    }

    /**
     * Liste tous les comptes de tous les clients
     */
	@Override
	public void listerComptes() {
		Collection <Client> clientele = this.getClients();
    	Iterator <Client> it = clientele.iterator();
    	Client client;
    	
    	//On va de client en client dans la collection client
		if(!clientele.isEmpty()){
	    	while(it.hasNext()){
				client = it.next();
				//On écrit le nom du client
				System.out.println(client.toString());
				Collection <CompteCourant> listeDeComptes = client.getListeComptes();
		    	Iterator <CompteCourant> itBis = listeDeComptes.iterator();
		    	//On va de compte en compte dans la collection compte
		    	if(!listeDeComptes.isEmpty()){
					while(itBis.hasNext()){
						//On écrit la phrase de description du compte
						System.out.println(itBis.next().toString());
					}
		    	}else{
		    		System.out.println("La liste de comptes est vide !");
		    	}
		    	System.out.println("-----------------------------------------");
			}
		}else{
			System.out.println("La liste de client est vide !");
		}
	}
	
	/**
	 * Sélection de l'utilisateur d'un client en affichant une liste
	 * @return Client client
	 */
	public Client selectionneClient(){
		Scanner sc = new Scanner(System.in);
		int choix = 0;
		Collection <Client> clientele = this.getClients();
		Client client = new Client();
		if(!clientele.isEmpty()){
			System.out.println("Veuillez saisir le numéro du client dans la liste suivante: ");
			 this.listerClients();
			 do{
				 choix = sc.nextInt();
				 if(choix <= 0 || choix > clientele.size()){
					 System.err.println("Vous devez saisir un numéro de client compris dans la liste (entre 1 et la fin de la liste)\n");
				 }else{
					 client = ((ArrayList<Client>) clientele).get(choix-1);//On n'oublie pas de retirer 1 car la liste commence ici à l'index 0 contrairement à la liste montrée à l'utilisateur
				 }
			 }while(choix <= 0 || choix > clientele.size());
		}else{
			System.err.println("\nLa liste de client est vide\n");
		}
		 return client;
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
}