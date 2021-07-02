package Ascension;

import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    private int idPlusProche=0;
    private double angle;
    private double distanceAvecPlusProche;
    private boolean estTape;
    
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
        this.rayonAttaque=66;
        this.avatar=avatar;
    }
    
    public boolean attaquePossibleSelonEtat(Avatar avatar){ //Ajouter la fonction etat par l'intermédiaire de la base de données SQL
        
        this.attaquePossible = false;
        if(avatar.getEtat() == 0){ //Récupération de l'état du joueur dans SQL
            attaquePossible = true;
        }
        return attaquePossible;
    }
    
    public void joueurPlusProche(Avatar avatar){ //Récupération de l'id des joueurs afin de pouvoir trouver la distance en utilisant la base SQL (Création d'une liste ou utilisation de la table SQL ?)        
        
        PreparedStatement requete;
        try {
            this.x=avatar.getX();
            this.y=avatar.getYmap();
            int id = avatar.getId();
            this.setIdPlusProche(0);
            int idPlusProche=0;
            requete = c.prepareStatement("SELECT id, x, y,vies FROM joueur");
            ResultSet resultat = requete.executeQuery();
            this.distanceAvecPlusProche=1000000000;
            while (resultat.next()) {
                int idEnnemie=resultat.getInt("id");
                int xEnnemie=resultat.getInt("x");
                int yEnnemie=resultat.getInt("y");
                int nbrVies = resultat.getInt("vies");
                if (id != idEnnemie && nbrVies>0){
                    
                    Double distance= Math.sqrt(Math.pow(x-xEnnemie,2)+Math.pow(y-yEnnemie,2)); //Récupérer chaque coordonée de x et de y en fonction des ID des joueurs afin d'obtenir toutes les distances entre les joueurs
                    if (distanceAvecPlusProche>=distance && distance<=this.rayonAttaque){
                        this.setIdPlusProche(idEnnemie);
                        System.out.println(idEnnemie);
                        this.distanceAvecPlusProche=distance;
                    }
                }
            }
            requete.close();
        } catch (SQLException ex) {
            Logger.getLogger(AccueilFrame.class.getName()).log(Level.SEVERE, null, ex);
        }  
        
        // Creation du Timer qui appelle this.actionPerformed() tous les 40 ms
    }
    
    
    
    public boolean peutAttaquer(Avatar avatar){                  // si il peut attaquer alors true
        boolean peutAttaquer=false;
        joueurPlusProche(avatar);
        if (this.idPlusProche!=0 && attaquePossibleSelonEtat(avatar)){
            peutAttaquer=true;
        }
        return peutAttaquer;
    }
    
    public boolean avatarEstTape(Avatar avatar){    // si avatar tapé alors true
        estTape=false;
        try {
            PreparedStatement requete = c.prepareStatement("SELECT etat FROM joueur WHERE id=?");
            requete.setInt(1, avatar.getId());
            ResultSet resultat2 = requete.executeQuery();
            while (resultat2.next()) {
                int direction = resultat2.getInt("etat");
                if (direction!=0){
                    this.estTape=true;
                    
                }
            }
            requete.close();
        } catch (SQLException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.estTape;
    }
    
    public int avatarEstTapeVers(Avatar avatar){
        int estTapeVers=0;
        try {
            PreparedStatement requete = c.prepareStatement("SELECT etat FROM joueur WHERE id=?");
            requete.setInt(1, avatar.getId());
            ResultSet resultat2 = requete.executeQuery();
            while (resultat2.next()) {
                int direction = resultat2.getInt("etat");
                if (direction!=0){
                    estTapeVers=direction;
                    
                }
            }
            requete.close();
        } catch (SQLException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return estTapeVers;
    }
    
    
    
    public int distanceTape(Avatar avatar){     //pour définir la distance de frappe
        int distanceTape=0;
        int perso=avatar.getPersonnage();
        return distanceTape=66;
    }
    
    public void tapeIdVers(int id){             // prend coordonnées de l'id pour déterminer l'état du joueur id qui est frappé
        int xId=0;
        int yId=0;
        this.angle=0;
        try {
            PreparedStatement requete = c.prepareStatement("SELECT x,y FROM joueur WHERE id=?");
            requete.setInt(1, id);
            ResultSet resultat2 = requete.executeQuery();
            while (resultat2.next()) {
                xId = resultat2.getInt("x");
                yId = resultat2.getInt("y");
            }
            requete.close();
        } catch (SQLException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        }
        double xPosi=xId-this.x;
        double yPosi=yId-this.y;
        System.out.println(xPosi);
        System.out.println(yPosi);

        int tapeVers=0;
        if(id==0){
            tapeVers = 1 + (int)(Math.random() * ((8 - 1) + 1));
            System.out.println("ici"+tapeVers);
        }else if(xPosi==0 && yPosi>0){
            tapeVers=7;
            System.out.println("ici"+tapeVers);
        }else if(xPosi==0 && yPosi<0){
            System.out.println("la");
            tapeVers=3;
            System.out.println("ici"+tapeVers);
        }else if(xPosi>0 && yPosi==0){
            tapeVers=1;
            System.out.println("ici"+tapeVers);
        }else if(xPosi<0 && yPosi==0){
            tapeVers=5;
            System.out.println("ici"+tapeVers);
        }else if( ((xPosi>0) && (yPosi<0)&& yPosi/xPosi>-1/2) || (xPosi>0 && yPosi>0 && (yPosi/xPosi<1/2)) ){
            tapeVers=1;
            System.out.println("ici"+tapeVers);
        }else if(xPosi>0 && yPosi<0 && yPosi/xPosi<=-1/2 && yPosi/xPosi  >=-2){
            tapeVers=2;
            System.out.println("ici"+tapeVers);
        }else if((xPosi>0 && yPosi<0 && yPosi/xPosi<-2)|| (xPosi<=0 && yPosi<=0 && yPosi/xPosi>2) ){
            System.out.println("ou la");
            tapeVers=3;
            System.out.println("ici"+tapeVers);
        }else if(xPosi<0 && yPosi<0 && yPosi/xPosi>=1/2   && yPosi/xPosi <= 2){
            tapeVers=4;
            System.out.println("ici"+tapeVers);
        }else if((xPosi<0 && yPosi<0 && yPosi/xPosi<1/2) || (xPosi<=0 && yPosi>=0 && yPosi/xPosi>-1/2 )){
            tapeVers=5;
            System.out.println("ici"+tapeVers);
        }else if(xPosi<0 && yPosi>0 && -1/2 >= yPosi/xPosi && yPosi/xPosi >= -2){
            tapeVers=6;
            System.out.println("ici"+tapeVers);
        }else if((xPosi>0 && yPosi>0 && yPosi/xPosi>2) || (xPosi<0 && yPosi>0 && yPosi/xPosi < -2) ){
            tapeVers=7;
            System.out.println("ici"+tapeVers);
        }else if(xPosi>0 && yPosi>0 && yPosi/xPosi>=1/2  && yPosi/xPosi<= 2){
            tapeVers=8;
            System.out.println("ici"+tapeVers);
        }else{
            System.out.println("Rien ne sepasse");
        }
        try{
            PreparedStatement requete3 = c.prepareStatement("UPDATE joueur SET etat=? WHERE id =?");
            requete3.setInt(1,tapeVers);
            requete3.setInt(2,id);
            requete3.executeUpdate();
            requete3.close();
        }catch (SQLException ex) {
            Logger.getLogger(SalonAttente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public double angle(int xAvatar, int yAvatar, int xEnnemi, int yEnnemi){
        double angle=Math.atan((yAvatar-yEnnemi)/(xAvatar-xEnnemi));
        return angle;
    }

    public void setIdPlusProche(int idPlusProche) {
        this.idPlusProche = idPlusProche;
    }
    
    public int getIdPlusProche() {
        return this.idPlusProche;
    }

    public double getDistanceAvecPlusProche() {
        return distanceAvecPlusProche;
    }

    public void setDistanceAvecPlusProche(int distanceAvecPlusProche) {
        this.distanceAvecPlusProche = distanceAvecPlusProche;
    }
    
    
    
    public int nbreJoueursRayonAttaque(int rayon){ //Etayer la fonction de manière à avoir le nombre de joueurs qui sont dans la zone de chaque joueur      //Utilisation rayon de SQL 
        int nombreJoueursDansZone = 0;
            if (distanceEntreJoueurs < rayon){
                nombreJoueursDansZone++;
            }
        return nombreJoueursDansZone;
    }
    
    public boolean presenceJoueursRayon(){
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
    
    
    public void actionPerformed(ActionEvent e) {
        
    }
}
