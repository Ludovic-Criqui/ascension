package Ascension;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.io.*;

public class Carte {

    private int largeur = 42;
    private int hauteur = 165;
    private int tailleTuile = 32;
    private BufferedImage uneTuile;
    private BufferedImage[] tuiles;
    private int[][] decor;
    private int Xmur = 123;
    private int Ymur = 5;
    
    public Carte() {
        try {
            BufferedImage tileset = ImageIO.read(new File("tileset.png"));
            tuiles = new BufferedImage[234];
            for (int i = 0; i < tuiles.length; i++) {
                int x = (i % 18) * tailleTuile;
                int y = (i / 18) * tailleTuile;
                tuiles[i] = tileset.getSubimage(x, y, tailleTuile, tailleTuile);
            }
        } catch (IOException ex) {
            Logger.getLogger(Carte.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.decor = lireTuile("Mine_Royale.txt");
    }

    public int[][] lireTuile(String nomFichier) {
        int nbLignes = 0;
        int nbColonnes = 0;
        int[][] tableau = new int[nbLignes][nbColonnes];
        System.out.println(tableau);
        try {
            String ligne;
            BufferedReader fichier = new BufferedReader(new FileReader(nomFichier));
            fichier.readLine();
            fichier.readLine();
            ligne = fichier.readLine();
            String[] champ = ligne.split(" ");
            nbColonnes = Integer.parseInt(champ[0]);
            nbLignes = Integer.parseInt(champ[1]);
            System.out.println(nbLignes + " " + nbColonnes);
            tableau = new int[nbLignes][nbColonnes];
            int i = 0;
            while (fichier.ready()) {
                ligne = fichier.readLine();
                champ = ligne.split(" ");
                for (int j = 0; j < nbColonnes; j++) {
                    int tile = Integer.parseInt(champ[j]);
                    tableau[i][j] = tile;
                    System.out.print(tile + " ");
                }
                i++;
                System.out.println();
            }
            fichier.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tableau;
    }

    public void miseAJour() {

    }

    public void rendu(Graphics2D contexte,int Ymap) {
        for (int i = 0; i < largeur; i++) {
            for (int j = 0; j < hauteur; j++) {
                contexte.drawImage(tuiles[decor[j][i]], i * 32, j * 32 + Ymap, null);
            }
        }

        contexte.drawImage(uneTuile, 0, 0, null);
        contexte.drawImage(uneTuile, 32, 0, null);

    }
    

//    public void rendu(Graphics2D contexte) {
//        for(int i=0; i<largeur; i++){
//            for(int j=0; j<hauteur;j++){
//                contexte.drawImage(tuiles[2],i*32, j*32,null);
//            }
//        }
////        contexte.drawImage(uneTuile, 0, 0, null);
////        contexte.drawImage(uneTuile, 32, 0, null);
//
//    }

    public void setXmur(int Xmur) {
        this.Xmur = Xmur;
    }

    public void setYmur(int Ymur) {
        this.Ymur = Ymur;
    }

    public int getXmur() {
        return Xmur;
    }

    public int getYmur() {
        return Ymur;
    }

    public BufferedImage[] getTuiles() {
        return tuiles;
    }

    public int[][] getDecor() {
        return decor;
    }
    
}