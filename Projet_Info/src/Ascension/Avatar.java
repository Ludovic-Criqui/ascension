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
    private int Ymap = -1440;
    private BufferedImage mario, steve, amongus, ratchet;
    private boolean gauche, droite, haut, bas, saut;
    private int id = 1;
    private int personnage;
    private long date;
    private long dateLimite;
    private int timerSaut = 5;
    private boolean aSaute;
    private String pseudo;

    public Avatar() {
        try {
            this.mario = ImageIO.read(new File("mario.png"));
            this.steve = ImageIO.read(new File("steve.png"));
            this.amongus = ImageIO.read(new File("amongus.png"));
            this.ratchet = ImageIO.read(new File("ratchet.png"));
        } catch (IOException ex) {
            Logger.getLogger(Avatar.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.x = 120;
        this.y = 145;
        // this.id = ;
        this.gauche = false;
        this.droite = false;
        this.haut = false;
        this.bas = false;
        this.saut = false;
        aSaute = false;
        dateLimite = 0;
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

    public void setSaut(boolean saut) {
        this.saut = saut;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void miseAJour() throws InterruptedException {
        if (x < 0) {
            x = 0;
        }

        if (y < 0) {
            y = 0;
        }

        if (Ymap < -1440) {
            Ymap = -1440;
        }

        if (Ymap > 385) {
            Ymap = 385;
        }

        if (this.saut) {
            date = System.currentTimeMillis();
            if (date > dateLimite) {
                if (!aSaute) {
                    aSaute = true;
                }
            }

            if (aSaute) {
                y -= 15;
                Thread.sleep(5);
                timerSaut--;
                if (timerSaut <= 0) {
                    aSaute = false;
                    dateLimite = System.currentTimeMillis() + 500;
                }
            }

        }
    }

    public void rendu(Graphics2D contexte) {
        // contexte.drawImage(this.mario, (int) x, (int) y, 50, 50, null);
    }

    public int getYmap() {
        return Ymap;
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

    public void setYmap(int Ymap) {
        this.Ymap = Ymap;
    }

}
