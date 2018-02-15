package View;

/**
 * Classe représentant la vue
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;



public class FenetreAffichage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5365713977188630998L;
	private static JList<String> menu;
	private static JList<String> affichageComptes;
	private static JList<String> affichageDetails;
	private static JButton valider;
	private static JFormattedTextField jtf;
	private Dimension dim;
	private static Container c;

	public FenetreAffichage() {

		this.setTitle("Ma banque");
		this.setSize(1200, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initComponents();
		this.setVisible(true);
	}

	/**
	 * Bouton valider
	 * @return le bouton
	 */
	public static JButton getValider() {
		return valider;
	}
	
	/**
	 * @return the JList menu
	 */
	public static JList<String> getMenu() {
		return menu;
	}

	/**
	 * @return the JList affichageComptes
	 */
	public static JList<String> getAffichageComptes() {
		return affichageComptes;
	}

	/**
	 * @return the JList affichageDetails
	 */
	public static JList<String> getAffichageDetails() {
		return affichageDetails;
	}
	
	/**
	 * Champs de texte pour rentrer le choix du menu
	 * @return le champs de texte
	 */
	public static JFormattedTextField getJtf() {
		return jtf;
	}

	/**
	 * Retourne la JFrame
	 * @return Container
	 */
	public static Container getJframe() {
		return c;
	}

	public void initComponents() {
		// Conteneur principal
		c = this.getContentPane();

		//panneau gauche
		JPanel panneauGauche = new JPanel(new BorderLayout());
		
		// texte menuAction
		JPanel panMenu = new JPanel();
		panMenu.setBackground(Color.white);
		panMenu.setPreferredSize(new Dimension(420, 200));
		panMenu.setBorder(BorderFactory.createTitledBorder("Menu actions"));
		//panneau pour créer des marges
		JPanel panMenuBis = new JPanel();
		panMenuBis.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
		panMenuBis.add(panMenu);
		

		menu = new JList<String>();
		String[]menuListe = {"1. Lister tous les clients", "2. Lister les comptes des clients",
				"3. Afficher le solde cumulé de tous les comptes de la liste de clients",
				"4. Effectuer un retrait sur un compte", "5. Effectuer un depôt sur un compte", "6. Effectuer un virement d'un compte à un autre", "7. Supprimer un client", 
				"8. Sauvegarder les données clients", "9. Restaurer les données sauvegardées"};
		menu.setListData(menuListe);
		panMenu.add(menu);

		// Ligne de texte pour rentrer les montants 
		JPanel choix = new JPanel();
		choix.setPreferredSize(new Dimension(250, 35));
		choix.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
		jtf = new JFormattedTextField();
		jtf.setPreferredSize(new Dimension(200, 25));
		choix.add(jtf);

		// Bouton valider
		JPanel bouton = new JPanel();
		bouton.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
		valider = new JButton("Valider le montant");
		valider.setPreferredSize(new Dimension(150, 35));
		bouton.add(valider);

		// Mise en place du panel de menu principal

		panneauGauche.add(panMenuBis, BorderLayout.NORTH);
		panneauGauche.add(choix, BorderLayout.CENTER);
		panneauGauche.add(bouton, BorderLayout.SOUTH);

		c.add(panneauGauche, BorderLayout.WEST);

		// centrer la fenêtre
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getWidth() / 2, dim.height / 2 - this.getHeight() / 2);
		
		
		
		
		
		// Creation d'un panneau à droite pour l'affichage
		JPanel panneauDroite = new JPanel(new BorderLayout());

		//menu de droite en haut pour les clients et les comptes
		JPanel panneauDroiteHaut = new JPanel();
		panneauDroiteHaut.setPreferredSize(new Dimension(520, 200));
		panneauDroiteHaut.setBackground(Color.WHITE);
		
		//panneau pour les marges 
		JPanel panneauDroiteHautBis = new JPanel();
		panneauDroiteHautBis.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
				
		
		//menu de droite en bas pour les comptes ou les details
		JPanel panneauDroiteBas = new JPanel();
		panneauDroiteBas.setPreferredSize(new Dimension(520, 200));
		panneauDroiteBas.setBackground(Color.WHITE);
		
		//panneau pour les marges
		JPanel panneauDroiteBasBis = new JPanel();
		panneauDroiteBasBis.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
		
		
		
		// zone de texte 1
		affichageComptes = new JList<>();
		JScrollPane scroll = new JScrollPane(affichageComptes);
		scroll.setPreferredSize(new Dimension(520, 200));
		panneauDroiteHaut.add(scroll);
		//zone de texte 2
		affichageDetails = new JList<>();
		JScrollPane scrollBis = new JScrollPane(affichageDetails);
		scrollBis.setPreferredSize(new Dimension(520, 200));
		panneauDroiteBas.add(scrollBis);
		
		panneauDroiteHautBis.add(panneauDroiteHaut);
		panneauDroiteBasBis.add(panneauDroiteBas);
		
		//ajout des 2 panneaux d'affichage au panneau de droite
		panneauDroite.add(panneauDroiteHautBis, BorderLayout.NORTH);
		panneauDroite.add(panneauDroiteBasBis, BorderLayout.SOUTH);
		
		
		// Ajout au panneau principal
		c.add(panneauDroite, BorderLayout.EAST);
		c.setVisible(true);

	}

/**
 * affiche une fenetre d'erreur
 * @param erreur String à fournir
 */
	public static void dialogErreur(String erreur) {
		JOptionPane.showMessageDialog(null, erreur, "Erreur",
				JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * affiche une fenetre d'information
	 * @param info String à fournir
	 */
	public static void dialogInfo(String info) {
		JOptionPane.showMessageDialog(null, info, "Information",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * affiche une fenetre de confirmation pour supprimer un client
	 * @return code de confirmation ou d'annulation
	 */
	public static int dialogConfirm(String string) {
		JOptionPane jop = new JOptionPane();			
		return JOptionPane.showConfirmDialog(null, string, "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	}

}
