package Controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import Model.Client;
import Model.CompteCourant;
import Model.Gestionnaire;
import View.ConsoleAffichage;
import View.FenetreAffichage;

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
		
		
		FenetreAffichage fenetre = new FenetreAffichage();
		
		
		
		
		
	
	}
		/*
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
									 
										 case 0 : choixClient = 0;
											 	  console.afficherChoixGestionnaire();
											 	  choix = sc.nextInt();
											 	  break;
										 case 1 : compte = client.selectionnerCompte();
										 		  montant = banquier.saisieMontant();
										 		  compte.depot(montant);
										 		  client.listerComptes();
											 	  break;
										 case 2 : compte = client.selectionnerCompte();
										 		  montant = banquier.saisieMontant();
										 		  compte.retrait(montant);
										 		  client.listerComptes();
											 	  break;
										 case 3 : System.out.println("Pour faire un virement d'un compte A � un compte B : ");
										 		  System.out.println("S�lectionnez compte A : ");
										 		  compte = client.selectionnerCompte();
										 		  System.out.println("S�lectionnez compte B : ");
										 		  CompteCourant compteB = client.selectionnerCompte();
								 		  		  montant = banquier.saisieMontant();
								 		  		  compte.virementSur(compteB, montant);
								 		  		  client.listerComptes();
											 	  break;
										 case 4 : System.out.println("Solde total tous comptes : " + df.format(banquier.SoldeTotalClient(client)) + " euros \n");
											 	  break;
										 case 5 : client.listerComptes();
											 	  break;
										default : System.err.println("Vous devez taper le numero correspondant � votre choix");
												  break;
									 }
						 }while(choixClient < 0 || choixClient > 0);
				 		 break;
				case 5 : client = banquier.selectionneClient();
						 banquier.supprimerClient(client);
						 break;
				case 6 : saveDataIntoFile("./assets/saveClients.txt");
						 break;
				case 7 : restoreDataFromFile("./assets/saveClients.txt");
						 break;
				default : System.err.println("Vous devez taper le numero correspondant � votre choix");
						  break;
			}
		}while(choix > 6 || choix != 0 || choix < 4 );
		
		
		sc.close();
	}
	
	*/
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
			
			try{
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
			}finally{
			//Lib�rer les ressources
			br.close();
			fr.close();
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
			System.err.println("Fichier non trouv�");
		}catch(IOException e){
			System.err.println("Erreur acces fichier");
			e.printStackTrace();
		}
		
		return clientele;
	}

	/**
	 * Cr�er un fichier de sauvegarde des clients et des comptes
	 * @param file
	 */
	private static void saveDataIntoFile(String pathName){
		ObjectOutputStream oos;
			
		try{
			oos = new ObjectOutputStream (
					new BufferedOutputStream(
							new FileOutputStream(
									new File(pathName))));
			try{
				oos.writeObject(banquier);
			}finally{
				oos.close();
			}
		
		}catch(FileNotFoundException e){
			System.err.println("FileNotFoundException lev�e");
		}catch(IOException e){
			System.err.println("IOException lev�e");
		}
	}
	
	/**
	 * Restaure les donn�es � partir du fichier sp�cifi�
	 * @param file
	 */
	private static void restoreDataFromFile(String pathName){
		ObjectInputStream ois;
		
		try{
			ois = new ObjectInputStream(
					new BufferedInputStream(
							new FileInputStream(
									new File(pathName))));
			
			try {
				System.out.println((ois.readObject().toString()));
				ois.close();
			}catch (ClassNotFoundException e) {
				System.err.println("ClassNotFoundException lev�e");
			}
		}catch(FileNotFoundException e){
			System.err.println("FileNotFoundException lev�e");
		}catch(IOException e){
			System.err.println("IOException lev�e");
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
