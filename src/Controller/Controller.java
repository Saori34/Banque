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
	static CompteCourant compte;
	
	private boolean clients = false;
	private boolean comptes = false;
	private boolean retrait = false;
	private boolean depot = false;
	
	
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
					try {
						int montant = Integer.parseInt(texte);
						if(montant < 0 ){
							FenetreAffichage.dialogErreurChiffre("Le montant ne doit pas être inférieur à 0");
						}
						if(retrait) {
							compte = ((ArrayList<CompteCourant>) (((ArrayList<Client>) DemoAppli.banquier.getClients()).get(getIndexListe(affichageComptes))).getListeComptes()).get(getIndexListe(affichageDetails));
							if(compte.retrait(montant)) {
								jtf.setText("");
								FenetreAffichage.dialogInfo("Retrait de " + montant + "€ effectué");
								affichageDetails.setListData(recupClient().listerComptes());
							}else {
								FenetreAffichage.dialogInfo("Le retrait n'a pas pu être effectué");
							}
						}
						else if(depot) {
							compte = ((ArrayList<CompteCourant>) (((ArrayList<Client>) DemoAppli.banquier.getClients()).get(getIndexListe(affichageComptes))).getListeComptes()).get(getIndexListe(affichageDetails));
							compte.depot(montant);
							jtf.setText("");
							FenetreAffichage.dialogInfo("Dépôt de " + montant + "€ effectué");
							affichageDetails.setListData(recupClient().listerComptes());
						}
						
					}catch(NumberFormatException e) {
						FenetreAffichage.dialogErreurChiffre("Le montant doit être un nombre supérieur à 0");
					}
				}else{		
					FenetreAffichage.dialogErreurChiffre("Veuillez taper un montant supérieur à 0");
				}	
		}else if(arg0.getSource() == jtf){
			
		}

	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		JList<String> liste = (JList<String>) arg0.getSource();
		if(arg0.getValueIsAdjusting() == false) {
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
					if(clients) {
						affichageDetails.setListData(recupClient().listerComptes());
						comptes = true;
					}
					break;
				case "affichageDetails" :
					//si on clique sur un élement de la liste de droite en bas
					if(comptes) {
							recupCompte();
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
		Collection<CompteCourant>comptes = new ArrayList<>();
		comptes = recupClient().getListeComptes(); 
		compte = ((ArrayList<CompteCourant>) comptes).get(getIndexListe(affichageDetails));
		return compte;
	}
	return null;
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
	
	case 0 : clients = true;
			comptes = false;
			affichageComptes.setListData(DemoAppli.banquier.listerClientsGUI());
			 break;
	case 1 : comptes = true;
			clients = false;
			affichageComptes.setListData(DemoAppli.banquier.listerComptes());
			 break;
	case 2 :comptes = false;
			clients = false;
			String[] list = {"Solde total tous comptes : " + df.format(DemoAppli.banquier.soldeTotalComptes()) + " euros"};
			affichageComptes.setListData(list);
			 break;
	case 3 : clients = true;
			comptes = false;
			 retrait = true;
			 depot = false;
			 affichageComptes.setListData(DemoAppli.banquier.listerClientsGUI());
			 break;
	case 4 : clients = true;
			comptes = false;
			retrait = false;
			depot = true;
			affichageComptes.setListData(DemoAppli.banquier.listerClientsGUI());
			break;
	case 5 : clients = true;
			comptes = true;
			affichageComptes.setListData(DemoAppli.banquier.listerClientsGUI());
			if(getIndexListe(affichageComptes)>= 0) {
				int res = FenetreAffichage.dialogConfirm();
				if(res == JOptionPane.OK_OPTION) {
					DemoAppli.banquier.supprimerClient((((ArrayList<Client>) DemoAppli.banquier.getClients()).get(getIndexListe(affichageComptes))));
			}
				}
			 break;
	case 6 : comptes = false;
			clients = false;
			DemoAppli.saveDataIntoFile("./assets/saveClients.txt");
			 break;
	}

}


}
