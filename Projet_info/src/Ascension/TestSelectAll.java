package Ascension;

import Outils.OutilsJDBC;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestSelectAll{

    public static void main(String[] args) {

        try {

            Connection connexion = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20202021_s2_vs2_tp1_ascension?serverTimezone=UTC", "etudiant", "YTDTvj9TR3CDYCmP");

            PreparedStatement requete = connexion.prepareStatement("SELECT* FROM joueur");
            ResultSet resultat = requete.executeQuery();
            OutilsJDBC.afficherResultSet(resultat);
//            while (resultat.next()) {
//                int idEnnemie=resultat.getInt("id");
//                int xEnnemie=resultat.getInt("x");
//                int yEnnemie=resultat.getInt("y");
//                System.out.println(idEnnemie);   
//            }
            requete.close();
            connexion.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

}
