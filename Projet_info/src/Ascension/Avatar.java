package Ascension;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Avatar {

    protected int x, y;
    private int yMap = -3520;
    private BufferedImage mario, steve, amongus, ratchet;
    private boolean gauche, droite, haut, bas ;
    private int saut;
    private int id = 1;
    private int personnage=1;
    private long date;
    private long dateLimite;
    private String pseudo;
    private int vie;
    private int etat;
    public Attaque attaque;
    private int partie;
    private int podium;

    public Avatar() {
        try {
            this.mario = ImageIO.read(new File("mario.png"));
            this.steve = ImageIO.read(new File("steve.png"));
            this.amongus = ImageIO.read(new File("amongus.png"));
            this.ratchet = ImageIO.read(new File("ratchet.png"));
        } catch (IOException ex) {
            Logger.getLogger(Avatar.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.x = 152;
        this.y = 0;
        this.id = id;
        this.gauche = false;
        this.droite = false;
        this.haut = false;
        this.bas = false;
        this.vie=3;
        this.etat=0;
        this.attaque = new Attaque();
        dateLimite = 0;
        this.partie=0;
        this.podium=2;
        this.saut=yMap;
    }
    
    public void setPersonnage(int personnage) {
        this.personnage = personnage;
    }
    
    public void setGauche(boolean gauche) {
        this.gauche = gauche;
    }

    public void setDroite(boolean droite) {
        this.droite = droite;
    }

    public void setHaut(boolean haut) {
        this.haut = haut;
    }

    public void setBas(boolean bas) {
        this.bas = bas;
    }

    public void setSaut(int saut) {
        this.saut = saut;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getVie() {
        return vie;
    }

    public void setVie(int vie) {
        this.vie = vie;
    }

    public int getEtat() {
        return etat;
    }

    public int getPartie() {
        return partie;
    }

    public void setPartie(int partie) {
        this.partie = partie;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public int getPodium() {
        return podium;
    }

    public void setPodium(int podium) {
        this.podium = podium;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        
        return y;
        
    }

    public int getPersonnage() {
        return personnage;
    }

    
    
    public void miseAJour() throws InterruptedException {
        if (x < 0) {
            x = 0;
        }

        if (y < 0) {
            y = 0;
        }

        if (yMap < -4000) {
            yMap = -4000;
            
        }

        if (yMap > 385) {
            yMap = 385;
        }
    }

    public void rendu(Graphics2D contexte) {
        // contexte.drawImage(this.mario, (int) x, (int) y, 50, 50, null);
    }

    public int getYmap() {
        return yMap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPseudo() {
        return pseudo;
    }
       
    public void setYmap(int Ymap) {
        this.yMap = Ymap;
    }

}
