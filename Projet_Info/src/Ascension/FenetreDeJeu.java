package Ascension;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
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
    private int[] listeSolide;

    public FenetreDeJeu() {
        // Creation du jeu
        this.jeu = new Jeu();
        // initialisation de la fenetre
        this.setSize(1312, 1952);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(this);
        this.jLabel1 = new JLabel();
        this.jLabel1.setPreferredSize(new java.awt.Dimension(1312, 1952));
        this.setContentPane(this.jLabel1);
        this.pack();
        this.listeSolide = new int[]{72, 73, 74, 84, 85, 86, 87, 128, 129, 130, 131, 132, 134, 135, 146, 147, 148, 149, 150, 152, 153, 156, 157, 158, 159, 160, 162, 163, 164, 165, 170, 171, 172, 173, 174, 175, 176, 177, 178, 180, 181, 182, 183, 186, 187, 188, 189, 190, 191, 192, 193, 195, 196, 197, 199, 200, 201, 204, 205, 206, 209, 210, 211, 213, 214, 215, 218, 219, 222, 223, 228, 229, 232, 233};
    
        // Creation du buffer pour l'affichage du jeu et recuperation du contexte graphique
        this.framebuffer = new BufferedImage(this.jLabel1.getWidth(), this.jLabel1.getHeight(), BufferedImage.TYPE_INT_ARGB);
        this.jLabel1.setIcon(new ImageIcon(framebuffer));
        this.contexte = this.framebuffer.createGraphics();

        // Creation du Timer qui appelle this.actionPerformed() tous les 40 ms
        this.timer = new Timer(40, this);
        this.timer.start();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2 - this.getWidth()/2, dim.height/2 - this.getHeight()/2);
    }
    
    public Jeu getJeu() {
        return jeu;
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

    public boolean nonSolide(int solide){
        boolean ok = true;
        for (int i=0; i<listeSolide.length; i++){
            if (listeSolide[i] == solide){
                ok = false;
            }
        }
        return ok;        
    }
    
    @Override
    public void keyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == evt.VK_RIGHT && this.nonSolide(this.jeu.getCarte().getDecor()[this.jeu.getCarte().getXmur()][this.jeu.getCarte().getYmur()+1]) ) {
            this.jeu.avatar.setX(this.jeu.avatar.getX() + 32);
            this.jeu.getCarte().setYmur(this.jeu.getCarte().getYmur()+1);
        }
        if (evt.getKeyCode() == evt.VK_LEFT && this.nonSolide(this.jeu.getCarte().getDecor()[this.jeu.getCarte().getXmur()][this.jeu.getCarte().getYmur()-1])) {
            this.jeu.avatar.setX(this.jeu.avatar.getX() - 32);
            this.jeu.getCarte().setYmur(this.jeu.getCarte().getYmur()-1);
        }
        if (evt.getKeyCode() == evt.VK_UP && this.nonSolide(this.jeu.getCarte().getDecor()[this.jeu.getCarte().getXmur()-1][this.jeu.getCarte().getYmur()])) {
            this.jeu.avatar.setYmap(this.jeu.avatar.getYmap() + 32);
            this.jeu.getCarte().setXmur(this.jeu.getCarte().getXmur()-1);
        }
        if (evt.getKeyCode() == evt.VK_DOWN && this.nonSolide(this.jeu.getCarte().getDecor()[this.jeu.getCarte().getXmur()+1][this.jeu.getCarte().getYmur()])) {
            this.jeu.avatar.setYmap(this.jeu.avatar.getYmap() - 32);
            this.jeu.getCarte().setXmur(this.jeu.getCarte().getXmur()+1);
        }
        if (evt.getKeyCode() == evt.VK_SPACE) {
            this.jeu.avatar.setSaut(true);
        }        
    }

    @Override
    public void keyReleased(KeyEvent evt) {
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
