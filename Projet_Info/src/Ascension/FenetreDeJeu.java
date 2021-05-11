package Ascension;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

public class FenetreDeJeu extends JFrame implements ActionListener, KeyListener {

    private BufferedImage framebuffer;
    private Graphics2D contexte;
    private JLabel jLabel1;
    private Jeu jeu;
    private Timer timer;

    public FenetreDeJeu() {
        // Creation du jeu
        this.jeu = new Jeu();
        // initialisation de la fenetre
        this.setSize(1273, 225);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(this);
        this.jLabel1 = new JLabel();
        this.jLabel1.setPreferredSize(new java.awt.Dimension(1273, 225));
        this.setContentPane(this.jLabel1);
        this.pack();

        

        // Creation du buffer pour l'affichage du jeu et recuperation du contexte graphique
        this.framebuffer = new BufferedImage(this.jLabel1.getWidth(), this.jLabel1.getHeight(), BufferedImage.TYPE_INT_ARGB);
        this.jLabel1.setIcon(new ImageIcon(framebuffer));
        this.contexte = this.framebuffer.createGraphics();

        // Creation du Timer qui appelle this.actionPerformed() tous les 40 ms
        this.timer = new Timer(40, this);
        this.timer.start();
    }

    // Methode appelee par le timer et qui effectue la boucle de jeu
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            this.jeu.miseAJour();
        } catch (InterruptedException ex) {
            Logger.getLogger(FenetreDeJeu.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.jeu.rendu(contexte);
        this.jLabel1.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // NOP
    }

    @Override
    public void keyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == evt.VK_RIGHT) {
            this.jeu.avatar.setDroite(true);
        }
        if (evt.getKeyCode() == evt.VK_LEFT) {
            this.jeu.avatar.setGauche(true);
        }
        if (evt.getKeyCode() == evt.VK_UP) {
            this.jeu.avatar.setHaut(true);
        }
        if (evt.getKeyCode() == evt.VK_DOWN) {
            this.jeu.avatar.setBas(true);
        }
        if (evt.getKeyCode() == evt.VK_SPACE) {
            this.jeu.avatar.setSaut(true);
        }        
    }

    @Override
    public void keyReleased(KeyEvent evt) {
        if (evt.getKeyCode() == evt.VK_RIGHT) {
            this.jeu.avatar.setDroite(false);
        }
        if (evt.getKeyCode() == evt.VK_LEFT) {
            this.jeu.avatar.setGauche(false);
        }
        if (evt.getKeyCode() == evt.VK_UP) {
            this.jeu.avatar.setHaut(false);
        }
        if (evt.getKeyCode() == evt.VK_DOWN) {
            this.jeu.avatar.setBas(false);
        }
        if (evt.getKeyCode() == evt.VK_SPACE) {
            this.jeu.avatar.setSaut(false);
        }        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FenetreDeJeu fenetre = new FenetreDeJeu();
        fenetre.setVisible(true);
    }

}
