package View;

public class ConsoleAffichage {

	public void afficherChoix(){
		System.out.println("\nBANQUE QUIPROQUO");
		System.out.println("=====================================================================");
		System.out.println("\nQue voulez-vous faire ? \n");
	}
	
	public void afficherChoixGestionnaire(){
		
	System.out.println(
			 "\n1. Lister tous les clients\n"
			+ "2. Lister les comptes des clients\n"
			+ "3. Afficher le solde cumulé de tous les comptes de la liste de clients\n"
			+ "4. Sélectionner un client\n"
			+ "5. Supprimer un client\n"
			+ "6. Sauvegarder les données clients\n"
			+ "7. Afficher les données sauvegardées\n"
			+ "0. Quitter\n");
	}
	
	public void afficherChoixManipComptes(){
		System.out.println(
				"\n0. Revenir en arriere\n"
				+ "1. Faire un dépôt\n"
				+ "2. Faire un retrait\n"
				+ "3. Faire un virement\n"
				+ "4. Afficher le solde cumulé\n"
				+ "5. Afficher tous les comptes\n");
		
	}
	
}
