package Ascension;

/**
 *
 * @author ngarreau
 */
public class Règles {
    
    /*-Voir poure redéfinir certaines règles:
    Déplacement sur les blocs échelle : possible de monter (2 OK) - impossible de redescendre d'une échelle (3 POK) - selon 0 et 1 : le joueur tombe
    Possibilité de perdre une vie si on prend un mur sur les côtes d'une échelle - possibilité de tomber s'il n'y a pas de mur sur les cotés de l'échelle  
    3 à 4 types de blocs : bloc solide - bloc transparent(Définir les transparents + ceux avec lampe et cristaux bleus) - bloc échelle - bloc bout d'échelle
    Bloc bout d'échelle : impossible de redescendre et possible de passer sur les côtés si tuile transparente
    */
    
    
    // private int hauteur; //Permet de trouver le bloc par sa position y avec le quotient
    // private int largeur; //Permet de trouver le bloc par sa position x avec le quotient
    private int x, y, hauteur, largeur;
    private int typeBloc;
    private int blocDessous;
    private int [][] blocInitial;
    private int positionInitiale;
    private int positionVerticale;
    private int hauteurChute;
    private boolean passeLaLigne;
    private boolean prendreBloc;
    private boolean marcheSurBloc;
    private boolean changeTuile;
    private Avatar avatar;
    private Carte carte;
    private int ligneFinY;
    private int ligneFinX;
    private int personneEnVie;
//    private int[] listeSolide;
    
    public void Regles(Avatar avatar,Carte carte){
        this.avatar = avatar;
        this.carte = carte;
//        this.listeSolide = new int[]{72, 73, 74, 84, 85, 86, 87, 128, 129, 130, 131, 132, 134, 135, 146, 147, 148, 149, 150, 152, 153, 156, 157, 158, 159, 160, 162, 163, 164, 165, 170, 171, 172, 173, 174, 175, 176, 177, 178, 180, 181, 182, 183, 186, 187, 188, 189, 190, 191, 192, 193, 195, 196, 197, 199, 200, 201, 204, 205, 206, 209, 210, 211, 213, 214, 215, 218, 219, 222, 223, 228, 229, 232, 233};
        this.ligneFinX=200;
        this.ligneFinY=-1300;
    } 
    
    
    public boolean passerLaLigne(int x, int y){
        while(y < 2*hauteur && x < 2*largeur){ //Voir si condition OK (à vérifier)
            this.passeLaLigne=false;
        }
        return passeLaLigne;
    }
    
    public boolean gagneTotalement(Avatar avatar){
        this.ligneFinX=33;
        this.ligneFinY=382;
        if ((avatar.getYmap()>=ligneFinY && avatar.getX()<=ligneFinX) || (personneEnVie==1 && avatar.getVie()>0)){
//            System.out.println((avatar.getYmap()>=ligneFinY && avatar.getYmap()>=ligneFinX));
//            System.out.println((personneEnVie==1 && avatar.getVie()>0));
//            System.out.println(ligneFinX);
//            System.out.println(ligneFinY);
            System.out.println(avatar.getYmap());
            return true;
        }
        return false;
    }
    
    public boolean gagneTemporairement(Avatar avatar){
        this.ligneFinX=1089;
        this.ligneFinY=382;
        if (avatar.getYmap()>=ligneFinY && avatar.getX()<=ligneFinX){
            return true;
        }
        return false;
    }
    
//    public boolean nonSolide(int solide){
//        boolean ok = true;
//        for (int i=0; i<listeSolide.length; i++){
//            if (listeSolide[i] == solide){
//                ok = false;
//            }
//        }
//        return ok;        
//    }
    
//    public int typeTuileMap(int x, int y){ //Prendre les coordonnées x et y de la map et non du joueur
//        if (bloc[x/largeur][y/hauteur] == ){
//            typeBloc = 0; //Bloc solide
//        }
//        else if (bloc[x/largeur][y/hauteur] == ){
//            typeBloc = 1; //Bloc échelle
//        }
//        else if (bloc[x/largeur][y/hauteur] == ) {
//            typeBloc = 2; //Bloc transparent
//        }
//        else if (bloc[x/largeur][y/hauteur] == ){
//            typeBloc = 3; //Bloc bout d'échelle
//        }
//        return typeBloc;
//    }
    
         
    public boolean sePrendreUnBloc(int x, int y){
        this.prendreBloc = false;
        if (typeBloc == 0){
            this.prendreBloc = true;
        }
//        if (typeBloc == 1 && Avatar.sensDeplacement == 3){ //Utilisation grâce au Tile Mapping en créant la map selon les coordonnees x et y 
//            this.prendreBloc = true;            
//        }
        return prendreBloc;
    }
    
//    public boolean marcheSurBloc(int x, int y){ //Problème à définir selon le sens de déplacement du joueur 
//        this.marcheSurBloc=true;
//        int ordonnéeDessous = (y/hauteur)-1;
//        if (bloc[x/largeur][(ordonnéeDessous] == && Avatar.sensDeplacement == 0 ){ //Vérifier que le bloc du dessous est un bloc solide où l'on peut marcher + ss déplacement à voir)
//            this.marcheSurBloc=false;
//        }
//        return marcheSurBloc;
//    }
    
    public int tomber (int x, int y){
        int hauteurChute = 0;
        int positionInitiale = y;
        int positionVerticale = y; //Permettre ensuite d'associer cette nouvelle positionVerticale à la nouvelle position du joueur 
        if (prendreBloc == true){
            while (typeBloc == 2){ //Cdt pour que le joueur ne retombe pas sur un bloc solide ou demi solide
                y=y+hauteur;
            }
            positionVerticale=y;
        }
        else if (marcheSurBloc == false)
            while (typeBloc == 2){ //Condition pour que le joueur retrouve un bloc solide
                y = y + hauteur; 
            }
            positionVerticale = y; //Permettre ensuite d'associer cette nouvelle positionVerticale à la nouvelle position du joueur 
        hauteurChute = positionInitiale - positionVerticale;    
        return hauteurChute; //Réutilisation de hauteurCHute dans la méthode qui permettra de gérer les vies car si la chute est de plus de y cases alors le joueur va perdre une vie 
    }
    
    public boolean changeTuile (int x, int y) throws InterruptedException{
        this.changeTuile = false;
        int positionInitX=x;
        int positionInitY=y;
        this.avatar.miseAJour(); //Permettre de mettre à jour à chaque étape les positions du joueur (Pb car mise à jour méthode non statique)
        if (positionInitX == x || positionInitY == y){
            this.changeTuile = false;
        }  
        else {
            this.changeTuile=true;
        }
        return changeTuile;
    }
    
}
