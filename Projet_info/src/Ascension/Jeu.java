package Ascension;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Jeu {

    private BufferedImage mario0, mario1,  mario2 , steve, steve1,steve2, amongus1,amongus2, amongus,link0,link1,link2, vie, mort, podium,imAvatar,imEnnemi;
    public Avatar avatar;
    private Connection c;
    public Carte carte;
    private int personnageAvatar;
    public Regles regles;
    public int sens=0;
    private int nombreViesEnnemi;

    public Jeu() {
        try {
            this.c = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20202021_s2_vs2_tp1_ascension?serverTimezone=UTC", "etudiant", "YTDTvj9TR3CDYCmP");
        } catch (SQLException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.carte = new Carte();
        try {
//            this.mario = ImageIO.read(new File("mario.png"));
//            this.steve = ImageIO.read(new File("steve.png"));
//            this.amongus = ImageIO.read(new File("amongus.png"));
////            this.ratchet = ImageIO.read(new File("ratchet.png"));
//            this.mario0 = ImageIO.read(new File("mario0.png"));
//            this.mario2 = ImageIO.read(new File("mario2.png"));
//            this.mario1 = ImageIO.read(new File("mario1.png"));
//            this.steve = ImageIO.read(new File("steve.png"));
//            this.steve1 = ImageIO.read(new File("steve1.png"));
//            this.steve2 = ImageIO.read(new File("steve2.png"));
//            this.amongus = ImageIO.read(new File("amongus.png"));
//            this.amongus2 = ImageIO.read(new File("amongus2.png"));
//            this.amongus1 = ImageIO.read(new File("amongus1.png"));
//            this.link0 = ImageIO.read(new File("link0.png"));
//            this.link2 = ImageIO.read(new File("link2.png"));
//            this.link1 = ImageIO.read(new File("link1.png"));
//            this.vie = ImageIO.read(new File("vie.png"));
            this.mort = ImageIO.read(new File("mort.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.avatar = new Avatar();
        this.regles=new Regles();
    }

    public Connection getC() {
        return c;
    }

        public Carte getCarte() {
        return carte;
    }

    public Avatar getAvatar() {
        return avatar;
    }
        
        

    public void miseAJour() throws InterruptedException {
        this.avatar.miseAJour();
//        if (this.regles.gagneTotalement(this.avatar)==true)
    }

    public void rendu(Graphics2D contexte) throws IOException {
        this.carte.rendu(contexte,this.avatar.getYmap());
        
        miseJourCoordonnéesSurSQL();
        mettreAJourVieAvatar();
        rafraichirPodium(contexte);
        afficherPodium(contexte);
        persoAvatar();
//        afficherViePerso(contexte);
        afficherPersoAvatar(contexte);
        afficherAutresJoueursVivants(contexte);
        afficherAutresJoueurMort(contexte);
//        afficherVieAutres(contexte);
        
    }
    
    public void afficherPodium(Graphics2D contexte){
        try {
//            PreparedStatement requete4 = c.prepareStatement("SELECT podium FROM joueur WHERE id=?");
//            ResultSet resultat4 = requete4.executeQuery();
            int nombrePodium1=this.avatar.getPodium(); //afficher le classement du joueur
            String nombrePodium2=String.valueOf(nombrePodium1);
            String nombrePodium3="podium".concat(nombrePodium2);
            String nombrePodium5=nombrePodium3.concat(".png");
            this.podium = ImageIO.read(new File(nombrePodium5));
            if (nombrePodium1==4){
//            if (regles.gagneTotalement(this.avatar)){
                contexte.drawImage(podium, 25, 37, 35, 35, null);
//            }
            } else {
//            else if (regles.gagneTemporairement(this.avatar)) {
                contexte.drawImage(podium, 25, 35, 30, 42, null);
//            }
            }
        } catch (IOException ex) {
                Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void rafraichirPodium(Graphics2D contexte){
        try {
            
            PreparedStatement requete4 = c.prepareStatement("SELECT id,vies FROM joueur ORDER BY y DESC");
            ResultSet resultat4 = requete4.executeQuery();
            int compteur = 1;
            while (resultat4.next()) {
                int autreId = resultat4.getInt("id");
                int viesJoueur = resultat4.getInt("vies");
                PreparedStatement requete5 = c.prepareStatement("UPDATE joueur SET podium = ? WHERE id = ? AND vies != 0");
//                if (this.avatar.getId()==autreId){
                requete5.setInt(1, compteur);
                requete5.setInt(2, autreId);
                if (this.avatar.getId()==autreId){
                    this.avatar.setPodium(compteur);
                }
                requete5.executeUpdate();
                requete5.close();
                compteur += 1;
            }
            requete4.close();
        } catch (SQLException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void miseJourCoordonnéesSurSQL(){
        try {
            PreparedStatement requete = c.prepareStatement("UPDATE joueur SET x = ?, y = ? WHERE id = ?");
            requete.setInt(1, this.avatar.getX());
            requete.setInt(2, this.avatar.getYmap());
            requete.setInt(3, this.avatar.getId());
            requete.executeUpdate();
            requete.close();
        } catch (SQLException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void persoAvatar(){
        try {
            PreparedStatement requete3 = c.prepareStatement("SELECT personnage FROM joueur WHERE id=?");
            requete3.setInt(1, this.avatar.getId());
            ResultSet resultat2 = requete3.executeQuery();
            while (resultat2.next()) {
                personnageAvatar = resultat2.getInt("personnage");
            }
            requete3.close();
        } catch (SQLException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    public void afficherViePerso(Graphics2D contexte) {
//        try {
//            int nombreVie1 = this.avatar.getVie();
//            String nombreVie2 = String.valueOf(nombreVie1);
//            String nombreVie3 = "vie".concat(nombreVie2);
//            String nombreVie5 = nombreVie3.concat(".png");
//            this.vie = ImageIO.read(new File(nombreVie5));
//            if (nombreVie1 == 0) {
//                contexte.drawImage(this.mort, this.avatar.getX() - 5, 385, 400, 400, null);
//            } else {
//                contexte.drawImage(this.vie, this.avatar.getX() + 5, 385, 33, 10, null);
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
//    public void afficherVie(Graphics2D contexte){
//        try {
//            int nombreVie1=this.avatar.getVie();
//            String nombreVie2=String.valueOf(nombreVie1);
//            String nombreVie3="vie".concat(nombreVie2);
//            String nombreVie5=nombreVie3.concat(".png");
//            this.vie = ImageIO.read(new File(nombreVie5));
//            if (nombreVie1==0){
//                contexte.drawImage(this.mort, 200, 200, 400, 400, null);
//            }
//            else {
//                contexte.drawImage(this.vie, 70, 50, 50, 15, null);
//            }
//        } catch (IOException ex) {
//                Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
    public void afficherPersoAvatar(Graphics2D contexte) throws IOException{
        recupererSens();
        if (this.regles.avatarMort(this.avatar)==false){
            String personnageJoueur2=String.valueOf(this.avatar.getPersonnage());
            String sens2=String.valueOf(sens);
            String sens3=personnageJoueur2.concat(sens2);
            String sens4=sens3.concat(".png");
            this.imAvatar = ImageIO.read(new File(sens4));
            contexte.drawImage(imAvatar, this.avatar.getX(), 400, 50, 50, null);
        } else{
            String sens2=String.valueOf(sens);
            String sens3="Boo".concat(sens2);
            String sens4=sens3.concat(".png");
            this.imAvatar = ImageIO.read(new File(sens4));
            contexte.drawImage(imAvatar, this.avatar.getX(), 400, 50, 50, null);
        }
    }
        
    
     public void rafraichirVie(Graphics2D contexte) {
        try {

            PreparedStatement requete4 = c.prepareStatement("SELECT vies FROM joueur WHERE id = ?");
            requete4.setInt(1, this.avatar.getId());
            ResultSet resultat4 = requete4.executeQuery();
            while (resultat4.next()) {
                int nbVies = resultat4.getInt("vies");
                this.avatar.setVie(nbVies);
            }
            requete4.close();
        } catch (SQLException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void recupererSens(){
        try {
            PreparedStatement requete3 = c.prepareStatement("SELECT sens FROM joueur WHERE id=?");
            requete3.setInt(1, this.avatar.getId());
            ResultSet resultat2 = requete3.executeQuery();
            while (resultat2.next()) {
                this.sens = resultat2.getInt("sens");
            }
            requete3.close();
        } catch (SQLException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void afficherAutresJoueursVivants(Graphics2D contexte) throws IOException{
        try {   
            PreparedStatement requete2 = c.prepareStatement("SELECT id, pseudo, personnage, x, y, vies, sens FROM joueur WHERE vies != 0");
            ResultSet resultat = requete2.executeQuery();
            while (resultat.next()) {
                int idjoueur = resultat.getInt("id");
                String pseudo = resultat.getString("pseudo");
                int personnageJoueur = resultat.getInt("personnage");
                int abscisse = resultat.getInt("x");
                int ordonnee = resultat.getInt("y");
                int nbVies = resultat.getInt("vies");
                int sens = resultat.getInt("sens");
                String personnageJoueur2=String.valueOf(personnageJoueur);
                String sens2=String.valueOf(sens);
                String sens3=personnageJoueur2.concat(sens2);
                String sens4=sens3.concat(".png");
                this.imEnnemi = ImageIO.read(new File(sens4));
                Font ecriture = new Font("Courier", Font.BOLD, 16);
                contexte.setFont(ecriture);
                contexte.drawString(pseudo, abscisse + 19 - 4 * (pseudo.length() - 1), this.avatar.getYmap() - ordonnee + 460);
                String nombreVie2 = String.valueOf(nbVies);
                String nombreVie3 = "vie".concat(nombreVie2);
                String nombreVie5 = nombreVie3.concat(".png");
                this.vie = ImageIO.read(new File(nombreVie5));
                contexte.drawImage(this.vie, abscisse + 5, this.avatar.getYmap() - ordonnee + 385, 33, 10, null);
                if (idjoueur!=this.avatar.getId()){
                    contexte.drawImage(imEnnemi, abscisse,this.avatar.getYmap()-ordonnee+400, 50, 50, null);
                    
                }
            }
            requete2.close();
        } catch (SQLException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void afficherAutresJoueurMort(Graphics2D contexte) throws IOException{
        try {   
            PreparedStatement requete2 = c.prepareStatement("SELECT id, pseudo, x, y, sens FROM joueur WHERE vies = 0");
            ResultSet resultat = requete2.executeQuery();
            while (resultat.next()) {
                int idjoueur = resultat.getInt("id");
                String pseudo = resultat.getString("pseudo");
                int abscisse = resultat.getInt("x");
                int ordonnee = resultat.getInt("y");
                int sens = resultat.getInt("sens");
                String sens2=String.valueOf(sens);
                String sens3="Boo".concat(sens2);
                String sens4=sens3.concat(".png");
                this.imEnnemi = ImageIO.read(new File(sens4));
                Font ecriture = new Font("Courier", Font.BOLD, 16);
                contexte.setFont(ecriture);
                contexte.drawString(pseudo, abscisse + 19 - 4 * (pseudo.length() - 1), this.avatar.getYmap() - ordonnee + 460);
                if (idjoueur!=this.avatar.getId()){
                    contexte.drawImage(imEnnemi, abscisse,this.avatar.getYmap()-ordonnee+400, 50, 50, null);
                }
            }
            requete2.close();
        } catch (SQLException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    public void afficherVieAutres(Graphics2D contexte) throws IOException {
//        rafraichirVie(contexte);
//        try {
//            PreparedStatement requete2 = c.prepareStatement("SELECT id, vies, x, y FROM joueur");
//            ResultSet resultat = requete2.executeQuery();
//            while (resultat.next()) {
//                int nbVies = resultat.getInt("vies");
//                int idJoueur = resultat.getInt("id");
//                int abscisse = resultat.getInt("x");
//                int ordonnee = resultat.getInt("y");
//                if (idJoueur == 1 && idJoueur != this.avatar.getId()) {
//                    String nombreVie2 = String.valueOf(nbVies);
//                    String nombreVie3 = "vie".concat(nombreVie2);
//                    String nombreVie5 = nombreVie3.concat(".png");
//                    this.vie = ImageIO.read(new File(nombreVie5));
//                    if (nbVies == 0) {
//                        contexte.drawImage(this.mort, abscisse - 5, this.avatar.getYmap() - ordonnee + 385, 400, 400, null);
//                    } else {
//                        contexte.drawImage(this.vie, abscisse + 5, this.avatar.getYmap() - ordonnee + 385, 33, 10, null);
//                    }
//                }
//                if (idJoueur == 2 && idJoueur != this.avatar.getId()) {
//                    String nombreVie2 = String.valueOf(nbVies);
//                    String nombreVie3 = "vie".concat(nombreVie2);
//                    String nombreVie5 = nombreVie3.concat(".png");
//                    this.vie = ImageIO.read(new File(nombreVie5));
//                    if (nbVies == 0) {
//                        contexte.drawImage(this.mort, abscisse - 5, this.avatar.getYmap() - ordonnee + 385, 400, 400, null);
//                    } else {
//                        contexte.drawImage(this.vie, abscisse + 5, this.avatar.getYmap() - ordonnee + 385, 33, 10, null);
//                    }
//                }
//                if (idJoueur == 3 && idJoueur != this.avatar.getId()) {
//                    String nombreVie2 = String.valueOf(nbVies);
//                    String nombreVie3 = "vie".concat(nombreVie2);
//                    String nombreVie5 = nombreVie3.concat(".png");
//                    this.vie = ImageIO.read(new File(nombreVie5));
//                    if (nbVies == 0) {
//                        contexte.drawImage(this.mort, abscisse - 5, this.avatar.getYmap() - ordonnee + 385, 400, 400, null);
//                    } else {
//                        contexte.drawImage(this.vie, abscisse + 5, this.avatar.getYmap() - ordonnee + 385, 33, 10, null);
//                    }
//                }
//                if (idJoueur == 4 && idJoueur != this.avatar.getId()) {
//                    String nombreVie2 = String.valueOf(nbVies);
//                    String nombreVie3 = "vie".concat(nombreVie2);
//                    String nombreVie5 = nombreVie3.concat(".png");
//                    this.vie = ImageIO.read(new File(nombreVie5));
//                    if (nbVies == 0) {
//                        contexte.drawImage(this.mort, abscisse - 5, this.avatar.getYmap() - ordonnee + 385, 400, 400, null);
//                    } else {
//                        contexte.drawImage(this.vie, abscisse + 5, this.avatar.getYmap() - ordonnee + 385, 33, 10, null);
//                    }
//                }
//            }
//            requete2.close();
//        } catch (SQLException ex) {
//            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    public void setPersonnageAvatar(int personnageAvatar) {
        this.personnageAvatar = personnageAvatar;
    }
    
    public boolean finirPartie(){
        boolean partieFinie=false;
        try {
            
            PreparedStatement requete = c.prepareStatement("SELECT id FROM joueur WHERE aFini = 1");
            ResultSet resultat = requete.executeQuery();
            int compteur1 = 1;
            while (resultat.next()) {
                compteur1 += 1;
            }
            requete.close();
            PreparedStatement requete1 = c.prepareStatement("SELECT id FROM joueur");
            ResultSet resultat1 = requete1.executeQuery();
            int compteur2 = 1;
            while (resultat1.next()) {
                compteur2 += 1;
            }
            requete.close();
            if (compteur1 ==compteur2 ){
                partieFinie=true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return partieFinie;
    }
    
    public void enleverVie(int id){
        recupererVie(id);
        if (nombreViesEnnemi>0){
            System.out.println(nombreViesEnnemi);
            int nouveauNbrVies=nombreViesEnnemi-1;
            try{
                PreparedStatement requete = c.prepareStatement("UPDATE joueur SET vies = ? WHERE id = ?");
                requete.setInt(1,nouveauNbrVies);
                requete.setInt(2, id);
                requete.executeUpdate();
                requete.close();
            }catch (SQLException ex) {
                Logger.getLogger(SalonAttente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void enleverPodium(int id){
        recupererVie(id);
        if (nombreViesEnnemi==0){
            try{
                PreparedStatement requete = c.prepareStatement("UPDATE joueur SET podium = 0 WHERE id = ?");
                requete.setInt(1, id);
                requete.executeUpdate();
                requete.close();
            }catch (SQLException ex) {
                Logger.getLogger(SalonAttente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public int recupererVie(int id){
        
        PreparedStatement requete;
        try {
            requete = c.prepareStatement("SELECT vies FROM joueur WHERE id=?");
            requete.setInt(1, id);
            ResultSet resultat = requete.executeQuery();
            while (resultat.next()) {
                nombreViesEnnemi = resultat.getInt("vies");
            }
            requete.close();
        } catch (SQLException ex) {
            Logger.getLogger(AcceuilFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nombreViesEnnemi;
    }
    
    public void mettreAJourVieAvatar(){
        this.avatar.setVie(recupererVie(this.avatar.getId()));
    }
}
