package Ascension;

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

    private BufferedImage mario, steve, amongus, ratchet, vie,mort;
    public Avatar avatar;
    private Connection c;
    private Carte carte;
    private int personnageAvatar;
    private Règles regles;

    public Jeu() {
        try {
            this.c = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20202021_s2_vs2_tp1_ascension?serverTimezone=UTC", "etudiant", "YTDTvj9TR3CDYCmP");
        } catch (SQLException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.carte = new Carte();
        try {
            this.mario = ImageIO.read(new File("mario.png"));
            this.steve = ImageIO.read(new File("steve.png"));
            this.amongus = ImageIO.read(new File("amongus.png"));
            this.ratchet = ImageIO.read(new File("ratchet.png"));
//            this.vie = ImageIO.read(new File("vie.png"));
            this.mort = ImageIO.read(new File("mort.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.avatar = new Avatar();
        this.regles=new Règles();
    }

    public Connection getC() {
        return c;
    }

        public Carte getCarte() {
        return carte;
    }

    public void miseAJour() throws InterruptedException {
        this.avatar.miseAJour();
//        if (this.regles.gagneTotalement(this.avatar)==true)
    }

    public void rendu(Graphics2D contexte) {
        this.carte.rendu(contexte,this.avatar.getYmap());

        try {
            PreparedStatement requete = c.prepareStatement("UPDATE joueur SET x = ?, y = ? WHERE id = ?");
            requete.setInt(1, this.avatar.getX());
            requete.setInt(2, this.avatar.getYmap());
            requete.setInt(3, this.avatar.getId());
//            System.out.println(regles.gagneTotalement(this.avatar));
//            System.out.println(regles.gagneTemporairement(this.avatar));
            if (regles.gagneTotalement(this.avatar)){
                contexte.drawImage(mario, this.avatar.getX(), 400, 100, 100, null);
            }
            else if (regles.gagneTemporairement(this.avatar)) {
                contexte.drawImage(mario, this.avatar.getX(), 400, 100, 100, null);
            }
//            System.out.println(this.avatar.getX());
//            System.out.println(this.avatar.getY());
            requete.executeUpdate();
            requete.close();
        } catch (SQLException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            PreparedStatement requete3 = c.prepareStatement("SELECT personnage FROM joueur");
            ResultSet resultat2 = requete3.executeQuery();
            while (resultat2.next()) {
                personnageAvatar = resultat2.getInt("personnage");
            }
            requete3.close();
        } catch (SQLException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            int nombreVie1=this.avatar.getVie();
            String nombreVie2=String.valueOf(nombreVie1);
            String nombreVie3="vie".concat(nombreVie2);
            String nombreVie5=nombreVie3.concat(".png");
            this.vie = ImageIO.read(new File(nombreVie5));
            if (nombreVie1==0){
                contexte.drawImage(this.mort, 200, 200, 400, 400, null);
            }
            else {
                contexte.drawImage(this.vie, 30, 250, 50, 15, null);
            }
            } catch (IOException ex) {
                Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
            }
        try {
            
            if (personnageAvatar == 1) {
                    contexte.drawImage(mario, this.avatar.getX(), 400, 50, 50, null);
                }
            if (personnageAvatar == 2) {
                    contexte.drawImage(steve, this.avatar.getX(), 400, 50, 50, null);
                }
            if (personnageAvatar == 3) {
                    contexte.drawImage(amongus, this.avatar.getX(), 400, 50, 50, null);
                }
            if (personnageAvatar == 4) {
                    contexte.drawImage(ratchet, this.avatar.getX(), 400, 50, 50, null);
                }
            
            PreparedStatement requete2 = c.prepareStatement("SELECT id, personnage, x, y FROM joueur");
            ResultSet resultat = requete2.executeQuery();
            while (resultat.next()) {
                int idjoueur = resultat.getInt("id");
                int personnageJoueur = resultat.getInt("personnage");
                int abscisse = resultat.getInt("x");
                int ordonnee = resultat.getInt("y");
                System.out.println(abscisse);
                System.out.println(ordonnee);
//                if (idjoueur == 1 && this.avatar.getId() != 1) {
//                    contexte.drawImage(mario, abscisse, ordonnee+this.avatar.getYmap(), 50, 50, null);
//                }
                if (personnageJoueur == 1 && idjoueur!=this.avatar.getId()){
                    contexte.drawImage(mario, abscisse,this.avatar.getYmap()-ordonnee, 50, 50, null);
                }
                if (personnageJoueur == 2 && idjoueur!=this.avatar.getId()){
                    contexte.drawImage(steve, abscisse, this.avatar.getYmap()-ordonnee, 50, 50, null);
                }
                if (personnageJoueur == 3 && idjoueur!=this.avatar.getId()){
                    contexte.drawImage(amongus, abscisse, this.avatar.getYmap()-ordonnee, 50, 50, null);
                }
                if (personnageJoueur == 4 && idjoueur!=this.avatar.getId()){
                    contexte.drawImage(ratchet, abscisse, this.avatar.getYmap()-ordonnee , 50, 50, null);
                }
            }
            requete2.close();
        } catch (SQLException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
