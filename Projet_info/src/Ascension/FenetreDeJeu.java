package Ascension;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

public class FenetreDeJeu extends JFrame implements ActionListener, KeyListener {

    private BufferedImage frameBuffer;
    private Graphics2D contexte;
    private JLabel jLabel1;
    private Jeu jeu;
    private Timer timer;
    private Regles regles;
    private int[] listeSolide;
    private long dateLimite;
    private long date = 0;
    private long date1 = 0;
    private int compteur = 0;
    private boolean aFiniTotalement = false;
    private FinDePartie fenetreFin;
    private Connection c;
    private javax.swing.JList<String> jList1;
    private boolean saute;
    private int[] valeurRecule = {0, 0};
    private boolean aCommence = false;
    private Musique tape;
    private Musique grotte;
    private int sens;
    private int compteurAction;

    public FenetreDeJeu() {
        // Creation du jeu
        this.jeu = new Jeu();
        // initialisation de la fenetre
        this.setSize(1344, 1280);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(this);
        this.jLabel1 = new JLabel();
        this.jLabel1.setPreferredSize(new java.awt.Dimension(1344, 1280));
        this.setContentPane(this.jLabel1);
        this.pack();
        this.fenetreFin = new FinDePartie();
//        this.regles=this.jeu.regles;
        this.listeSolide = new int[]{72, 73, 74, 84, 85, 86, 87, 128, 129, 130, 131, 132, 134, 135, 146, 147, 148, 149, 150, 152, 153, 156, 157, 158, 159, 160, 162, 163, 164, 165, 170, 171, 172, 173, 174, 175, 176, 177, 178, 180, 181, 182, 183, 186, 187, 188, 189, 190, 191, 192, 193, 195, 196, 197, 199, 200, 201, 204, 205, 206, 209, 210, 211, 213, 214, 215, 218, 219, 222, 223, 228, 229, 232, 233};
        this.regles = this.jeu.regles;

        // Creation du buffer pour l'affichage du jeu et recuperation du contexte graphique
        this.frameBuffer = new BufferedImage(this.jLabel1.getWidth(), this.jLabel1.getHeight(), BufferedImage.TYPE_INT_ARGB);
        this.jLabel1.setIcon(new ImageIcon(frameBuffer));
        this.contexte = this.frameBuffer.createGraphics();

        // Creation du Timer qui appelle this.actionPerformed() tous les 40 ms
        this.timer = new Timer(10, this);
        this.timer.start();

        //Positionnement fenetre de Jeu
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getWidth() / 2, dim.height / 2 - this.getHeight() / 2);

        try {
            this.c = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20202021_s2_vs2_tp1_ascension?serverTimezone=UTC", "etudiant", "YTDTvj9TR3CDYCmP");
        } catch (SQLException ex) {
            Logger.getLogger(FinDePartie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Jeu getJeu() {
        return jeu;
    }

    // Methode appelee par le timer et qui effectue la boucle de jeu
    @Override
    public void actionPerformed(ActionEvent e) {
        date1 = System.currentTimeMillis();
        if (this.isVisible()) {
            mettreAJourSens();
            //            sonGrotte();
            try {
                this.jeu.miseAJour();
            } catch (InterruptedException ex) {
                Logger.getLogger(FenetreDeJeu.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                this.jeu.rendu(contexte);
            } catch (IOException ex) {
                Logger.getLogger(FenetreDeJeu.class.getName()).log(Level.SEVERE, null, ex);
            }

            this.jLabel1.repaint();
            
            if (compteurAction == 5) {
                if (saute == false) {
                    if (this.jeu.regles.echelle(this.jeu.getCarte().getDecor()[this.jeu.getCarte().getxMur()][this.jeu.getCarte().getyMur()]) == false && this.jeu.regles.avatarMort(this.jeu.avatar) == false) {
                        avatarTombe();
                    }
                }

                compteurAction = 0;
            }
            if (this.jeu.regles.avatarMort(this.jeu.avatar) == false) {

                if (this.jeu.avatar.attaque.avatarEstTape(this.jeu.avatar)) {
                    if (aCommence == false) {
                        valeurRecule[0] = this.jeu.regles.recule(this.jeu.avatar.attaque.avatarEstTapeVers(this.jeu.avatar))[0];
                        valeurRecule[1] = this.jeu.regles.recule(this.jeu.avatar.attaque.avatarEstTapeVers(this.jeu.avatar))[1];
                        aCommence = true;
                        etat0();
                        System.out.println("premier if a commencé " + aCommence);
                    }
                    if (aCommence == true && (valeurRecule[0] != 0 || valeurRecule[1] != 0)) {
                        System.out.println("deuxieme if a commencé " + aCommence);
                        System.out.println(valeurRecule);
                        avatarRecule();
                    }
                    if (aCommence == true && (valeurRecule[0] == 0 && valeurRecule[1] == 0)) {
                        aCommence = false;
                        System.out.println("dernier if a commencé " + aCommence);

                    }
                }
            }
            compteurAction += 1;
            if (this.jeu.regles.gagneTotalement(this.jeu.avatar)) {
                aFiniTotalement = true;
            }
            if (this.jeu.regles.avatarMort(this.jeu.avatar) || this.jeu.regles.gagneTotalement(this.jeu.avatar)) {
                aFini();
                this.jeu.enleverPodium(this.jeu.avatar.getId());
            }
            if (this.jeu.finirPartie()) {
                this.setVisible(false);
                try {
                    this.fenetreFin.affichagePodium();
                } catch (InterruptedException ex) {
                    Logger.getLogger(FenetreDeJeu.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.fenetreFin.setVisible(true);
            }
        }
    }

    public void sonSeFaitTaper() {
        try {
            this.tape = new Musique(new File("tape.wav"));
        } catch (Exception ex) {
            Logger.getLogger(AccueilFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        tape.open();
        tape.play();
    }

    public void sonGrotte() {
        try {
            this.grotte = new Musique(new File("grotte.wav"));
        } catch (Exception ex) {
            Logger.getLogger(AccueilFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        grotte.open();
        grotte.play();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // NOP
    }

    public void etat0() {
        try {
            PreparedStatement requete = c.prepareStatement("UPDATE joueur SET etat = 0 WHERE id = ?");
            requete.setInt(1, this.jeu.avatar.getId());
            requete.executeUpdate();
            requete.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void aFini() {
        try {
            PreparedStatement requete = c.prepareStatement("UPDATE joueur SET aFini = 1 WHERE id = ?");
            requete.setInt(1, this.jeu.avatar.getId());
            requete.executeUpdate();
            requete.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean nonSolide(int solide) {
        boolean ok = true;
        for (int i = 0; i < listeSolide.length; i++) {
            if (listeSolide[i] == solide) {
                ok = false;
            }
        }
        return ok;
    }

    public void avatarTombe() {
        if (this.regles.peutTomber(this.jeu.carte)) {
            this.jeu.avatar.setYmap(this.jeu.avatar.getYmap() - 32);
            this.jeu.getCarte().setxMur(this.jeu.getCarte().getxMur() + 1);
        }
    }

    public void avatarRecule() {
        if (this.jeu.regles.avatarMort(this.jeu.avatar) == false) {
            if (this.jeu.regles.peutReculer(this.jeu.carte, valeurRecule)) {
                if (valeurRecule[0] > 0 && this.jeu.regles.peutReculer(this.jeu.carte, new int[]{1, 0})) {
                    this.jeu.avatar.setX(this.jeu.avatar.getX() + 32);
                    this.jeu.getCarte().setyMur(this.jeu.getCarte().getyMur() + 1);
                }
                if (valeurRecule[0] < 0 && this.jeu.regles.peutReculer(this.jeu.carte, new int[]{-1, 0})) {
                    this.jeu.avatar.setX(this.jeu.avatar.getX() - 32);
                    this.jeu.getCarte().setyMur(this.jeu.getCarte().getyMur() - 1);
                }
                if (valeurRecule[1] > 0 && this.jeu.regles.peutReculer(this.jeu.carte, new int[]{0, 1})) {
                    this.jeu.avatar.setYmap(this.jeu.avatar.getYmap() - 32);
                    this.jeu.getCarte().setxMur(this.jeu.getCarte().getxMur() + 1);
                }
                if (valeurRecule[1] < 0 && this.jeu.regles.peutReculer(this.jeu.carte, new int[]{0, -1})) {
                    this.jeu.avatar.setYmap(this.jeu.avatar.getYmap() + 32);
                    this.jeu.getCarte().setxMur(this.jeu.getCarte().getxMur() - 1);
                }
            }
            if (valeurRecule[0] > 0) {
                valeurRecule[0] = valeurRecule[0] - 1;
            }
            if (valeurRecule[0] < 0) {
                valeurRecule[0] = valeurRecule[0] + 1;
            }
            if (valeurRecule[1] > 0) {
                valeurRecule[1] = valeurRecule[1] - 1;
            }
            if (valeurRecule[1] < 0) {
                valeurRecule[1] = valeurRecule[1] + 1;
            }
        }

    }

    public void mettreAJourSens() {
        try {
            PreparedStatement requete = c.prepareStatement("UPDATE joueur SET sens= ? WHERE id = ?");
            requete.setInt(1, sens);
            requete.setInt(2, this.jeu.avatar.getId());
            requete.executeUpdate();
            requete.close();
        } catch (SQLException ex) {
            Logger.getLogger(FenetreDeJeu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void keyPressed(KeyEvent evt) {

        if (aFiniTotalement == false) {
            date = System.currentTimeMillis();
            System.out.println(this.jeu.avatar.getX());
            System.out.println(this.jeu.avatar.getYmap());
            if (date > dateLimite) {
                if ((evt.getKeyCode() == evt.VK_D) && (this.nonSolide(this.jeu.getCarte().getDecor()[this.jeu.getCarte().getxMur()][this.jeu.getCarte().getyMur() + 1]) || this.jeu.regles.avatarMort(this.jeu.avatar))) {
                    this.jeu.avatar.setX(this.jeu.avatar.getX() + 32);
                    this.jeu.getCarte().setyMur(this.jeu.getCarte().getyMur() + 1);
                    this.sens = 0;
                    dateLimite = date + 100;
                }
                if ((evt.getKeyCode() == evt.VK_Q) && (this.nonSolide(this.jeu.getCarte().getDecor()[this.jeu.getCarte().getxMur()][this.jeu.getCarte().getyMur() - 1]) || this.jeu.regles.avatarMort(this.jeu.avatar))) {
                    this.jeu.avatar.setX(this.jeu.avatar.getX() - 32);
                    this.jeu.getCarte().setyMur(this.jeu.getCarte().getyMur() - 1);
                    this.sens = 1;
                    dateLimite = date + 100;
                }
                if ((evt.getKeyCode() == evt.VK_Z && this.jeu.regles.echelle(this.jeu.getCarte().getDecor()[this.jeu.getCarte().getxMur()][this.jeu.getCarte().getyMur()])) && this.nonSolide(this.jeu.getCarte().getDecor()[this.jeu.getCarte().getxMur() - 1][this.jeu.getCarte().getyMur()])) {
                    this.jeu.avatar.setYmap(this.jeu.avatar.getYmap() + 32);
                    this.jeu.getCarte().setxMur(this.jeu.getCarte().getxMur() - 1);
                    this.sens = 2;
                    dateLimite = date + 100;
                }
                if ((evt.getKeyCode() == evt.VK_Z) && this.jeu.regles.avatarMort(this.jeu.avatar)) {
                    this.jeu.avatar.setYmap(this.jeu.avatar.getYmap() + 32);
                    this.jeu.getCarte().setxMur(this.jeu.getCarte().getxMur() - 1);
                    this.sens = 2;
                    dateLimite = date + 100;
                }
                if ((evt.getKeyCode() == evt.VK_S) && (this.nonSolide(this.jeu.getCarte().getDecor()[this.jeu.getCarte().getxMur() + 1][this.jeu.getCarte().getyMur()]) || this.jeu.regles.avatarMort(this.jeu.avatar))) {
                    this.jeu.avatar.setYmap(this.jeu.avatar.getYmap() - 32);
                    this.jeu.getCarte().setxMur(this.jeu.getCarte().getxMur() + 1);
                    this.sens = 3;
                    dateLimite = date + 100;
                }
                if ((evt.getKeyCode() == evt.VK_A) && this.nonSolide(this.jeu.getCarte().getDecor()[this.jeu.getCarte().getxMur() - 1][this.jeu.getCarte().getyMur()]) && this.nonSolide(this.jeu.getCarte().getDecor()[this.jeu.getCarte().getxMur() - 1][this.jeu.getCarte().getyMur() - 1])) {
                    this.jeu.avatar.setYmap(this.jeu.avatar.getYmap() + 32);
                    this.jeu.getCarte().setxMur(this.jeu.getCarte().getxMur() - 1);
                    this.jeu.avatar.setX(this.jeu.avatar.getX() - 32);
                    this.jeu.getCarte().setyMur(this.jeu.getCarte().getyMur() - 1);
                    this.sens = 1;
                    dateLimite = date + 100;
                }
                if ((evt.getKeyCode() == evt.VK_E) && this.nonSolide(this.jeu.getCarte().getDecor()[this.jeu.getCarte().getxMur() - 1][this.jeu.getCarte().getyMur()]) && this.nonSolide(this.jeu.getCarte().getDecor()[this.jeu.getCarte().getxMur() - 1][this.jeu.getCarte().getyMur() + 1])) {
                    this.jeu.avatar.setYmap(this.jeu.avatar.getYmap() + 32);
                    this.jeu.getCarte().setxMur(this.jeu.getCarte().getxMur() - 1);
                    this.jeu.avatar.setX(this.jeu.avatar.getX() + 32);
                    this.jeu.getCarte().setyMur(this.jeu.getCarte().getyMur() + 1);
                    this.sens = 0;
                    dateLimite = date + 100;
                }
                //                if(compteur<=4){
                if ((evt.getKeyCode() == evt.VK_Z) && this.nonSolide(this.jeu.getCarte().getDecor()[this.jeu.getCarte().getxMur() + 1][this.jeu.getCarte().getyMur()]) == false && this.jeu.regles.avatarMort(this.jeu.avatar) == false) {
                    while (compteur <= 4 && this.nonSolide(this.jeu.getCarte().getDecor()[this.jeu.getCarte().getxMur() - 1][this.jeu.getCarte().getyMur()])) {
                        //                    if (evt.getKeyCode() == evt.VK_SPACE && this.nonSolide(this.jeu.getCarte().getDecor()[this.jeu.getCarte().getXmur()-1][this.jeu.getCarte().getYmur()])) {
                        saute = true;
                        this.jeu.avatar.setYmap(this.jeu.avatar.getYmap() + 32);
                        this.jeu.getCarte().setxMur(this.jeu.getCarte().getxMur() - 1);
                        dateLimite = date + 40;
                        compteur += 1;
                    }
                    saute = false;
                    compteur = 0;
                }
                if (this.jeu.regles.avatarMort(this.jeu.avatar) == false) {
                    if (evt.getKeyCode() == evt.VK_SPACE && this.jeu.avatar.attaque.peutAttaquer(this.jeu.avatar)) {
                        this.jeu.enleverVie(this.jeu.avatar.attaque.getIdPlusProche());
                        if (this.jeu.avatar.attaque.getDistanceAvecPlusProche() == 0 && this.jeu.avatar.attaque.peutAttaquer(this.jeu.avatar)) {
                            this.jeu.avatar.attaque.tapeIdVers(0);
                        } else {
                            this.jeu.avatar.attaque.joueurPlusProche(this.jeu.avatar);
                            this.jeu.avatar.attaque.tapeIdVers(this.jeu.avatar.attaque.getIdPlusProche());
                        }
                    }
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent evt) {

    }

    public static void main(String[] args) {
        FenetreDeJeu fenetre = new FenetreDeJeu();
        fenetre.setVisible(true);
    }

}
