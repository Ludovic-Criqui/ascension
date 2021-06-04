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
    
    
    public boolean passerLaLigne(int x, int y){
        while(y < 2*hauteur et x < 2*largeur){ //Voir si condition OK (à vérifier)
            this.passeLaLigne=false;
        }
        return passeLaLigne;
    }
    
    public int typeTuileMap(int x, int y){ //Prendre les coordonnées x et y de la map et non du joueur
        if (bloc[x/largeur][y/hauteur] == ){
            typeBloc = 0; //Bloc solide
        }
        else if (bloc[x/largeur][y/hauteur] == ){
            typeBloc = 1; //Bloc échelle
        }
        else if (bloc[x/largeur][y/hauteur] == ) {
            typeBloc = 2; //Bloc transparent
        }
        else if (bloc[x/largeur][y/hauteur] == ){
            typeBloc = 3; //Bloc bout d'échelle
        }
        return typeBloc;
    }
    
         
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
    
    public boolean marcheSurBloc(int x, int y){ //Problème à définir selon le sens de déplacement du joueur 
        this.marcheSurBloc=true;
        int ordonnéeDessous = (y/hauteur)-1;
        if (bloc[x/largeur][(ordonnéeDessous] == && Avatar.sensDeplacement == 0 ){ //Vérifier que le bloc du dessous est un bloc solide où l'on peut marcher + ss déplacement à voir)
            this.marcheSurBloc=false;
        }
        return marcheSurBloc;
    }
    
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
    
    public boolean changeTuile (int x, int y){
        this.changeTuile = false;
        int positionInitX=x;
        int positionInitY=y;
        Avatar.miseAJour(); //Permettre de mettre à jour à chaque étape les positions du joueur (Pb car mise à jour méthode non statique)
        if (positionInitX == x || positionInitY == y){
            this.changeTuile = false;
        }  
        else {
            this.changeTuile=true;
        }
        return changeTuile;
    }
    
}
