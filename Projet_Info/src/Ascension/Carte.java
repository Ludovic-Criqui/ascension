/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ascension;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.io.*;

/**
 * Exemple de classe carte
 *
 * @author guillaume.laurent
 */
public class Carte {

    private int largeur = 40;
    private int hauteur = 60;
    private int tailleTuile = 32;

    private BufferedImage uneTuile;
    private BufferedImage[] tuiles;
    private int[][] decor;
    
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
        this.decor = lireTuile("ascension_mac.txt");
    }

    public int[][] lireTuile(String nomFichier) {
        int nbLignes = 0;
        int nbColonnes = 0;
        int[][] tableau = new int[nbLignes][nbColonnes];
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

    public void rendu(Graphics2D contexte) {
        for (int i = 0; i < largeur; i++) {
            for (int j = 0; j < hauteur; j++) {
                contexte.drawImage(tuiles[decor[j][i]], i * 32, j * 32, null);
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
}
