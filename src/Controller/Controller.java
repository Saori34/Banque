package Controller;
/**
 * Classe controller faisant lien entre le vue et le model
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Model.Client;
import Model.Compte;
import Model.CompteCourant;
import Test.DemoAppli;
import View.FenetreAffichage;

public class Controller implements ActionListener, ListSelectionListener {

	private JButton valider; 
	private JFormattedTextField jtf;
	private JList<String> menu;
	private JList <String>affichageComptes;
	private JList<String> affichageDetails;
	static DecimalFormat df = new DecimalFormat("##0.##");
	static CompteCourant compte1;
	static CompteCourant compte2;
	static CompteCourant compte;
	
	private boolean clients = false;
	private boolean comptes = false;
	private boolean isFirst = true;
	
	
	public Controller() {
		valider = FenetreAffichage.getValider(); 
		jtf = FenetreAffichage.getJtf();
		menu = FenetreAffichage.getMenu();
		affichageComptes = FenetreAffichage.getAffichageComptes();
		affichageDetails = FenetreAffichage.getAffichageDetails();
		affichageComptes.setName("affichageComptes");
		affichageDetails.setName("affichageDetails");
		menu.setName("menu");
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//si on clique sur le bouton valider
		if(arg0.getSource() == valider) {
				if(jtf.getText() != "") {
					String texte = jtf.getText();
					//si un compte est selectionne
					if(affichageDetails.getSelectedIndex() != -1){
						try {
							int montant = Integer.parseInt(texte);
							if(montant < 0 ){
								FenetreAffichage.dialogErreur("Le montant ne doit pas être inférieur à 0");
							}
							if(menu.getSelectedIndex() == 3 ) {//si retrait
								compte = recupCompte();
								if(compte.retrait(montant)) {
									jtf.setText("");
									FenetreAffichage.dialogInfo("Retrait de " + montant + "€ effectué");
									affichageDetails.setListData(recupClient().listerComptes());
								}else {
									FenetreAffichage.dialogInfo("Le retrait n'a pas pu être effectué");
								}
							}
							else if(menu.getSelectedIndex() == 4) {//si depot
								compte = recupCompte();
								compte.depot(montant);
								jtf.setText("");
								FenetreAffichage.dialogInfo("Dépôt de " + montant + "€ effectué");
								affichageDetails.setListData(recupClient().listerComptes());
							}
							else if(menu.getSelectedIndex() == 5) {//si virement
								compte2 = recupCompte();
								if(compte1.virementSur(compte2, montant)) {
									jtf.setText("");
									FenetreAffichage.dialogInfo("Virement de " + montant + "€ effectué");
									affichageDetails.setListData(recupClient().listerComptes());
								}
							}
							
						}catch(NumberFormatException e) {
							FenetreAffichage.dialogErreur("Le montant doit être un nombre supérieur à 0");
						}
					}else {
						FenetreAffichage.dialogErreur("Vous devez d'abord sélectionner un compte");
					}
				}else{		
					FenetreAffichage.dialogErreur("Veuillez taper un montant supérieur à 0");
				}	
		}else if(arg0.getSource() == jtf){
			
		}

	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		JList<String> liste = (JList<String>) arg0.getSource();
		if(!arg0.getValueIsAdjusting()) {
			switch(liste.getName()) {
				case "menu":
					//si on clique sur le menu
					comptes = false;
					clients = false;
					int index = menu.getSelectedIndex();
					choixMenu(index);	
					break;	
				case "affichageComptes" :
					// si on clique sur un élement de la liste de droite en haut
					//si ce sont les clients qui sont affiches
					if(clients) {
						//on verifie qu'un client est selectionne
						if(affichageComptes.getSelectedIndex() != -1) {
							affichageDetails.setListData(recupClient().listerComptes());
							comptes = true;
							//si on a choisi l'option supprimer client
							if(menu.getSelectedIndex() == 6) {
									int res = FenetreAffichage.dialogConfirm("Etes-vous sûr de vouloir supprimer ce client ?");
									if(res == JOptionPane.OK_OPTION) {
										DemoAppli.banquier.supprimerClient(recupClient());
										afficherListeClients();
									}
							}
						}
					}
					break;
				case "affichageDetails" :
					//si on clique sur un élement de la liste de droite en bas
					//si ce sont les comptes clients qui sont affiches
					if(comptes) {
						//si un item est sélectionné
						if(affichageDetails.getSelectedIndex() != -1) {
							//si on a choisi l'option virement
							if(menu.getSelectedIndex() == 5) {
								//si isFirst = true alors 1er compte
								if(isFirst) {
									isFirst = false;
									compte1 = recupCompte();
									FenetreAffichage.dialogInfo("Veuillez sélectionner le compte sur lequel faire le virement");
								}else {
									isFirst = true;
									FenetreAffichage.dialogInfo("Veuillez saisir le montant du virement et cliquer sur valider");
								}
							}
							else {
								compte = recupCompte();
							}
						}
					}
					break;	
			}
		}
	}

/**
 * Renvoie le client selectionne dans la liste
 * @return client 
 */
public Client recupClient() {
	if(clients) {
		Collection<Client>clientele = new ArrayList<>();
		clientele = DemoAppli.banquier.getClients();
		Client client = ((ArrayList<Client>) clientele).get(getIndexListe(affichageComptes));
		return client;
	}
	return null;
}

/**
 * Renvoie le compte selectionne dans la liste
 * @return CompteCourant
 */
public CompteCourant recupCompte() {
	if(comptes) {
		Compte cpt;
		Collection<CompteCourant>comptes = new ArrayList<>();
		comptes = recupClient().getListeComptes(); 
		cpt = ((ArrayList<CompteCourant>) comptes).get(getIndexListe(affichageDetails));
		return (CompteCourant) cpt;
	}
	return null;
}

/**
 * Affiche la liste des cliens dans la fenetre prévue à cet effet
 */
public void afficherListeClients() {
	affichageComptes.setListData(DemoAppli.banquier.listerClientsGUI());
}

/**
 * renvoie l'index de la liste selectionnee pour retrouver l'objet de la collection
 * @param liste selectionnee
 * @return int index
 */
public int getIndexListe(JList liste) {
	return liste.getSelectedIndex();
}

public void choixMenu(int index){
	
	switch(index){
	
	case 0 :
		clients = true;
		comptes = false;
		afficherListeClients();
		 break;
	case 1 : 
		comptes = true;
		clients = false;
		affichageComptes.setListData(DemoAppli.banquier.listerComptes());
		 break;
	case 2 :
		comptes = false;
		clients = false;
		//on affiche le solde total des comptes
		String[] list = {"Solde total tous comptes : " + df.format(DemoAppli.banquier.soldeTotalComptes()) + " euros"};
		affichageComptes.setListData(list);
		 break;
	case 3 : 
		clients = true;
		comptes = false;
		afficherListeClients();
		break;
	case 4 :
		clients = true;
		comptes = false;
		afficherListeClients();
		break;
	case 5 :
		clients = true;
		comptes = false;
		afficherListeClients();
		FenetreAffichage.dialogInfo("Veuillez sélectionner le compte à partir duquel faire le virement");
	case 6 :
		clients = true;
		comptes = true;
		afficherListeClients();
		 break;
	case 7 : 
		comptes = false;
		clients = false;
		int res = FenetreAffichage.dialogConfirm("Etes-vous sûr de vouloir sauvegarder les données ?");
		if(res == JOptionPane.OK_OPTION) {
			//on sauvegarde la liste clients
			if(DemoAppli.saveDataIntoFile("./assets/saveClients.txt"))
				FenetreAffichage.dialogInfo("La sauvegarde a bien été effectuée");
		}
		
		 break;
	case 8 :
		comptes = false;
		clients = false;
		int result = FenetreAffichage.dialogConfirm("Etes-vous sûr de vouloir restaurer les données sauvegardées ?");
		if(result == JOptionPane.OK_OPTION) {
			//on restaure la liste clients
			DemoAppli.banquier.setClients(DemoAppli.restoreDataFromFile("./assets/saveClients.txt"));
			//on l'assigne au gestionnaire 
			afficherListeClients();
			FenetreAffichage.dialogInfo("La sauvegarde a été chargée");
		}
		break;
	}
	
}


}
