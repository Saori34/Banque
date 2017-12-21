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
		
		//On extrait les données clients du fichier
		File file = new File(".assets/bankCustomers.txt");
		extractDatafromFile(file);
		//On ajoute cette collection client à une instance Gestionnaire "banquier"
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
										default : System.err.println("Vous devez taper le numero correspondant à votre choix");
												  break;
									 }
						 }while(choixClient > 5 || choixClient <= 0);
				 		 break;
				default : System.err.println("Vous devez taper le numero correspondant à votre choix");
						  break;
			}
		}while(choix > 3 || choix != 0);
		
		
		sc.close();
	}
	/**
	 * Créer une collection de client à partir des données d'un fichier texte formaté
	 * @param file
	 * @return collection de client
	 */
	private static Collection<Client> extractDatafromFile(File file){
		
		//Variables pour la création des clients
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
				//On sépare en fonction du signe "*"
				data = tmp.split("[*]");
				//On met en majuscule
				id = data[0].toUpperCase();
				//On remplace tous les caractères n'étant pas des voyelles par une chaine vide
				id = id.replaceAll("[^AEIOUY]", "");
				//Si la chaine restante (seulement les voyelles donc) est égale ou sup à 2 on inverse les 2 premières voyelles et on concatene
				if(id.length()>=2){
					id = String.valueOf(id.charAt(1)).concat(String.valueOf(id.charAt(0)));
					//Si il y a moins de 2 voyelles on double la 1ère (il y a forcément au moins une voyelle dans un prénom)
				}else{
					id = String.valueOf(id.charAt(0)).concat(String.valueOf(id.charAt(0)));
				}
				
				//Puis on concatène la chaine de 2 voyelles à l'identifiant numérique
				id = id.concat(data[1]);
				
				//On crée un nouveau client avec tous ces paramètres
				client = new Client(data[0], profil, id, Integer.parseInt(data[2]), data[3] );
							
				//On ajoute le client à la collection
				clientele.add(client);
			}
			
			//Libérer les ressources
			br.close();
			fr.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
			System.err.println("Fichier non trouvé");
		}catch(IOException e){
			System.err.println("Erreur acces fichier");
			e.printStackTrace();
		}
		
		return clientele;
	}

	
}
