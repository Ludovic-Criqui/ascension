package Ascension;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Regles {

    /*-Voir poure redéfinir certaines règles:
    Déplacement sur les blocs échelle : possible de monter (2 OK) - impossible de redescendre d'une échelle (3 POK) - selon 0 et 1 : le joueur tombe
    Possibilité de perdre une vie si on prend un mur sur les côtes d'une échelle - possibilité de tomber s'il n'y a pas de mur sur les cotés de l'échelle  
    3 à 4 types de blocs : bloc solide - bloc transparent(Définir les transparents + ceux avec lampe et cristaux bleus) - bloc échelle - bloc bout d'échelle
    Bloc bout d'échelle : impossible de redescendre et possible de passer sur les côtés si tuile transparente
     */
    // private int hauteur; //Permet de trouver le bloc par sa position y avec le quotient
    // private int largeur; //Permet de trouver le bloc par sa position x avec le quotient
    private int hauteur, largeur;
    private int typeBloc;
    private boolean passeLaLigne;
    private boolean prendreBloc;
    private boolean changeTuile;
    private Avatar avatar;
    private Carte carte;
    private int ligneFinY;
    private int ligneFinX1;
    private int ligneFinX2;
    private int personneEnVie;
    private int[] listeSolide;
    private int[] listeEchelle;
    private Connection c;

    public void Regles() {
        this.listeSolide = new int[]{72, 73, 74, 84, 85, 86, 87, 128, 129, 130, 131, 132, 134, 135, 146, 147, 148, 149, 150, 152, 153, 156, 157, 158, 159, 160, 162, 163, 164, 165, 170, 171, 172, 173, 174, 175, 176, 177, 178, 180, 181, 182, 183, 186, 187, 188, 189, 190, 191, 192, 193, 195, 196, 197, 199, 200, 201, 204, 205, 206, 209, 210, 211, 213, 214, 215, 218, 219, 222, 223, 228, 229, 232, 233};
        try {
            this.c = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20202021_s2_vs2_tp1_ascension?serverTimezone=UTC", "etudiant", "YTDTvj9TR3CDYCmP");
        } catch (SQLException ex) {
            Logger.getLogger(FinDePartie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean nonSolide(int solide) {
        boolean ok = true;
        this.listeSolide = new int[]{72, 73, 74, 84, 85, 86, 87, 128, 129, 130, 131, 132, 134, 135, 146, 147, 148, 149, 150, 152, 153, 156, 157, 158, 159, 160, 162, 163, 164, 165, 170, 171, 172, 173, 174, 175, 176, 177, 178, 180, 181, 182, 183, 186, 187, 188, 189, 190, 191, 192, 193, 195, 196, 197, 199, 200, 201, 204, 205, 206, 209, 210, 211, 213, 214, 215, 218, 219, 222, 223, 228, 229, 232, 233};

        for (int i = 0; i < listeSolide.length; i++) {
            if (listeSolide[i] == solide) {
                ok = false;
            }
        }
        return ok;
    }

    public boolean passerLaLigne(int x, int y) {
        while (y < 2 * hauteur && x < 2 * largeur) { //Voir si condition OK (à vérifier)
            this.passeLaLigne = false;
        }
        return passeLaLigne;
    }

    public boolean gagneTotalement(Avatar avatar) {
        this.ligneFinX1 = 56;
        this.ligneFinX2 = 1240;
        this.ligneFinY = -288;
//        personneEnVie();
        if ((avatar.getYmap() >= ligneFinY && avatar.getX() <= ligneFinX1) || (personneEnVie == 1 && avatar.getVie() > 0) || (avatar.getYmap() >= ligneFinY && avatar.getX() >= ligneFinX2)) {
            return true;
        }
        return false;
    }

    public boolean gagneTemporairement(Avatar avatar) {
        this.ligneFinY = -288;
//        personneEnVie();
        if (avatar.getYmap() >= ligneFinY || (personneEnVie == 1 && avatar.getVie() > 0)) {
            return true;
        }
        return false;
    }

    public boolean avatarMort(Avatar avatar) {
        boolean mort = false;
        if (avatar.getVie() <= 0) {
            mort = true;
        }
        return mort;
    }

    public boolean sePrendreUnBloc(int x, int y) {
        this.prendreBloc = false;
        if (typeBloc == 0) {
            this.prendreBloc = true;
        }
        return prendreBloc;
    }

    public int tomber(Avatar avatar) {
        int y = 0;
        if (this.nonSolide(this.carte.getDecor()[this.carte.getxMur() + 1][this.carte.getyMur()])) {
            y = -32;
        }
        return y;
    }

    public boolean peutTomber(Carte carte) {
        boolean tombe = false;
        if (nonSolide(carte.getDecor()[carte.getxMur() + 1][carte.getyMur()])) {
            tombe = true;
        }
        return tombe;
    }

    public int tombe(Avatar avatar, Carte carte) {
        int y = 0;
        if (peutTomber(carte)) {
            while (peutTomber(carte)) {
                y = y + this.tomber(avatar);
            }
        }
        return y;
    }

    public int[] recule(int direction) {
        int[] reculeDeTantXetTantY = new int[2];
        reculeDeTantXetTantY[0] = 0;
        reculeDeTantXetTantY[1] = 0;
        if (direction == 1) {
            reculeDeTantXetTantY[0] = 5;
            reculeDeTantXetTantY[1] = 0;
        } else if (direction == 2) {
            reculeDeTantXetTantY[0] = 5;
            reculeDeTantXetTantY[1] = 5;
        } else if (direction == 3) {
            reculeDeTantXetTantY[0] = 0;
            reculeDeTantXetTantY[1] = 5;
        } else if (direction == 4) {
            reculeDeTantXetTantY[0] = -5;
            reculeDeTantXetTantY[1] = 5;
        } else if (direction == 5) {
            reculeDeTantXetTantY[0] = -5;
            reculeDeTantXetTantY[1] = 0;
        } else if (direction == 6) {
            reculeDeTantXetTantY[0] = -5;
            reculeDeTantXetTantY[1] = -5;
        } else if (direction == 7) {
            reculeDeTantXetTantY[0] = 0;
            reculeDeTantXetTantY[1] = -5;
        } else if (direction == 8) {
            reculeDeTantXetTantY[0] = 3;
            reculeDeTantXetTantY[1] = -5;

        }
        return reculeDeTantXetTantY;
    }

    public boolean peutReculer(Carte carte, int[] recule) {
        boolean peutReculer = false;
        if (nonSolide(carte.getDecor()[carte.getxMur() + recule[1]][carte.getyMur() + recule[0]])) {
            peutReculer = true;
        }
        return peutReculer;
    }

    public boolean changeTuile(int x, int y) throws InterruptedException {
        this.changeTuile = false;
        int positionInitX = x;
        int positionInitY = y;
        this.avatar.miseAJour(); //Permettre de mettre à jour à chaque étape les positions du joueur (Pb car mise à jour méthode non statique)
        if (positionInitX == x || positionInitY == y) {
            this.changeTuile = false;
        } else {
            this.changeTuile = true;
        }
        return changeTuile;
    }

    public boolean hautEchelle(int hautEchelle) {
        boolean ok = true;
        if (115 == hautEchelle) {
            ok = false;
        }
        return ok;
    }

    public boolean echelle(int echelle) {
        boolean ok = false;
        this.listeEchelle = new int[]{133, 151, 115};
        for (int i = 0; i < listeEchelle.length; i++) {
            if (listeEchelle[i] == echelle) {
                ok = true;
            }
        }
        return ok;
    }

}
