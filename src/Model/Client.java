package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;

/**
 * 
 */
public class Client extends Personne implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1819121287678461865L;
	/**
     * Variables
     */
    private String numClient;
    private Collection<CompteCourant> listeComptes;
    private Collection<Compte> cagnote = new ArrayList<>();
    private String genre;
    private int age;
    
    
    /**
     * Default constructor
     */
    public Client() {    	
    }
    

	/**
	 * @param numClient
	 * @param compte
	 * @param genre
	 * @param age
	 */
	public Client(String prenom, String profil, String numClient, int age, String genre) {
		super(prenom, profil);
		this.numClient = numClient;
		this.genre = genre;
		this.age = age;
		listeComptes = this.CreerListeComptes();		
	}



	/**
	 * @return the numClient
	 */
	public String getNumClient() {
		return numClient;
	}
	
	public String getName() {
        return super.getPrenom();
    }
	
	/**
	 * 
	 * @param num
	 */
	public void setNumClient(String num){
		this.numClient = num;
	}

	/**
	 * @return the compte
	 */
	public Collection<CompteCourant> getListeComptes() {
		return listeComptes;
	}

	/**
	 * @param compte the compte to set
	 */
	public void setCompte(CompteCourant compte) {
		this.listeComptes.add(compte);
	}
	
	public Collection<Compte> getCagnote() {
        return cagnote;
    }

    public void setCagnote(Collection<Compte> cagnote) {
        this.cagnote = cagnote;
    }
	
	/**
	 * 
	 * @return genre
	 */
	public String getGenre() {
		return genre;
	}
	
	/**
	 * 
	 * @param genre
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	/**
	 * 
	 * @return age
	 */
	public int getAge() {
		return age;
	}
	
	/**
	 * 
	 * @param age
	 */
	public void setAge(int age) {
		this.age = age;
	}
	
	@Override
	public String toString(){
		return "Client ==> ID n°" + this.getNumClient() + " : " + this.getPrenom() + ", " + this.getAge() + " ans, " + this.getGenre();
	}
	
	/**
	 * Liste les comptes du client en tableau de String pour être affiches dans des JList
	 */
	@Override
	public String[] listerComptes(){
		Collection <CompteCourant> listeDeComptes = this.getListeComptes();
    	Iterator <CompteCourant> it = listeDeComptes.iterator();
    	String[] comptes = new String[listeDeComptes.size()];
    	if(!listeDeComptes.isEmpty()){
			while(it.hasNext()){
				for(int i=0; i<listeDeComptes.size(); i++){
					comptes[i] = it.next().toString();
				}
			}
    	}else{
    		System.out.println("La liste de comptes est vide !");
    	}
    	return comptes;
	}
	
	/**
	 * Selectionne un compte grâce à la saisie de l'utilisateur dans une liste
	 * @return Compte compte
	 */
	public CompteCourant selectionnerCompte(){

		Scanner sc = new Scanner(System.in);
		int choix = 0;
		Collection <CompteCourant> listeComptes = this.getListeComptes();
		CompteCourant compte = new CompteCourant();
		if(!listeComptes.isEmpty()){
			System.out.println("Veuillez saisir le numéro du compte dans la liste suivante: ");
			 this.listerComptes();
			 do{
				 choix = sc.nextInt();
				 if(choix <= 0 || choix > listeComptes.size()){
					 System.err.println("Vous devez saisir un numéro de compte compris dans la liste (entre 1 et la fin de la liste)\n");
				 }else{
					 compte = ((ArrayList<CompteCourant>) listeComptes).get(choix-1);//On n'oublie pas de retirer 1 car la liste commence ici à l'index 0 contrairement à la liste montrée à l'utilisateur
				 }
			 }while(choix <= 0 || choix > listeComptes.size());
		}else{
			System.out.println("\nLa liste de comptes est vide\n");
		}
		 return compte;
	}
	
	
		
	/**
	 * Créer collection de comptes
	 */
	public Collection<CompteCourant> CreerListeComptes(){
		//On donne au hasard un nombre de comptes entre 0 et 3
		int nbComptes = (int) Math.floor(Math.random()*4);
		CompteCourant compte = new CompteCourant();
		listeComptes = new ArrayList<CompteCourant>();	
		for(int i=0 ; i<nbComptes ;i++ ){
			compte = new CompteCourant();
			listeComptes.add(compte);
		}
		
		return listeComptes;
	}
	
	


	

    
    
 





}