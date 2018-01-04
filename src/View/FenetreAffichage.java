package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class FenetreAffichage extends JFrame{
	
	private JTextField choixMenuText;
	private JTextArea menu;
	private JButton valider;
	private MaskFormatter chiffre;
	private JFormattedTextField jtf;
	
	
	public FenetreAffichage(){
		this.setTitle("Ma banque");
		this.setSize(800, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		initComponents();
		this.setVisible(true);
	}
	
	public void initComponents(){
		
		//texte menuAction
		JPanel panMenu = new JPanel();
	    panMenu.setBackground(Color.white);
	    panMenu.setPreferredSize(new Dimension(420, 200));
	    panMenu.setBorder(BorderFactory.createTitledBorder("Menu actions"));
	    JPanel panMenuBis = new JPanel();
	    panMenuBis.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
	    panMenuBis.add(panMenu);
	    
	    menu = new JTextArea(	    
	    		"1. Lister tous les clients\n"
				+ "2. Lister les comptes des clients\n"
				+ "3. Afficher le solde cumulé de tous les comptes de la liste de clients\n"
				+ "4. Sélectionner un client\n"
				+ "5. Supprimer un client\n"
				+ "6. Sauvegarder les données clients\n"
				+ "7. Afficher les données sauvegardées\n"
				+ "0. Quitter\n", 8, 30);
	    menu.setEditable(false);
	    //panMenu.add(titreMenu);
		panMenu.add(menu);
		
		
		//Ligne de texte pour selectionner parmi la liste
		JPanel choix = new JPanel();
		choix.setPreferredSize(new Dimension(250, 35));
		choix.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
		try{
			  chiffre = new MaskFormatter("#");
			  jtf = new JFormattedTextField(chiffre);
			}catch(ParseException e){
				e.printStackTrace();
				}
		
	    jtf.setPreferredSize(new Dimension(200, 25));
	  
	    choix.add(jtf);
	    
	    
	    //Bouton valider
	    JPanel bouton = new JPanel();
	    bouton.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
	    valider = new JButton("Valider");
	    valider.setPreferredSize(new Dimension(100, 35));
	    bouton.add(valider);
	    

		//Mise en place du panel de menu principal
	    JPanel content = new JPanel(new BorderLayout());
	    content.setBackground(Color.white);
	    content.add(panMenuBis, BorderLayout.NORTH);
	    content.add(choix, BorderLayout.CENTER);
	    content.add(bouton, BorderLayout.SOUTH);
	    
	    
	    
	    this.getContentPane().add(content, BorderLayout.WEST);
	}

}
