package Ascension;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Jeu {

    private BufferedImage fond;
    public Avatar avatar;

    public Jeu() {
        try {
            this.fond = ImageIO.read(new File("mario1.png"));
        } catch (IOException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.avatar = new Avatar();
    }

    public void miseAJour() throws InterruptedException {
        this.avatar.miseAJour();
    }

    public void rendu(Graphics2D contexte) {
        contexte.drawImage(this.fond, 0, 0, null);
        this.avatar.rendu(contexte);
    }

}
