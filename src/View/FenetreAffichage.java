package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import Controller.DemoAppli;

public class FenetreAffichage extends JFrame{
	
	private JTextArea menu, affichageComptes;
	private JButton valider;
	private MaskFormatter chiffre;
	private JFormattedTextField jtf;
	
	
	
	public FenetreAffichage(){
		this.setTitle("Ma banque");
		this.setSize(1200, 800);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		initComponents();
		this.setVisible(true);
	}
	
	public void initComponents(){
		//Conteneur principal
		JPanel content = new JPanel(new BorderLayout());
	    content.setBackground(Color.white);
		
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
	  
		panMenu.add(menu);
		 
		
		//Ligne de texte pour rentrer le choix de la liste
		JPanel choix = new JPanel();
		choix.setPreferredSize(new Dimension(250, 35));
		choix.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
		try{
			  chiffre = new MaskFormatter("#");
			  chiffre.setPlaceholder("8");
			  jtf = new JFormattedTextField(chiffre);
			  
			}catch(ParseException e){
				System.err.println("Erreur dans le JFormattedTextField");
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
	    
	    content.add(panMenuBis, BorderLayout.NORTH);
	    content.add(choix, BorderLayout.CENTER);
	    content.add(bouton, BorderLayout.SOUTH);
	    
	    
	    
	    this.getContentPane().add(content, BorderLayout.WEST);
	    
	    valider.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String texte = jtf.getText();
				int choix = Integer.parseInt(texte);
				
				if(choix == 8){
					JOptionPane erreur = new JOptionPane();
					JOptionPane.showMessageDialog(null, "Vous devez saisir un chiffre entre 0 et 7", "Erreur", JOptionPane.ERROR_MESSAGE);
					
				}else{
					initPanneauDroite();
					choixBouton();				
				}
			}	
	    });

	}
	
	public void initPanneauDroite(){
		//Creation d'un panneau à droite pour l'affichage
		JPanel panneauDroite = new JPanel();
		panneauDroite.setBackground(Color.WHITE);
	    panneauDroite.setPreferredSize(new Dimension(420, 200));
	    panneauDroite.setBorder(BorderFactory.createTitledBorder("Affichage"));
	    JPanel panneauDroiteBis = new JPanel();
	    panneauDroiteBis.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
	    panneauDroiteBis.add(panneauDroite);
	    //zone de texte
		affichageComptes = new JTextArea();
		affichageComptes.setEditable(false);
		panneauDroite.add(affichageComptes);
		//Ajout au panneau principal
		this.getContentPane().add(panneauDroite, BorderLayout.EAST);
		this.setVisible(true);
	
	}
	
	public void choixBouton(){
		String texte = jtf.getText();
		int choix = Integer.parseInt(texte);
		switch(choix){
		
		case 1 : affichageComptes.setText(DemoAppli.banquier.listerClientsGUI());
				 break;/*
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
								 case 3 : System.out.println("Pour faire un virement d'un compte A à un compte B : ");
								 		  System.out.println("Sélectionnez compte A : ");
								 		  compte = client.selectionnerCompte();
								 		  System.out.println("Sélectionnez compte B : ");
								 		  CompteCourant compteB = client.selectionnerCompte();
						 		  		  montant = banquier.saisieMontant();
						 		  		  compte.virementSur(compteB, montant);
						 		  		  client.listerComptes();
									 	  break;
								 case 4 : System.out.println("Solde total tous comptes : " + df.format(banquier.SoldeTotalClient(client)) + " euros \n");
									 	  break;
								 case 5 : client.listerComptes();
									 	  break;
								default : System.err.println("Vous devez taper le numero correspondant à votre choix");
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
		default : System.err.println("Vous devez taper le numero correspondant à votre choix");
				  break;
		*/
		}
	}

}
