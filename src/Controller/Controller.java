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
import Test.DemoAppli;
import View.FenetreAffichage;

public class Controller implements ActionListener, ListSelectionListener {

	private JButton valider; 
	private JFormattedTextField jtf;
	private JList<String> menu;
	private JList <String>affichageComptes;
	private JList<String> affichageDetails;
	static DecimalFormat df = new DecimalFormat("##0.##");
	static Compte compte1;
	static Compte compte2;
	static Compte compte;
	
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
								FenetreAffichage.dialogErreur("Le montant ne doit pas �tre inf�rieur � 0");
							}
							if(menu.getSelectedIndex() == 3 ) {//si retrait
								compte = recupCompte();
								if(compte.retrait(montant)) {
									jtf.setText("");
									FenetreAffichage.dialogInfo("Retrait de " + montant + "� effectu�");
									affichageDetails.setListData(recupClient().listerComptes());
								}else {
									FenetreAffichage.dialogInfo("Le retrait n'a pas pu �tre effectu�");
								}
							}
							else if(menu.getSelectedIndex() == 4) {//si depot
								compte = recupCompte();
								compte.depot(montant);
								jtf.setText("");
								FenetreAffichage.dialogInfo("D�p�t de " + montant + "� effectu�");
								affichageDetails.setListData(recupClient().listerComptes());
							}
							else if(menu.getSelectedIndex() == 5) {//si virement
								compte2 = recupCompte();
								if(compte1.virementSur(compte2, montant)) {
									jtf.setText("");
									FenetreAffichage.dialogInfo("Virement de " + montant + "� effectu�");
									affichageDetails.setListData(recupClient().listerComptes());
								}
							}
							
						}catch(NumberFormatException e) {
							FenetreAffichage.dialogErreur("Le montant doit �tre un nombre sup�rieur � 0");
						}
					}else {
						FenetreAffichage.dialogErreur("Vous devez d'abord s�lectionner un compte");
					}
				}else{		
					FenetreAffichage.dialogErreur("Veuillez taper un montant sup�rieur � 0");
				}	
		}else if(arg0.getSource() == jtf){
			
		}

	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		JList<String> liste = (JList<String>) arg0.getSource();
		if(!arg0.getValueIsAdjusting()) {
			switch(liste.getName()) {
				case "menu"://si on clique sur le menu
					comptes = false;
					clients = false;
					int index = menu.getSelectedIndex();
					choixMenu(index);	
					break;	
				case "affichageComptes" :// si on clique sur un �lement de la liste de droite en haut
					if(clients) {//si ce sont les clients qui sont affiches
						if(affichageComptes.getSelectedIndex() != -1) {//on verifie qu'un client est selectionne
							affichageDetails.setListData(recupClient().listerComptes());
							comptes = true;
							if(menu.getSelectedIndex() == 6) {//si on a choisi l'option supprimer client
								int res = FenetreAffichage.dialogConfirm("Etes-vous s�r de vouloir supprimer ce client ?");
								if(res == JOptionPane.OK_OPTION) {
									DemoAppli.banquier.supprimerClient(recupClient());
									afficherListeClients();
								}
							}
						}
					}
					break;
				case "affichageDetails" : //si on clique sur un �lement de la liste de droite en bas
					if(comptes) {//si ce sont les comptes clients qui sont affiches
						if(affichageDetails.getSelectedIndex() != -1) {//si un item est s�lectionn�
							valider.setEnabled(true);
							if(menu.getSelectedIndex() == 5) {//si on a choisi l'option virement
								if(isFirst) {//si isFirst = true alors 1er compte
									isFirst = false;
									compte1 = recupCompte();
									FenetreAffichage.dialogInfo("Veuillez s�lectionner le compte sur lequel faire le virement");
								}
								else {
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
public Compte recupCompte() {
	if(comptes) {
		Compte cpt;
		Collection<Compte>comptes = new ArrayList<>();
		comptes = recupClient().getListeComptes(); 
		cpt = ( (ArrayList<Compte>) comptes).get(getIndexListe(affichageDetails));
		return cpt;
	}
	return null;
}

/**
 * Affiche la liste des cliens dans la fenetre pr�vue � cet effet
 */
public void afficherListeClients() {
	affichageComptes.setListData(DemoAppli.banquier.listerClientsGUI());
}

/**
 * renvoie l'index de la liste selectionnee pour retrouver l'objet de la collection
 * @param liste selectionnee
 * @return int index
 */
public int getIndexListe(JList<String> liste) {
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
		FenetreAffichage.dialogInfo("Veuillez s�lectionner le compte � partir duquel faire le virement");
	case 6 :
		clients = true;
		comptes = true;
		afficherListeClients();
		 break;
	case 7 : 
		comptes = false;
		clients = false;
		int res = FenetreAffichage.dialogConfirm("Etes-vous s�r de vouloir sauvegarder les donn�es ?");
		if(res == JOptionPane.OK_OPTION) {
			//on sauvegarde la liste clients
			if(DemoAppli.saveDataIntoFile("./assets/saveClients.txt"))
				FenetreAffichage.dialogInfo("La sauvegarde a bien �t� effectu�e");
		}
		
		 break;
	case 8 :
		comptes = false;
		clients = false;
		int result = FenetreAffichage.dialogConfirm("Etes-vous s�r de vouloir restaurer les donn�es sauvegard�es ?");
		if(result == JOptionPane.OK_OPTION) {
			//on restaure la liste clients
			DemoAppli.banquier.setClients(DemoAppli.restoreDataFromFile("./assets/saveClients.txt"));
			//on l'assigne au gestionnaire 
			afficherListeClients();
			FenetreAffichage.dialogInfo("La sauvegarde a �t� charg�e");
		}
		break;
	}
	
}


}
