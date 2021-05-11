package Ascension;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Avatar {

    protected int x, y;
    private BufferedImage mario, luigi;
    private boolean gauche, droite, haut, bas, saut;
    private int id;
    private int personnage;

    public Avatar() {
        try {
            this.mario = ImageIO.read(new File("mario.png"));
            this.luigi = ImageIO.read(new File("luigi.png"));
        } catch (IOException ex) {
            Logger.getLogger(Avatar.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.x = 100;
        this.y = 145;
        this.gauche = false;
        this.droite = false;
        this.haut = false;
        this.bas = false;
        this.saut = false;
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
        if (this.gauche) {
            x -= 5;
        }
        if (this.droite) {
            x += 5;
        }
        if (this.bas) {
            y += 5;
        }
        if (this.haut) {
            y -= 5;
        }

        if (x < 0) {
            x = 0;
        }


        if (this.saut) {
            
            }
        }
    

    public void rendu(Graphics2D contexte) {
        //contexte.drawImage(this.mario, (int) x, (int) y, 50, 50, null);
    }

}
