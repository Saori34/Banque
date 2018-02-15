package Test;
/**
 * Classe permettant de lancer le programme
 */
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
import java.util.ArrayList;
import java.util.Collection;

import Controller.Controller;
import Model.Client;
import Model.Gestionnaire;
import View.FenetreAffichage;

public class DemoAppli {

	static Collection <Client> clientele;
	public static Gestionnaire banquier = new Gestionnaire();
	
    public static FenetreAffichage fenetre;
    public static Controller control;

	
	
	
	public static void main(String[] args) {
		
		//On extrait les donn�es clients du fichier
		File file = new File(".assets/bankCustomers.txt");
		extractDatafromFile(file);
		//On ajoute cette collection client � une instance Gestionnaire "banquier"
		banquier.setClients(clientele);
		
		//On affiche la fenetre
		fenetre = new FenetreAffichage();
		
		//On instancie le controller et les listeners
		control = new Controller();
		FenetreAffichage.getValider().addActionListener(control);
		FenetreAffichage.getMenu().addListSelectionListener(control);
		FenetreAffichage.getJtf().addActionListener(control);
		FenetreAffichage.getAffichageComptes().addListSelectionListener(control);
		FenetreAffichage.getAffichageDetails().addListSelectionListener(control);
		
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
	public static boolean saveDataIntoFile(String pathName){
		ObjectOutputStream oos;
			
		Collection <Client> clients = clientele;
		
		try{
			oos = new ObjectOutputStream (
					new BufferedOutputStream(
							new FileOutputStream(
									new File(pathName))));
			try{
				oos.writeObject(clients);
				oos.flush();
			}finally{
				oos.close();
			}
		
		}catch(FileNotFoundException e){
			System.err.println("FileNotFoundException lev�e");
		}catch(IOException e){
			System.err.println("IOException lev�e");
		}
		return true;
	}
	
	/**
	 * Restaure les donn�es � partir du fichier sp�cifi�
	 * @param file
	 */
	public static Collection<Client> restoreDataFromFile(String pathName){
		ObjectInputStream ois;
		Collection<Client> clients = new ArrayList<Client>();
		try{
			ois = new ObjectInputStream(
					new BufferedInputStream(
							new FileInputStream(
									new File(pathName))));
			
			try {
				clients = (Collection<Client>) ois.readObject();
				ois.close();
			}catch (ClassNotFoundException e) {
				System.err.println("ClassNotFoundException lev�e");
			}
		}catch(FileNotFoundException e){
			System.err.println("FileNotFoundException lev�e");
		}catch(IOException e){
			System.err.println("IOException lev�e");
		}
		return clients;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
