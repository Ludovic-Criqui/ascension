package Ascension;

import static java.lang.Math.sqrt;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Attaque {
    
    private boolean attaquePossible;
    private boolean attaqueDeGroupe;
    private boolean joueursDansRayon;
    private int typeAttaque;
    private int rayonAttaque;
    private int nombreJoueursDansZone;
    private int distanceEntreJoueurs;
    protected int x, y;
    private Avatar avatar;
    private Connection c;
    
// Voir la méthode de création des attaques afin de définir plusieurs attaques (2 de chaque type(unique et groupe))
/* Création table SQL d'attaque en définissant : (les x et y où sont positionner certains objets qui permettront d'avoir des attaques ?), le type d'attaque (attaque unique = 1 et attaque de groupe = 2) , 
le rayon de chaque attaque avec un entier, ce que fait l'attaque aux autres joueurs 
(1 = joueur ne peut plus bouger pdt x secondes, 2 = joueur tombe directement de x cases après attaque, 3 = joueur étourdi (exemple direction inversé)) 
    => création de méthodes pour définir ce que chaque attaque fait */
    
    public Attaque(){
        try {
            this.c = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20202021_s2_vs2_tp1_ascension?serverTimezone=UTC", "etudiant", "YTDTvj9TR3CDYCmP");
        } catch (SQLException ex) {
            Logger.getLogger(Attaque.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.rayonAttaque=50;
    }
    
    public boolean attaquePossibleSelonEtat(){ //Ajouter la fonction etat par l'intermédiaire de la base de données SQL
        this.attaquePossible = false;
        if(this.avatar.getEtat() == 0){ //Récupération de l'état du joueur dans SQL
            attaquePossible = true;
        }
        return attaquePossible;
    }
    
    public float distanceEntreJoueur(Avatar joueur){ //Récupération de l'id des joueurs afin de pouvoir trouver la distance en utilisant la base SQL (Création d'une liste ou utilisation de la table SQL ?)        
        int distance=(int) Math.sqrt((this.avatar.getX()-joueur.getX())^2+(this.avatar.getY()-joueur.getY())^2); //Récupérer chaque coordonée de x et de y en fonction des ID des joueurs afin d'obtenir toutes les distances entre les joueurs
        return distance;        
        //Création d'une boucle permettant de connaître la position de chaque joueur par rapport à un joueur
        //Calcul de la distance avec chaque joueur
 
    }
    
    public int nbreJoueursRayonAttaque(int rayon){ //Etayer la fonction de manière à avoir le nombre de joueurs qui sont dans la zone de chaque joueur      //Utilisation rayon de SQL 
        int nombreJoueursDansZone = 0;
            if (distanceEntreJoueurs < rayon){
                nombreJoueursDansZone++;
            }
        return nombreJoueursDansZone;
    }
    
    public boolean présenceJoueursRayon(){
        this.joueursDansRayon=true;
        if (this.nombreJoueursDansZone == 0){
            this.joueursDansRayon = false;
        }
        return joueursDansRayon;
    }

    public boolean attaqueDeGroupe(){
        this.attaqueDeGroupe = false;
        if (typeAttaque == 2){ //Possible ajouter dans table SQL Attaque
            attaqueDeGroupe = true;
        }
        return attaqueDeGroupe;
    }
    
//    public int joueurPlusProche(){ //Définir le choix de l'id d'un joueur et comparer sa distance avec les autres joueurs + faire de même pour tous les joueurs
//        //Possible d'utiliser directement les données de la base SQL en rappelant un attribut distance ? 
//        //Retourne l'entier ID du joueur le plus proche du joueur qui va venir réaliser l'attaque  
//    }
    
    public void attaquer(){ //Pouvoir utiliser l'attaque et affecter les autres joueurs
        if (attaquePossible == true){
            if (joueursDansRayon == true){
                if(attaqueDeGroupe == true){
                    //Le joueur attaque et immobilise les autres joueurs 
                }
                else{
                    //Le joueur attaque le joueur le plus proche de lui en réutilisant la méthode joueurPlusProche
                }
            }
        }
    }
}
