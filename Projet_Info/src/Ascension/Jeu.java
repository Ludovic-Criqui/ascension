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

    private BufferedImage fond, mario, steve, amongus, ratchet;
    public Avatar avatar;
    private Connection c;
    private Carte carte;

    public Jeu() {
        try {
            this.c = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20202021_s2_vs2_tp1_ascension?serverTimezone=UTC", "etudiant", "YTDTvj9TR3CDYCmP");
        } catch (SQLException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.carte = new Carte();
        try {
            this.fond = ImageIO.read(new File("fond.png"));
            this.mario = ImageIO.read(new File("mario.png"));
            this.steve = ImageIO.read(new File("steve.png"));
            this.amongus = ImageIO.read(new File("amongus.png"));
            this.ratchet = ImageIO.read(new File("ratchet.png"));
        } catch (IOException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.avatar = new Avatar();
    }

    public void miseAJour() throws InterruptedException {
        this.avatar.miseAJour();
    }

    public void rendu(Graphics2D contexte) {
        this.carte.rendu(contexte);
        int id = 2;
        try {
            PreparedStatement requete = c.prepareStatement("UPDATE joueur SET x = ?, y = ? WHERE id = ?");
            requete.setInt(1, this.avatar.getX());
            requete.setInt(2, this.avatar.getY());
            requete.setInt(3, id);
            requete.executeUpdate();
            requete.close();
        } catch (SQLException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            PreparedStatement requete2 = c.prepareStatement("SELECT x, y FROM joueur");
            ResultSet resultat = requete2.executeQuery();
            while (resultat.next()) {
                int abscisse = resultat.getInt("x");
                int ordonnee = resultat.getInt("y");
                if (id == 1) {
                    contexte.drawImage(mario, abscisse, ordonnee, 50, 50, null);
                }
                if (id == 2){
                    contexte.drawImage(steve, abscisse, ordonnee, 50, 50, null);
                }
                if (id == 3){
                    contexte.drawImage(amongus, abscisse, ordonnee, 50, 50, null);
                }
                if (id == 4){
                    contexte.drawImage(ratchet, abscisse, ordonnee, 50, 50, null);
                }
            }
            requete2.close();
        } catch (SQLException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
