package Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import Model.Client;
import Model.CompteCourant;
import Model.Gestionnaire;
import View.ConsoleAffichage;

public class DemoAppli {

	static Collection <Client> clientele;
	static Gestionnaire banquier = new Gestionnaire();
	static ConsoleAffichage console = new ConsoleAffichage(); 
	static DecimalFormat df = new DecimalFormat("##0.##");
	
	
	
	
	public static void main(String[] args) {
		
		//On extrait les donn�es clients du fichier
		File file = new File(".assets/bankCustomers.txt");
		extractDatafromFile(file);
		//On ajoute cette collection client � une instance Gestionnaire "banquier"
		banquier.setClients(clientele);
		
		
		//On affiche l'interface de choix possible dans la console
		Scanner sc = new Scanner(System.in);
		int choix;
		Client client;
		CompteCourant compte;
		
		console.afficherChoix();
		
		do{
			console.afficherChoixGestionnaire();
			choix = sc.nextInt();
					
			switch(choix){
		
				case 1 : banquier.listerClients();
						 break;
				case 2 : banquier.listerComptes();
						 break;
				case 3 : System.out.println("Solde total tous comptes : " + df.format(banquier.soldeTotalComptes()) + " euros \n");
						 break;
				case 4 : client = banquier.selectionneClient();
						 int choixClient;
						 
						 do{
							 console.afficherChoixManipComptes();
							 choixClient = sc.nextInt();
							 double montant = 0.0;
									 switch(choixClient){
									 
										 case 0 : console.afficherChoixGestionnaire();
											 	  choix = sc.nextInt();
											 	  break;
										 case 1 : compte = client.selectionnerCompte();
										 		  montant = sc.nextDouble();
										 		  compte.depot(montant);
											 	  break;
										 case 2 : 
											 	  break;
										 case 3 : 
											 	  break;
										 case 4 : System.out.println("Solde total tous comptes : " + df.format(banquier.SoldeTotalClient(client)) + " euros \n");
											 	  break;
										 case 5 : client.listerComptes();
											 	  break;
										 case 6 : banquier.supprimerClient(client);
										 		  console.afficherChoixGestionnaire();
										 		  choix = sc.nextInt();
										 		  break;
										default : System.err.println("Vous devez taper le numero correspondant � votre choix");
												  break;
									 }
						 }while(choixClient > 5 || choixClient <= 0);
				 		 break;
				default : System.err.println("Vous devez taper le numero correspondant � votre choix");
						  break;
			}
		}while(choix > 3 || choix != 0);
		
		
		sc.close();
	}
	/**
	 * Cr�er une collection de client � partir des donn�es d'un fichier texte format�
	 * @param file
	 * @return collection de client
	 */
	private static Collection<Client> extractDatafromFile(File file){
		
		//Variables pour la cr�ation des clients
		clientele = new ArrayList<Client>();
		Client client = new Client();
		String profil = client.getClass().getSimpleName();
		
		//Chemin fichier
		String pathname = "./assets";
		String fileName = "/bankCustomers.txt";
		String []data;
		
		file = new File(pathname + fileName);
		
		try{
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			
			//extraction chaine du fichier
			String tmp, id;
			while((tmp = br.readLine()) != null){
				//On s�pare en fonction du signe "*"
				data = tmp.split("[*]");
				//On met en majuscule
				id = data[0].toUpperCase();
				//On remplace tous les caract�res n'�tant pas des voyelles par une chaine vide
				id = id.replaceAll("[^AEIOUY]", "");
				//Si la chaine restante (seulement les voyelles donc) est �gale ou sup � 2 on inverse les 2 premi�res voyelles et on concatene
				if(id.length()>=2){
					id = String.valueOf(id.charAt(1)).concat(String.valueOf(id.charAt(0)));
					//Si il y a moins de 2 voyelles on double la 1�re (il y a forc�ment au moins une voyelle dans un pr�nom)
				}else{
					id = String.valueOf(id.charAt(0)).concat(String.valueOf(id.charAt(0)));
				}
				
				//Puis on concat�ne la chaine de 2 voyelles � l'identifiant num�rique
				id = id.concat(data[1]);
				
				//On cr�e un nouveau client avec tous ces param�tres
				client = new Client(data[0], profil, id, Integer.parseInt(data[2]), data[3] );
							
				//On ajoute le client � la collection
				clientele.add(client);
			}
			
			//Lib�rer les ressources
			br.close();
			fr.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
			System.err.println("Fichier non trouv�");
		}catch(IOException e){
			System.err.println("Erreur acces fichier");
			e.printStackTrace();
		}
		
		return clientele;
	}

	
}
